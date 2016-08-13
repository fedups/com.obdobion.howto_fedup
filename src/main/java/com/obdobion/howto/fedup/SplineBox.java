package com.obdobion.howto.fedup;

import java.io.PrintWriter;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPlugin;

public class SplineBox implements IPlugin
{
    @Arg(shortName = 'X', longName = "width", required = true, range = { "1" }, help = "external width in inches")
    float   x;

    @Arg(shortName = 'Y', longName = "length", required = true, range = { "1" }, help = "external length in inches")
    float   y;

    @Arg(shortName = 'Z', longName = "depth", required = true, range = { "1" }, help = "external depth in inches")
    float   z;

    @Arg(allowCamelCaps = true,
            range = { "0.375" },
            defaultValues = ".5",
            help = "side board thickness in inches")
    float   sideThickness;

    @Arg(allowCamelCaps = true,
            inList = { "0.1875", "0.25", "0.375", "0.5" },
            defaultValues = ".25",
            help = "bottom board thickness in inches")
    float   bottomThickness;

    @Arg(allowCamelCaps = true,
            range = { "0.0625", "0.75" },
            defaultValues = ".5",
            help = "top board thickness in inches")
    float   topThickness;

    @Arg(allowCamelCaps = true,
            range = { "0.0625", "0.25" },
            defaultValues = ".125",
            help = "depth of the dado in the sides for supporting the top and the bottom")
    float   dadoDepth;

    @Arg(allowCamelCaps = true,
            range = { "0", "4" },
            defaultValues = "1",
            help = "indicates the amount of extra length / width on pieces before final cut")
    float   roughAllowance;

    @Arg(allowCamelCaps = true,
            help = "indicates that the bottom / top pieces should be edge joined on the short side, by default the long side is to be joined")
    boolean edgeJoinOnShortSides;

    public SplineBox()
    {
    }

    float bitDepthForLidRemoval()
    {
        return sideThickness - dadoDepth;
    }

    float bitDepthForTopRabit()
    {
        return topThickness - dadoThickness();
    }

    float bitHeightBasedOnTotalThickness(final float thickness)
    {
        return (thickness / 2) + 0.75F;
    }

    float boardFeet()
    {
        int sqInches = 0;

        sqInches += (x * y) * topThickness;
        sqInches += (x * y) * bottomThickness;
        sqInches += 2 * (z * y) * sideThickness;
        sqInches += 2 * (z * x) * sideThickness;

        return (float) Math.ceil(sqInches / 144);
    }

    float bottomEdgeRouterBitHeight()
    {
        return bitHeightBasedOnTotalThickness(bottomThickness);
    }

    float bottomXTotal()
    {
        return x - (2 * sideThickness) + dadoDepth;
    }

    float bottomY()
    {
        return y - (2 * sideThickness) + dadoDepth;
    }

    float dadoThickness()
    {
        return bottomThickness;
    }

