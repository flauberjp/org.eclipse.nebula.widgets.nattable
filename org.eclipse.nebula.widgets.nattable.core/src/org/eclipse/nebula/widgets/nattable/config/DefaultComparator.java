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
package org.eclipse.nebula.widgets.nattable.config;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class DefaultComparator implements Comparator<Object> {

    private static DefaultComparator singleton;

    public static final DefaultComparator getInstance() {
        if (singleton == null) {
            singleton = new DefaultComparator();
        }
        return singleton;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public int compare(final Object o1, final Object o2) {
        if (o1 == null) {
            if (o2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else if (o2 == null) {
            return 1;
        } else if (o1 instanceof Comparable && o2 instanceof Comparable) {
            return ((Comparable) o1).compareTo(o2);
        } else {
            return o1.toString().compareTo(o2.toString());
        }
    }

}
