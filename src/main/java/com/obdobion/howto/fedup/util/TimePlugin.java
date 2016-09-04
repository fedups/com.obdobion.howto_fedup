package com.obdobion.howto.fedup.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.calendar.CalendarFactory;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

/**
 * <p>
 * DatePlugin class.
 * </p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class TimePlugin implements IPluginCommand
{
    static public enum CalendarCalculatorFormat
    {
        JavaLong,
        Jason,
        Formula,
        Specified
    }

    /** Constant <code>GROUP="Utility"</code> */
    static final public String GROUP = "Utility";
    /** Constant <code>NAME="date"</code> */
    static final public String NAME  = "time";

    @Arg(positional = true,
            caseSensitive = true,
            defaultValues = "now",
            help = "The base time, to which any modifications will be applied.  The current time will be used by default")
    LocalTime                  baseTime;

    @Arg(range = { "0" },
            allowCamelCaps = true,
            defaultValues = "-1",
            help = "If specified this will override the time parameter.")
    long                       javaTime;

    @Arg(shortName = 'm',
            allowCamelCaps = true,
            defaultValues = "+0s",
            help = "Indicates changes that will be applied to the time before formatting occurs.")
    String[]                   timeModifications;

    @Arg(longName = "as",
            shortName = 'a',
            defaultValues = "Specified",
            help = "Indicates how the time should be presented.")
    CalendarCalculatorFormat   formatType;

    @Arg(shortName = 'f',
            defaultValues = "HH:mm:ss.SSS",
            help = "Used along with the '--as specified'.  Use the standard Java DateTimeFormatter rules.")
    DateTimeFormatter          format;

    /**
     * <p>
     * Constructor for DatePlugin.
     * </p>
     */
    public TimePlugin()
    {}

    /** {@inheritDoc} */
    @Override
    public int execute(final Context context) throws ParseException
    {
        final Outline message = context.getOutline();

        LocalDateTime ldt = null;

        if (javaTime != -1L)
            ldt = CalendarFactory.convert(javaTime * 1000);
        else if (baseTime == null)
            ldt = CalendarFactory.now(timeModifications);
        else
            ldt = CalendarFactory.modify(baseTime, timeModifications);

        switch (formatType)
        {
            case JavaLong:
                message.printf("%d", CalendarFactory.asDateLong(ldt));
                break;
            case Jason:
                message.printf(CalendarFactory.asJSON(ldt));
                break;
            case Formula:
                message.printf(CalendarFactory.asFormula(ldt));
                break;
            case Specified:
                message.printf(format.format(ldt));
                break;
            default:
                break;
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
        return "Apply modifications to a time and present the results in a variety of formats.";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }
}
