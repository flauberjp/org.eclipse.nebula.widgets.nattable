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
package org.eclipse.nebula.widgets.nattable.reorder.command;

import static org.junit.Assert.assertEquals;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.DataLayerFixture;
import org.junit.Before;
import org.junit.Test;

public class ReorderColumnCommandTest {

    private ColumnReorderLayer columnReorderLayer;

    @Before
    public void setup() {
        this.columnReorderLayer = new ColumnReorderLayer(new DataLayerFixture());
    }

    @Test
    public void testReorderColumnCommand() {
        int fromColumnPosition = 4;
        int toColumnPosition = 1;
        ILayerCommand reorderColumnCommand = new ColumnReorderCommand(
                this.columnReorderLayer, fromColumnPosition, toColumnPosition);

        this.columnReorderLayer.doCommand(reorderColumnCommand);

        assertEquals(0, this.columnReorderLayer.getColumnIndexByPosition(0));
        assertEquals(4, this.columnReorderLayer.getColumnIndexByPosition(1));
        assertEquals(1, this.columnReorderLayer.getColumnIndexByPosition(2));
        assertEquals(2, this.columnReorderLayer.getColumnIndexByPosition(3));
        assertEquals(3, this.columnReorderLayer.getColumnIndexByPosition(4));
    }

}
