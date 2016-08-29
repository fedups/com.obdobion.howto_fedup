package com.obdobion.howto.fedup.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.calendar.CalendarFactory;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;

public class CalendarCalculator implements IPluginCommand
{
    static public enum CalendarCalculatorFormat
    {
        JavaLong,
        Jason,
        Formula,
        Specified
    }

    static final public String GROUP = "Utility";
    static final public String NAME  = "date";

    @Arg(positional = true,
            caseSensitive = true,
            defaultValues = "today",
            help = "The base date, to which any modifications will be applied.  The current date/time will be used by default")
    Calendar                   baseDate;

    @Arg(defaultValues = "false",
            help = "Indicates that the time should be included in the date otherwise it will assume midnight (AM)")
    boolean                    time;

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
            caseSensitive = true,
            defaultValues = "",
            help = "Used along with the '--as specified'.  Use the standard Java Simple Date formatter rules.  Ex: 'yyyy/MM/dd' ")
    String                     format;

    public CalendarCalculator()
    {
    }

    @Override
    public int execute(final Context context)
    {
        final Outline message = context.getOutline();

        LocalDateTime ldt = null;

        if (baseDate == null)
            ldt = CalendarFactory.now(dateModifications);
        else
            ldt = CalendarFactory.modify(baseDate, dateModifications);
        if (javaTime != -1L)
            ldt = CalendarFactory.convert(javaTime * 1000);
        if (!time)
            ldt = CalendarFactory.noTime(ldt);

        if (format == null || format.trim().length() == 0)
            if (!time)
                format = "MM/dd/yyyy";
            else
                format = "MM/dd/yyyy@HH:mm:ss.SSS";

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
            final SimpleDateFormat sdf = new SimpleDateFormat(format);
            message.printf(sdf.format(CalendarFactory.asDate(ldt)));
            break;
        default:
            break;
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
        return "Apply modifications to a date and present the results in a variety of formats.";
    }

    @Override
    public boolean isOnceAndDone()
    {
        return false;
    }
}
