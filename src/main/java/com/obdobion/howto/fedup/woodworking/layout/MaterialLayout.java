package com.obdobion.howto.fedup.woodworking.layout;

import java.util.List;

@FunctionalInterface
/**
 * <p>MaterialLayout interface.</p>
 *
 * @author Chris DeGreef fedupforone@gmail.com
 */
public interface MaterialLayout
{
    /**
     * <p>layout.</p>
     *
     * @param neededBoards a {@link java.util.List} object.
     * @return a {@link java.util.List} object.
     */
    public List<RawBoard> layout(final List<MilledBoard> neededBoards);
}
