/*******************************************************************************
 * Copyright (c) 2013, 2020 Dirk Fauth and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.nattable.reorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.reorder.command.ResetRowReorderCommand;
import org.eclipse.nebula.widgets.nattable.reorder.command.RowReorderCommand;
import org.eclipse.nebula.widgets.nattable.test.fixture.command.LayerCommandFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.BaseDataLayerFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.DataLayerFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowReorderLayerTest {

    private IUniqueIndexLayer underlyingLayer;
    private RowReorderLayer rowReorderLayer;

    @Before
    public void setUp() {
        this.underlyingLayer = new BaseDataLayerFixture(4, 4);
        this.rowReorderLayer = new RowReorderLayer(this.underlyingLayer);
    }

    @Test
    public void reorderViewableRowsBottomToTop() throws Exception {
        // 0 1 2 3
        assertEquals(0, this.rowReorderLayer.getRowIndexByPosition(0));
        assertEquals(3, this.rowReorderLayer.getRowIndexByPosition(3));

        // 3 0 1 2
        this.rowReorderLayer.reorderRowPosition(3, 0);
        assertEquals(1, this.rowReorderLayer.getRowPositionByIndex(0));
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(3));

        assertEquals(3, this.rowReorderLayer.getRowIndexByPosition(0));
        assertEquals(2, this.rowReorderLayer.getRowIndexByPosition(3));

        // 0 1 3 2
        this.rowReorderLayer.reorderRowPosition(0, 3);
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(0));
        assertEquals(1, this.rowReorderLayer.getRowPositionByIndex(1));
        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(2));

        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(0));
        assertEquals(1, this.rowReorderLayer.getRowPositionByIndex(1));
        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(2));
        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(3));
    }

    @Test
    /**
     * 	Index		1	2	3	0
     *          --------------------
     *  Position 	0 	1	2	3
     */
    public void reorderViewableRowsTopToBottomByPosition() throws Exception {
        // Moving to the end
        this.rowReorderLayer.reorderRowPosition(0, 4);

        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(0));

        assertEquals(0, this.rowReorderLayer.getRowIndexByPosition(3));
        assertEquals(1, this.rowReorderLayer.getRowIndexByPosition(0));
    }

    @Test
    /**
     * 	Index		2 	0	1	3
     *          --------------------
     *  Position 	0 	1	2	3
     */
    public void reorderMultipleRowsTopToBottom() throws Exception {
        this.rowReorderLayer.reorderMultipleRowPositions(new int[] { 0, 1 }, 3);

        assertEquals(2, this.rowReorderLayer.getRowIndexByPosition(0));
        assertEquals(0, this.rowReorderLayer.getRowIndexByPosition(1));
        assertEquals(1, this.rowReorderLayer.getRowIndexByPosition(2));
        assertEquals(3, this.rowReorderLayer.getRowIndexByPosition(3));
    }

    @Test
    /**
     * 	Index		2 	3	0	1
     *          --------------------
     *  Position 	0 	1	2	3
     */
    public void reorderMultipleRowsTopToBottomToTheEnd() throws Exception {
        this.rowReorderLayer.reorderMultipleRowPositions(new int[] { 0, 1 }, 4);

        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(0));
        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(1));
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(2));
        assertEquals(1, this.rowReorderLayer.getRowPositionByIndex(3));
    }

    @Test
    /**
     * 	Index		0	1	3	2
     *          --------------------
     *  Position 	0 	1	2	3
     */
    public void reorderViewableRowsBottomToTopByPosition() throws Exception {
        this.rowReorderLayer.reorderRowPosition(3, 2);

        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(0));

        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(0));
    }

    @Test
    /**
     * 	Index		2	3	0	1
     *          --------------------
     *  Position 	0 	1	2	3
     */
    public void reorderMultipleRowsBottomToTop() throws Exception {
        List<Integer> fromRowPositions = Arrays.asList(new Integer[] { 2, 3 });

        this.rowReorderLayer.reorderMultipleRowPositions(fromRowPositions, 0);

        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(0));
        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(1));
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(2));
        assertEquals(1, this.rowReorderLayer.getRowPositionByIndex(3));
    }

    @Test
    /**
     * 	Index		2	3	0	1 ... 20
     *          --------------------
     *  Position 	0 	1	2	3 ... 20
     */
    public void reorderMultipleRowsLargeArrayToEdges() throws Exception {

        RowReorderLayer reorderLayer = new RowReorderLayer(
                new BaseDataLayerFixture(20, 20));

        List<Integer> fromRowPositions = Arrays.asList(new Integer[] { 10, 11,
                12, 13 });

        reorderLayer.reorderMultipleRowPositions(fromRowPositions, 0);

        assertEquals(10, reorderLayer.getRowIndexByPosition(0));
        assertEquals(11, reorderLayer.getRowIndexByPosition(1));
        assertEquals(12, reorderLayer.getRowIndexByPosition(2));
        assertEquals(13, reorderLayer.getRowIndexByPosition(3));
        assertEquals(0, reorderLayer.getRowIndexByPosition(4));

        fromRowPositions = Arrays.asList(new Integer[] { 8, 9, 10, 11 });

        reorderLayer.reorderMultipleRowPositions(fromRowPositions, 8);

        assertEquals(4, reorderLayer.getRowIndexByPosition(8));
        assertEquals(5, reorderLayer.getRowIndexByPosition(9));
        assertEquals(6, reorderLayer.getRowIndexByPosition(10));
        assertEquals(7, reorderLayer.getRowIndexByPosition(11));

        fromRowPositions = Arrays.asList(new Integer[] { 8, 9, 10, 11 });

        reorderLayer.reorderMultipleRowPositions(fromRowPositions,
                reorderLayer.getColumnCount());

        assertEquals(7, reorderLayer.getRowIndexByPosition(19));
        assertEquals(6, reorderLayer.getRowIndexByPosition(18));
        assertEquals(5, reorderLayer.getRowIndexByPosition(17));
        assertEquals(4, reorderLayer.getRowIndexByPosition(16));
    }

    @Test
    public void commandPassedOnToParentIfCannotBeHandled() throws Exception {
        RowReorderLayer reorderLayer = new RowReorderLayer(
                new DataLayerFixture());
        assertFalse(reorderLayer.doCommand(new LayerCommandFixture()));
    }

    @Test
    public void canHandleRowReorderCommand() throws Exception {
        RowReorderLayer reorderLayer = new RowReorderLayer(
                new DataLayerFixture());
        RowReorderCommand reorderCommand = new RowReorderCommand(reorderLayer,
                0, 2);
        assertTrue(reorderLayer.doCommand(reorderCommand));
    }

    @Test
    public void getHeightForReorderedRows() throws Exception {
        this.underlyingLayer = new DataLayerFixture();
        this.rowReorderLayer = new RowReorderLayer(this.underlyingLayer);

        // 0 1 2 3 4 - see DataLayerFixture
        this.rowReorderLayer.reorderRowPosition(0, 7);

        // 1 2 3 4 0
        Assert.assertEquals(70, this.rowReorderLayer.getRowHeightByPosition(0));
        Assert.assertEquals(25, this.rowReorderLayer.getRowHeightByPosition(1));
        Assert.assertEquals(40, this.rowReorderLayer.getRowHeightByPosition(2));
        Assert.assertEquals(50, this.rowReorderLayer.getRowHeightByPosition(3));
        Assert.assertEquals(40, this.rowReorderLayer.getRowHeightByPosition(4));
        Assert.assertEquals(100, this.rowReorderLayer.getRowHeightByPosition(5));
        Assert.assertEquals(40, this.rowReorderLayer.getRowHeightByPosition(6));
    }

    @Test
    public void getHeightForMultipleRowsReordering() throws Exception {
        this.underlyingLayer = new DataLayerFixture();
        this.rowReorderLayer = new RowReorderLayer(this.underlyingLayer);

        // 0 1 2 3 4 - see DataLayerFixture
        this.rowReorderLayer.reorderMultipleRowPositions(Arrays.asList(1, 2), 7);

        // 0 3 4 1 2
        assertEquals(40, this.rowReorderLayer.getRowHeightByPosition(0));
        assertEquals(40, this.rowReorderLayer.getRowHeightByPosition(1));
        assertEquals(50, this.rowReorderLayer.getRowHeightByPosition(2));
        assertEquals(40, this.rowReorderLayer.getRowHeightByPosition(3));
        assertEquals(100, this.rowReorderLayer.getRowHeightByPosition(4));
        assertEquals(70, this.rowReorderLayer.getRowHeightByPosition(5));
        assertEquals(25, this.rowReorderLayer.getRowHeightByPosition(6));
    }

    @Test
    public void getStartYForReorderedRow() throws Exception {
        this.underlyingLayer = new DataLayerFixture();
        this.rowReorderLayer = new RowReorderLayer(this.underlyingLayer);

        // 0 1 2 3 4 - see DataLayerFixture
        this.rowReorderLayer.reorderRowPosition(0, 5);

        // Index: 1 2 3 4 0 Height: 70 25 40 50 40 100 40
        assertEquals(0, this.rowReorderLayer.getStartYOfRowPosition(0));
        assertEquals(70, this.rowReorderLayer.getStartYOfRowPosition(1));
        assertEquals(95, this.rowReorderLayer.getStartYOfRowPosition(2));
        assertEquals(135, this.rowReorderLayer.getStartYOfRowPosition(3));
        assertEquals(185, this.rowReorderLayer.getStartYOfRowPosition(4));
        assertEquals(225, this.rowReorderLayer.getStartYOfRowPosition(5));
        assertEquals(265, this.rowReorderLayer.getStartYOfRowPosition(6));
    }

    @Test
    public void shouldResetReordering() {
        this.rowReorderLayer.reorderRowPosition(0, 4);

        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(0));

        assertEquals(0, this.rowReorderLayer.getRowIndexByPosition(3));
        assertEquals(1, this.rowReorderLayer.getRowIndexByPosition(0));

        this.rowReorderLayer.resetReorder();

        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(0));

        assertEquals(3, this.rowReorderLayer.getRowIndexByPosition(3));
        assertEquals(0, this.rowReorderLayer.getRowIndexByPosition(0));
    }

    @Test
    public void shouldResetReorderingViaCommand() {
        this.rowReorderLayer.reorderRowPosition(0, 4);

        assertEquals(2, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(0));

        assertEquals(0, this.rowReorderLayer.getRowIndexByPosition(3));
        assertEquals(1, this.rowReorderLayer.getRowIndexByPosition(0));

        this.rowReorderLayer.doCommand(new ResetRowReorderCommand());

        assertEquals(3, this.rowReorderLayer.getRowPositionByIndex(3));
        assertEquals(0, this.rowReorderLayer.getRowPositionByIndex(0));

        assertEquals(3, this.rowReorderLayer.getRowIndexByPosition(3));
        assertEquals(0, this.rowReorderLayer.getRowIndexByPosition(0));
    }

}
