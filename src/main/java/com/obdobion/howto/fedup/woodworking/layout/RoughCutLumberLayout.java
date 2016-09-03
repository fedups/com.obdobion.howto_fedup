package com.obdobion.howto.fedup.woodworking.layout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * MaterialLayout.
 *
 * The dimensions are for the raw wood. It is expected that it is not
 * dimensional and the edges will be rough all around and that that thicknesses
 * will be 1/4 less than advertised.
 *
 * The result will be given in as many boards as possible so that they can be
 * added together as needed when longer boards can be found.
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class RoughCutLumberLayout implements MaterialLayout
{
    float maxRawBoardWidth;
    float maxRawBoardThickness;
    float maxRawBoardLength;

    /**
     * <p>
     * Constructor for RoughCutLumberLayout.
     * </p>
     */
    public RoughCutLumberLayout()
    {
        maxRawBoardWidth = 9F;
        maxRawBoardThickness = 0.75f;
    }

    /** {@inheritDoc} */
    @Override
    public List<RawBoard> layout(final List<MilledBoard> neededBoards)
    {
        // #TODO lots to do here

        final List<RawBoard> boards = new ArrayList<>();

        neededBoards.forEach(milled -> boards.add(new RawBoard(milled)));

        boards.sort((o1, o2) -> {
            if (o1.getThickness() < o2.getThickness())
                return -11;
            if (o1.getThickness() > o2.getThickness())
                return 1;
            if (o1.getLength() < o2.getLength())
                return -1;
            if (o1.getLength() > o2.getLength())
                return 1;
            if (o1.getWidth() < o2.getWidth())
                return -1;
            if (o1.getWidth() > o2.getWidth())
                return 1;
            return 0;
        });

        return boards;
    }
}
