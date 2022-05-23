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
package org.eclipse.nebula.widgets.nattable.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class DefaultCharacterDisplayConverterTest {

    private DefaultCharacterDisplayConverter characterConverter = new DefaultCharacterDisplayConverter();

    @Test
    public void testNonNullDataToDisplay() {
        assertEquals("a", this.characterConverter.canonicalToDisplayValue('a'));
        assertEquals("1", this.characterConverter.canonicalToDisplayValue('1'));
    }

    @Test
    public void testNullDataToDisplay() {
        assertEquals("", this.characterConverter.canonicalToDisplayValue(null));
    }

    @Test
    public void testNonNullDisplayToData() {
        assertEquals('a', this.characterConverter.displayToCanonicalValue("a"));
        assertEquals('1', this.characterConverter.displayToCanonicalValue("1"));
    }

    @Test
    public void testNullDisplayToData() {
        assertNull(this.characterConverter.displayToCanonicalValue(""));
    }

    @Test(expected = ConversionFailedException.class)
    public void testConversionException() {
        this.characterConverter.displayToCanonicalValue("abc");
    }

}
