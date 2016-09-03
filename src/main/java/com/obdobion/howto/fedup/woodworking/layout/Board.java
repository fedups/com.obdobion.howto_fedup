package com.obdobion.howto.fedup.woodworking.layout;

/**
 * <p>
 * Abstract Board class.
 * </p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public abstract class Board
{
    private final String title;
    private final float  width;
    private final float  length;
    private final float  thickness;

    /**
     * <p>
     * Constructor for Board.
     * </p>
     *
     * @param anotherBoard a
     *            {@link com.obdobion.howto.fedup.woodworking.layout.Board}
     *            object.
     */
    public Board(final Board anotherBoard)
    {
        title = anotherBoard.title;
        width = anotherBoard.width;
        length = anotherBoard.length;
        thickness = anotherBoard.thickness;
    }

    /**
     * <p>
     * Constructor for Board.
     * </p>
     *
     * @param title a {@link java.lang.String} object.
     * @param width a float.
     * @param length a float.
     * @param thickness a float.
     */
    public Board(final String title, final float width, final float length, final float thickness)
    {
        this.title = title;
        this.width = width;
        this.length = length;
        this.thickness = thickness;
    }

    /**
     * <p>
     * Getter for the field <code>length</code>.
     * </p>
     *
     * @return the length
     */
    public float getLength()
    {
        return length;
    }

    /**
     * <p>
     * Getter for the field <code>thickness</code>.
     * </p>
     *
     * @return the thickness
     */
    public float getThickness()
    {
        return thickness;
    }

    /**
     * <p>
     * Getter for the field <code>title</code>.
     * </p>
     *
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * <p>
     * Getter for the field <code>width</code>.
     * </p>
     *
     * @return the width
     */
    public float getWidth()
    {
        return width;
    }
}
