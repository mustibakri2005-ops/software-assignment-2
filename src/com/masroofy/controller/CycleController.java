package com.masroofy.controller;

import com.masroofy.model.BudgetCycle;
import com.masroofy.repository.CycleRepository;
import com.masroofy.repository.TransactionRepository;
import com.masroofy.service.LimitCalculator;
import java.time.LocalDate;

/**
 * Coordinates budget-cycle creation and daily rollover behavior.
 */
public class CycleController {
    private final CycleRepository cycleRepository;
    private final TransactionRepository transactionRepository;
    private final LimitCalculator limitCalculator;

    /** Creates a controller with required collaborators. */
    public CycleController(CycleRepository cycleRepository, TransactionRepository transactionRepository, LimitCalculator limitCalculator) {
        this.cycleRepository = cycleRepository;
        this.transactionRepository = transactionRepository;
        this.limitCalculator = limitCalculator;
    }

    /** Starts a new active cycle. */
    public BudgetCycle startCycle(double allowance, LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double dailyLimit = limitCalculator.calculateInitialLimit(allowance, days);
        return cycleRepository.save(new BudgetCycle(allowance, startDate, endDate, dailyLimit));
    }

    /** Returns the current active cycle or throws an error if none exists. */
    public BudgetCycle getActiveCycle() {
        return cycleRepository.findActive().orElseThrow(() -> new IllegalStateException("No active budget cycle. Please set up a cycle first."));
    }

    /** Applies daily rollover when the user opens the app on a later day. */
    public void rolloverIfNeeded(LocalDate today) {
        BudgetCycle cycle = getActiveCycle();
        if (today.isAfter(cycle.getLastRolloverDate())) {
            double spent = transactionRepository.sumSpent(cycle.getId());
            double remaining = cycle.getAllowance() - spent;
            double newLimit = limitCalculator.recalculateLimit(remaining, cycle.daysRemaining(today));
            cycle.applyRollover(today, newLimit);
            cycleRepository.update(cycle);
        }
    }
}
