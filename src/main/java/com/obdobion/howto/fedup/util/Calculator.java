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

    @Arg(positional = true,
            caseSensitive = true,
            help = "An algebraic equation that will be solved.  Quotes around the entire equation are not usually necessary.  However, when characters like '=' and ';' are needed then quotes will be required.")
    String[]                    equation;

    public Calculator()
    {
    }

    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();

        final StringBuilder concatenatedInput = new StringBuilder();
        for (final String s : equation)
            concatenatedInput.append(s).append(" ");

        try
        {
            message.printf(Equ.getInstance().evaluate(concatenatedInput.toString()).toString());

        } catch (final Exception e)
        {
            logger.error("{}: {}", concatenatedInput.toString(), e.getMessage(), e);
            message.printf("%1$s: %2$s", concatenatedInput.toString(), e.getMessage());
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
