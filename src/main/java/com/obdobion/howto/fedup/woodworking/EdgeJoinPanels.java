package com.obdobion.howto.fedup.woodworking;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

/**
 * <p>
 * EdgeJoinPanels class.
 * </p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class EdgeJoinPanels implements IPluginCommand
{
    /** Constant <code>GROUP="WoodWorking"</code> */
    static final public String GROUP = "WoodWorking";
    /** Constant <code>NAME="edgeJoin"</code> */
    static final public String NAME  = "edgeJoin";

    @Arg(shortName = 'X',
            longName = "width",
            required = true,
            range = { "1" },
            help = "external left to right in inches")
    float                      x;

    @Arg(shortName = 'Y',
            longName = "depth",
            required = true,
            range = { "1" },
            help = "external front to back in inches")
    float                      y;

    @Arg(shortName = 't',
            inList = { "0.1875", "0.25", "0.375", "0.5" },
            defaultValues = ".25",
            help = "board thickness in inches")
    float                      thickness;

    @Arg(allowCamelCaps = true,
            help = "indicates that the panel pieces should be edge joined on the short side, by default the long side is to be joined")
    boolean                    edgeJoinOnShortSides;

    @Arg(allowCamelCaps = true,
            range = { "0", "4" },
            defaultValues = "1",
            help = "indicates the amount of extra length / width on pieces before final cut")
    float                      roughAllowance;

    /**
     * <p>
     * Constructor for EdgeJoinPanels.
     * </p>
     */
    public EdgeJoinPanels()
    {}

    float bottomEdgeRouterBitHeight()
    {
        return (thickness / 2) + 0.75F;
    }

    /** {@inheritDoc} */
    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();
        showPanel(message.add("Make the panel"));
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
        return "Indicates the tools and methods for edge joining boards to make a panel";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }

    float roughBottomThickness()
    {
        return thickness + 0.125F;
    }

    float roughBottomXTotal()
    {
        final float lengthToUse;
        if (edgeJoinOnShortSides)
            if (x < y)
                lengthToUse = y;
            else
                lengthToUse = x;
        else if (x > y)
            lengthToUse = y;
        else
            lengthToUse = x;
        return lengthToUse + roughAllowance;
    }

    float roughBottomY()
    {
        final float lengthToUse;
        if (edgeJoinOnShortSides)
            if (x < y)
                lengthToUse = x;
            else
                lengthToUse = y;
        else if (x > y)
            lengthToUse = x;
        else
            lengthToUse = y;
        return lengthToUse + roughAllowance;
    }

    private void showPanel(final Outline o)
    {
        o.add("Use the edge joint router bit to join pieces of wood together.");
        o.add(
                "When adding the widths of the boards to get the total amount needed for the panel (%1$4.3f), subtract 1/2\" from each board width.",
                roughBottomXTotal());
        o.add("Each piece should be %1$4.3f long and the thickness of each is %2$4.3f.",
                roughBottomY(), roughBottomThickness());
        o.add("Set the height of the bit to %1$4.3f.", bottomEdgeRouterBitHeight());
        o.add("After the glue has dried, plane it to %1$4.3f and trim it to %2$4.3f, %3$4.3f",
                thickness, y, x);
    }

}
