package com.masroofy.repository;

import com.masroofy.model.Category;
import com.masroofy.model.Transaction;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Stores transactions and calculates totals needed by dashboard, history, and notification flows.
 */
public class TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    /** Saves a transaction. */
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    /** Finds transactions belonging to a cycle. */
    public List<Transaction> findByCycle(String cycleId) {
        return transactions.stream().filter(t -> t.getCycleId().equals(cycleId)).toList();
    }

    /** Calculates the total amount spent in a cycle. */
    public double sumSpent(String cycleId) {
        return findByCycle(cycleId).stream().mapToDouble(Transaction::getAmount).sum();
    }

    /** Calculates category totals for chart/insight display. */
    public Map<Category, Double> sumByCategory(String cycleId) {
        Map<Category, Double> totals = new EnumMap<>(Category.class);
        for (Category category : Category.values()) totals.put(category, 0.0);
        for (Transaction transaction : findByCycle(cycleId)) {
            totals.merge(transaction.getCategory(), transaction.getAmount(), Double::sum);
        }
        return totals;
    }
}
