package com.obdobion.howto.fedup.util;

import java.text.ParseException;
import java.time.LocalDateTime;
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
public class DateTimePlugin implements IPluginCommand
{
    /** Constant <code>GROUP="Utility"</code> */
    static final public String GROUP = "Utility";
    /** Constant <code>NAME="date"</code> */
    static final public String NAME  = "datetime";

    @Arg(positional = true,
            caseSensitive = true,
            defaultValues = "now",
            help = "The base date, to which any modifications will be applied.  The current date/time will be used by default")
    LocalDateTime              baseDate;

    @Arg(range = { "0" },
            allowCamelCaps = true,
            defaultValues = "-1",
            help = "If specified this will override the --date parameter.")
    long                       javaTime;

    @Arg(shortName = 'm',
            allowCamelCaps = true,
            defaultValues = "+0s",
            help = "Indicates changes that will be applied to the date before formatting occurs.")
    String[]                   dateModifications;

    @Arg(longName = "as",
            shortName = 'a',
            defaultValues = "Specified",
            help = "Indicates how the date should be presented.")
    CalendarCalculatorFormat   formatType;

    @Arg(shortName = 'f',
            defaultValues = "MM/dd/yyyy HH:mm:ss.SSS",
            help = "Used along with the '--as specified'.  Use the standard Java DateTimeFormatter rules.")
    DateTimeFormatter          format;

    /**
     * <p>
     * Constructor for DatePlugin.
     * </p>
     */
    public DateTimePlugin()
    {}

    /** {@inheritDoc} */
    @Override
    public int execute(final Context context) throws ParseException
    {
        final Outline message = context.getOutline();

        LocalDateTime ldt = null;

        if (javaTime != -1L)
            ldt = CalendarFactory.convert(javaTime / 1000);
        else if (baseDate == null)
            ldt = CalendarFactory.today(dateModifications);
        else
            ldt = CalendarFactory.modify(baseDate, dateModifications);

        switch (formatType)
        {
            case JavaTime:
                message.printf("%d", CalendarFactory.asDateLong(ldt));
                break;
            case JSON:
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
        return "Apply modifications to a date/time and present the results in a variety of formats.";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }
}