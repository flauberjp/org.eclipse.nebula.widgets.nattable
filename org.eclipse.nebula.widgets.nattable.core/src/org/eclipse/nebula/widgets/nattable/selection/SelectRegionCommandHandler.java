/*******************************************************************************
 * Copyright (c) 2018, 2020 Dirk Fauth.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.selection;

import java.util.Arrays;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinateComparator;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRegionCommand;
import org.eclipse.nebula.widgets.nattable.selection.event.RowSelectionEvent;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Command handler for the {@link SelectRegionCommand}.
 *
 * @since 1.6
 */
public class SelectRegionCommandHandler implements ILayerCommandHandler<SelectRegionCommand> {

    protected final SelectionLayer selectionLayer;

    /**
     *
     * @param selectionLayer
     *            The {@link SelectionLayer} on which this handler should
     *            operate.
     */
    public SelectRegionCommandHandler(SelectionLayer selectionLayer) {
        this.selectionLayer = selectionLayer;
    }

    @Override
    public boolean doCommand(ILayer targetLayer, SelectRegionCommand command) {
        if (command.convertToTargetLayer(this.selectionLayer)) {
            selectRegion(command.getRegion(), command.isShiftMask(), command.isControlMask(), command.getAnchorColumnPosition(), command.getAnchorRowPosition());
            return true;
        }
        return false;
    }

    protected void selectRegion(Rectangle region, boolean withShiftMask, boolean withControlMask, int anchorColumn, int anchorRow) {
        Range changedRows = null;

        if (SelectionUtils.noShiftOrControl(withShiftMask, withControlMask)) {
            // no modifier
            this.selectionLayer.clear(false);
            this.selectionLayer.selectCell(region.x, region.y, false, false);
            this.selectionLayer.selectRegion(region.x, region.y, region.width, region.height);
            this.selectionLayer.moveSelectionAnchor(
                    anchorColumn < 0 ? region.x : anchorColumn,
                    anchorRow < 0 ? region.y : anchorRow);

            changedRows = new Range(region.y, (region.height < Integer.MAX_VALUE) ? region.y + region.height : this.selectionLayer.getRowCount() - region.y);
        } else if (SelectionUtils.bothShiftAndControl(withShiftMask, withControlMask)
                || SelectionUtils.isShiftOnly(withShiftMask, withControlMask)) {
            // SHIFT or CTRL + SHIFT modifier enabled
            changedRows = selectRegionWithShiftKey(region, anchorColumn, anchorRow);
        } else if (SelectionUtils.isControlOnly(withShiftMask, withControlMask)) {
            // CTRL modifier enabled
            changedRows = selectRegionWithCtrlKey(region, anchorColumn, anchorRow);
        }

        // Set last selected position to the recently clicked cell
        this.selectionLayer.setLastSelectedCell(region.x, region.y);

        this.selectionLayer.fireLayerEvent(
                new RowSelectionEvent(
                        this.selectionLayer,
                        changedRows,
                        // -1 to avoid scrolling to the selection anchor
                        // position
                        -1,
                        withShiftMask,
                        withControlMask));
    }

    /**
     * Selects a region with SHIFT modifier enabled. That means the selection
     * range is calculated based on the current selection anchor and the corner
     * of the given region that is most away from the anchor.
     *
     * @param region
     *            The region to be selected.
     * @param anchorColumn
     *            The column position to which the selection anchor should be
     *            moved to or -1 if the calculated anchor column position should
     *            be used.
     * @param anchorRow
     *            The row position to which the selection anchor should be moved
     *            to or -1 if the calculated anchor row position should be used.
     * @return The row positions that have gained selection.
     */
    protected Range selectRegionWithShiftKey(Rectangle region, int anchorColumn, int anchorRow) {
        int startCol = region.x;
        int startRow = region.y;
        int noCol = region.width;
        int noRow = region.height;

        // This method selects the range based on the selection anchor and the
        // clicked position. Therefore the selection prior adding the newly
        // calculated selection needs to be cleared in advance.
        Rectangle lastSelectedRegion = this.selectionLayer.getLastSelectedRegion();
        if (lastSelectedRegion != null) {
            this.selectionLayer.getSelectionModel().clearSelection(lastSelectedRegion);
        } else {
            this.selectionLayer.getSelectionModel().clearSelection();
        }

        PositionCoordinate anchor = this.selectionLayer.getSelectionAnchor();
        if (anchor.columnPosition != SelectionLayer.NO_SELECTION
                && anchor.rowPosition != SelectionLayer.NO_SELECTION) {

            // if the region.width is Integer.MAX_VALUE we do not calculate
            if (region.width < Integer.MAX_VALUE) {
                if (startCol < anchor.columnPosition) {
                    noCol = Math.abs(anchor.columnPosition - startCol) + 1;
                } else {
                    startCol = anchor.columnPosition;
                    noCol = (region.x + region.width) - anchor.columnPosition;
                }
            }

            // if the region.height is Integer.MAX_VALUE we do not calculate
            if (region.height < Integer.MAX_VALUE) {
                if (startRow < anchor.rowPosition) {
                    noRow = Math.abs(anchor.rowPosition - startRow) + 1;
                } else {
                    startRow = anchor.rowPosition;
                    noRow = (region.y + region.height) - anchor.rowPosition;
                }
            }
        } else {
            // if there is no last selected region we need to set the anchor
            // for correct behavior on further actions
            this.selectionLayer.moveSelectionAnchor(
                    anchorColumn < 0 ? startCol : anchorColumn,
                    anchorRow < 0 ? startRow : anchorRow);
        }

        this.selectionLayer.selectRegion(startCol, startRow, noCol, noRow);

        return new Range(startRow, (noRow < Integer.MAX_VALUE) ? startRow + noRow : this.selectionLayer.getRowCount() - startRow);
    }

