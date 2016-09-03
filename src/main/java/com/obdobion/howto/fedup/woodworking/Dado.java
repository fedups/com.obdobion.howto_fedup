package com.obdobion.howto.fedup.woodworking;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

/**
 * <p>
 * Dado class.
 * </p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class Dado implements IPluginCommand
{
    /** Constant <code>GROUP="WoodWorking"</code> */
    static final public String GROUP = "WoodWorking";
    /** Constant <code>NAME="dado"</code> */
    static final public String NAME  = "dado";

    @Arg(shortName = 'W',
            range = { "0.125", "0.75" },
            defaultValues = ".25",
            help = "width of the dado in inches")
    float                      width;

    @Arg(shortName = 'D',
            range = { "0.125", "0.75" },
            defaultValues = ".25",
            help = "depth of the dado in inches")
    float                      depth;

    @Arg(shortName = 'X',
            required = true,
            range = { "0" },
            help = "distance from edge of board")
    float                      location;

    @Arg(defaultValues = "EdgeToTop", help = "Indicates what the measurement measures")
    MeasurementType            type;

    @Arg(defaultValues = "TableSaw", help = "Indicates what tool will be used to make the dado")
    ToolType                   tool;

    /**
     * <p>
     * Constructor for Dado.
     * </p>
     */
    public Dado()
    {}

    float convertDistanceToEdgeToTop()
    {
        switch (type)
        {
            case EdgeToBottom:
                return location - width;
            case EdgeToCenter:
                return location - (width / 2);
            case EdgeToTop:
                return location;
        }
        return location;
    }

    /** {@inheritDoc} */
    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();
        showDado(message.add("Cut the dado"));
        message.print(context);
        return 0;
    }

    float fenceLocation()
    {
        switch (tool)
        {
            case TableSaw:
                return convertDistanceToEdgeToTop();
            case Router:
                return convertDistanceToEdgeToTop() + (width / 2);
        }
        return 0F;
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
        return "The tools and methods to plow out a dado";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }

    private void showDado(final Outline o)
    {
        switch (tool)
        {
            case TableSaw:
                o.add("Install a %1$4.3f dado blade with a height of %2$4.3f and the fence at %3$4.3f.",
                        width, depth, fenceLocation());
                break;
            case Router:
                o.add("Use the %1$4.3f straight cut router bit, set the depth to %2$4.3f and the fence at %3$4.3f.",
                        width, depth, fenceLocation());
                break;
        }
    }

}
