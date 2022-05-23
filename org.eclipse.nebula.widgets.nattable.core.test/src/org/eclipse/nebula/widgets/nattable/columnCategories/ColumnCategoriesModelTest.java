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
package org.eclipse.nebula.widgets.nattable.columnCategories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.nebula.widgets.nattable.test.fixture.ColumnCategoriesModelFixture;
import org.junit.Before;
import org.junit.Test;

public class ColumnCategoriesModelTest {

    private ColumnCategoriesModel model;

    @Before
    public void setup() {
        this.model = new ColumnCategoriesModelFixture();
    }

    @Test
    public void shouldCreateModelCorrectly() throws Exception {
        assertEquals(6, this.model.getRootCategory().getNumberOfChildren());
        assertEquals(
                "[{ROOT,Root,[17,18,19,a,b,c]}, "
                        + "{COLUMN,17,[]}, {COLUMN,18,[]}, {COLUMN,19,[]}, "
                        + "{CATEGORY,a,[0,2,3,4,5,6]}, {COLUMN,0,[]}, {COLUMN,2,[]}, {COLUMN,3,[]}, {COLUMN,4,[]}, {COLUMN,5,[]}, {COLUMN,6,[]}, "
                        + "{CATEGORY,b,[b1,b2]}, {CATEGORY,b1,[7,8]}, {COLUMN,7,[]}, {COLUMN,8,[]}, "
                        + "{CATEGORY,b2,[9,10,11]}, {COLUMN,9,[]}, {COLUMN,10,[]}, {COLUMN,11,[]}, "
                        + "{CATEGORY,c,[12,13,14,15,16]}, {COLUMN,12,[]}, {COLUMN,13,[]}, {COLUMN,14,[]}, {COLUMN,15,[]}, {COLUMN,16,[]}]",
                this.model.toString());
    }

    @Test
    public void removeColumnIndexesFromModel() throws Exception {
        assertEquals(6, this.model.getRootCategory().getNumberOfChildren());
        this.model.removeColumnIndex(17);
        assertEquals(5, this.model.getRootCategory().getNumberOfChildren());
    }

    @Test
    public void rootMustBeTheFirstNodeSet() throws Exception {
        boolean caughtEx = false;
        try {
            ColumnCategoriesModel model = new ColumnCategoriesModel();
            model.addCategory(new Node("1"), "1a");
        } catch (Exception e) {
            caughtEx = true;
        }
        assertTrue(caughtEx);
    }

    @Test
    public void rootCanOnlyBeSetOnce() throws Exception {
        boolean caughtEx = false;
        try {
            ColumnCategoriesModel model = new ColumnCategoriesModel();
            model.addRootCategory("Root");
            model.addRootCategory("Root");
        } catch (Exception e) {
            caughtEx = true;
            assertEquals(
                    "Root has been set already. Clear using (clear()) to reset.",
                    e.getMessage());
        }
        assertTrue(caughtEx);

        this.model.clear();
        this.model.addRootCategory("Root");
    }
}
