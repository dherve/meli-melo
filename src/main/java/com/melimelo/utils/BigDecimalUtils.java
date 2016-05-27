package com.melimelo.utils;

import java.math.BigDecimal;

/**
 * Provide methods to perform mathematical operation on BigDecimal numbers.
 */
public final class BigDecimalUtils {

    /**
     * The infinity is defined as BigDecimal with the value =
     * {@link Double.MAX_VALUE} *{@link Double.MAX_VALUE}. Java doesn't define
     * BigDecimal.MAX_VALUE, so we needed a marker value that can be used for an
     * "infinite" big decimal. The assumption is that within the context usage,
     * the values should be below the proposed values. Rigorously speaking, this
     * is not accurate, as BigDecimal number can have really bigger numbers.
     */
    public static final BigDecimal INFINITY = BigDecimal
            .valueOf(Double.MAX_VALUE)
            .multiply(BigDecimal.valueOf(Double.MAX_VALUE));

    private BigDecimalUtils() {
        throw new AssertionError("Howdy! What are you trying to do ?");
    }

    /**
     * Check if a big decimal number is negative.
     * 
     * @param number the big decimal number to check.
     * @return true if the provided number is negative, false otherwise.
     */
    public static final boolean isNegative(final BigDecimal number) {
        return number.signum() == -1;
    }

    /**
     * Compute the sum of 2 big decimal numbers. Handle the case where one of
     * the number is {@link BigDecimalUtils#INFINITY}
     * 
     * @param number1 the first number to add
     * @param number2 the second number to add.
     * @return the sum of the provided number, {@link BigDecimalUtils#INFINITY}
     *         if one of the number is {@link BigDecimalUtils#INFINITY}
     */
    public static final BigDecimal sum(BigDecimal number1, BigDecimal number2) {
        if (isInfinite(number1) || isInfinite(number2)) {
            return INFINITY;
        }
        return number1.add(number2);
    }

    /**
     * Check if a number if infinity.
     * 
     * @param value the value of the number to check.
     * @return true if the number is infinity
     */
    public static final boolean isInfinite(BigDecimal value) {
        return INFINITY.compareTo(value) < 1;
    }

    /**
     * Check if the first value is bigger than the second value.
     * 
     * @param firstValue the first number to check.
     * @param secondValue the second number to check.
     * @return true if the first number is bigger, false otherwise.
     */
    public static final boolean isBigger(final BigDecimal firstValue,
            final BigDecimal secondValue) {
        return firstValue.compareTo(secondValue) == 1;
    }

    /**
     * Find the maximum number between two big decimal numbers.
     * 
     * @param firstValue the first big decimal number.
     * @param secondValue the second big decimal number.
     * @return the biggest number of the two numbers provided.
     */
    public static final BigDecimal max(final BigDecimal firstValue,
            final BigDecimal secondValue) {
        return isBigger(firstValue, secondValue) ? firstValue : secondValue;
    }

    /**
     * Find the minimum number between two big decimal numbers.
     * 
     * @param firstValue the first big decimal number.
     * @param secondValue the second big decimal number.
     * @return the smallest number of the two numbers provided.
     */
    public static final BigDecimal min(final BigDecimal firstValue,
            final BigDecimal secondValue) {
        return isBigger(firstValue, secondValue) ? secondValue : firstValue;
    }

}
