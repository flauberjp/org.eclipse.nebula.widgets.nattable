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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.nebula.widgets.nattable.test.fixture.layer.ViewportLayerFixture;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ScrollBar;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Horizontal scrolling of the viewport.
 *
 * The <code>DataLayerFixture</code> has the following config. Used by all
 * tests. The viewport is 200px wide.
 *
 * Col pos 0 1 2 3 4 ------ ---- - ---- --- Col width 150 100 35 100 80
 */
public class HorizontalScrollBarHandlerTest {

    ViewportLayerFixture viewport;
    private ScrollBar scrollBar;
    private HorizontalScrollBarHandler scrollHandler;

    @Before
    public void init() {
        this.viewport = new ViewportLayerFixture();
        this.scrollBar = ViewportLayerFixture.DEFAULT_SCROLLABLE.getHorizontalBar();
        this.scrollHandler = new HorizontalScrollBarHandler(this.viewport, this.scrollBar);

        assertEquals(0, this.viewport.getColumnIndexByPosition(0));
        assertEquals(1, this.viewport.getColumnIndexByPosition(1));
    }

    private void scrollViewportByOffset(int offset) {
        this.scrollHandler.setViewportOrigin(this.viewport.getOrigin().getX() + offset);
    }

    private void scrollViewportToPixel(int x) {
        this.scrollHandler.setViewportOrigin(x);
    }

    @Test
    public void scrollViewportLeftByPage() throws Exception {
        this.viewport.moveColumnPositionIntoViewport(3);
        assertEquals(1, this.viewport.getColumnIndexByPosition(0));

        this.viewport.moveColumnPositionIntoViewport(4);
        assertEquals(2, this.viewport.getColumnIndexByPosition(0));
    }

    @Test
    public void scrollViewportLeftByOffset() throws Exception {
        // Origin adjusted
        this.viewport.setOriginX(this.viewport.getStartXOfColumnPosition(2));
        scrollViewportByOffset(-1);
        assertEquals(1, this.viewport.getColumnIndexByPosition(0));

        this.viewport.setOriginX(this.viewport.getStartXOfColumnPosition(1));
        scrollViewportByOffset(-1);
        assertEquals(0, this.viewport.getColumnIndexByPosition(0));
    }

    @Test
    public void scrollViewportRightByOffset() throws Exception {
        scrollViewportByOffset(200);
        assertEquals(1, this.viewport.getColumnIndexByPosition(0));

        scrollViewportByOffset(200);
        assertEquals(2, this.viewport.getColumnIndexByPosition(0));
    }

    @Test
    public void dragRight() throws Exception {
        scrollViewportToPixel(300);
        assertEquals(2, this.viewport.getColumnIndexByPosition(0));
        assertEquals(3, this.viewport.getColumnIndexByPosition(1));
    }

    @Test
    public void dragLeft() throws Exception {
        // Origin adjusted
        this.viewport.moveColumnPositionIntoViewport(3);
        assertEquals(1, this.viewport.getColumnIndexByPosition(0));

        scrollViewportToPixel(50);
        assertEquals(0, this.viewport.getColumnIndexByPosition(0));
    }

    /*
     * Test for issue reported in http://nattable.org/jira/browse/NTBL-99.
     * Resizing the last column to be larger than the width of a table and
     * scrolling to the right results in a all white background and no columns
     *
     * COLUMNS 0 1 |------|------| 250 250
     */
    @Test
    public void issueNTBL99MoveByColumn() throws Exception {
        this.viewport = new ViewportLayerFixture(2, 1, 250, 40);
        this.scrollHandler = new HorizontalScrollBarHandler(this.viewport, this.scrollBar);

        assertEquals(200, this.viewport.getClientAreaWidth());
        assertEquals(1, this.viewport.getColumnCount());

        assertEquals(0, this.viewport.getColumnIndexByPosition(0));

        scrollViewportByOffset(200);
        assertEquals(0, this.viewport.getColumnIndexByPosition(0));

        scrollViewportByOffset(200);
        assertEquals(1, this.viewport.getColumnIndexByPosition(0));

        // No more scrolling
        scrollViewportByOffset(200);
        assertEquals(1, this.viewport.getColumnIndexByPosition(0));
    }

    @Test
    public void issueNTBL99MoveByPage() throws Exception {
        this.viewport = new ViewportLayerFixture(2, 1, 250, 40);
        this.scrollHandler = new HorizontalScrollBarHandler(this.viewport, this.scrollBar);

        assertEquals(200, this.viewport.getClientAreaWidth());
        assertEquals(1, this.viewport.getColumnCount());

        assertEquals(0, this.viewport.getColumnIndexByPosition(0));
    }

    @Test
    public void horizontalScrollbarThumbSize() throws Exception {
        this.viewport = new ViewportLayerFixture(new Rectangle(0, 0, 250, 100));
        this.scrollHandler = new HorizontalScrollBarHandler(this.viewport, this.scrollBar);

        assertEquals(250, this.viewport.getWidth());
        this.scrollHandler.recalculateScrollBarSize();

        // Fixture data - viewport (250px), scrollable(465px)
        assertEquals(250, this.scrollHandler.scroller.getThumb());

        this.viewport.moveColumnPositionIntoViewport(9);
        assertEquals(250, this.scrollHandler.scroller.getThumb());
    }

    @Test
    public void horizontalScrollbarThumbSizeCalcNoScrollingNeeded()
            throws Exception {
        this.viewport = new ViewportLayerFixture(new Rectangle(0, 0, 500, 500));
        this.scrollHandler = new HorizontalScrollBarHandler(this.viewport, this.scrollBar);

        this.scrollHandler.recalculateScrollBarSize();
        assertEquals(465, this.viewport.getWidth());

        assertEquals(465, this.scrollHandler.scroller.getThumb());
        assertFalse(this.scrollBar.isEnabled());
        assertFalse(this.scrollBar.isVisible());
    }

}
