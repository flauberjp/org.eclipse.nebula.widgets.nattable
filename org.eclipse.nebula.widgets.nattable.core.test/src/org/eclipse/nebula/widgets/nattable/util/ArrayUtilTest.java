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
package org.eclipse.nebula.widgets.nattable.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ArrayUtilTest {

    @Test
    public void isEmpty() {
        assertTrue(ArrayUtil.isEmpty(new int[] {}));
        assertFalse(ArrayUtil.isEmpty(new int[] { 100 }));

        assertTrue(ArrayUtil.isEmpty(new String[] {}));
        assertFalse(ArrayUtil.isEmpty(new String[] { "S" }));
    }

    @Test
    public void isNotEmpty() {
        assertTrue(ArrayUtil.isNotEmpty(new int[] { 0 }));
        assertFalse(ArrayUtil.isNotEmpty(new int[] {}));

        assertTrue(ArrayUtil.isNotEmpty(new String[] { "S" }));
        assertFalse(ArrayUtil.isNotEmpty(new String[] {}));
    }

    @Test
    public void conversionToIntArray() throws Exception {
        List<Integer> list = new LinkedList<Integer>();
        list.add(20);
        list.add(10);
        list.add(25);
        list.add(15);

        int[] intArray = ArrayUtil.asIntArray(list);
        assertEquals(4, intArray.length);

        assertEquals(20, intArray[0]);
        assertEquals(10, intArray[1]);
        assertEquals(25, intArray[2]);
        assertEquals(15, intArray[3]);

    }
}