    /**
     * Selects a region with CTRL modifier enabled. That means the current
     * selection is extended by the given region.
     *
     * @param region
     *            The region to be selected.
     * @param anchorColumn
     *            The column position to which the selection anchor should be
     *            moved to or -1 if the calculated anchor column position should
     *            be used.
     * @param anchorRow
     *            The row position to which the selection anchor should be moved
     *            to or -1 if the calculated anchor row position should be used.
     * @return The row positions that have gained selection.
     */
    protected Range selectRegionWithCtrlKey(Rectangle region, int anchorColumn, int anchorRow) {
        if (this.selectionLayer.allCellsSelectedInRegion(region)) {
            // clear if all cells in the region are selected
            this.selectionLayer.clearSelection(region);
            this.selectionLayer.setLastSelectedRegion(null);

            // update anchor
            PositionCoordinate[] selectedCells = this.selectionLayer.getSelectedCellPositions();
            if (selectedCells.length > 0
                    && this.selectionLayer.getSelectionAnchor().columnPosition == SelectionLayer.NO_SELECTION
                    && this.selectionLayer.getSelectionAnchor().rowPosition == SelectionLayer.NO_SELECTION) {

                // determine column to move the anchor to
                // for this we sort the coordinates in a deterministic way
                Arrays.sort(selectedCells, new PositionCoordinateComparator());

                // if another cell in the region.x column is selected, only
                // search for a new anchor in that column
                PositionCoordinate toPos = null;
                if (this.selectionLayer.isColumnPositionSelected(region.x)) {
                    for (int i = 0; i < selectedCells.length; i++) {
                        if (selectedCells[i].rowPosition < region.y
                                && selectedCells[i].columnPosition == region.x) {
                            toPos = selectedCells[i];
                        } else {
                            break;
                        }
                    }
                }

                if (toPos == null && this.selectionLayer.isRowPositionSelected(region.y)) {
                    for (int i = 0; i < selectedCells.length; i++) {
                        if (selectedCells[i].rowPosition == region.y
                                && selectedCells[i].columnPosition < region.x) {
                            toPos = selectedCells[i];
                        } else {
                            break;
                        }
                    }
                }

                // search for another selected cell as new anchor if there is
                // none in the same column
                if (toPos == null) {
                    toPos = selectedCells[0];
                    for (int i = 0; i < selectedCells.length; i++) {
                        if (selectedCells[i].rowPosition < region.y
                                || selectedCells[i].columnPosition < region.x) {
                            toPos = selectedCells[i];
                        } else {
                            break;
                        }
                    }
                }

                this.selectionLayer.moveSelectionAnchor(
                        anchorColumn < 0 ? toPos.columnPosition : anchorColumn,
                        anchorRow < 0 ? toPos.rowPosition : anchorRow);
            }
        } else {
            // if none or at least one cell in the region is already
            // selected, simply add
            this.selectionLayer.selectRegion(region.x, region.y, region.width, region.height);
            this.selectionLayer.moveSelectionAnchor(
                    anchorColumn < 0 ? region.x : anchorColumn,
                    anchorRow < 0 ? region.y : anchorRow);
        }

        return new Range(region.y, (region.height < Integer.MAX_VALUE) ? region.y + region.height : this.selectionLayer.getRowCount() - region.y);
    }

    @Override
    public Class<SelectRegionCommand> getCommandClass() {
        return SelectRegionCommand.class;
    }

}
