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
package org.eclipse.nebula.widgets.nattable.examples.fixtures;

import java.util.Map;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ReflectiveColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultRowHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.stack.DefaultBodyLayerStack;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;

/**
 * Factory for assembling GridLayer and the child layers - with support for
 * GlazedLists and sorting
 *
 * @see <a href="http://publicobject.com/glazedlists/"> http://publicobject.com/
 *      glazedlists/</a>
 */
public class GlazedListsGridLayer<T> extends GridLayer {

    private ColumnOverrideLabelAccumulator columnLabelAccumulator;
    private DataLayer bodyDataLayer;
    private DefaultBodyLayerStack bodyLayerStack;
    private ListDataProvider<T> bodyDataProvider;
    private GlazedListsColumnHeaderLayerStack<T> columnHeaderLayerStack;

    public GlazedListsGridLayer(
            EventList<T> eventList,
            String[] propertyNames,
            Map<String, String> propertyToLabelMap,
            IConfigRegistry configRegistry) {
        this(eventList, propertyNames, propertyToLabelMap, configRegistry, true);
    }

    public GlazedListsGridLayer(
            EventList<T> eventList,
            String[] propertyNames,
            Map<String, String> propertyToLabelMap,
            IConfigRegistry configRegistry,
            boolean useDefaultConfiguration) {

        this(eventList, new ReflectiveColumnPropertyAccessor<>(propertyNames),
                new DefaultColumnHeaderDataProvider(propertyNames, propertyToLabelMap),
                configRegistry,
                useDefaultConfiguration);
    }

    public GlazedListsGridLayer(
            EventList<T> eventList,
            IColumnPropertyAccessor<T> columnPropertyAccessor,
            IDataProvider columnHeaderDataProvider,
            IConfigRegistry configRegistry,
            boolean useDefaultConfiguration) {

        super(useDefaultConfiguration);

        // Body - with list event listener
        // NOTE: Remember to use the SortedList constructor with 'null' for the
        // Comparator
        SortedList<T> sortedList = new SortedList<>(eventList, null);
        this.bodyDataProvider = new ListDataProvider<>(sortedList, columnPropertyAccessor);

        this.bodyDataLayer =
                new DataLayer(this.bodyDataProvider);
        GlazedListsEventLayer<T> glazedListsEventLayer =
                new GlazedListsEventLayer<>(this.bodyDataLayer, eventList);
        this.bodyLayerStack =
                new DefaultBodyLayerStack(glazedListsEventLayer);

        // Column header
        this.columnHeaderLayerStack =
                new GlazedListsColumnHeaderLayerStack<>(
                        columnHeaderDataProvider,
                        sortedList,
                        columnPropertyAccessor,
                        configRegistry, this.bodyLayerStack);

        // Row header
        DefaultRowHeaderDataProvider rowHeaderDataProvider =
                new DefaultRowHeaderDataProvider(this.bodyDataProvider);
        DefaultRowHeaderDataLayer rowHeaderDataLayer =
                new DefaultRowHeaderDataLayer(rowHeaderDataProvider);
        RowHeaderLayer rowHeaderLayer =
                new RowHeaderLayer(
                        rowHeaderDataLayer,
                        this.bodyLayerStack,
                        this.bodyLayerStack.getSelectionLayer());

        // Corner
        DefaultCornerDataProvider cornerDataProvider =
                new DefaultCornerDataProvider(
                        this.columnHeaderLayerStack.getDataProvider(),
                        rowHeaderDataProvider);
        DataLayer cornerDataLayer =
                new DataLayer(cornerDataProvider);
        CornerLayer cornerLayer =
                new CornerLayer(
                        cornerDataLayer,
                        rowHeaderLayer,
                        this.columnHeaderLayerStack);

        // Grid
        setBodyLayer(this.bodyLayerStack);
        setColumnHeaderLayer(this.columnHeaderLayerStack);
        setRowHeaderLayer(rowHeaderLayer);
        setCornerLayer(cornerLayer);
    }

    public ColumnOverrideLabelAccumulator getColumnLabelAccumulator() {
        return this.columnLabelAccumulator;
    }

    public DataLayer getBodyDataLayer() {
        return this.bodyDataLayer;
    }

    public ListDataProvider<T> getBodyDataProvider() {
        return this.bodyDataProvider;
    }

    public GlazedListsColumnHeaderLayerStack<T> getColumnHeaderLayerStack() {
        return this.columnHeaderLayerStack;
    }

    public DefaultBodyLayerStack getBodyLayerStack() {
        return this.bodyLayerStack;
    }
}
