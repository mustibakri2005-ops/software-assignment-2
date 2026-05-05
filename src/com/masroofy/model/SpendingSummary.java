package com.masroofy.model;

import java.util.List;
import java.util.Map;

/**
 * Dashboard value object containing the calculated information shown to the user.
 */
public class SpendingSummary {
    private final double dailyLimit;
    private final double spentRatio;
    private final Map<Category, Double> categoryTotals;
    private final List<Transaction> recentTransactions;

    /** Creates an immutable dashboard summary. */
    public SpendingSummary(double dailyLimit, double spentRatio, Map<Category, Double> categoryTotals, List<Transaction> recentTransactions) {
        this.dailyLimit = dailyLimit;
        this.spentRatio = spentRatio;
        this.categoryTotals = Map.copyOf(categoryTotals);
        this.recentTransactions = List.copyOf(recentTransactions);
    }

    /** @return current recommended daily spending limit */
    public double getDailyLimit() { return dailyLimit; }
    /** @return spent amount divided by allowance */
    public double getSpentRatio() { return spentRatio; }
    /** @return totals grouped by category */
    public Map<Category, Double> getCategoryTotals() { return categoryTotals; }
    /** @return recent transactions for dashboard display */
    public List<Transaction> getRecentTransactions() { return recentTransactions; }
}
