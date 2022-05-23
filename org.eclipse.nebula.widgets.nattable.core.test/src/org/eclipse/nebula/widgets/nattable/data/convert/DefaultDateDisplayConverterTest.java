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
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class DefaultDateDisplayConverterTest {

    private static final TimeZone utc = TimeZone.getTimeZone("UTC");
    private static final Date FROZEN_DATE = new Date(0);

    @Test
    public void happyPath() throws Exception {
        DefaultDateDisplayConverter converter = new DefaultDateDisplayConverter("yyyy.MM.dd HH:mm:ss", utc);
        assertEquals("1970.01.01 00:00:00", converter.canonicalToDisplayValue(FROZEN_DATE));
        assertEquals(FROZEN_DATE, converter.displayToCanonicalValue("1970.01.01 00:00:00"));
    }

    @Test
    public void defaultDisplayFormat() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(utc);

        DefaultDateDisplayConverter converter = new DefaultDateDisplayConverter(utc);
        assertEquals(sdf.format(FROZEN_DATE), converter.canonicalToDisplayValue(FROZEN_DATE));
        assertEquals(FROZEN_DATE, converter.displayToCanonicalValue(sdf.format(FROZEN_DATE)));
    }

    @Test
    public void invalidDataType() throws Exception {
        DefaultDateDisplayConverter converter = new DefaultDateDisplayConverter();

        System.err.println("** THE FOLLOWING STACK TRACE IS EXPECTED **");
        assertEquals("XXX", converter.canonicalToDisplayValue("XXX"));

        try {
            converter.displayToCanonicalValue("AAA");
            fail("ConversionFailedException should have been throwed");
        } catch (ConversionFailedException e) {
        }

    }
}
