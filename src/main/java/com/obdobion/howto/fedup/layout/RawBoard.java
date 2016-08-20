package com.obdobion.howto.fedup.layout;

/**
 * <p>RawBoard class.</p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public class RawBoard extends Board
{
    /**
     * <p>Constructor for RawBoard.</p>
     *
     * @param board a {@link com.obdobion.howto.fedup.layout.Board} object.
     */
    public RawBoard(final Board board)
    {
        super(board);
    }

    /**
     * <p>Constructor for RawBoard.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @param width a float.
     * @param length a float.
     * @param thickness a float.
     */
    public RawBoard(final String title, final float width, final float length, final float thickness)
    {
        super(title, width, length, thickness);
    }
}
