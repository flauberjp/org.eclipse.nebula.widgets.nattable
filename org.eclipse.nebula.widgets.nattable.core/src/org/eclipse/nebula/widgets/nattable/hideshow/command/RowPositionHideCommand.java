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

import org.eclipse.nebula.widgets.nattable.command.AbstractPositionCommand;
import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;

/**
 * Command to hide a row by additional providing a column as context
 * information. This is for example needed for the HierarchicalTreeLayer to hide
 * multiple rows based on the level header column.
 *
 * @since 1.6
 */
public class RowPositionHideCommand extends AbstractPositionCommand {

    public RowPositionHideCommand(ILayer layer, int columnPosition, int rowPosition) {
        super(layer, columnPosition, rowPosition);
    }

    protected RowPositionHideCommand(RowPositionHideCommand command) {
        super(command);
    }

    @Override
    public ILayerCommand cloneCommand() {
        return new RowPositionHideCommand(this);
    }

}
