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
 *
 *****************************************************************************/
package org.eclipse.nebula.widgets.nattable.formula.function;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FunctionTest {

    @Test
    public void shouldSumEmptyValues() {
        FunctionValue function = new SumFunction(new ArrayList<FunctionValue>());
        assertEquals(new BigDecimal(0), function.getValue());
    }

    @Test
    public void shouldSumSingleValue() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue function = new SumFunction(values);
        assertEquals(new BigDecimal(42), function.getValue());
    }

    @Test
    public void shouldSumTwoValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        values.add(new BigDecimalFunctionValue(23));
        FunctionValue function = new SumFunction(values);
        assertEquals(new BigDecimal(65), function.getValue());
    }

    @Test
    public void shouldSumMultipleValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(3));
        values.add(new BigDecimalFunctionValue(12));
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue function = new SumFunction(values);
        assertEquals(new BigDecimal(5 + 3 + 12 + 42), function.getValue());
    }

    @Test
    public void shouldSumMultipleSumFunctions() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(3));
        FunctionValue function1 = new SumFunction(values);

        values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(12));
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue function2 = new SumFunction(values);

        FunctionValue function = new SumFunction(Arrays.asList(function1, function2));
        assertEquals(new BigDecimal(5 + 3 + 12 + 42), function.getValue());
    }

    @Test
    public void shouldAvgEmptyValues() {
        FunctionValue function = new AverageFunction(new ArrayList<FunctionValue>());
        assertEquals(new BigDecimal(0), function.getValue());
    }

    @Test
    public void shouldAvgSingleValue() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue function = new AverageFunction(values);
        assertEquals(new BigDecimal(42), function.getValue());
    }

    @Test
    public void shouldAvgTwoValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        values.add(new BigDecimalFunctionValue(23));
        FunctionValue function = new AverageFunction(values);
        assertEquals(BigDecimal.valueOf(32.5), function.getValue());
    }

    @Test
    public void shouldAvgMultipleValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5d));
        values.add(new BigDecimalFunctionValue(3));
        values.add(new BigDecimalFunctionValue("12"));
        values.add(new BigDecimalFunctionValue(new BigDecimal(40)));
        FunctionValue function = new AverageFunction(values);
        assertEquals(BigDecimal.valueOf((5d + 3 + 12 + 40) / 4), function.getValue());
    }

    @Test
    public void shouldAvgNonTerminal() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(3));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new AverageFunction(values);
        assertEquals(new BigDecimal("3.333333333"), function.getValue());
    }

    @Test
    public void shouldMultiplyEmptyValues() {
        FunctionValue function = new ProductFunction(new ArrayList<FunctionValue>());
        assertEquals(new BigDecimal(0), function.getValue());
    }

    @Test
    public void shouldMultiplySingleValue() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue fFunction = new ProductFunction(values);
        assertEquals(new BigDecimal(42), fFunction.getValue());
    }

    @Test
    public void shouldMultiplyTwoValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(4));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new ProductFunction(values);
        assertEquals(new BigDecimal(8), function.getValue());
    }

    @Test
    public void shouldMultiplyMultipleValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(3));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new ProductFunction(values);
        assertEquals(new BigDecimal(5 * 3 * 2), function.getValue());
    }

    @Test
    public void shouldDivideEmptyValues() {
        FunctionValue function = new QuotientFunction(new ArrayList<FunctionValue>());
        assertEquals(new BigDecimal(0), function.getValue());
    }

    @Test
    public void shouldDivideSingleValue() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue fFunction = new QuotientFunction(values);
        assertEquals(new BigDecimal(42), fFunction.getValue());
    }

    @Test
    public void shouldDivideTwoValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(4));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new QuotientFunction(values);
        assertEquals(new BigDecimal(2), function.getValue());
    }

    @Test
    public void shouldDivideMultipleValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(20));
        values.add(new BigDecimalFunctionValue(4));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new QuotientFunction(values);
        assertEquals(BigDecimal.valueOf(20d / 4d / 2d), function.getValue());
    }

    @Test
    public void shouldDivideNonTerminating() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(-3));
        FunctionValue function = new QuotientFunction(values);
        assertEquals(new BigDecimal(5).divide(new BigDecimal(-3), 9, RoundingMode.HALF_UP), function.getValue());
    }

    @Test(expected = FunctionException.class)
    public void shouldThrowExceptionOnDivisionByZero() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(0));
        FunctionValue function = new QuotientFunction(values);
        assertEquals(new BigDecimal(5), function.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNegateEmptyValues() {
        FunctionValue function = new NegateFunction(new ArrayList<FunctionValue>());
        assertEquals(new BigDecimal(0), function.getValue());
    }

    @Test
    public void shouldNegateSingleValue() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue function = new NegateFunction(values);
        assertEquals(new BigDecimal(-42), function.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNegateTwoValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(4));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new NegateFunction(values);
        assertEquals(new BigDecimal(2), function.getValue());
    }

    @Test
    public void shouldCalculateNestedFunctions() {
        List<FunctionValue> productValues = new ArrayList<>();
        productValues.add(new BigDecimalFunctionValue(4));
        productValues.add(new BigDecimalFunctionValue(5));
        FunctionValue productFunction = new ProductFunction(productValues);

        List<FunctionValue> sumValues = new ArrayList<>();
        sumValues.add(productFunction);
        sumValues.add(new BigDecimalFunctionValue(5));
        FunctionValue sumFunction = new SumFunction(sumValues);

        List<FunctionValue> divValues = new ArrayList<>();
        divValues.add(sumFunction);
        divValues.add(new BigDecimalFunctionValue(5));
        FunctionValue divFunction = new QuotientFunction(divValues);

        FunctionValue negFunction = new NegateFunction(divFunction);

        assertEquals(new BigDecimal(-5), negFunction.getValue());
    }

    @Test
    public void shouldPowerEmptyValues() {
        FunctionValue function = new PowerFunction(new ArrayList<FunctionValue>());
        assertEquals(new BigDecimal(0), function.getValue());
    }

    @Test
    public void shouldPowerSingleValue() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(42));
        FunctionValue function = new PowerFunction(values);
        assertEquals(new BigDecimal(42), function.getValue());
    }

    @Test
    public void shouldPowerTwoValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new PowerFunction(values);
        assertEquals(new BigDecimal(25), function.getValue());
    }

    @Test
    public void shouldPowerMultipleValues() {
        List<FunctionValue> values = new ArrayList<>();
        values.add(new BigDecimalFunctionValue(5));
        values.add(new BigDecimalFunctionValue(3));
        values.add(new BigDecimalFunctionValue(2));
        FunctionValue function = new PowerFunction(values);
        assertEquals(new BigDecimal(15625), function.getValue());
    }
}
