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
package org.eclipse.nebula.widgets.nattable.test.fixture.layer;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultGridLayer;
import org.eclipse.nebula.widgets.nattable.test.fixture.data.DataProviderFixture;
import org.eclipse.nebula.widgets.nattable.util.IClientAreaProvider;
import org.eclipse.swt.graphics.Rectangle;

public class GridLayerFixture extends DefaultGridLayer {

    public static final IDataProvider bodyDataProvider = new DataProviderFixture(
            10, 5);
    public static final IDataProvider colHeaderDataProvider = new DataProviderFixture(
            10, 1);
    public static final IDataProvider rowHeaderDataProvider = new DataProviderFixture(
            1, 5);
    public static final IDataProvider cornerDataProvider = new DataProviderFixture(
            1, 1);

    public GridLayerFixture() {
        super(bodyDataProvider, colHeaderDataProvider, rowHeaderDataProvider,
                cornerDataProvider);

        setClientAreaProvider(new IClientAreaProvider() {

            private Rectangle clientArea = new Rectangle(0, 0, 400, 400);

            @Override
            public Rectangle getClientArea() {
                return this.clientArea;
            }

        });
    }

    public GridLayerFixture(IDataProvider customBodyDataProvider) {
        super(customBodyDataProvider, colHeaderDataProvider,
                rowHeaderDataProvider, cornerDataProvider);
    }
}
