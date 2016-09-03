package com.obdobion.howto.fedup.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obdobion.algebrain.Equ;
import com.obdobion.argument.annotation.Arg;
import com.obdobion.calendar.TemporalHelper;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

/**
 * <p>
 * Calculator class.
 * </p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class Calculator implements IPluginCommand
{
    private final static Logger logger = LoggerFactory.getLogger(Calculator.class.getName());
    /** Constant <code>GROUP="Utility"</code> */
    static final public String  GROUP  = "Utility";
    /** Constant <code>NAME="calc"</code> */
    static final public String  NAME   = "calc";

    @Arg(positional = true,
            required = true,
            caseSensitive = true,
            help = "An algebraic equation that will be solved.  Quotes around the entire equation are usually necessary since special math characters are also special to command line arguments in general.")
    Equ                         equation;

    @Arg(longName = "rpn",
            shortName = 'r',
            help = "Shows the \"reverse polish notation\" for the equations rather than computing an answer.")
    boolean                     showRPN;

    @Arg(shortName = 'd',
            defaultValues = "3",
            help = "The number of decimal places to show for floating point results.")
    int                         decimalPlaces;

    /**
     * <p>
     * Constructor for Calculator.
     * </p>
     */
    public Calculator()
    {}

    /** {@inheritDoc} */
    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();
        try
        {
            if (showRPN)
                showRPN(message);
            else
                showEvaluation(message);

        } catch (final Exception e)
        {
            logger.error("{}: {}", equation.toString(), e.getMessage(), e);
            message.printf("%1$s: %2$s", equation.toString(), e.getMessage());
        }
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
        return "You can use this command to try out anything you can do with the Algebrain package.";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }

    private void showEvaluation(final Outline message) throws Exception
    {
        final Object resultOfEqu = equation.evaluate();
        if (resultOfEqu instanceof LocalDateTime)
        {
            final LocalDateTime ldt = (LocalDateTime) resultOfEqu;
            if (ldt.toLocalDate() == LocalDate.MIN)
                message.printf(TemporalHelper.getOutputTF().format(ldt.toLocalTime()));
            else if (ldt.toLocalTime() == LocalTime.MIN)
                message.printf(TemporalHelper.getOutputDF().format(ldt.toLocalDate()));
            else
                message.printf(TemporalHelper.getOutputDTF().format(ldt));

        } else if (resultOfEqu instanceof Long)
            message.printf("%d", resultOfEqu);
        else if (resultOfEqu instanceof Double)
            message.printf("%." + decimalPlaces + "f", resultOfEqu);
        else

            message.printf(resultOfEqu.toString());
    }

    private void showRPN(final Outline message) throws Exception
    {
        message.printf(equation.showRPN());
    }
}
