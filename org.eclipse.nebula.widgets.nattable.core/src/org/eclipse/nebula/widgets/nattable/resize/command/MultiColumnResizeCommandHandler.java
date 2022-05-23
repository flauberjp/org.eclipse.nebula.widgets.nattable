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
package org.eclipse.nebula.widgets.nattable.resize.command;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionUtil;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.resize.event.ColumnResizeEvent;

public class MultiColumnResizeCommandHandler extends AbstractLayerCommandHandler<MultiColumnResizeCommand> {

    private final DataLayer dataLayer;

    public MultiColumnResizeCommandHandler(DataLayer dataLayer) {
        this.dataLayer = dataLayer;
    }

    @Override
    public Class<MultiColumnResizeCommand> getCommandClass() {
        return MultiColumnResizeCommand.class;
    }

    @Override
    protected boolean doCommand(MultiColumnResizeCommand command) {
        int[] columnPositions = command.getColumnPositionsArray();

        for (int columnPosition : columnPositions) {
            int newColumnWidth = command.downScaleValue()
                    ? this.dataLayer.downScaleColumnWidth(command.getColumnWidth(columnPosition))
                    : command.getColumnWidth(columnPosition);

            this.dataLayer.setColumnWidthByPosition(columnPosition, newColumnWidth, false);
        }

        List<Range> ranges = PositionUtil.getRanges(columnPositions);
        for (Range range : ranges) {
            this.dataLayer.fireLayerEvent(new ColumnResizeEvent(this.dataLayer, range));
        }

        return true;
    }

}
