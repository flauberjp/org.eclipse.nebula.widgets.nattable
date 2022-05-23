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
package org.eclipse.nebula.widgets.nattable.hideshow.event;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.coordinate.PositionUtil;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.ColumnStructuralChangeEvent;
import org.eclipse.nebula.widgets.nattable.layer.event.StructuralDiff;
import org.eclipse.nebula.widgets.nattable.layer.event.StructuralDiff.DiffTypeEnum;

public class HideColumnPositionsEvent extends ColumnStructuralChangeEvent {

    /**
     * Creates a new HideColumnPositionsEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given column positions match.
     * @param columnPositions
     *            The positions of the columns that have changed.
     */
    public HideColumnPositionsEvent(ILayer layer, Collection<Integer> columnPositions) {
        super(layer, PositionUtil.getRanges(columnPositions));
    }

    /**
     * Creates a new HideColumnPositionsEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given column positions match.
     * @param columnPositions
     *            The positions of the columns that have changed.
     * @since 2.0
     */
    public HideColumnPositionsEvent(ILayer layer, int... columnPositions) {
        super(layer, PositionUtil.getRanges(columnPositions));
    }

    /**
     * Creates a new HideColumnPositionsEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given column positions match.
     * @param columnPositions
     *            The positions of the columns that have changed.
     * @param columnIndexes
     *            The indexes of the columns that have changed.
     *
     * @since 1.6
     */
    public HideColumnPositionsEvent(ILayer layer, Collection<Integer> columnPositions, Collection<Integer> columnIndexes) {
        super(layer, PositionUtil.getRanges(columnPositions), columnIndexes);
    }

    /**
     * Creates a new HideColumnPositionsEvent based on the given information.
     *
     * @param layer
     *            The ILayer to which the given column positions match.
     * @param columnPositions
     *            The positions of the columns that have changed.
     * @param columnIndexes
     *            The indexes of the columns that have changed.
     *
     * @since 2.0
     */
    public HideColumnPositionsEvent(ILayer layer, int[] columnPositions, int[] columnIndexes) {
        super(layer, PositionUtil.getRanges(columnPositions), columnIndexes);
    }

    /**
     * Clone constructor.
     *
     * @param event
     *            The event to clone.
     */
    protected HideColumnPositionsEvent(HideColumnPositionsEvent event) {
        super(event);
    }

    @Override
    public HideColumnPositionsEvent cloneEvent() {
        return new HideColumnPositionsEvent(this);
    }

    @Override
    public Collection<StructuralDiff> getColumnDiffs() {
        Collection<StructuralDiff> columnDiffs =
                new ArrayList<>(getColumnPositionRanges().size());

        for (Range range : getColumnPositionRanges()) {
            StructuralDiff diff = new StructuralDiff(
                    DiffTypeEnum.DELETE,
                    range,
                    new Range(range.start, range.start));
            columnDiffs.add(diff);
        }

        return columnDiffs;
    }

    @Override
    public boolean convertToLocal(ILayer localLayer) {
        super.convertToLocal(localLayer);
        return true;
    }

}
