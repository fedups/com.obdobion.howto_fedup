package com.obdobion.howto.fedup;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.obdobion.argument.CmdLine;
import com.obdobion.argument.ICmdLine;
import com.obdobion.howto.App;
import com.obdobion.howto.Config;
import com.obdobion.howto.Context;
import com.obdobion.howto.PluginManager;
import com.obdobion.howto.fedup.layout.MilledBoard;
import com.obdobion.howto.fedup.layout.RawBoard;
import com.obdobion.howto.fedup.layout.RoughCutLumberLayout;

/**
 * SplineBoxTest.
 *
 * Specify all defaults relative to the current test so that if defaults change
 * in the future they will not effect these test cases.
 *
 * @author Chris DeGreef fedupforone@gmail.com
 * @since 0.0.3
 */
public class SplineBoxTest
{
    PrintWriter sysout;

    /**
     * <p>boards.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @Test
    public void boards() throws Exception
    {
        final List<MilledBoard> neededBoards = new ArrayList<>();
        neededBoards.add(new MilledBoard("1", 4, 12, 0.75F));
        neededBoards.add(new MilledBoard("2", 2, 18, 0.75F));

        final List<RawBoard> rawBoards = new RoughCutLumberLayout().layout(neededBoards);

        Assert.assertEquals("", 2, rawBoards.size());

        Assert.assertEquals("", 0.0, rawBoards.get(0).getWidth(), 4);
        Assert.assertEquals("", 0.0, rawBoards.get(0).getLength(), 12);
        Assert.assertEquals("", 0.0, rawBoards.get(0).getThickness(), 0.75f);

        Assert.assertEquals("", 0.0, rawBoards.get(1).getWidth(), 2);
        Assert.assertEquals("", 0.0, rawBoards.get(1).getLength(), 18);
        Assert.assertEquals("", 0.0, rawBoards.get(1).getThickness(), 0.75f);
    }

    /**
     * <p>execute.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @Test
    public void execute() throws Exception
    {
        final SplineBox box = new SplineBox();
        final ICmdLine cmdline = CmdLine.load(box, "-X 7 -Y 8 -Z 2.875 --RA 1 --DD 0.125 --BT .25 --TT .5");

        final Config config = new Config(".");
        final Context context = PluginManager.createContext(config, new PluginManager(config));
        context.setParser(cmdline);
        box.execute(context);
        App.destroyContext(context);
    }

    /**
     * <p>help.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @Test
    public void help() throws Exception
    {
        final Dado box = new Dado();
        CmdLine.load(box, "--help");
    }

    /**
     * <p>testNormals.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @Test
    public void testNormals() throws Exception
    {
        final SplineBox box = new SplineBox();
        CmdLine.load(box, "-X 8 -Y 6 -Z 2.5 --RA 1 --DD 0.125 --BT .25 --TT .5");
        Assert.assertEquals("roughBottomY", 6.125, box.roughBottomY(), 0);
        Assert.assertEquals("roughBottomXTotal", 8.125, box.roughBottomXTotal(), 0);
        Assert.assertEquals("bottomY", 5.125, box.bottomY(), 0);
        Assert.assertEquals("bottomXTotal", 7.125, box.bottomXTotal(), 0);

        Assert.assertEquals("roughTopY", 6.125, box.roughTopY(), 0);
        Assert.assertEquals("roughTopXTotal", 8.125, box.roughTopXTotal(), 0);
        Assert.assertEquals("roughTopThickness", 0.625, box.roughTopThickness(), 0);
        Assert.assertEquals("topY", 5.125, box.topY(), 0);
        Assert.assertEquals("topXTotal", 7.125, box.topXTotal(), 0);
        Assert.assertEquals("topEdgeRouterBitHeight", 1, box.topEdgeRouterBitHeight(), 0.001);

        Assert.assertEquals("roughYSideLength", 7.0, box.roughYSideLength(), 0.001);

        Assert.assertEquals("roughXSideLength", 9.0, box.roughXSideLength(), 0.001);

        Assert.assertEquals("fenceForBottomDado", 2.375, box.fenceForBottomDado(), 0.001);
        Assert.assertEquals("fenceForTopDado", 0.25, box.fenceForTopDado(), 0.001);
        Assert.assertEquals("fenceForLidInnerDado", 1, box.fenceForLidInnerDado(), 0.001);
        Assert.assertEquals("fenceForLidRemoval", 1.25, box.fenceForLidRemoval(), 0.001);

        Assert.assertEquals("fenceForTopSpline", 0.875, box.fenceForTopSpline(), 0.001);
        Assert.assertEquals("fenceForMiddleSpline", 1.625, box.fenceForMiddleSpline(), 0.001);
        Assert.assertEquals("fenceForBottomSpline", 2.25, box.fenceForBottomSpline(), 0.001);
    }
}
