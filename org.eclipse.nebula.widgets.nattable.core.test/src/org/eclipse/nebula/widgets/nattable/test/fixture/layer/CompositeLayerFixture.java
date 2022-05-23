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

import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;

/**
 * Fixture for the CompositeLayer with 2 rows and 2 columns
 *
 * <pre>
 * {@code
 *             |  |
 *             |  |
 *  Corner     7  |   Col Header
 *             |  |
 *             v  |      |<-Hidden->
 *  --------------|------|-----------
 *  <---- 5 ----->|<-2-->|<--3----->
 *             ^  |
 *             |  |<---- 5 -------->
 *  Row        7  |   Body
 *  Header     |  |
 *             v  |
 * }
 * </pre>
 */
public class CompositeLayerFixture extends CompositeLayer {

    private static final int ROW_COUNT = 2;
    private static final int COLUMN_COUNT = 2;

    public DataLayerFixture cornerLayer;
    public DataLayerFixture colHeaderLayer;
    public DataLayerFixture rowHeaderLayer;
    public ViewportLayerFixture bodyLayer;

    public CompositeLayerFixture() {
        super(COLUMN_COUNT, ROW_COUNT);

        this.cornerLayer = new DataLayerFixture(5, 5);
        setChildLayer(GridRegion.CORNER, this.cornerLayer, 0, 0);

        this.colHeaderLayer = new DataLayerFixture(25, 5);
        setChildLayer(GridRegion.COLUMN_HEADER, this.colHeaderLayer, 1, 0);

        this.rowHeaderLayer = new DataLayerFixture(10, 5);
        setChildLayer(GridRegion.ROW_HEADER, this.rowHeaderLayer, 0, 1);

        this.bodyLayer = new ViewportLayerFixture();
        setChildLayer(GridRegion.BODY, this.bodyLayer, 1, 1);
    }

    /**
     * Fixture with all columns equal width and all rows equal height
     */
    public CompositeLayerFixture(int width, int height) {
        super(COLUMN_COUNT, ROW_COUNT);

        this.cornerLayer = new DataLayerFixture(width, height);
        setChildLayer(GridRegion.CORNER, this.cornerLayer, 0, 0);

        this.colHeaderLayer = new DataLayerFixture(width, height);
        setChildLayer(GridRegion.COLUMN_HEADER, this.colHeaderLayer, 1, 0);

        this.rowHeaderLayer = new DataLayerFixture(width, height);
        setChildLayer(GridRegion.ROW_HEADER, this.rowHeaderLayer, 0, 1);

        this.bodyLayer = new ViewportLayerFixture(width, height);
        setChildLayer(GridRegion.BODY, this.bodyLayer, 1, 1);
    }
}
