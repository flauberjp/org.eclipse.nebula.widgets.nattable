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
package org.eclipse.nebula.widgets.nattable.data.validate;

public class DefaultNumericDataValidator extends DataValidator {

    @Override
    public boolean validate(int columnIndex, int rowIndex, Object newValue) {
        try {
            if (newValue != null) {
                Double.valueOf(newValue.toString());
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
