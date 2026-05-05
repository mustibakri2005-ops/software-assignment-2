package com.masroofy.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Represents one active allowance/budget cycle.
 */
public class BudgetCycle {
    private final String id;
    private final double allowance;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private double remainingBalance;
    private double dailyLimit;
    private LocalDate lastRolloverDate;
    private boolean active;

    /**
     * Creates a new active budget cycle.
     *
     * @param allowance total allowance for the cycle
     * @param startDate first day of the cycle
     * @param endDate last day of the cycle
     * @param initialDailyLimit calculated daily limit
     */
    public BudgetCycle(double allowance, LocalDate startDate, LocalDate endDate, double initialDailyLimit) {
        if (allowance <= 0) throw new IllegalArgumentException("Allowance must be greater than zero.");
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("End date cannot be before start date.");
        this.id = UUID.randomUUID().toString();
        this.allowance = allowance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingBalance = allowance;
        this.dailyLimit = initialDailyLimit;
        this.lastRolloverDate = startDate;
        this.active = true;
    }

    /** Deducts an expense amount from the remaining balance. */
    public void applyExpense(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Expense amount must be positive.");
        remainingBalance -= amount;
    }

    /** Updates the cycle after a new day is detected. */
    public void applyRollover(LocalDate newDate, double recalculatedLimit) {
        this.lastRolloverDate = newDate;
        this.dailyLimit = recalculatedLimit;
        if (newDate.isAfter(endDate)) active = false;
    }

    /** @return remaining days including the current day */
    public long daysRemaining(LocalDate currentDate) {
        if (currentDate.isAfter(endDate)) return 0;
        return ChronoUnit.DAYS.between(currentDate, endDate) + 1;
    }

    /** @return true if the cycle end date has passed */
    public boolean isExpired(LocalDate currentDate) { return currentDate.isAfter(endDate); }

    /** @return unique cycle id */
    public String getId() { return id; }
    /** @return original allowance */
    public double getAllowance() { return allowance; }
    /** @return start date */
    public LocalDate getStartDate() { return startDate; }
    /** @return end date */
    public LocalDate getEndDate() { return endDate; }
    /** @return remaining balance */
    public double getRemainingBalance() { return remainingBalance; }
    /** @return current daily limit */
    public double getDailyLimit() { return dailyLimit; }
    /** @return last date rollover was applied */
    public LocalDate getLastRolloverDate() { return lastRolloverDate; }
    /** @return whether this cycle is active */
    public boolean isActive() { return active; }
    /** Sets the active flag. */
    public void setActive(boolean active) { this.active = active; }
}
