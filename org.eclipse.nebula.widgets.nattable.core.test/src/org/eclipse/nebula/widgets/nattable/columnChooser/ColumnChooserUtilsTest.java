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
package org.eclipse.nebula.widgets.nattable.columnChooser;

import static org.eclipse.nebula.widgets.nattable.columnChooser.ColumnChooserUtils.getColumnLabel;
import static org.eclipse.nebula.widgets.nattable.test.fixture.layer.ColumnHeaderLayerFixture.getDataLayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.dataset.fixture.data.RowDataListFixture;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultGridLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.test.fixture.ColumnEntriesFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.ColumnHeaderLayerFixture;
import org.junit.Before;
import org.junit.Test;

public class ColumnChooserUtilsTest {

    private List<ColumnEntry> entriesFixture;

    @Before
    public void setup() {
        this.entriesFixture = ColumnEntriesFixture.getEntriesWithOddIndexes();
    }

    @Test
    public void find() {
        ColumnEntry found = ColumnChooserUtils.find(this.entriesFixture, 5);
        assertEquals("Index5", found.getLabel());

        found = ColumnChooserUtils.find(this.entriesFixture, 42);
        assertNull(found);
    }

    @Test
    public void getPositionsFromEntries() {
        List<Integer> positions = ColumnChooserUtils.getColumnEntryPositions(this.entriesFixture);
        assertEquals("[2, 6, 3, 4, 5]", positions.toString());
    }

    @Test
    public void getIndexesFromEntries() {
        List<Integer> indexes = ColumnChooserUtils.getColumnEntryIndexes(this.entriesFixture);
        assertEquals("[1, 3, 5, 7, 9]", indexes.toString());
    }

    @Test
    public void listContainsEntry() {
        assertTrue(ColumnChooserUtils.containsIndex(this.entriesFixture, 9));
        assertFalse(ColumnChooserUtils.containsIndex(this.entriesFixture, -9));
    }

    @Test
    public void shouldProvideRenamedLabelsIfTheColumnHasBeenRenamed() {
        ColumnHeaderLayerFixture columnHeaderLayer = new ColumnHeaderLayerFixture();
        assertEquals("[1, 0]", getColumnLabel(columnHeaderLayer, getDataLayer(), 1));

        columnHeaderLayer.renameColumnPosition(1, "renamed");
        assertEquals("renamed*", getColumnLabel(columnHeaderLayer, getDataLayer(), 1));
    }

    @Test
    public void getVisibleColumnEntries() {
        DefaultGridLayer gridLayer = new DefaultGridLayer(
                RowDataListFixture.getList(),
                RowDataListFixture.getPropertyNames(),
                RowDataListFixture.getPropertyToLabelMap());
        ColumnHideShowLayer columnHideShowLayer = gridLayer.getBodyLayer().getColumnHideShowLayer();
        ColumnHeaderLayer columnHeaderLayer = gridLayer.getColumnHeaderLayer();
        DataLayer columnHeaderDataLayer = (DataLayer) gridLayer.getColumnHeaderDataLayer();

        List<ColumnEntry> visibleEntries = ColumnChooserUtils.getVisibleColumnsEntries(
                columnHideShowLayer,
                columnHeaderLayer,
                columnHeaderDataLayer);

        // All columns shown
        assertEquals(RowDataListFixture.getPropertyNames().length, visibleEntries.size());

        // Hide a few columns
        gridLayer.getBodyLayer().getColumnHideShowLayer().hideColumnPositions(Arrays.asList(1, 2, 3));
        visibleEntries = ColumnChooserUtils.getVisibleColumnsEntries(
                columnHideShowLayer,
                columnHeaderLayer,
                columnHeaderDataLayer);
        assertEquals(RowDataListFixture.getPropertyNames().length - 3l, visibleEntries.size());

        // Check the hidden entries
        List<ColumnEntry> hiddenEntries = ColumnChooserUtils.getHiddenColumnEntries(
                columnHideShowLayer,
                columnHeaderLayer,
                columnHeaderDataLayer);
        assertEquals(3, hiddenEntries.size());
    }
}
