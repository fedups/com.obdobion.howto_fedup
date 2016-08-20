package com.obdobion.howto.fedup;

import java.util.ArrayList;
import java.util.List;

import com.obdobion.argument.annotation.Arg;
import com.obdobion.howto.Context;
import com.obdobion.howto.IPluginCommand;
import com.obdobion.howto.Outline;
import com.obdobion.howto.fedup.layout.MaterialLayout;
import com.obdobion.howto.fedup.layout.MilledBoard;
import com.obdobion.howto.fedup.layout.RoughCutLumberLayout;

/**
 *
 * SplineBox.
 *
 * The fence for a router table is at 0 for the center of the bit.
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class SplineBox implements IPluginCommand
{
    /** Constant <code>GROUP="WoodWorking"</code> */
    static final public String GROUP = "WoodWorking";
    /** Constant <code>NAME="splineBox"</code> */
    static final public String NAME  = "splineBox";

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

    @Arg(shortName = 'Z',
            longName = "height",
            required = true,
            range = { "1" },
            help = "external bottom to top in inches")
    float                      z;

    @Arg(allowCamelCaps = true,
            range = { "0.375" },
            defaultValues = ".5",
            help = "side board thickness in inches")
    float                      sideThickness;

    @Arg(allowCamelCaps = true,
            inList = { "0.1875", "0.25", "0.375", "0.5" },
            defaultValues = ".25",
            help = "bottom board thickness in inches")
    float                      bottomThickness;

    @Arg(allowCamelCaps = true,
            range = { "0.0625", "0.75" },
            defaultValues = ".5",
            help = "top board thickness in inches")
    float                      topThickness;

    @Arg(allowCamelCaps = true,
            range = { "0.0625", "0.25" },
            defaultValues = ".125",
            help = "depth of the dado in the sides for supporting the top and the bottom")
    float                      dadoDepth;

    @Arg(allowCamelCaps = true,
            range = { "0", "4" },
            defaultValues = "1",
            help = "indicates the amount of extra length / width on pieces before final cut")
    float                      roughAllowance;

    Context                    context;

    /**
     * <p>Constructor for SplineBox.</p>
     */
    public SplineBox()
    {
    }

    float bitDepthForLidRemoval()
    {
        return sideThickness - dadoDepth;
    }

    float bitDepthForTopRabbet()
    {
        return topThickness - dadoThickness();
    }

    float bitHeightBasedOnTotalThickness(final float thickness)
    {
        return (thickness / 2) + 0.75F;
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

    /** {@inheritDoc} */
    @Override
    public int execute(final Context p_context)
    {
        context = p_context;

        final Outline message = context.getOutline();

        showOverview(message.add("Spline Box"));
        showMaterials(message.add("Materials"));
        showBottom(message.add("Make the bottom"));
        showTop(message.add("Make the top"));
        showSides(message.add("Make the sides"));
        showAssembly(message.add("Assemble"));
        showSplines(message.add("Insert splines"));
        showRemoveLid(message.add("Remove Lid"));

        message.print(context);
        return 0;
    }

    float fenceForBottomDado()
    {
        return sideBoardWidth() - dadoThickness() - insetZForBottom();
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

    float fenceForTopSpline()
    {
        return fenceForLidInnerDado() - (splineSize() / 2);
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
        return "How to build a spline box.";
    }

    float insetZForBottom()
    {
        return 0.125F;
    }

    float insideX()
    {
        return x - (2 * sideThickness);
    }

    float insideY()
    {
        return y - (2 * sideThickness);
    }

    float insideZ()
    {
        return z - bottomThickness - insetZForBottom() - topThickness;
    }

    List<MilledBoard> neededBoards()
    {
        final List<MilledBoard> boards = new ArrayList<>();
        boards.add(new MilledBoard("bottom", roughBottomY(), roughBottomXTotal(), bottomThickness));
        boards.add(new MilledBoard("top", roughTopY(), roughTopXTotal(), topThickness));
        boards.add(new MilledBoard("side 1", sideBoardWidth(), roughXSideLength(), sideThickness));
        boards.add(new MilledBoard("side 2", sideBoardWidth(), roughXSideLength(), sideThickness));
        boards.add(new MilledBoard("side 3", sideBoardWidth(), roughYSideLength(), sideThickness));
        boards.add(new MilledBoard("side 4", sideBoardWidth(), roughYSideLength(), sideThickness));
        return boards;
    }

    float roughBottomXTotal()
    {
        return x - (2 * sideThickness) + dadoDepth + roughAllowance;
    }

    float roughBottomY()
    {
        return y - (2 * sideThickness) + dadoDepth + roughAllowance;
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

    private void showAssembly(final Outline o)
    {
        o.add("Glue, clamp and wait for it to dry.");
    }

    /**
     * @param o
     */
    private void showBottom(final Outline o)
    {
        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(EdgeJoinPanels.GROUP, EdgeJoinPanels.NAME),
                "-X %1$4.3f -Y %2$4.3f --th %3$4.3f --RA %4$4.3f",
                bottomXTotal(),
                bottomY(),
                bottomThickness,
                roughAllowance);
    }

    private void showMaterials(final Outline o)
    {
        final MaterialLayout mat = new RoughCutLumberLayout();
        o.add("   Width   Length   Thickness");
        mat.layout(neededBoards()).forEach(rawBoard -> o.add("%1$8.3f%2$9.3f%3$12.3f   %4$-20s",
                rawBoard.getWidth(),
                rawBoard.getLength(),
                rawBoard.getThickness(),
                rawBoard.getTitle()));
    }

    private void showOverview(final Outline o)
    {
        o.add("external width%1$6.2f, depth%2$6.2f, height%3$6.3f", y, x, z);
        o.add("internal width%1$6.2f, depth%2$6.2f, height%3$6.3f", insideY(), insideX(), insideZ());
    }

    /**
     * @param o
     */
    private void showRemoveLid(final Outline o)
    {
        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(Dado.GROUP, Dado.NAME),
                "-W %1$4.3f -D %2$4.3f -X %3$4.3f --type ETT --tool TS",
                dadoThickness(),
                bitDepthForLidRemoval(),
                fenceForLidRemoval());
    }

    private void showSides(final Outline o)
    {
        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(MiteredBox.GROUP, MiteredBox.NAME),
                "--sides 4 --EL %1$4.3f --EW %2$4.3f --BW %3$4.3f --th %4$4.3f --RA %5$4.3f",
                y,
                x,
                sideBoardWidth(),
                sideThickness,
                roughAllowance);

        o.add("Place the top edge of each board to the fence and the inside of the box side down.");
        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(Dado.GROUP, Dado.NAME),
                "-W %1$4.3f -D %2$4.3f -X %3$4.3f --type ETT --tool TS",
                dadoThickness(),
                dadoDepth,
                fenceForBottomDado());

        o.add("Place the top edge of each board to the fence and the inside of the box side down.");
        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(Dado.GROUP, Dado.NAME),
                "-W %1$4.3f -D %2$4.3f -X %3$4.3f --type ETT --tool TS",
                dadoThickness(),
                dadoDepth,
                fenceForTopDado());

        o.add("Place the top edge of each board to the fence and the inside of the box side down.");
        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(Dado.GROUP, Dado.NAME),
                "-W %1$4.3f -D %2$4.3f -X %3$4.3f --type ETT --tool TS",
                dadoThickness(),
                dadoDepth,
                fenceForLidInnerDado());
    }

    private void showSplines(final Outline o)
    {
        final Outline splines = o.add(
                "The following cuts are made with the top of the box to the fence. Dado for the splines by installing a 0.250 wide dado blade and setting the depth to 0.75\".  Cut each of the 4 corners as follows.");
        splines.add("Set the fence to %1$4.3f.", fenceForTopSpline());
        splines.add("Set the fence to %1$4.3f.", fenceForMiddleSpline());
        splines.add("Set the fence to %1$4.3f.", fenceForBottomSpline());
    }

    private void showTop(final Outline o)
    {
        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(EdgeJoinPanels.GROUP, EdgeJoinPanels.NAME),
                "-X %1$4.3f -Y %2$4.3f --th %3$4.3f --RA %4$4.3f",
                topXTotal(),
                topY(),
                topThickness,
                roughAllowance);

        o.add("Top face down on the table.");

        context.getPluginManager().run(context,
                context.getPluginManager().uniqueNameFor(Dado.GROUP, Dado.NAME),
                "-W %1$4.3f -D %2$4.3f -X %3$4.3f --type ETT --tool TS",
                bitDepthForTopRabbet(),
                topThickness - dadoThickness(),
                0F);
    }

    float sideBoardWidth()
    {
        /*
         * Allowing for the dado that removes the top
         */
        return z + dadoThickness();
    }

    float splineSize()
    {
        return 0.25F;
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
}
