/*******************************************************************************
 * Copyright (c) 2012, 2020 Original authors and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.resize;

import static org.junit.Assert.assertEquals;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.test.fixture.command.AutoResizeColumnCommandFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.command.AutoResizeRowCommandFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.CellFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.DataLayerFixture;
import org.eclipse.nebula.widgets.nattable.util.GCFactory;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;

public class MaxCellBoundsHelperTest {

    @Test
    public void shouldCalculatePreferredColumnWidths() {
        DataLayerFixture dataLayer = new DataLayerFixture(2, 3, 10, 10);
        IDataProvider dataProvider = dataLayer.getDataProvider();

        // Col 0
        dataProvider.setDataValue(0, 0, "Long");
        dataProvider.setDataValue(0, 1, "Longer");
        dataProvider.setDataValue(0, 2, "Longest Text");

        // Col 1
        dataProvider.setDataValue(1, 0, "Elephant");
        dataProvider.setDataValue(1, 1, "Cat");
        dataProvider.setDataValue(1, 2, "Rat");

        AutoResizeColumnCommandFixture command = new AutoResizeColumnCommandFixture();
        GCFactory gcFactory = command.getGCFactory();
        IConfigRegistry registry = command.getConfigRegistry();
        GC gc = gcFactory.createGC();
        int col0MaxTextWidth = new TextPainter().getPreferredWidth(new CellFixture("Longest Text"), gc, registry);
        int col1MaxTextWidth = new TextPainter().getPreferredWidth(new CellFixture("Elephant"), gc, registry);
        gc.dispose();

        int[] maxColumnWidths = MaxCellBoundsHelper.getPreferredColumnWidths(registry, gcFactory, dataLayer, new int[] { 0, 1 });

        // Adjust widths
        int col0AdjustedMaxWidth = dataLayer.getLayerPainter().adjustCellBounds(0, 0, new Rectangle(0, 0, maxColumnWidths[0], 10)).width;
        int col1AdjustedMaxWidth = dataLayer.getLayerPainter().adjustCellBounds(1, 0, new Rectangle(0, 0, maxColumnWidths[1], 10)).width;

        assertEquals(col0MaxTextWidth, col0AdjustedMaxWidth);
        assertEquals(col1MaxTextWidth, col1AdjustedMaxWidth);
    }

    @Test
    public void shouldHandleColumnPositionOutsideScope() {
        DataLayerFixture dataLayer = new DataLayerFixture(2, 3, 10, 10);
        IDataProvider dataProvider = dataLayer.getDataProvider();

        // Col 0
        dataProvider.setDataValue(0, 0, "Longest Text");
        dataProvider.setDataValue(0, 1, "Longer");
        dataProvider.setDataValue(0, 2, "Long");

        // Col 1
        dataProvider.setDataValue(1, 0, "Elephant");
        dataProvider.setDataValue(1, 1, "Cat");
        dataProvider.setDataValue(1, 2, "Rat");

        AutoResizeColumnCommandFixture command = new AutoResizeColumnCommandFixture();
        GCFactory gcFactory = command.getGCFactory();
        IConfigRegistry registry = command.getConfigRegistry();
        GC gc = gcFactory.createGC();
        int col0MaxTextWidth = new TextPainter().getPreferredWidth(new CellFixture("Longest Text"), gc, registry);
        int col1MaxTextWidth = new TextPainter().getPreferredWidth(new CellFixture("Elephant"), gc, registry);
        gc.dispose();

        int[] maxColumnWidths = MaxCellBoundsHelper.getPreferredColumnWidths(registry, gcFactory, dataLayer, new int[] { 0, 1, 2 });

        // Adjust widths
        int col0AdjustedMaxWidth = dataLayer.getLayerPainter().adjustCellBounds(0, 0, new Rectangle(0, 0, maxColumnWidths[0], 10)).width;
        int col1AdjustedMaxWidth = dataLayer.getLayerPainter().adjustCellBounds(1, 0, new Rectangle(0, 0, maxColumnWidths[1], 10)).width;

        assertEquals(col0MaxTextWidth, col0AdjustedMaxWidth);
        assertEquals(col1MaxTextWidth, col1AdjustedMaxWidth);
        assertEquals(-1, maxColumnWidths[2]);
    }

    @Test
    public void shouldCalculatePreferredRowHeights() {
        DataLayerFixture dataLayer = new DataLayerFixture(3, 2, 10, 10);
        IDataProvider dataProvider = dataLayer.getDataProvider();

        // Row 0
        dataProvider.setDataValue(0, 0, "..");
        dataProvider.setDataValue(1, 0, "...");
        dataProvider.setDataValue(2, 0, "...");

        // Row 1
        dataProvider.setDataValue(0, 1, "Elephant\nNashorn");
        dataProvider.setDataValue(1, 1, "Cat");
        dataProvider.setDataValue(2, 1, "Rat");

        AutoResizeRowCommandFixture command = new AutoResizeRowCommandFixture();
        GCFactory gcFactory = command.getGCFactory();
        IConfigRegistry registry = command.getConfigRegistry();
        GC gc = gcFactory.createGC();
        int row0MaxTextHeight = new TextPainter().getPreferredHeight(new CellFixture(".."), gc, registry);
        int row1MaxTextHeight = new TextPainter(false, true, false, true).getPreferredHeight(new CellFixture("Elephant\nNashorn"), gc, registry);
        gc.dispose();

        int[] maxRowHeights = MaxCellBoundsHelper.getPreferredRowHeights(registry, gcFactory, dataLayer, new int[] { 0, 1 });

        // Adjust heights
        int row0AdjustedMaxHeight = dataLayer.getLayerPainter().adjustCellBounds(0, 0, new Rectangle(0, 0, 10, maxRowHeights[0])).height;
        int row1AdjustedMaxHeight = dataLayer.getLayerPainter().adjustCellBounds(0, 1, new Rectangle(0, 0, 10, maxRowHeights[1])).height;

        assertEquals(row0MaxTextHeight, row0AdjustedMaxHeight);
        assertEquals(row1MaxTextHeight, row1AdjustedMaxHeight);
    }

    @Test
    public void shouldHandleRowPositionOutsideScope() {
        DataLayerFixture dataLayer = new DataLayerFixture(3, 2, 10, 10);
        IDataProvider dataProvider = dataLayer.getDataProvider();

        // Row 0
        dataProvider.setDataValue(0, 0, "..");
        dataProvider.setDataValue(1, 0, "...");
        dataProvider.setDataValue(2, 0, "...");

        // Row 1
        dataProvider.setDataValue(0, 1, "Elephant\nNashorn");
        dataProvider.setDataValue(1, 1, "Cat\nDog\nMouse");
        dataProvider.setDataValue(2, 1, "Rat");

        AutoResizeRowCommandFixture command = new AutoResizeRowCommandFixture();
        GCFactory gcFactory = command.getGCFactory();
        IConfigRegistry registry = command.getConfigRegistry();
        GC gc = gcFactory.createGC();
        int row0MaxTextHeight = new TextPainter().getPreferredHeight(new CellFixture(".."), gc, registry);
        int row1MaxTextHeight = new TextPainter(false, true, false, true).getPreferredHeight(new CellFixture("Cat\nDog\nMouse"), gc, registry);
        gc.dispose();

        int[] maxRowHeights = MaxCellBoundsHelper.getPreferredRowHeights(registry, gcFactory, dataLayer, new int[] { 0, 1, 2 });

        // Adjust heights
        int row0AdjustedMaxHeight = dataLayer.getLayerPainter().adjustCellBounds(0, 0, new Rectangle(0, 0, 10, maxRowHeights[0])).height;
        int row1AdjustedMaxHeight = dataLayer.getLayerPainter().adjustCellBounds(0, 1, new Rectangle(0, 0, 10, maxRowHeights[1])).height;

        assertEquals(row0MaxTextHeight, row0AdjustedMaxHeight);
        assertEquals(row1MaxTextHeight, row1AdjustedMaxHeight);
        assertEquals(-1, maxRowHeights[2]);
    }

}
