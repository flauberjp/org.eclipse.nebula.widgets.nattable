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
package org.eclipse.nebula.widgets.nattable.layer.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Event indicating a change in the structure of the rows. This event carried
 * RowDiffs (Collection&lt;StructuralDiff&gt;) indicating the rows which have
 * changed.
 */
public abstract class RowStructuralChangeEvent extends RowVisualChangeEvent implements IStructuralChangeEvent {

    /**
     * Creates a new RowStructuralChangeEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given row positions match.
     * @param rowPositionRanges
     *            The row position ranges for the rows that have changed.
     */
    public RowStructuralChangeEvent(ILayer layer, Range... rowPositionRanges) {
        this(layer, Arrays.asList(rowPositionRanges));
    }

    /**
     * Creates a new RowStructuralChangeEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given column positions match.
     * @param rowPositionRanges
     *            The row position ranges for the rows that have changed.
     */
    public RowStructuralChangeEvent(ILayer layer, Collection<Range> rowPositionRanges) {
        super(layer, rowPositionRanges);
    }

    /**
     * Creates a new RowStructuralChangeEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given row positions match.
     * @param rowPositionRanges
     *            The row position ranges for the rows that have changed.
     * @param rowIndexes
     *            The indexes of the rows that have changed.
     *
     * @since 1.6
     */
    public RowStructuralChangeEvent(ILayer layer, Collection<Range> rowPositionRanges, Collection<Integer> rowIndexes) {
        super(layer, rowPositionRanges, rowIndexes);
    }

    /**
     * Creates a new RowStructuralChangeEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given row positions match.
     * @param rowPositionRanges
     *            The row position ranges for the rows that have changed.
     * @param rowIndexes
     *            The indexes of the rows that have changed.
     *
     * @since 2.0
     */
    public RowStructuralChangeEvent(ILayer layer, Collection<Range> rowPositionRanges, int... rowIndexes) {
        super(layer, rowPositionRanges, rowIndexes);
    }

    /**
     * Creates a new RowStructuralChangeEvent based on the given instance.
     * Mainly needed for cloning.
     *
     * @param event
     *            The RowStructuralChangeEvent out of which the new instance
     *            should be created.
     */
    protected RowStructuralChangeEvent(RowStructuralChangeEvent event) {
        super(event);
    }

    @Override
    public Collection<Rectangle> getChangedPositionRectangles() {
        Collection<Rectangle> changedPositionRectangles = new ArrayList<>();

        Collection<Range> ranges = getRowPositionRanges();
        if (ranges != null && !ranges.isEmpty()) {
            int topmostColumnPosition = Integer.MAX_VALUE;
            for (Range range : ranges) {
                if (range.start < topmostColumnPosition) {
                    topmostColumnPosition = range.start;
                }
            }

            int columnCount = getLayer().getColumnCount();
            int rowCount = getLayer().getRowCount();
            changedPositionRectangles.add(new Rectangle(
                    0,
                    topmostColumnPosition,
                    columnCount,
                    rowCount - topmostColumnPosition));
        }

        return changedPositionRectangles;
    }

    @Override
    public boolean isHorizontalStructureChanged() {
        return false;
    }

    @Override
    public boolean isVerticalStructureChanged() {
        return true;
    }

    @Override
    public Collection<StructuralDiff> getColumnDiffs() {
        return null;
    }

}
