/*****************************************************************************
 * Copyright (c) 2015, 2020 CEA LIST.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *****************************************************************************/
package org.eclipse.nebula.widgets.nattable.formula.function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Returns the average of a list of supplied numbers.
 *
 * @since 1.4
 */
public class AverageFunction extends SumFunction {

    public AverageFunction() {
        super();
    }

    public AverageFunction(List<FunctionValue> values) {
        super(values);
    }

    @Override
    public BigDecimal getValue() {
        BigDecimal sum = super.getValue();
        if (!this.values.isEmpty()) {
            try {
                return sum.divide(new BigDecimal(this.values.size()));
            } catch (ArithmeticException e) {
                if (e.getMessage().startsWith("Non-terminating")) { //$NON-NLS-1$
                    return sum.divide(new BigDecimal(this.values.size()), 9, RoundingMode.HALF_UP);
                } else {
                    throw e;
                }
            }
        }
        return sum;
    }

}
