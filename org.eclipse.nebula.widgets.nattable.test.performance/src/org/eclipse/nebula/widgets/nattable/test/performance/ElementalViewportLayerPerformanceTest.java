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
package org.eclipse.nebula.widgets.nattable.test.performance;

import java.util.Arrays;

import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DummyBodyDataProvider;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.junit.Test;

public class ElementalViewportLayerPerformanceTest extends
        AbstractLayerPerformanceTest {

    @Test
    public void testViewportDataLayerPerformance() {
        this.layer = new ViewportLayer(new DataLayer(new DummyBodyDataProvider(
                1000000, 1000000)));
    }

    @Test
    public void testViewportReorderDataLayerPerformance() {
        ColumnReorderLayer reorderLayer = new ColumnReorderLayer(new DataLayer(
                new DummyBodyDataProvider(1000000, 1000000)));
        reorderLayer.reorderColumnPosition(1, 2);
        this.layer = new ViewportLayer(reorderLayer);
    }

    @Test
    public void testViewportHideShowDataLayerPerformance() {
        ColumnHideShowLayer hideShowLayer = new ColumnHideShowLayer(
                new DataLayer(new DummyBodyDataProvider(1000000, 1000000)));
        hideShowLayer
                .hideColumnPositions(Arrays.asList(new Integer[] { 3, 5 }));
        this.layer = new ViewportLayer(hideShowLayer);
    }

    @Test
    public void testViewportSelectionDataLayerPerformance() {
        this.layer = new ViewportLayer(new SelectionLayer(new DataLayer(
                new DummyBodyDataProvider(1000000, 1000000))));
    }

    @Test
    public void testCompositeViewportDataLayerPerformance() {
        CompositeLayer compositeLayer = new CompositeLayer(1, 1);
        compositeLayer.setChildLayer(GridRegion.BODY, new ViewportLayer(
                new DataLayer(new DummyBodyDataProvider(1000000, 1000000))), 0,
                0);

        this.layer = compositeLayer;
    }

}