    @Override
    public int execute(final Context context)
    {
        final PrintWriter pw = context.getConsoleOutput();
        pw.printf("Spline Box -------------------------\n");

        pw.printf("\nMaterials --------------------------\n\n");
        pw.printf("  %1$4.3 board feet.  However, a standard size 4/4, 6\" wide,\n", boardFeet());
        pw.printf("  would require a length of %1$d.\n", boardFeet());

        pw.printf("\nMake the bottom --------------------\n\n");
        pw.printf("  Use the edge joint router bit to join pieces of wood together.\n");
        pw.printf("  When adding the widths of the boards to get the total amount \n");
        pw.printf("  needed for the bottom (%1$4.3f) subtract 1/2\" from each board width.\n",
                roughBottomXTotal());
        pw.printf("  Each piece should be %1$4.3f long and the thickness of each is %2$4.3f.\n",
                roughBottomY(), roughBottomThickness());
        pw.printf("  Set the height of the bit to %1$4.3f.\n", bottomEdgeRouterBitHeight());
        pw.printf("  After the glue has dried, plane it to %1$4.3f and trim it to %2$4.3f, %3$4.3f\n",
                bottomThickness, bottomY(), bottomXTotal());

        pw.printf("\nMake the top -----------------------\n\n");
        pw.printf("  Use the edge joint router bit to join pieces of wood together.\n");
        pw.printf("  When adding the widths of the boards to get the total amount \n");
        pw.printf("  needed for the top (%1$4.3f) subtract 1/2\" from each board width.\n",
                roughTopXTotal());
        pw.printf("  Each piece should be %1$4.3f long and the thickness of each is %2$4.3f.\n",
                roughTopY(), roughTopThickness());
        pw.printf("  Set the height of the bit to %1$4.3f.\n", topEdgeRouterBitHeight());
        pw.printf("  After the glue has dried, plane it to %1$4.3f and trim it to %2$4.3f, %3$4.3f\n",
                topThickness, topY(), topXTotal());

        pw.printf("\nRabit around the top ---------------\n\n");
        pw.printf("  Use the straight cut router bit nearest to %1$4.3f wide.\n", dadoThickness());
        pw.printf("  Set the depth to %1$4.3f and the fence to %2$4.3f.\n", bitDepthForTopRabit(),
                fenceForTopRabit());
        pw.printf("  Route all around the sides with the top face down on the table.\n");

        pw.printf("\nMake the sides -------------------\n\n");
        pw.printf("  First cut two boards %1$4.3f long and two more %2$4.3f long.\n",
                roughYSideLength(), roughXSideLength());
        pw.printf("  Rip these 4 boards to be %1$4.3f wide.\n", z);
        pw.printf("  Plane these 4 boards to be %1$4.3f thick.\n", sideThickness);
        pw.printf("  Place the top edge of each board to the fence for each of these dados.\n");
        pw.printf("  Dado for the bottom by installing a %1$4.3f wide dado blade,\n", dadoThickness());
        pw.printf(
                "  setting the fence to %1$4.3f and blade depth to %2$4.3f.  Rip each of the 4 sides for the bottom.\n",
                fenceForBottomDado(), dadoDepth);
        pw.printf("  Dado for the top by installing a %1$4.3f wide dado blade,\n", dadoThickness());
        pw.printf("  setting the fence to %1$4.3f and blade depth to %2$4.3f.  Rip each of the 4 sides for the top.\n",
                fenceForTopDado(), dadoDepth);
        pw.printf("  Dado for the lid by installing a %1$4.3f wide dado blade,\n", dadoThickness());
        pw.printf("  setting the fence to %1$4.3f and blade depth to %2$4.3f.  Rip each of the 4 sides for the lid.\n",
                fenceForLidInnerDado(), dadoDepth);

        pw.printf("\nAssemble -------------------------\n\n");
        pw.printf("  After it is all glued and dried.\n");

        pw.printf("\nInsert splines -------------------\n\n");
        pw.printf("  The following cuts are made with the top of the box to the fence.\n");
        pw.printf("  Dado for the splines by installing a 0.250 wide dado blade,\n");
        pw.printf("   - setting the fence to %1$4.3f and blade depth to 0.750.  Rip each of the 4 corners.\n",
                fenceForTopSpline());
        pw.printf("   - setting the fence to %1$4.3f.  Rip each of the 4 corners.\n",
                fenceForMiddleSpline());
        pw.printf("   - setting the fence to %1$4.3f.  Rip each of the 4 corners.\n",
                fenceForBottomSpline());

        pw.printf("\nRemove Lid -----------------------\n\n");
        pw.printf("  Use the straight cut router bit nearest to %1$4.3f wide.\n", dadoThickness());
        pw.printf("  Set the depth to %1$4.3f and the fence to %2$4.3f.\n", bitDepthForLidRemoval(),
                fenceForLidRemoval());
        pw.printf("  Route all around the sides with the top against the fence.\n");
        return 0;
    }

    float fenceForBottomDado()
    {
        return z - dadoThickness() - 0.125F;
    }

    float fenceForBottomSpline()
    {
        return fenceForBottomDado() - (splineSize() / 2);
    }

    float fenceForLidInnerDado()
    {
        return topThickness + (2 * splineSize());
    }

    float fenceForLidRemoval()
    {
        return fenceForLidInnerDado() + dadoThickness();
    }

    float fenceForMiddleSpline()
    {
        return fenceForLidRemoval() + dadoThickness() + (splineSize() / 2);
    }

    float fenceForTopDado()
    {
        return topThickness - dadoThickness();
    }

    float fenceForTopRabit()
    {
        return dadoDepth / 2;
    }

    float fenceForTopSpline()
    {
        return fenceForLidInnerDado() - (splineSize() / 2);
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

    int lengthNeeded()
    {
        int inches = 0;

        inches += 2 * (y + roughAllowance);
        inches += 2 * (x + roughAllowance);
        inches += (2 * (x + roughAllowance)) * (y / 6);

        return inches;
    }

    float roughBottomThickness()
    {
        return bottomThickness + 0.125F;
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
        return lengthToUse - (2 * sideThickness) + dadoDepth + roughAllowance;
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
        return lengthToUse - (2 * sideThickness) + dadoDepth + roughAllowance;
    }

    float roughTopThickness()
    {
        return topThickness + 0.125F;
    }

    float roughTopXTotal()
    {
        return roughBottomXTotal();
    }

    float roughTopY()
    {
        return roughBottomY();
    }

    float roughXSideLength()
    {
        return x + roughAllowance;
    }

    float roughYSideLength()
    {
        return y + roughAllowance;
    }

    float splineSize()
    {
        return 0.25F;
    }

    float tan45()
    {
        return (float) (Math.tan(45) * (Math.PI / 180));
    }

    float topEdgeRouterBitHeight()
    {
        return bitHeightBasedOnTotalThickness(topThickness);
    }

    float topXTotal()
    {
        return bottomXTotal();
    }

    float topY()
    {
        return bottomY();
    }

    float xSideLength()
    {
        return tan45() / sideThickness + x;
    }

    float ySideLength()
    {
        return tan45() / sideThickness + y;
    }
}
