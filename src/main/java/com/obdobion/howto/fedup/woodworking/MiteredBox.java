package com.obdobion.howto.fedup.woodworking;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

/**
 * <p>
 * MiteredBox class.
 * </p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class MiteredBox implements IPluginCommand
{
    /** Constant <code>GROUP="WoodWorking"</code> */
    static final public String GROUP = "WoodWorking";
    /** Constant <code>NAME="miteredBox"</code> */
    static final public String NAME  = "miteredBox";

    @Arg(inList = { "4" }, defaultValues = "4", help = "Indicates the number of sides to the box")
    int                        sides;

    @Arg(shortName = 'Y',
            allowCamelCaps = true,
            required = true,
            range = { "1" },
            help = "external front to back in inches")
    float                      externalLength;

    @Arg(shortName = 'X',
            allowCamelCaps = true,
            required = true,
            range = { "1" },
            help = "external left to right in inches")
    float                      externalWidth;

    @Arg(shortName = 'Z',
            allowCamelCaps = true,
            required = true,
            range = { "0.5" },
            help = "height of the box in inches")
    float                      boardWidth;

    @Arg(range = { "0.375" },
            defaultValues = ".5",
            help = "board thickness in inches")
    float                      thickness;

    @Arg(allowCamelCaps = true,
            range = { "0", "4" },
            defaultValues = "1",
            help = "indicates the amount of extra length / width on pieces before final cut")
    float                      roughAllowance;

    /**
     * <p>
     * Constructor for MiteredBox.
     * </p>
     */
    public MiteredBox()
    {}

    /** {@inheritDoc} */
    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();
        showFrame(message.add("Cut the box sides"));
        message.print(context);
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public String getGroup()
    {
        return GROUP;
    }

    /** {@inheritDoc} */
    @Override
    public String getName()
    {
        return NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getOverview()
    {
        return "The tools and methods to create the frame of a box using the Incr Miter jig";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }

    float miterAngle()
    {
        return 360 / (2 * sides);
    }

    float roughXSideLength()
    {
        return xSideLength() + roughAllowance;
    }

    float roughYSideLength()
    {
        return ySideLength() + roughAllowance;
    }

    private void showFrame(final Outline o)
    {
        o.add("Cut %3$d boards to be about %1$4.3f long and %3$d more to be about %2$4.3f long.",
                roughYSideLength(),
                roughXSideLength(),
                sides / 2);

        o.add("Rip all boards to be %1$4.3f x %2$4.3f", boardWidth, thickness);

        o.add("Set the tablesaw blade to 90 degrees.  (The following is not correct yet...)");
        o.add("Set the fence to %1$4.3f inches and %2$2.1f degrees. and cut one end of %3$d boards.",
                xSideLength() + 0.5f, miterAngle(), sides / 2);
        o.add("Set the fence to %1$4.3f and cut the other end of the same boards.  Put these boards on the side.",
                xSideLength());
        o.add("Set the fence to %1$4.3f and cut one end of the other %2$d boards.", ySideLength() + 0.5f, sides / 2);
        o.add("Set the fence to %1$4.3f and cut the other end of the these boards.", ySideLength());
    }

    float tan(final float miterAngle)
    {
        return (float) (Math.tan(miterAngle * Math.PI / 180));
    }

    float xSideLength()
    {
        return (thickness / tan(miterAngle())) + externalWidth;
    }

    float ySideLength()
    {
        return (thickness / tan(miterAngle())) + externalLength;
    }

}
