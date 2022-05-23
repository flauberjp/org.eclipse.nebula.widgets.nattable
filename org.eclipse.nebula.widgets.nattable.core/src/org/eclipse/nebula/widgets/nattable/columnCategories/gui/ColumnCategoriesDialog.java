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

import static org.eclipse.nebula.widgets.nattable.columnChooser.ColumnChooserUtils.getColumnEntryPositions;
import static org.eclipse.nebula.widgets.nattable.util.ObjectUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.nattable.Messages;
import org.eclipse.nebula.widgets.nattable.columnCategories.ColumnCategoriesModel;
import org.eclipse.nebula.widgets.nattable.columnCategories.IColumnCategoriesDialogListener;
import org.eclipse.nebula.widgets.nattable.columnCategories.Node;
import org.eclipse.nebula.widgets.nattable.columnCategories.Node.Type;
import org.eclipse.nebula.widgets.nattable.columnChooser.ColumnEntry;
import org.eclipse.nebula.widgets.nattable.columnChooser.gui.AbstractColumnChooserDialog;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.nebula.widgets.nattable.util.ObjectUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * JFace/SWT based column chooser dialog which displays the available/hidden
 * columns in a tree viewer. This tree viewer is based on the
 * {@link ColumnCategoriesModel}.
 */
public class ColumnCategoriesDialog extends AbstractColumnChooserDialog {

    private final ColumnCategoriesModel model;
    private List<ColumnEntry> hiddenColumnEntries;
    private List<ColumnEntry> visibleColumnsEntries;
    private TreeViewer treeViewer;
    private ListViewer listViewer;
    private ISelection lastListSelection;

    private ListenerList<IColumnCategoriesDialogListener> listeners = new ListenerList<>();

