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
package org.eclipse.nebula.widgets.nattable.data;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.dataset.fixture.data.RowDataFixture;
import org.eclipse.nebula.widgets.nattable.dataset.fixture.data.RowDataListFixture;
import org.junit.Before;
import org.junit.Test;

public class FilterListDataProviderTest {

    private List<RowDataFixture> values;
    private IRowDataProvider<RowDataFixture> dataProvider;

    @Before
    public void setup() {
        this.values = RowDataListFixture.getList();

        String[] propertyNames = RowDataListFixture.getPropertyNames();
        IColumnPropertyAccessor<RowDataFixture> columnPropertyAccessor = new ReflectiveColumnPropertyAccessor<RowDataFixture>(
                propertyNames);

        this.dataProvider = new AbstractFilterListDataProvider<RowDataFixture>(
                this.values, columnPropertyAccessor) {

            @Override
            protected boolean show(RowDataFixture object) {
                return !(object.rating.equals("AAA"));
            }
        };
    }

    @Test
    public void testRowCount() {
        // the data list contains 13 values
        assertEquals(13, this.values.size());

        // data provider now only shows 10 objects
        assertEquals(10, this.dataProvider.getRowCount());
    }

    @Test
    public void testGetRowObject() {
        // as the object on position 1 in the data list is now invisible, the
        // object on position 2 should be
        // the same as the object on position 1 in
        // AbstractFilterListDataProvider
        assertEquals(this.values.get(2), this.dataProvider.getRowObject(1));
        // as 3 objects in the data list are now invisible, the object on
        // position 12 should be
        // the same as the object on position 9 in
        // AbstractFilterListDataProvider
        assertEquals(this.values.get(12), this.dataProvider.getRowObject(9));
    }

    @Test
    public void testIndexOfRowObject() {
        // the index of the object on position 2 in the data list should have
        // position 1 in AbstractFilterListDataProvider
        assertEquals(1, this.dataProvider.indexOfRowObject(this.values.get(2)));
        // the index of the object on position 12 in the data list should have
        // position 9 in AbstractFilterListDataProvider
        assertEquals(9, this.dataProvider.indexOfRowObject(this.values.get(12)));
    }

    @Test
    public void testGetDataValue() {
        assertEquals(
                this.values.get(2).rating,
                this.dataProvider.getDataValue(
                        RowDataListFixture
                                .getColumnIndexOfProperty(RowDataListFixture.RATING_PROP_NAME),
                        1));
        assertEquals(
                this.values.get(12).rating,
                this.dataProvider.getDataValue(
                        RowDataListFixture
                                .getColumnIndexOfProperty(RowDataListFixture.RATING_PROP_NAME),
                        9));
    }

    @Test
    public void testSetDataValue() {
        // set the rating of the object on position 1 in
        // AbstractFilterListDataProvider to D
        this.dataProvider.setDataValue(RowDataListFixture
                .getColumnIndexOfProperty(RowDataListFixture.RATING_PROP_NAME),
                1, "D");

        assertEquals("D", this.values.get(2).rating);

        // set the rating of the object on position 1 in
        // AbstractFilterListDataProvider to D
        this.dataProvider.setDataValue(RowDataListFixture
                .getColumnIndexOfProperty(RowDataListFixture.RATING_PROP_NAME),
                9, "E");

        assertEquals("E", this.values.get(12).rating);
    }

    @Test
    public void testRemoveVisibleData() {
        // as there is one invisible object before this position, the visible
        // row position is 7
        assertEquals(this.values.get(8), this.dataProvider.getRowObject(7));
        // as the following object in the data list is not visible, the next
        // visible element on visible row position 8
        // is on position 10 in the data list
        assertEquals(this.values.get(10), this.dataProvider.getRowObject(8));

        // remove object on position 8 within the data list
        this.values.remove(8);

        // now the AbstractFilterListDataProvider should only show 9 items
        assertEquals(9, this.dataProvider.getRowCount());
        // as we removed a visible item, there is a new visible item on visible
        // row position 7
        // which matches the object on position 9 within the data list (two
        // invisible items before)
        assertEquals(this.values.get(9), this.dataProvider.getRowObject(7));
    }

    @Test
    public void testAddVisibleData() {
        RowDataFixture temp = RowDataListFixture.getList().get(8);
        this.values.add(8, temp);

        // now the AbstractFilterListDataProvider row count should be 10 again
        assertEquals(11, this.dataProvider.getRowCount());
        // as there is one invisible object before this position, the visible
        // row position is 7 again
        assertEquals(this.values.get(8), this.dataProvider.getRowObject(7));
        assertEquals(this.values.get(9), this.dataProvider.getRowObject(8));
        assertEquals(this.values.get(11), this.dataProvider.getRowObject(9));
    }

    @Test
    public void testRemoveInvisibleData() {
        // there is one invisible item before on position 1 and on position 9 in
        // the data list
        // is also an invisible item. so accessing the visible object on
        // position 8 in
        // AbstractFilterListDataProvider should return the object on real row
        // position 10
        assertEquals(this.values.get(10), this.dataProvider.getRowObject(8));

        // remove object on position 9 within the data list, which should be
        // invisible
        this.values.remove(9);

        // the AbstractFilterListDataProvider should still show 10 items
        assertEquals(10, this.dataProvider.getRowCount());
        // as we removed the invisible item on position 9, now the access to the
        // visible row
        // position 8 should return the object on real row position 9
        assertEquals(this.values.get(9), this.dataProvider.getRowObject(8));
    }

    @Test
    public void testAddInvisibleData() {
        // there is one invisible item before on position 1 and on position 9 in
        // the data list
        // is also an invisible item. so accessing the visible object on
        // position 8 in
        // AbstractFilterListDataProvider should return the object on real row
        // position 10
        assertEquals(this.values.get(10), this.dataProvider.getRowObject(8));

        // add an invisible item on position 8
        RowDataFixture temp = RowDataListFixture.getList().get(1);
        this.values.add(8, temp);

        // the AbstractFilterListDataProvider should still show 10 items
        assertEquals(10, this.dataProvider.getRowCount());
        // as we added an invisible item on position 8, now the access to the
        // visible row
        // position 8 should return the object on real row position 11
        assertEquals(this.values.get(11), this.dataProvider.getRowObject(8));
    }
}
