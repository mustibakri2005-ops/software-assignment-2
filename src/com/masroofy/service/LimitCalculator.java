package com.masroofy.service;

/**
 * Centralises all budget formulas so screens and repositories do not contain calculation rules.
 */
public class LimitCalculator {
    /** Calculates the first daily limit from allowance and number of days. */
    public double calculateInitialLimit(double allowance, long days) {
        if (days <= 0) throw new IllegalArgumentException("Days must be positive.");
        return roundMoney(allowance / days);
    }

    /** Recalculates the daily limit after spending or daily rollover. */
    public double recalculateLimit(double remainingBalance, long remainingDays) {
        if (remainingDays <= 0) return 0.0;
        return roundMoney(remainingBalance / remainingDays);
    }

    /** Calculates what percentage of the original allowance has been spent. */
    public double calculateSpentRatio(double spent, double allowance) {
        if (allowance <= 0) return 0.0;
        return spent / allowance;
    }

    private double roundMoney(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
