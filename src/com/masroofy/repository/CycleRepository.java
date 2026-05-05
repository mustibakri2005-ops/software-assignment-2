package com.masroofy.repository;

import com.masroofy.model.BudgetCycle;
import java.util.Optional;

/**
 * Stores budget cycle records. This implementation is in-memory for the course prototype.
 */
public class CycleRepository {
    private BudgetCycle activeCycle;

    /** Saves a new active cycle and replaces any previous active cycle. */
    public BudgetCycle save(BudgetCycle cycle) {
        this.activeCycle = cycle;
        return cycle;
    }

    /** Updates the active cycle after expense logging or rollover. */
    public void update(BudgetCycle cycle) {
        this.activeCycle = cycle;
    }

    /** @return the current active cycle if one exists */
    public Optional<BudgetCycle> findActive() {
        return Optional.ofNullable(activeCycle).filter(BudgetCycle::isActive);
    }
}
