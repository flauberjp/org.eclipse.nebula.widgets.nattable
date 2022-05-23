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
package org.eclipse.nebula.widgets.nattable.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.swt.graphics.Rectangle;

public class LayerAssert {

    public static void assertLayerEquals(ILayer expectedLayer, ILayer actualLayer) {
        // Horizontal features

        // Columns

        int expectedColumnCount = expectedLayer.getColumnCount();
        int actualColumnCount = actualLayer.getColumnCount();
        assertEquals("Column count", expectedColumnCount,
                actualColumnCount);

        // Assert.assertEquals("Preferred column count",
        // expectedLayer.getPreferredColumnCount(),
        // actualLayer.getPreferredColumnCount());

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            int expectedColumnIndexByPosition = expectedLayer
                    .getColumnIndexByPosition(columnPosition);
            int actualColumnIndexByPosition = actualLayer
                    .getColumnIndexByPosition(columnPosition);
            assertEquals("Column index by position (" + columnPosition
                    + ")", expectedColumnIndexByPosition,
                    actualColumnIndexByPosition);
        }

        for (int localColumnPosition = 0; localColumnPosition < expectedColumnCount; localColumnPosition++) {
            int expectedLocalToUnderlyingColumnPosition = expectedLayer
                    .localToUnderlyingColumnPosition(localColumnPosition);
            int actualLocalToUnderlyingColumnPosition = actualLayer
                    .localToUnderlyingColumnPosition(localColumnPosition);
            assertEquals("Local to underlying column position ("
                    + localColumnPosition + ")",
                    expectedLocalToUnderlyingColumnPosition,
                    actualLocalToUnderlyingColumnPosition);
        }

        // for (int underlyingColumnPosition = 0; underlyingColumnPosition <
        // expectedLayer.getColumnCount(); underlyingColumnPosition++) {
        // Assert.assertEquals("Underlying to local column position (" +
        // underlyingColumnPosition + ")",
        // expectedLayer.underlyingToLocalColumnPosition(null,
        // underlyingColumnPosition),
        // actualLayer.underlyingToLocalColumnPosition(null,
        // underlyingColumnPosition));
        // }

        // Width

        int expectedWidth = expectedLayer.getWidth();
        int actualWidth = actualLayer.getWidth();
        assertEquals("Width", expectedWidth, actualWidth);

