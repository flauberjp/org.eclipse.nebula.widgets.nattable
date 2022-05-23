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
 *     Vincent Lorenzo <vincent.lorenzo@cea.fr> - Bug 478622
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.selection.event;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.coordinate.PositionUtil;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.RowVisualChangeEvent;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;

public class RowSelectionEvent extends RowVisualChangeEvent implements ISelectionEvent {

    private final SelectionLayer selectionLayer;
    private int rowPositionToMoveIntoViewport;

    /**
     * The shift mask used.
     */
    private boolean withShiftMask = false;

    /**
     * The control mask used.
     */
    private boolean withControlMask = false;

    /**
     * Constructor.
     *
     * @param selectionLayer
     *            The selection layer.
     * @param rowPositions
     *            The positions of the rows.
     * @param rowPositionToMoveIntoViewport
     *            The row position to move into the viewport.
     * @param withShiftMask
     *            Boolean to determinate if the shift mask is used.
     * @param withControlMask
     *            Boolean to determinate if the control mask is used.
     * @since 1.4
     */
    public RowSelectionEvent(SelectionLayer selectionLayer,
            Collection<Integer> rowPositions, int rowPositionToMoveIntoViewport,
            boolean withShiftMask, boolean withControlMask) {
        super(selectionLayer, PositionUtil.getRanges(rowPositions));
        this.selectionLayer = selectionLayer;
        this.rowPositionToMoveIntoViewport = rowPositionToMoveIntoViewport;
        this.withShiftMask = withShiftMask;
        this.withControlMask = withControlMask;
    }

    /**
     * Constructor.
     *
     * @param selectionLayer
     *            The selection layer.
     * @param rowPositions
     *            The positions of the rows.
     * @param rowPositionToMoveIntoViewport
     *            The row position to move into the viewport.
     * @param withShiftMask
     *            Boolean to determinate if the shift mask is used.
     * @param withControlMask
     *            Boolean to determinate if the control mask is used.
     * @since 2.0
     */
    public RowSelectionEvent(SelectionLayer selectionLayer,
            int[] rowPositions, int rowPositionToMoveIntoViewport,
            boolean withShiftMask, boolean withControlMask) {
        super(selectionLayer, PositionUtil.getRanges(rowPositions));
        this.selectionLayer = selectionLayer;
        this.rowPositionToMoveIntoViewport = rowPositionToMoveIntoViewport;
        this.withShiftMask = withShiftMask;
        this.withControlMask = withControlMask;
    }

    /**
     * Constructor.
     *
     * @param selectionLayer
     *            The selection layer.
     * @param rowPositionRange
     *            The range of row positions that where selected.
     * @param rowPositionToMoveIntoViewport
     *            The row position to move into the viewport.
     * @param withShiftMask
     *            Boolean to determinate if the shift mask is used.
     * @param withControlMask
     *            Boolean to determinate if the control mask is used.
     * @since 1.6
     */
    public RowSelectionEvent(SelectionLayer selectionLayer,
            Range rowPositionRange, int rowPositionToMoveIntoViewport,
            boolean withShiftMask, boolean withControlMask) {
        super(selectionLayer, Arrays.asList(rowPositionRange));
        this.selectionLayer = selectionLayer;
        this.rowPositionToMoveIntoViewport = rowPositionToMoveIntoViewport;
        this.withShiftMask = withShiftMask;
        this.withControlMask = withControlMask;
    }

    // Copy constructor
    protected RowSelectionEvent(RowSelectionEvent event) {
        super(event);
        this.selectionLayer = event.selectionLayer;
        this.rowPositionToMoveIntoViewport = event.rowPositionToMoveIntoViewport;
        this.withShiftMask = event.withShiftMask;
        this.withControlMask = event.withControlMask;
    }

    @Override
    public SelectionLayer getSelectionLayer() {
        return this.selectionLayer;
    }

    public int getRowPositionToMoveIntoViewport() {
        return this.rowPositionToMoveIntoViewport;
    }

    /**
     * Returns if the shift mask is used.
     *
     * @return <code>true</code> if the shift mask is used, <code>false</code>
     *         otherwise.
     *
     * @since 1.4
     */
    public boolean isWithShiftMask() {
        return this.withShiftMask;
    }

    /**
     * Returns if the control mask is used.
     *
     * @return <code>true</code> if the control mask is used, <code>false</code>
     *         otherwise.
     *
     * @since 1.4
     */
    public boolean isWithControlMask() {
        return this.withControlMask;
    }

    @Override
    public boolean convertToLocal(ILayer localLayer) {
        this.rowPositionToMoveIntoViewport = localLayer.underlyingToLocalRowPosition(
                getLayer(), this.rowPositionToMoveIntoViewport);

        return super.convertToLocal(localLayer);
    }

    @Override
    public RowSelectionEvent cloneEvent() {
        return new RowSelectionEvent(this);
    }

}
