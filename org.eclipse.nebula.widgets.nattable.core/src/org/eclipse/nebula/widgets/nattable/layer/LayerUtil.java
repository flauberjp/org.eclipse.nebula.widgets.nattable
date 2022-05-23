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
 *     Dirk Fauth <dirk.fauth@googlemail.com> - Bug 462459
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.layer;

public final class LayerUtil {

    private LayerUtil() {
        // private default constructor for helper class
    }

    /**
     * Modifier that is used by layers if they add additional columns or rows.
     * By using this modifier layers can mark an added position to tell the
     * conversion that it can handle the negative index to position conversion.
     * For example, the HierarchicalTreeLayer adds structural columns to
     * separate levels, these columns do not have an index as they are added. As
     * some layers need the local column position, a conversion is performed
     * first getting the column index and then get back the local position by
     * index. Each added column is multiplied with this modifier and an
     * identifier so the returned index is greater than -1, to tell the
     * index-position transformation that calculation back to position is
     * possible, and via the identifier the concrete position in the
     * HierarchicalTreeLayer can be retrieved.
     *
     * @since 1.6
     */
    public static final int ADDITIONAL_POSITION_MODIFIER = -13;

    public static final int getColumnPositionByX(ILayer layer, int x) {
        int width = layer.getWidth();

        if (x < 0 || x >= width) {
            return -1;
        }

        return findColumnPosition(0, 0, layer, x, width, layer.getColumnCount());
    }

    protected static final int findColumnPosition(
            int xOffset, int columnOffset, ILayer layer, int x, int totalWidth, int columnCount) {

        if (columnOffset == columnCount) {
            return columnCount;
        }

        double size = (double) (totalWidth - xOffset) / (columnCount - columnOffset);
        int columnPosition = columnOffset + (int) ((x - xOffset) / size);

        int startX = layer.getStartXOfColumnPosition(columnPosition);
        int endX = startX + layer.getColumnWidthByPosition(columnPosition);
        if (x < startX) {
            if (startX == totalWidth && startX != endX) {
                return columnCount;
            }
            return findColumnPosition(xOffset, columnOffset, layer, x, startX, columnPosition);
        } else if (x >= endX) {
            return findColumnPosition(endX, columnPosition + 1, layer, x, totalWidth, columnCount);
        } else {
            return columnPosition;
        }
    }

    public static final int getRowPositionByY(ILayer layer, int y) {
        int height = layer.getHeight();

        if (y < 0 || y >= height) {
            return -1;
        }

        return findRowPosition(0, 0, layer, y, height, layer.getRowCount());
    }

    protected static final int findRowPosition(
            int yOffset, int rowOffset, ILayer layer, int y, int totalHeight, int rowCount) {

        if (rowOffset == rowCount) {
            return rowCount;
        }

        double size = (double) (totalHeight - yOffset) / (rowCount - rowOffset);
        int rowPosition = rowOffset + (int) ((y - yOffset) / size);

        int startY = layer.getStartYOfRowPosition(rowPosition);
        int endY = startY + layer.getRowHeightByPosition(rowPosition);
        if (y < startY) {
            if (startY == totalHeight && startY != endY) {
                return rowCount;
            }
            return findRowPosition(yOffset, rowOffset, layer, y, startY, rowPosition);
        } else if (y >= endY) {
            return findRowPosition(endY, rowPosition + 1, layer, y, totalHeight, rowCount);
        } else {
            return rowPosition;
        }
    }

    /**
     * Convert column position from the source layer to the target layer
     *
     * @param sourceLayer
     *            source layer
     * @param sourceColumnPosition
     *            column position in the source layer
     * @param targetLayer
     *            layer to convert the from position to
     * @return converted column position, or -1 if conversion not possible
     */
    public static final int convertColumnPosition(
            ILayer sourceLayer, int sourceColumnPosition, IUniqueIndexLayer targetLayer) {

        if (targetLayer == sourceLayer) {
            return sourceColumnPosition;
        }
        int columnIndex = sourceLayer.getColumnIndexByPosition(sourceColumnPosition);
        if (columnIndex < 0 && (columnIndex % ADDITIONAL_POSITION_MODIFIER != 0)) {
            // if the negative index is multiplied with the additional position
            // modifier we assume that the back conversion is possible
            return -1;
        }
        return targetLayer.getColumnPositionByIndex(columnIndex);
    }

    /**
     * Convert row position from the source layer to the target layer
     *
     * @param sourceLayer
     *            source layer
     * @param sourceRowPosition
     *            position in the source layer
     * @param targetLayer
     *            layer to convert the from position to
     * @return converted row position, or -1 if conversion not possible
     */
    public static final int convertRowPosition(
            ILayer sourceLayer, int sourceRowPosition, IUniqueIndexLayer targetLayer) {

        if (targetLayer == sourceLayer) {
            return sourceRowPosition;
        }
        int rowIndex = sourceLayer.getRowIndexByPosition(sourceRowPosition);
        if (rowIndex < 0 && (rowIndex % ADDITIONAL_POSITION_MODIFIER != 0)) {
            // if the negative index is multiplied with the additional position
            // modifier we assume that the back conversion is possible
            return -1;
        }
        return targetLayer.getRowPositionByIndex(rowIndex);
    }

}
