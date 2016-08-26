package com.obdobion.howto.fedup.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obdobion.algebrain.Equ;
import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

public class Calculator implements IPluginCommand
{
    private final static Logger logger = LoggerFactory.getLogger(Calculator.class.getName());
    static final public String  GROUP  = "Utility";
    static final public String  NAME   = "calc";

    @Arg(positional = true, caseSensitive = true)
    Equ                         equation;

    public Calculator()
    {
    }

    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();

        try
        {
            message.printf(Equ.getInstance().evaluate().toString());

        } catch (final Exception e)
        {
            logger.error("{} : {}", equation.toString(), e.getMessage(), e);
            message.printf(e.getMessage());
        }
        return 0;
    }

    @Override
    public String getGroup()
    {
        return GROUP;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public String getOverview()
    {
        return "Show the results of an equation.";
    }

    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }
}
