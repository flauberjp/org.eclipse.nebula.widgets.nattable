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

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.Test;

public class DefaultShortDisplayConverterTest {

    private DefaultShortDisplayConverter shortConverter = new DefaultShortDisplayConverter();

    @Test
    public void testNonNullDataToDisplay() {
        assertEquals("123", this.shortConverter.canonicalToDisplayValue(Short.valueOf("123")));
    }

    @Test
    public void testNullDataToDisplay() {
        assertEquals(null, this.shortConverter.canonicalToDisplayValue(null));
    }

    @Test
    public void testNonNullDisplayToData() {
        assertEquals(Short.valueOf("123"), this.shortConverter.displayToCanonicalValue("123"));
    }

    @Test
    public void testNullDisplayToData() {
        assertEquals(null, this.shortConverter.displayToCanonicalValue(""));
    }

    @Test(expected = ConversionFailedException.class)
    public void testConversionException() {
        this.shortConverter.displayToCanonicalValue("abc");
    }

    @Test(expected = ConversionFailedException.class)
    public void testConversionExceptionTooBig() {
        this.shortConverter.setNumberFormat(null);
        this.shortConverter.displayToCanonicalValue("32768");
    }

    @Test
    public void testConvertLocalized() {
        this.shortConverter.setNumberFormat(NumberFormat.getInstance(Locale.ENGLISH));
        assertEquals(Short.valueOf("1234"), this.shortConverter.displayToCanonicalValue("1,234"));
        assertEquals("1,234", this.shortConverter.canonicalToDisplayValue(Short.valueOf("1234")));
    }

    @Test(expected = ConversionFailedException.class)
    public void testFailConvertLocalized() {
        this.shortConverter.setNumberFormat(null);
        assertEquals(Short.valueOf("1234"), this.shortConverter.displayToCanonicalValue("1,234"));
    }

    @Test
    public void testConvertNonLocalized() {
        this.shortConverter.setNumberFormat(null);
        assertEquals("1234", this.shortConverter.canonicalToDisplayValue(Short.valueOf("1234")));
    }
}
