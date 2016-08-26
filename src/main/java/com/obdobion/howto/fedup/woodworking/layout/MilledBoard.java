package com.obdobion.howto.fedup.woodworking.layout;

/**
 * <p>MilledBoard class.</p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class MilledBoard extends Board
{
    /**
     * <p>Constructor for MilledBoard.</p>
     *
     * @param board a {@link com.obdobion.howto.fedup.woodworking.layout.Board} object.
     */
    public MilledBoard(final Board board)
    {
        super(board);
    }

    /**
     * <p>Constructor for MilledBoard.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @param width a float.
     * @param length a float.
     * @param thickness a float.
     */
    public MilledBoard(final String title, final float width, final float length, final float thickness)
    {
        super(title, width, length, thickness);
    }
}
