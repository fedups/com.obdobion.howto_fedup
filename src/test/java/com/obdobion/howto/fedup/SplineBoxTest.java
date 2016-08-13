package com.obdobion.howto.fedup;

import org.junit.Assert;
import org.junit.Test;

import com.obdobion.argument.CmdLine;

/**
 * SplineBoxTest.
 *
 * Specify all defaults relative to the current test so that if defaults change
 * in the future they will not effect these test cases.
 *
 * @author Chris DeGreef fedupforone@gmail.com
 *
 */
public class SplineBoxTest
{
    @Test
    public void edgeJoinShortSide() throws Exception
    {
        final SplineBox box = new SplineBox();
        CmdLine.load(box, "-X 8 -Y 6 -Z 2.5 --EJOSS --RA 1 --DD 0.125 --BT .25 --TT .5");
        Assert.assertEquals("roughBottomY", 6.125, box.roughBottomY(), 0);
        Assert.assertEquals("roughBottomXTotal", 8.125, box.roughBottomXTotal(), 0);
        Assert.assertEquals("roughBottomThickness", 0.375, box.roughBottomThickness(), 0);
        Assert.assertEquals("bottomY", 5.125, box.bottomY(), 0);
        Assert.assertEquals("bottomXTotal", 7.125, box.bottomXTotal(), 0);
        Assert.assertEquals("bottomEdgeRouterBitHeight", 0.875, box.bottomEdgeRouterBitHeight(), 0.001);

        Assert.assertEquals("roughTopY", 6.125, box.roughTopY(), 0);
        Assert.assertEquals("roughTopXTotal", 8.125, box.roughTopXTotal(), 0);
        Assert.assertEquals("roughTopThickness", 0.625, box.roughTopThickness(), 0);
        Assert.assertEquals("topY", 5.125, box.topY(), 0);
        Assert.assertEquals("topXTotal", 7.125, box.topXTotal(), 0);
        Assert.assertEquals("topEdgeRouterBitHeight", 1, box.topEdgeRouterBitHeight(), 0.001);
    }

    @Test
    public void help() throws Exception
    {
        final SplineBox box = new SplineBox();
        CmdLine.load(box, "--help");
    }

    @Test
    public void testNormals() throws Exception
    {
        final SplineBox box = new SplineBox();
        CmdLine.load(box, "-X 8 -Y 6 -Z 2.5 --RA 1 --DD 0.125 --BT .25 --TT .5");
        Assert.assertEquals("roughBottomY", 8.125, box.roughBottomY(), 0);
        Assert.assertEquals("roughBottomXTotal", 6.125, box.roughBottomXTotal(), 0);
        Assert.assertEquals("roughBottomThickness", 0.375, box.roughBottomThickness(), 0);
        Assert.assertEquals("bottomY", 5.125, box.bottomY(), 0);
        Assert.assertEquals("bottomXTotal", 7.125, box.bottomXTotal(), 0);
        Assert.assertEquals("bottomEdgeRouterBitHeight", 0.875, box.bottomEdgeRouterBitHeight(), 0.001);

        Assert.assertEquals("roughTopY", 8.125, box.roughTopY(), 0);
        Assert.assertEquals("roughTopXTotal", 6.125, box.roughTopXTotal(), 0);
        Assert.assertEquals("roughTopThickness", 0.625, box.roughTopThickness(), 0);
        Assert.assertEquals("topY", 5.125, box.topY(), 0);
        Assert.assertEquals("topXTotal", 7.125, box.topXTotal(), 0);
        Assert.assertEquals("topEdgeRouterBitHeight", 1, box.topEdgeRouterBitHeight(), 0.001);

        Assert.assertEquals("roughYSideLength", 7.0, box.roughYSideLength(), 0.001);
        Assert.assertEquals("ySideLength", 6.056, box.ySideLength(), 0.001);

        Assert.assertEquals("roughXSideLength", 9.0, box.roughXSideLength(), 0.001);
        Assert.assertEquals("xSideLength", 8.056, box.xSideLength(), 0.001);

        Assert.assertEquals("fenceForBottomDado", 2.125, box.fenceForBottomDado(), 0.001);
        Assert.assertEquals("fenceForTopDado", 0.25, box.fenceForTopDado(), 0.001);
        Assert.assertEquals("fenceForLidInnerDado", 1, box.fenceForLidInnerDado(), 0.001);
        Assert.assertEquals("fenceForLidRemoval", 1.25, box.fenceForLidRemoval(), 0.001);

        Assert.assertEquals("fenceForTopSpline", 0.875, box.fenceForTopSpline(), 0.001);
        Assert.assertEquals("fenceForMiddleSpline", 1.625, box.fenceForMiddleSpline(), 0.001);
        Assert.assertEquals("fenceForBottomSpline", 2.0, box.fenceForBottomSpline(), 0.001);

        // Assert.assertEquals("boardFeet", 2.0, box.boardFeet(), 0.001);
        Assert.assertEquals("lengthNeeded", 50, box.lengthNeeded());
    }
}
