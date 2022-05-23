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
package org.eclipse.nebula.widgets.nattable.group.action;

import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.test.fixture.group.ColumnGroupModelFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.DataLayerFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColumnGroupReorderDragModeTest {

    private ILayer testLayer;
    private ColumnGroupModel columnGroupModel;
    private ColumnGroupHeaderReorderDragMode groupReorderDragMode;

    @Before
    public void setup() {
        this.testLayer = new DataLayerFixture(10, 5, 100, 20);
        this.columnGroupModel = new ColumnGroupModelFixture();
        this.groupReorderDragMode = new ColumnGroupHeaderReorderDragMode(
                this.columnGroupModel);
    }

    /*
     * Test Fixture
     *
     * 0 1 2 3 4 5 6 7 ... 10 11 12
     * ------------------------------------------------------------------ |<- G1
     * ->| |<-- G2 -->| |<--- G3 --->|
     */

    @Test
    public void isValidTargetColumnPositionMovingRight() throws Exception {
        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 0, 0));
        Assert.assertFalse(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 0, 1));

        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 0, 2));

        Assert.assertFalse(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 0, 3));
        Assert.assertFalse(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 0, 4));

        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 0, 5));
        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 0, 6));
    }

    @Test
    public void isValidTargetColumnPositionMovingLeft() throws Exception {
        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 10));

        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 9));
        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 6));
        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 5));

        Assert.assertFalse(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 4));
        Assert.assertFalse(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 3));

        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 2));

        Assert.assertFalse(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 1));
        // it is allowed to reorder column 11 to 0, because that means to
        // reorder G3 to the beginning
        Assert.assertTrue(this.groupReorderDragMode.isValidTargetColumnPosition(
                this.testLayer, 11, 0));
    }
}
