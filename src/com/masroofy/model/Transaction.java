package com.masroofy.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a single expense transaction recorded inside a budget cycle.
 */
public class Transaction {
    private final String id;
    private final String cycleId;
    private final Category category;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String note;

    /**
     * Creates a transaction with a generated id and the current timestamp.
     *
     * @param cycleId id of the budget cycle the transaction belongs to
     * @param category selected expense category
     * @param amount expense amount, must be positive
     * @param note optional short note
     */
    public Transaction(String cycleId, Category category, double amount, String note) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be greater than zero.");
        }
        this.id = UUID.randomUUID().toString();
        this.cycleId = Objects.requireNonNull(cycleId);
        this.category = Objects.requireNonNull(category);
        this.amount = amount;
        this.note = note == null ? "" : note.trim();
        this.timestamp = LocalDateTime.now();
    }

    /** @return generated transaction id */
    public String getId() { return id; }

    /** @return parent budget cycle id */
    public String getCycleId() { return cycleId; }

    /** @return spending category */
    public Category getCategory() { return category; }

    /** @return transaction amount */
    public double getAmount() { return amount; }

    /** @return time at which the expense was created */
    public LocalDateTime getTimestamp() { return timestamp; }

    /** @return optional user note */
    public String getNote() { return note; }

    @Override
    public String toString() {
        return timestamp.toLocalDate() + " | " + category + " | " + amount + " | " + note;
    }
}
