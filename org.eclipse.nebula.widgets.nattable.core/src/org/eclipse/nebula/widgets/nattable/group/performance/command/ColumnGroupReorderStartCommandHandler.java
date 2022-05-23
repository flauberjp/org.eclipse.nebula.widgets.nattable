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
import org.eclipse.nebula.widgets.nattable.layer.LayerUtil;

/**
 * Command handler for the {@link ColumnGroupReorderStartCommand}. Needed for
 * reordering via drag mode for setting the from position. This is necessary as
 * on drag the viewport could scroll and therefore the real from position could
 * not be determined anymore.
 *
 * @see ColumnGroupReorderEndCommandHandler
 *
 * @since 1.6
 */
public class ColumnGroupReorderStartCommandHandler extends AbstractLayerCommandHandler<ColumnGroupReorderStartCommand> {

    private final ColumnGroupHeaderLayer columnGroupHeaderLayer;

    public ColumnGroupReorderStartCommandHandler(ColumnGroupHeaderLayer columnGroupHeaderLayer) {
        this.columnGroupHeaderLayer = columnGroupHeaderLayer;
    }

    @Override
    protected boolean doCommand(ColumnGroupReorderStartCommand command) {
        int fromColumnPosition = command.getColumnPosition();

        this.columnGroupHeaderLayer.setReorderFromColumnPosition(
                LayerUtil.convertColumnPosition(this.columnGroupHeaderLayer, fromColumnPosition, this.columnGroupHeaderLayer.getPositionLayer()));

        return true;
    }

    @Override
    public Class<ColumnGroupReorderStartCommand> getCommandClass() {
        return ColumnGroupReorderStartCommand.class;
    }

}
