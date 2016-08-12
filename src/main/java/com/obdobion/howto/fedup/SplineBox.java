package com.obdobion.howto.fedup;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPlugin;

public class SplineBox implements IPlugin
{
    @Arg(shortName = 'w', required = true, range = { "1" }, help = "external width in inches")
    float width;

    @Arg(shortName = 'h', required = true, range = { "1" }, help = "external height in inches")
    float height;

    @Arg(shortName = 'l', required = true, range = { "1" }, help = "extenal length in inches")
    float length;

    @Arg(shortName = 't', required = true, range = { "0.375" }, help = "board thickness in inches")
    float thickness;

    public SplineBox()
    {
    }

    @Override
    public int execute(final Context context)
    {
        context.getConsoleOutput().printf("figure it out yourself");
        return 0;
    }

    @Override
    public String getName()
    {
        return "splineBox";
    }

    @Override
    public String getOverview()
    {
        return "How to build a spline box.";
    }

}
