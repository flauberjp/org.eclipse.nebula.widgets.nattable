/*******************************************************************************
 * Copyright (c) 2018, 2020 Dirk Fauth and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.nattable.hierarchical.command;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.hierarchical.HierarchicalTreeLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeCollapseAllCommand;

/**
 * Command handler for the TreeCollapseAllCommand.
 * <p>
 * Will search over the whole tree structure in the associated TreeLayer to
 * identify collapsible nodes and collapse them one after the other.
 * </p>
 *
 * @see HierarchicalTreeLayer
 * @see TreeCollapseAllCommand
 *
 * @since 1.6
 */
public class HierarchicalTreeCollapseAllCommandHandler implements ILayerCommandHandler<TreeCollapseAllCommand> {

    /**
     * The HierarchicalTreeLayer to which this command handler is connected.
     */
    private final HierarchicalTreeLayer treeLayer;

    /**
     *
     * @param treeLayer
     *            The HierarchicalTreeLayer to which this command handler should
     *            be connected.
     */
    public HierarchicalTreeCollapseAllCommandHandler(HierarchicalTreeLayer treeLayer) {
        this.treeLayer = treeLayer;
    }

    @Override
    public boolean doCommand(ILayer targetLayer, TreeCollapseAllCommand command) {
        this.treeLayer.collapseAll();
        return true;
    }

    @Override
    public Class<TreeCollapseAllCommand> getCommandClass() {
        return TreeCollapseAllCommand.class;
    }

}
