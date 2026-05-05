package com.masroofy.controller;

import com.masroofy.model.BudgetCycle;
import com.masroofy.model.Category;
import com.masroofy.model.Transaction;
import com.masroofy.repository.CycleRepository;
import com.masroofy.repository.TransactionRepository;
import com.masroofy.service.LimitCalculator;
import com.masroofy.service.NotificationService;

/**
 * Handles rapid expense logging and threshold notifications.
 */
public class ExpenseController {
    private static final double THRESHOLD_RATIO = 0.80;
    private final CycleController cycleController;
    private final CycleRepository cycleRepository;
    private final TransactionRepository transactionRepository;
    private final LimitCalculator limitCalculator;
    private final NotificationService notificationService;

    /** Creates an expense controller with required collaborators. */
    public ExpenseController(CycleController cycleController, CycleRepository cycleRepository, TransactionRepository transactionRepository, LimitCalculator limitCalculator, NotificationService notificationService) {
        this.cycleController = cycleController;
        this.cycleRepository = cycleRepository;
        this.transactionRepository = transactionRepository;
        this.limitCalculator = limitCalculator;
        this.notificationService = notificationService;
    }

    /** Logs an expense, updates the active cycle, and checks whether a threshold alert is needed. */
    public Transaction logExpense(double amount, Category category, String note) {
        BudgetCycle cycle = cycleController.getActiveCycle();
        Transaction transaction = new Transaction(cycle.getId(), category, amount, note);
        transactionRepository.save(transaction);
        cycle.applyExpense(amount);
        cycleRepository.update(cycle);
        double spent = transactionRepository.sumSpent(cycle.getId());
        double ratio = limitCalculator.calculateSpentRatio(spent, cycle.getAllowance());
        if (ratio >= THRESHOLD_RATIO) {
            notificationService.sendThresholdAlert(ratio);
        }
        return transaction;
    }
}
