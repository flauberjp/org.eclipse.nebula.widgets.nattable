/*******************************************************************************
 * Copyright (c) 2019, 2020 Dirk Fauth.
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
package org.eclipse.nebula.widgets.nattable.group.performance.command;

import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.group.performance.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.reorder.command.ColumnReorderStartCommand;

/**
 * Command handler for the {@link ColumnReorderStartCommand} that is registered
 * on the positionLayer of the {@link ColumnGroupHeaderLayer} to avoid handling
 * in case the reordering would break an unbreakable group.
 *
 * @since 1.6
 */
public class GroupColumnReorderStartCommandHandler extends AbstractLayerCommandHandler<ColumnReorderStartCommand> {

    private final ColumnGroupHeaderLayer columnGroupHeaderLayer;

    public GroupColumnReorderStartCommandHandler(ColumnGroupHeaderLayer columnGroupHeaderLayer) {
        this.columnGroupHeaderLayer = columnGroupHeaderLayer;
    }

    @Override
    protected boolean doCommand(ColumnReorderStartCommand command) {
        int fromColumnPosition = command.getFromColumnPosition();

        this.columnGroupHeaderLayer.setReorderFromColumnPosition(fromColumnPosition);

        // we need to remember the from position in order to check if the
        // reorder operation is valid
        // the command shouldn't get consumed here, since the reordering needs
        // to take place in the ColumnReorderLayer
        return false;
    }

    @Override
    public Class<ColumnReorderStartCommand> getCommandClass() {
        return ColumnReorderStartCommand.class;
    }

}
