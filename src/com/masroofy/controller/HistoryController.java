package com.masroofy.controller;

import com.masroofy.model.Transaction;
import com.masroofy.repository.TransactionRepository;
import java.util.Comparator;
import java.util.List;

/**
 * Retrieves transaction history for display.
 */
public class HistoryController {
    private final CycleController cycleController;
    private final TransactionRepository transactionRepository;

    /** Creates a history controller. */
    public HistoryController(CycleController cycleController, TransactionRepository transactionRepository) {
        this.cycleController = cycleController;
        this.transactionRepository = transactionRepository;
    }

    /** Gets transaction history for the active cycle in reverse chronological order. */
    public List<Transaction> getTransactionHistory() {
        String cycleId = cycleController.getActiveCycle().getId();
        return transactionRepository.findByCycle(cycleId).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();
    }
}
