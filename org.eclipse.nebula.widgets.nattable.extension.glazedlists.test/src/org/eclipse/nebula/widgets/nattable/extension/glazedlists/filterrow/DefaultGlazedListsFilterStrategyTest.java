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
package org.eclipse.nebula.widgets.nattable.extension.glazedlists.filterrow;

import static org.junit.Assert.assertEquals;

import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.ReflectiveColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.dataset.fixture.data.RowDataFixture;
import org.eclipse.nebula.widgets.nattable.dataset.fixture.data.RowDataListFixture;
import org.eclipse.nebula.widgets.nattable.dataset.person.Person;
import org.eclipse.nebula.widgets.nattable.dataset.person.PersonService;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.fixture.DataLayerFixture;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.config.DefaultFilterRowConfiguration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;

public class DefaultGlazedListsFilterStrategyTest {

    private static FilterList<Person> filterList;

    private static ConfigRegistry configRegistry;
    private static DataLayerFixture columnHeaderLayer;
    private static FilterRowDataProvider<Person> dataProvider;

    private static String[] personPropertyNames = {
            "firstName",
            "lastName",
            "gender",
            "married",
            "birthday" };

    @BeforeClass
    public static void init() {
        // initialize the collection with a big amount of values
        filterList = new FilterList<>(GlazedLists.eventList(PersonService.getFixedPersons()));
        for (int i = 1; i < 1000; i++) {
            filterList.addAll(PersonService.getFixedPersons());
        }
        configRegistry = new ConfigRegistry();

        new DefaultNatTableStyleConfiguration().configureRegistry(configRegistry);
        new DefaultFilterRowConfiguration().configureRegistry(configRegistry);

        columnHeaderLayer = new DataLayerFixture(5, 2, 100, 50);
        dataProvider = new FilterRowDataProvider<>(
                new DefaultGlazedListsFilterStrategy<>(
                        filterList,
                        new ReflectiveColumnPropertyAccessor<Person>(personPropertyNames),
                        configRegistry),
                columnHeaderLayer,
                columnHeaderLayer.getDataProvider(), configRegistry);
    }

    @Before
    public void setup() {
        // ensure to start over with a clear filter
        dataProvider.clearAllFilters();
    }

    @Test
    public void shouldFilterForSimpsons() {
        assertEquals(18000, filterList.size());

        dataProvider.setDataValue(1, 1, "Simpson");

        assertEquals(10000, filterList.size());
    }

    @Test
    public void shouldFilterForMultipleCriteria() {
        assertEquals(18000, filterList.size());

        // filter: contains m
        // per fixed we have 3 Homer, 2 Marge, 2 Maude
        dataProvider.setDataValue(0, 1, "m");

        assertEquals(7000, filterList.size());

        dataProvider.setDataValue(1, 1, "Flanders");

        assertEquals(2000, filterList.size());
    }

    @Test
    public void shouldResetFilterinSameOrder() {
        // filter: contains m
        // per fixed we have 3 Homer, 2 Marge, 2 Maude
        dataProvider.setDataValue(0, 1, "m");
        dataProvider.setDataValue(1, 1, "Flanders");
        assertEquals(2000, filterList.size());

        dataProvider.setDataValue(1, 1, null);
        assertEquals(7000, filterList.size());

        dataProvider.setDataValue(0, 1, null);
        assertEquals(18000, filterList.size());
    }

    @Test
    public void shouldResetFilterinDifferentOrder() {
        // filter: contains m
        // per fixed we have 3 Homer, 2 Marge, 2 Maude
        dataProvider.setDataValue(0, 1, "m");
        dataProvider.setDataValue(1, 1, "Flanders");
        assertEquals(2000, filterList.size());

        dataProvider.setDataValue(0, 1, null);
        assertEquals(8000, filterList.size());

        dataProvider.setDataValue(1, 1, null);
        assertEquals(18000, filterList.size());
    }

    @Test
    public void shouldFilterTwoBooleanColumns() {
        EventList<RowDataFixture> eventList = GlazedLists.eventList(RowDataListFixture.getList());
        FilterList<RowDataFixture> filterList = new FilterList<>(eventList);
        IColumnPropertyAccessor<RowDataFixture> columnPropertyAccessor =
                new ReflectiveColumnPropertyAccessor<>(RowDataListFixture.getPropertyNames());

        ConfigRegistry configRegistry = new ConfigRegistry();

        new DefaultNatTableStyleConfiguration().configureRegistry(configRegistry);
        new DefaultFilterRowConfiguration().configureRegistry(configRegistry);

        DataLayerFixture columnHeaderLayer = new DataLayerFixture(5, 2, 100, 50);
        FilterRowDataProvider<RowDataFixture> dataProvider = new FilterRowDataProvider<>(
                new DefaultGlazedListsFilterStrategy<>(
                        filterList,
                        columnPropertyAccessor,
                        configRegistry),
                columnHeaderLayer,
                columnHeaderLayer.getDataProvider(), configRegistry);

        assertEquals(13, filterList.size());
        dataProvider.setDataValue(27, 1, "true");
        assertEquals(13, filterList.size());
        dataProvider.setDataValue(28, 1, "true");
        assertEquals(0, filterList.size());
    }

    @Test
    public void shouldReEvaluateWithoutChange() {
        assertEquals(18000, filterList.size());

        dataProvider.setDataValue(1, 1, "Simpson");

        assertEquals(10000, filterList.size());

        // trigger again, get an event, no changes
        dataProvider.setDataValue(1, 1, "Simpson");

        assertEquals(10000, filterList.size());

        // trigger again, get an event, no changes
        dataProvider.setDataValue(1, 1, "Simpson");

        assertEquals(10000, filterList.size());
    }

    @Test
    public void shouldReEvaluateWithChange() {
        FilterList<Person> persons = new FilterList<>(GlazedLists.eventList(PersonService.getFixedPersons()));
        DataLayerFixture columnHeaderLayer = new DataLayerFixture(5, 2, 100, 50);
        FilterRowDataProvider<Person> dataProvider = new FilterRowDataProvider<>(
                new DefaultGlazedListsFilterStrategy<>(
                        persons,
                        new ReflectiveColumnPropertyAccessor<Person>(personPropertyNames),
                        configRegistry),
                columnHeaderLayer,
                columnHeaderLayer.getDataProvider(), configRegistry);

        assertEquals(18, persons.size());

        dataProvider.setDataValue(1, 1, "Simpson");

        assertEquals(10, persons.size());

        persons.get(0).setLastName("Flanders");
        assertEquals(10, persons.size());

        // trigger again, get an event, list updated
        dataProvider.setDataValue(1, 1, "Simpson");

        assertEquals(9, persons.size());

        persons.get(0).setLastName("Flanders");

        // trigger again, get an event, list updated
        dataProvider.setDataValue(1, 1, "Simpson");

        assertEquals(8, persons.size());
    }

}
