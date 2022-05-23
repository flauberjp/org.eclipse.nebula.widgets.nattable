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
package org.eclipse.nebula.widgets.nattable.resize.event;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.test.fixture.layer.BaseColumnReorderLayerFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.DataLayerFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.LayerListenerFixture;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/*
 * This test was for selective repainting during resize i.e paint the bare minimum needed.
 * This was done for performance reasons. This approach to performance has been abandoned.
 * This would be a useful thing to do but is on low priority now. Hence these tests will
 * be ignored till we pick this up again.
 */
@Ignore
public class ResizingVisibleReorderedColumnsTest {

    private LayerListenerFixture layerListener;

    private DataLayerFixture dataLayer;

    private BaseColumnReorderLayerFixture reorderLayer;

    @Before
    public void setUp() {
        this.dataLayer = new DataLayerFixture(100, 40);
        this.reorderLayer = new BaseColumnReorderLayerFixture(this.dataLayer);
        this.layerListener = new LayerListenerFixture();
        this.reorderLayer.addLayerListener(this.layerListener);
    }

    @Test
    public void changeShouldIncludeLastColumn() {
        // Reorder columns should now be 0, 2, 3, 4, 1
        this.reorderLayer.reorderColumnPosition(1, 4);

        // Resize last column
        this.dataLayer.setColumnWidthByPosition(1, 200);

        // The changed position rectangle should just have one column, and the
        // column position should be the last column (4)
        Rectangle expectedRectangle = new Rectangle(4, 0, 1, 7);
        Collection<Rectangle> actualRectangles = ((ColumnResizeEvent) this.layerListener
                .getReceivedEvent(ColumnResizeEvent.class))
                        .getChangedPositionRectangles();
        assertEquals(expectedRectangle, actualRectangles.iterator().next());
    }

    @Test
    public void changeShouldIncludeHalfOfGrid() {
        // Reorder columns should now be 0, 1, 3, 2, 4
        this.reorderLayer.reorderColumnPosition(3, 2);

        // Resize last column
        this.dataLayer.setColumnWidthByPosition(3, 200);

        // The changed position rectangle should just have one column, and the
        // column position should be the last column (4)
        Rectangle expectedRectangle = new Rectangle(2, 0, 3, 7);
        Collection<Rectangle> actualRectangles = ((ColumnResizeEvent) this.layerListener
                .getReceivedEvent(ColumnResizeEvent.class))
                        .getChangedPositionRectangles();
        assertEquals(expectedRectangle, actualRectangles.iterator().next());
    }

    @Test
    public void changeShouldIncludeAllColumns() {
        // Reorder columns again, should now be 3, 0, 1, 2, 4
        this.reorderLayer.reorderColumnPosition(3, 0);

        // Resize first column
        this.dataLayer.setColumnWidthByPosition(3, 200);

        // The changed position rectangle should now be the entire grid
        Rectangle expectedRectangle = new Rectangle(0, 0, 5, 7);
        Collection<Rectangle> actualRectangles = ((ColumnResizeEvent) this.layerListener
                .getReceivedEvent(ColumnResizeEvent.class))
                        .getChangedPositionRectangles();
        assertEquals(expectedRectangle, actualRectangles);
    }
}
