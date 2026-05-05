package com.masroofy.controller;

import com.masroofy.model.BudgetCycle;
import com.masroofy.model.SpendingSummary;
import com.masroofy.model.Transaction;
import com.masroofy.repository.TransactionRepository;
import com.masroofy.service.LimitCalculator;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Prepares the dashboard view model by combining cycle, transaction, and calculation data.
 */
public class DashboardController {
    private final CycleController cycleController;
    private final TransactionRepository transactionRepository;
    private final LimitCalculator limitCalculator;

    /** Creates a dashboard controller. */
    public DashboardController(CycleController cycleController, TransactionRepository transactionRepository, LimitCalculator limitCalculator) {
        this.cycleController = cycleController;
        this.transactionRepository = transactionRepository;
        this.limitCalculator = limitCalculator;
    }

    /** Loads all data needed by the dashboard screen. */
    public SpendingSummary loadDashboard(LocalDate today) {
        cycleController.rolloverIfNeeded(today);
        BudgetCycle cycle = cycleController.getActiveCycle();
        double spent = transactionRepository.sumSpent(cycle.getId());
        double ratio = limitCalculator.calculateSpentRatio(spent, cycle.getAllowance());
        List<Transaction> recent = transactionRepository.findByCycle(cycle.getId()).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(5)
                .toList();
        return new SpendingSummary(cycle.getDailyLimit(), ratio, transactionRepository.sumByCategory(cycle.getId()), recent);
    }
}
