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
package org.eclipse.nebula.widgets.nattable.columnCategories.gui;

import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.nattable.Messages;
import org.eclipse.nebula.widgets.nattable.columnCategories.Node;
import org.eclipse.nebula.widgets.nattable.columnChooser.ColumnChooserUtils;
import org.eclipse.nebula.widgets.nattable.columnChooser.ColumnEntry;
import org.eclipse.nebula.widgets.nattable.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnCategoriesLabelProvider extends LabelProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ColumnCategoriesLabelProvider.class);

    List<ColumnEntry> hiddenEntries;

    public ColumnCategoriesLabelProvider(List<ColumnEntry> hiddenEntries) {
        this.hiddenEntries = hiddenEntries;
    }

    @Override
    public String getText(Object element) {
        Node node = (Node) element;
        switch (node.getType()) {
            case CATEGORY:
                return node.getData();
            case COLUMN:
                int index = Integer.parseInt(node.getData());
                ColumnEntry columnEntry = ColumnChooserUtils.find(this.hiddenEntries, index);
                if (ObjectUtils.isNull(columnEntry)) {
                    LOG.error("Column index {} is present in the Column Categories model, but not in the underlying data", index); //$NON-NLS-1$
                    return String.valueOf(index);
                }
                return columnEntry.getLabel();
            default:
                return Messages.getString("Unknown"); //$NON-NLS-1$
        }
    }
}