        // Assert.assertEquals("Preferred width",
        // expectedLayer.getPreferredWidth(), actualLayer.getPreferredWidth());

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            int expectedColumnWidthByPosition = expectedLayer
                    .getColumnWidthByPosition(columnPosition);
            int actualColumnWidthByPosition = actualLayer
                    .getColumnWidthByPosition(columnPosition);
            assertEquals("Column width by position (" + columnPosition
                    + ")", expectedColumnWidthByPosition,
                    actualColumnWidthByPosition);
        }

        // Column resize

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            boolean expectedColumnPositionResizable = expectedLayer
                    .isColumnPositionResizable(columnPosition);
            boolean actualColumnPositionResizable = actualLayer
                    .isColumnPositionResizable(columnPosition);
            assertEquals("Column position resizable (" + columnPosition
                    + ")", expectedColumnPositionResizable,
                    actualColumnPositionResizable);
        }

        // X

        for (int x = 0; x < expectedWidth; x++) {
            int expectedColumnPositionByX = expectedLayer
                    .getColumnPositionByX(x);
            int actualColumnPositionByX = actualLayer.getColumnPositionByX(x);
            assertEquals("Column position by X (" + x + ")",
                    expectedColumnPositionByX, actualColumnPositionByX);
        }

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            int expectedStartXOfColumnPosition = expectedLayer
                    .getStartXOfColumnPosition(columnPosition);
            int actualStartXOfColumnPosition = actualLayer
                    .getStartXOfColumnPosition(columnPosition);
            assertEquals("Start X of column position (" + columnPosition
                    + ")", expectedStartXOfColumnPosition,
                    actualStartXOfColumnPosition);
        }

        // Vertical features

        // Rows

        int expectedRowCount = expectedLayer.getRowCount();
        int actualRowCount = actualLayer.getRowCount();
        assertEquals("Row count", expectedRowCount, actualRowCount);

        // Assert.assertEquals("Preferred row count",
        // expectedLayer.getPreferredRowCount(),
        // actualLayer.getPreferredRowCount());

        for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
            int expectedRowIndexByPosition = expectedLayer
                    .getRowIndexByPosition(rowPosition);
            int actualRowIndexByPosition = actualLayer
                    .getRowIndexByPosition(rowPosition);
            assertEquals("Row index by position (" + rowPosition + ")",
                    expectedRowIndexByPosition, actualRowIndexByPosition);
        }

        for (int localRowPosition = 0; localRowPosition < expectedRowCount; localRowPosition++) {
            int expectedLocalToUnderlyingRowPosition = expectedLayer
                    .localToUnderlyingRowPosition(localRowPosition);
            int actualLocalToUnderlyingRowPosition = actualLayer
                    .localToUnderlyingRowPosition(localRowPosition);
            assertEquals("Local to underlying row position ("
                    + localRowPosition + ")",
                    expectedLocalToUnderlyingRowPosition,
                    actualLocalToUnderlyingRowPosition);
        }

        // for (int underlyingRowPosition = 0; underlyingRowPosition <
        // expectedLayer.getRowCount(); underlyingRowPosition++) {
        // Assert.assertEquals("Underlying to local row position (" +
        // underlyingRowPosition + ")",
        // expectedLayer.underlyingToLocalRowPosition(null,
        // underlyingRowPosition),
        // actualLayer.underlyingToLocalRowPosition(null,
        // underlyingRowPosition));
        // }

        // Height

        int expectedHeight = expectedLayer.getHeight();
        int actualHeight = actualLayer.getHeight();
        assertEquals("Height", expectedHeight, actualHeight);

        // Assert.assertEquals("Preferred height",
        // expectedLayer.getPreferredHeight(),
        // actualLayer.getPreferredHeight());

        for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
            int expectedRowHeightByPosition = expectedLayer
                    .getRowHeightByPosition(rowPosition);
            int actualRowHeightByPosition = actualLayer
                    .getRowHeightByPosition(rowPosition);
            assertEquals("Row height by position (" + rowPosition + ")",
                    expectedRowHeightByPosition, actualRowHeightByPosition);
        }

        // Row resize

        for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
            boolean expectedRowPositionResizable = expectedLayer
                    .isRowPositionResizable(rowPosition);
            boolean actualRowPositionResizable = actualLayer
                    .isRowPositionResizable(rowPosition);
            assertEquals("Row position resizable (" + rowPosition + ")",
                    expectedRowPositionResizable, actualRowPositionResizable);
        }

        // Y

        for (int y = 0; y < expectedHeight; y++) {
            int expectedRowPositionByY = expectedLayer.getRowPositionByY(y);
            int actualRowPositionByY = actualLayer.getRowPositionByY(y);
            assertEquals("Row position by Y (" + y + ")",
                    expectedRowPositionByY, actualRowPositionByY);
        }

        for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
            int expectedStartYOfRowPosition = expectedLayer
                    .getStartYOfRowPosition(rowPosition);
            int actualStartYOfRowPosition = actualLayer
                    .getStartYOfRowPosition(rowPosition);
            assertEquals(
                    "Start Y of row position (" + rowPosition + ")",
                    expectedStartYOfRowPosition, actualStartYOfRowPosition);
        }

        // Cell features

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
                ILayerCell expectedCellByPosition = expectedLayer
                        .getCellByPosition(columnPosition, rowPosition);
                ILayerCell actualCellByPosition = actualLayer
                        .getCellByPosition(columnPosition, rowPosition);
                assertEquals("Cell by position (" + columnPosition
                        + ", " + rowPosition + ")", expectedCellByPosition,
                        actualCellByPosition);
            }
        }

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
                Rectangle expectedBoundsByPosition = expectedLayer
                        .getBoundsByPosition(columnPosition, rowPosition);
                Rectangle actualBoundsByPosition = actualLayer
                        .getBoundsByPosition(columnPosition, rowPosition);
                assertEquals("Bounds by position (" + columnPosition
                        + ", " + rowPosition + ")", expectedBoundsByPosition,
                        actualBoundsByPosition);
            }
        }

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
                DisplayMode expectedDisplayModeByPosition = expectedLayer
                        .getDisplayModeByPosition(columnPosition, rowPosition);
                DisplayMode actualDisplayModeByPosition = actualLayer
                        .getDisplayModeByPosition(columnPosition, rowPosition);
                assertEquals("Display mode by position ("
                        + columnPosition + ", " + rowPosition + ")",
                        expectedDisplayModeByPosition,
                        actualDisplayModeByPosition);
            }
        }

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
                LabelStack expectedConfigLabelsByPosition = expectedLayer
                        .getConfigLabelsByPosition(columnPosition, rowPosition);
                LabelStack actualConfigLabelsByPosition = actualLayer
                        .getConfigLabelsByPosition(columnPosition, rowPosition);
                assertEquals("Config labels by position ("
                        + columnPosition + ", " + rowPosition + ")",
                        expectedConfigLabelsByPosition,
                        actualConfigLabelsByPosition);
            }
        }

        for (int columnPosition = 0; columnPosition < expectedColumnCount; columnPosition++) {
            for (int rowPosition = 0; rowPosition < expectedRowCount; rowPosition++) {
                Object expectedDataValueByPosition = expectedLayer
                        .getDataValueByPosition(columnPosition, rowPosition);
                Object actualDataValueByPosition = actualLayer
                        .getDataValueByPosition(columnPosition, rowPosition);
                assertEquals("Data value by position (" + columnPosition
                        + ", " + rowPosition + ")",
                        expectedDataValueByPosition, actualDataValueByPosition);
            }
        }
    }

}
