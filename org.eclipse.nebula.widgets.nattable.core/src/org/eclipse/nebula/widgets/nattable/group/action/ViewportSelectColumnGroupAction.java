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
package org.eclipse.nebula.widgets.nattable.group.action;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.group.command.ViewportSelectColumnGroupCommand;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.swt.events.MouseEvent;

/**
 * {@link IMouseAction} that is used to trigger the selection of all columns
 * belonging to a column group.
 *
 * @since 1.6
 */
public class ViewportSelectColumnGroupAction implements IMouseAction {

    private final boolean withShiftMask;
    private final boolean withControlMask;

    public ViewportSelectColumnGroupAction(boolean withShiftMask, boolean withControlMask) {
        this.withShiftMask = withShiftMask;
        this.withControlMask = withControlMask;
    }

    @Override
    public void run(NatTable natTable, MouseEvent event) {
        // only perform the selection if the cursor is null
        if (natTable.getCursor() == null) {
            ILayerCell cell = natTable.getCellByPosition(
                    natTable.getColumnPositionByX(event.x),
                    natTable.getRowPositionByY(event.y));
            ViewportSelectColumnGroupCommand command = new ViewportSelectColumnGroupCommand(
                    natTable,
                    cell.getColumnPosition(),
                    cell.getOriginColumnPosition(),
                    cell.getColumnSpan(),
                    this.withShiftMask,
                    this.withControlMask);
            natTable.doCommand(command);
        }
    }

}
