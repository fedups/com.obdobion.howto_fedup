package com.obdobion.howto.fedup;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

/**
 * <p>MiteredFrame class.</p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class MiteredFrame implements IPluginCommand
{
    /** Constant <code>GROUP="WoodWorking"</code> */
    static final public String GROUP = "WoodWorking";
    /** Constant <code>NAME="miteredFrame"</code> */
    static final public String NAME  = "miteredFrame";

    @Arg(inList = { "4" }, defaultValues = "4", help = "Indicates the number of sides to the frame")
    int                        sides;

    @Arg(allowCamelCaps = true,
            required = true,
            range = { "1" },
            help = "external front to back in inches")
    float                      externalLength;

    @Arg(allowCamelCaps = true,
            required = true,
            range = { "1" },
            help = "external left to right in inches")
    float                      externalWidth;

    @Arg(allowCamelCaps = true,
            required = true,
            range = { "0.5" },
            help = "width of a board in inches")
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
     * <p>Constructor for MiteredFrame.</p>
     */
    public MiteredFrame()
    {
    }

    /** {@inheritDoc} */
    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();
        showFrame(message.add("Cut the frame"));
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
        return "The tools and methods to create a frame using the Incr Miter jig";
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

        o.add("Set the tablesaw blade to %1$2.1f degrees.", miterAngle());
        o.add("Set the fence to %1$4.3f and cut one end of %2$d boards.", xSideLength() + 0.5f, sides / 2);
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
        return (tan(miterAngle()) / thickness) + externalWidth;
    }

    float ySideLength()
    {
        return (tan(miterAngle()) / thickness) + externalLength;
    }

}
