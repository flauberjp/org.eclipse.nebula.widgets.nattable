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
package org.eclipse.nebula.widgets.nattable.hideshow.command;

import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.hideshow.IRowHideShowLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.RowHideShowLayer;
import org.eclipse.nebula.widgets.nattable.hierarchical.HierarchicalTreeLayer;

/**
 * Command handler for the {@link RowPositionHideCommand}. This handler is
 * intended to be registered with the {@link RowHideShowLayer} and only inspects
 * the row position. Typically the {@link RowPositionHideCommand} is handled by
 * layers that support special handling based on the column position, e.g. the
 * HierarchicalTreeLayer.
 *
 * @see HierarchicalTreeLayer
 *
 * @since 1.6
 */
public class RowPositionHideCommandHandler extends AbstractLayerCommandHandler<RowPositionHideCommand> {

    private final IRowHideShowLayer rowHideShowLayer;

    /**
     *
     * @param rowHideShowLayer
     *            The {@link IRowHideShowLayer} to which this command handler
     *            should be registered.
     * @since 2.0
     */
    public RowPositionHideCommandHandler(IRowHideShowLayer rowHideShowLayer) {
        this.rowHideShowLayer = rowHideShowLayer;
    }

    @Override
    public Class<RowPositionHideCommand> getCommandClass() {
        return RowPositionHideCommand.class;
    }

    @Override
    protected boolean doCommand(RowPositionHideCommand command) {
        this.rowHideShowLayer.hideRowPositions(command.getRowPosition());
        return true;
    }
}
