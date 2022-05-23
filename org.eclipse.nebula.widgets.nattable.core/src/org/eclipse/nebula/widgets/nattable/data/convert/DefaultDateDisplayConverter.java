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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.nebula.widgets.nattable.Messages;
import org.eclipse.nebula.widgets.nattable.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a java.util.Date object to a given format and vice versa
 */
public class DefaultDateDisplayConverter extends DisplayConverter {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDateDisplayConverter.class);

    private SimpleDateFormat dateFormat;

    /**
     * Convert {@link Date} to {@link String} using the default format from
     * {@link SimpleDateFormat}
     */
    public DefaultDateDisplayConverter() {
        this(null, null);
    }

    public DefaultDateDisplayConverter(TimeZone timeZone) {
        this(null, timeZone);
    }

    /**
     * @param dateFormat
     *            as specified in {@link SimpleDateFormat}
     */
    public DefaultDateDisplayConverter(String dateFormat) {
        this(dateFormat, null);
    }

    public DefaultDateDisplayConverter(String dateFormat, TimeZone timeZone) {
        if (dateFormat != null) {
            this.dateFormat = new SimpleDateFormat(dateFormat);
        } else {
            this.dateFormat = new SimpleDateFormat();
        }

        if (timeZone != null) {
            this.dateFormat.setTimeZone(timeZone);
        }
    }

    @Override
    public Object canonicalToDisplayValue(Object canonicalValue) {
        try {
            if (ObjectUtils.isNotNull(canonicalValue)) {
                return this.dateFormat.format(canonicalValue);
            }
        } catch (Exception e) {
            LOG.warn("Error on conversion", e); //$NON-NLS-1$
        }
        return canonicalValue;
    }

    @Override
    public Object displayToCanonicalValue(Object displayValue) {
        try {
            return this.dateFormat.parse(displayValue.toString());
        } catch (Exception e) {
            throw new ConversionFailedException(Messages.getString("DefaultDateDisplayConverter.failure", //$NON-NLS-1$
                    displayValue, this.dateFormat.toPattern()), e);
        }
    }

}
