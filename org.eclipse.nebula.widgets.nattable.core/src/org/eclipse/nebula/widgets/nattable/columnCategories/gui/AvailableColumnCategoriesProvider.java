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

import static org.eclipse.nebula.widgets.nattable.util.ObjectUtils.isNull;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.widgets.nattable.columnCategories.ColumnCategoriesModel;
import org.eclipse.nebula.widgets.nattable.columnCategories.Node;
import org.eclipse.nebula.widgets.nattable.columnChooser.ColumnEntry;

/**
 * Provides data to the tree viewer representation of Column categories. Data is
 * in the form of {@link Node} objects exposed from the
 * {@link ColumnCategoriesModel}
 */
public class AvailableColumnCategoriesProvider implements ITreeContentProvider {

    private final ColumnCategoriesModel model;
    private List<String> hiddenIndexes = new ArrayList<>();

    public AvailableColumnCategoriesProvider(ColumnCategoriesModel model) {
        this.model = model;
    }

    /**
     * Hide the given {@link ColumnEntry} (ies) i.e. do not show them in the
     * viewer.
     *
     * @param entriesToHide
     *            the entries to hide
     */
    public void hideEntries(List<ColumnEntry> entriesToHide) {
        for (ColumnEntry hiddenColumnEntry : entriesToHide) {
            this.hiddenIndexes.add(String.valueOf(hiddenColumnEntry.getIndex()));
        }
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        return getFilteredChildren(castToNode(parentElement).getChildren())
                .toArray();
    }

    @Override
    public Object getParent(Object element) {
        return castToNode(element).getParent();
    }

    @Override
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return isNull(this.model.getRootCategory()) ? new Object[] {}
                : getFilteredChildren(this.model.getRootCategory().getChildren())
                        .toArray();
    }

    private List<Node> getFilteredChildren(List<Node> allChildren) {
        List<Node> children = new ArrayList<>(allChildren);
        for (Node child : allChildren) {
            if (this.hiddenIndexes.contains(child.getData())) {
                children.remove(child);
            }
        }
        return children;
    }

    private Node castToNode(Object element) {
        return (Node) element;
    }

    @Override
    public void dispose() {
        // No op.
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // No op.
    }

}
