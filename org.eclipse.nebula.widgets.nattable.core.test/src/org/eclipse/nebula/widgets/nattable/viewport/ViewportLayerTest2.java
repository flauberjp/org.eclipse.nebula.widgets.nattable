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
package org.eclipse.nebula.widgets.nattable.viewport;

import org.eclipse.nebula.widgets.nattable.test.LayerAssert;
import org.eclipse.nebula.widgets.nattable.test.fixture.TestLayer;
import org.eclipse.nebula.widgets.nattable.util.IClientAreaProvider;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Before;
import org.junit.Test;

public class ViewportLayerTest2 {

    private TestLayer dataLayer;
    private ViewportLayer viewportLayer;

    @Before
    public void setup() {
        String columnInfo = "0:0;100 | 1:1;100 | 2:2;100 | 3:3;100";
        String rowInfo = "0:0;40  | 1:1;40  | 2:2;40  | 3:3;40";

        String cellInfo = "A0 | B0 | C0 | D0 \n" + "A1 | B1 | C1 | D1 \n"
                + "A2 | B2 | C2 | D2 \n" + "A3 | B3 | C3 | D3 \n";

        this.dataLayer = new TestLayer(4, 4, columnInfo, rowInfo, cellInfo);

        this.viewportLayer = new ViewportLayer(this.dataLayer);
        this.viewportLayer.setClientAreaProvider(new IClientAreaProvider() {

            @Override
            public Rectangle getClientArea() {
                return new Rectangle(0, 0, 200, 400);
            }

        });
    }

    @Test
    public void testScrollRight() {
        this.viewportLayer.setOriginX(this.viewportLayer.getStartXOfColumnPosition(2));

        String columnInfo = "2:2;100 | 3:3;100";
        String rowInfo = "0:0;40  | 1:1;40  | 2:2;40  | 3:3;40";

        String cellInfo = "C0 | D0 \n" + "C1 | D1 \n" + "C2 | D2 \n"
                + "C3 | D3 \n";

        TestLayer expectedLayer = new TestLayer(2, 4, 400, 4, 4, 160,
                columnInfo, rowInfo, cellInfo);

        LayerAssert.assertLayerEquals(expectedLayer, this.viewportLayer);
    }

}
