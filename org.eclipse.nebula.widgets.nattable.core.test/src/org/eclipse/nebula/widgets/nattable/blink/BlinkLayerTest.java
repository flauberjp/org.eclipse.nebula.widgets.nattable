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
package org.eclipse.nebula.widgets.nattable.blink;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IRowIdAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ReflectiveColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.dataset.fixture.data.BlinkingRowDataFixture;
import org.eclipse.nebula.widgets.nattable.dataset.fixture.data.RowDataListFixture;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.event.PropertyUpdateEvent;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.DataLayerFixture;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

public class BlinkLayerTest {

    private static final String NOT_BLINKING_LABEL = "Not Blinking";
    private static final String BLINKING_LABEL = "Blinking";

    private static final String TEST_LABEL = "TestLabel";

    private DataLayer dataLayer;
    private BlinkLayer<BlinkingRowDataFixture> layerUnderTest;
    private final ConfigRegistry configRegistry = new ConfigRegistry();
    private List<BlinkingRowDataFixture> dataList;
    private ListDataProvider<BlinkingRowDataFixture> listDataProvider;
    private PropertyChangeListener propertyChangeListener;
    private Display display;

    @Before
    public void setUp() {
        this.display = Display.getDefault();
        this.dataList = new LinkedList<BlinkingRowDataFixture>();
        IColumnPropertyAccessor<BlinkingRowDataFixture> columnPropertyAccessor = new ReflectiveColumnPropertyAccessor<BlinkingRowDataFixture>(
                RowDataListFixture.getPropertyNames());
        this.listDataProvider = new ListDataProvider<BlinkingRowDataFixture>(
                this.dataList, columnPropertyAccessor);
        this.propertyChangeListener = getPropertyChangeListener();

        this.dataLayer = new DataLayer(this.listDataProvider);
        this.layerUnderTest = new BlinkLayer<BlinkingRowDataFixture>(
                this.dataLayer,
                this.listDataProvider,
                new IRowIdAccessor<BlinkingRowDataFixture>() {

                    @Override
                    public Serializable getRowId(BlinkingRowDataFixture rowObject) {
                        return rowObject.getSecurity_id();
                    }
                },
                columnPropertyAccessor,
                this.configRegistry);

        this.layerUnderTest.blinkingEnabled = true;

        registerBlinkConfigTypes();
        load10Rows();
    }

    @Test
    public void shouldReturnTheBlinkConfigTypeWhenARowIsUpdated()
            throws Exception {
        this.layerUnderTest.setBlinkDurationInMilis(100);

        this.dataList.get(0).setAsk_price(100);
        LabelStack blinkLabels = this.layerUnderTest.getConfigLabelsByPosition(6, 0);

        // Blink started
        assertEquals(1, blinkLabels.size());
        assertEquals(BLINKING_LABEL, blinkLabels.get(0));

        // After 50 ms
        Thread.sleep(50);
        blinkLabels = this.layerUnderTest.getConfigLabelsByPosition(6, 0);
        assertEquals(1, blinkLabels.size());

        // Wait for blink to elapse
        Thread.sleep(110);
        // Force running the event queue to ensure any Display.asyncExecs are
        // run.
        while (this.display.readAndDispatch())
            ;

        blinkLabels = this.layerUnderTest.getConfigLabelsByPosition(6, 0);
        assertEquals(0, blinkLabels.size());
    }

    @Test
    public void layerStackShouldUpdate() throws Exception {
        // add label accumulator to DataLayer
        this.dataLayer.setConfigLabelAccumulator(new IConfigLabelAccumulator() {

            @Override
            public void accumulateConfigLabels(LabelStack configLabels,
                    int columnPosition, int rowPosition) {
                configLabels.addLabel(TEST_LABEL);
            }
        });

        this.layerUnderTest.setBlinkDurationInMilis(100);

        this.dataList.get(0).setAsk_price(100);
        LabelStack blinkLabels = this.layerUnderTest.getConfigLabelsByPosition(6, 0);

        // Blink started
        assertEquals(2, blinkLabels.size());
        assertEquals(BLINKING_LABEL, blinkLabels.get(0));
        assertEquals(TEST_LABEL, blinkLabels.get(1));

        // After 50 ms
        Thread.sleep(50);
        blinkLabels = this.layerUnderTest.getConfigLabelsByPosition(6, 0);
        assertEquals(2, blinkLabels.size());

        // Wait for blink to elapse
        Thread.sleep(110);
        // Force running the event queue to ensure any Display.asyncExecs are
        // run.
        while (this.display.readAndDispatch())
            ;

        blinkLabels = this.layerUnderTest.getConfigLabelsByPosition(6, 0);
        assertEquals(1, blinkLabels.size());
        assertEquals(TEST_LABEL, blinkLabels.get(0));
    }

    /**
     * Sets the even rows to blink
     */
    private void registerBlinkConfigTypes() {
        IBlinkingCellResolver blinkingCellResolver = new BlinkingCellResolver() {
            @Override
            public String[] resolve(Object oldValue, Object newValue) {
                Double doubleValue = Double.valueOf(newValue.toString());
                return doubleValue.intValue() % 2 == 0 ? new String[] { BLINKING_LABEL }
                        : new String[] { NOT_BLINKING_LABEL };
            }
        };

        this.configRegistry.registerConfigAttribute(
                BlinkConfigAttributes.BLINK_RESOLVER, blinkingCellResolver,
                DisplayMode.NORMAL);
    }

    /**
     * Listen for updates and put them in the {@link UpdateEventsCache}.
     * BlinkLayer needs this cache to be updated in order to work.
     */
    private PropertyChangeListener getPropertyChangeListener() {
        return new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                PropertyUpdateEvent<BlinkingRowDataFixture> updateEvent = new PropertyUpdateEvent<BlinkingRowDataFixture>(
                        new DataLayerFixture(),
                        (BlinkingRowDataFixture) event.getSource(),
                        event.getPropertyName(), event.getOldValue(),
                        event.getNewValue());
                BlinkLayerTest.this.layerUnderTest.handleLayerEvent(updateEvent);
            }
        };
    }

    private void load10Rows() {
        List<BlinkingRowDataFixture> list = BlinkingRowDataFixture
                .getList(this.propertyChangeListener);
        for (BlinkingRowDataFixture blinkingRowDataFixture : list) {
            this.dataList.add(blinkingRowDataFixture);
        }
    }
}