    public ColumnCategoriesDialog(Shell shell, ColumnCategoriesModel model,
            List<ColumnEntry> hiddenColumnEntries,
            List<ColumnEntry> visibleColumnsEntries) {
        super(shell);
        this.model = model;
        this.hiddenColumnEntries = hiddenColumnEntries;
        this.visibleColumnsEntries = visibleColumnsEntries;
        setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE);
    }

    @Override
    public void populateDialogArea(Composite parent) {
        GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);
        parent.setLayout(new GridLayout(4, false));

        // Labels
        createLabels(
                parent,
                Messages.getString("ColumnChooser.availableColumns"), //$NON-NLS-1$
                Messages.getString("ColumnChooser.selectedColumns")); //$NON-NLS-1$
        GridData gridData = GridDataFactory.fillDefaults().grab(true, true).create();

        // Left tree - column categories
        this.treeViewer = new TreeViewer(parent);

        populateAvailableTree();
        this.treeViewer.getControl().setLayoutData(gridData);

        // Add/remove buttons
        Composite buttonComposite = new Composite(parent, SWT.NONE);
        buttonComposite.setLayout(new GridLayout(1, true));
        createAddButton(buttonComposite);
        createRemoveButton(buttonComposite);
        addListenersToTreeViewer();

        // Right list - selected columns
        this.listViewer = new ListViewer(parent, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        populateSelectedList();
        addListenersToListViewer();

        // Up/down buttons
        Composite upDownbuttonComposite = new Composite(parent, SWT.NONE);
        upDownbuttonComposite.setLayout(new GridLayout(1, true));
        createUpButton(upDownbuttonComposite);
        createDownButton(upDownbuttonComposite);
    }

    private void populateSelectedList() {
        VisibleColumnsProvider listProvider = new VisibleColumnsProvider(this.visibleColumnsEntries);
        this.listViewer.setContentProvider(listProvider);
        this.listViewer.setLabelProvider(listProvider);
        this.listViewer.setInput(listProvider);

        this.listViewer.setContentProvider(listProvider);
        this.listViewer.getControl().setLayoutData(GridDataFactory
                .fillDefaults()
                .grab(true, true)
                .create());
    }

    private void addListenersToTreeViewer() {
        this.treeViewer.getControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                addSelected();
            }
        });
    }

    private void addListenersToListViewer() {
        this.listViewer.getControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                removeSelected();
            }
        });

        this.listViewer.getControl().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean controlMask = (e.stateMask & SWT.MOD1) == SWT.MOD1;
                if (controlMask && e.keyCode == SWT.ARROW_UP) {
                    moveSelectedUp();
                    e.doit = false;
                } else if (controlMask && e.keyCode == SWT.ARROW_DOWN) {
                    moveSelectedDown();
                    e.doit = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.character == ' ')
                    removeSelected();
            }
        });
    }

    private void populateAvailableTree() {
        AvailableColumnCategoriesProvider provider = new AvailableColumnCategoriesProvider(this.model);
        provider.hideEntries(this.visibleColumnsEntries);

        this.treeViewer.setContentProvider(provider);
        this.treeViewer.setLabelProvider(new ColumnCategoriesLabelProvider(this.hiddenColumnEntries));
        this.treeViewer.setInput(provider);
    }

    private Button createDownButton(Composite upDownbuttonComposite) {
        Button downButton = new Button(upDownbuttonComposite, SWT.PUSH);
        downButton.setImage(GUIHelper.getImage("arrow_down")); //$NON-NLS-1$
        downButton.setLayoutData(GridDataFactory
                .fillDefaults()
                .grab(false, true)
                .align(SWT.CENTER, SWT.CENTER)
                .create());
        downButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                moveSelectedDown();
            }
        });
        return downButton;
    }

    private Button createUpButton(Composite upDownbuttonComposite) {
        Button upButton = new Button(upDownbuttonComposite, SWT.PUSH);
        upButton.setImage(GUIHelper.getImage("arrow_up")); //$NON-NLS-1$
        upButton.setLayoutData(GridDataFactory
                .fillDefaults()
                .grab(false, true)
                .align(SWT.CENTER, SWT.CENTER)
                .create());
        upButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                moveSelectedUp();
            }

        });
        return upButton;
    }

    private Button createRemoveButton(Composite buttonComposite) {
        Button removeButton = new Button(buttonComposite, SWT.PUSH);
        removeButton.setImage(GUIHelper.getImage("arrow_left")); //$NON-NLS-1$
        removeButton.setLayoutData(GridDataFactory
                .fillDefaults()
                .grab(false, true)
                .align(SWT.CENTER, SWT.CENTER)
                .create());
        removeButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                removeSelected();
            }
        });
        return removeButton;
    }

    private Button createAddButton(Composite buttonComposite) {
        Button addButton = new Button(buttonComposite, SWT.PUSH);
        addButton.setImage(GUIHelper.getImage("arrow_right")); //$NON-NLS-1$
        addButton.setLayoutData(GridDataFactory
                .fillDefaults()
                .grab(false, true)
                .align(SWT.CENTER, SWT.CENTER)
                .create());
        addButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                addSelected();
            }

        });
        return addButton;
    }

    // Respond to button clicks / user actions

    protected void removeSelected() {
        fireItemsRemoved(getColumnPositionsFromListViewer());
    }

    protected void addSelected() {
        fireItemsSelected(getColumnIndexesFromTreeNodes());
    }

    /**
     * Add a {@link IColumnCategoriesDialogListener} that is triggered for
     * modifications in the selected tree.
     *
     * @param listener
     *            the listener to add.
     * @since 2.0
     */
    public void addListener(IColumnCategoriesDialogListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Remove a {@link IColumnCategoriesDialogListener} that is triggered for
     * modifications in the selected tree.
     *
     * @param listener
     *            the listener to remove.
     * @since 2.0
     */
    public void removeListener(IColumnCategoriesDialogListener listener) {
        this.listeners.remove(listener);
    }

    protected final void fireItemsSelected(List<Integer> addedColumnIndexes) {
        if (isNotEmpty(addedColumnIndexes)) {
            for (IColumnCategoriesDialogListener listener : this.listeners) {
                listener.itemsSelected(addedColumnIndexes);
            }
        }
    }

    protected final void fireItemsRemoved(List<Integer> removedColumnPositions) {
        if (isNotEmpty(removedColumnPositions)) {
            for (IColumnCategoriesDialogListener listener : this.listeners) {
                listener.itemsRemoved(removedColumnPositions);
            }
        }
    }

    protected final void fireItemsMoved(MoveDirectionEnum direction, List<Integer> toPositions) {
        for (IColumnCategoriesDialogListener listener : this.listeners) {
            listener.itemsMoved(direction, toPositions);
        }
    }

    protected void moveSelectedUp() {
        List<Integer> selectedPositions = getColumnEntryPositions(getSelectedColumnEntriesFromListViewer());

        // First position selected
        if (!selectedPositions.contains(0)) {
            fireItemsMoved(MoveDirectionEnum.UP, selectedPositions);
        }
    }

    protected void moveSelectedDown() {
        List<Integer> selectedPositions = getColumnEntryPositions(getSelectedColumnEntriesFromListViewer());

        // Last position selected
        if (!selectedPositions.contains(this.visibleColumnsEntries.size())) {
            fireItemsMoved(MoveDirectionEnum.DOWN, selectedPositions);
        }
    }

    /**
     * @return selected column position(s) from the list viewer
     */
    private List<Integer> getColumnPositionsFromListViewer() {
        return getColumnEntryPositions(getSelectedColumnEntriesFromListViewer());
    }

    private List<ColumnEntry> getSelectedColumnEntriesFromListViewer() {
        this.lastListSelection = this.listViewer.getSelection();
        Object[] objects = ((StructuredSelection) this.lastListSelection).toArray();
        List<ColumnEntry> entries = new ArrayList<>();

        for (Object object : objects) {
            entries.add((ColumnEntry) object);
        }
        return entries;
    }

    /**
     * @return selected columns index(s) from the tree viewer
     */
    private List<Integer> getColumnIndexesFromTreeNodes() {
        Object[] nodes = ((TreeSelection) this.treeViewer.getSelection()).toArray();

        List<Integer> indexes = new ArrayList<>();
        for (Object object : nodes) {
            Node node = (Node) object;
            if (Type.COLUMN == node.getType()) {
                indexes.add(Integer.parseInt(node.getData()));
            }
        }
        return indexes;
    }

    public void refresh(List<ColumnEntry> hiddenColumnEntries, List<ColumnEntry> visibleColumnsEntries) {
        this.hiddenColumnEntries = hiddenColumnEntries;
        this.visibleColumnsEntries = visibleColumnsEntries;
        populateAvailableTree();
        populateSelectedList();
        if (ObjectUtils.isNotNull(this.lastListSelection)) {
            this.listViewer.setSelection(this.lastListSelection);
        }
    }
}
