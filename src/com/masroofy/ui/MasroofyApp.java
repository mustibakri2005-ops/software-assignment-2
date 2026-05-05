package com.masroofy.ui;

import com.masroofy.controller.CycleController;
import com.masroofy.controller.DashboardController;
import com.masroofy.controller.ExpenseController;
import com.masroofy.controller.HistoryController;
import com.masroofy.model.Category;
import com.masroofy.model.SpendingSummary;
import com.masroofy.model.Transaction;
import com.masroofy.repository.CycleRepository;
import com.masroofy.repository.TransactionRepository;
import com.masroofy.service.LimitCalculator;
import com.masroofy.service.NotificationService;
import java.time.LocalDate;

/**
 * Console entry point used to demonstrate the Masroofy prototype without producing an exe or jar file.
 */
public class MasroofyApp {
    /** Runs a short demonstration of the first seven user stories. */
    public static void main(String[] args) {
        CycleRepository cycleRepository = new CycleRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        LimitCalculator calculator = new LimitCalculator();
        NotificationService notificationService = new NotificationService();

        CycleController cycleController = new CycleController(cycleRepository, transactionRepository, calculator);
        ExpenseController expenseController = new ExpenseController(cycleController, cycleRepository, transactionRepository, calculator, notificationService);
        DashboardController dashboardController = new DashboardController(cycleController, transactionRepository, calculator);
        HistoryController historyController = new HistoryController(cycleController, transactionRepository);

        cycleController.startCycle(3000, LocalDate.now(), LocalDate.now().plusDays(29));
        expenseController.logExpense(120, Category.FOOD, "Lunch");
        expenseController.logExpense(80, Category.TRANSPORT, "Metro and bus");
        expenseController.logExpense(200, Category.STUDY, "Course materials");

        SpendingSummary summary = dashboardController.loadDashboard(LocalDate.now());
        System.out.println("Daily limit: " + summary.getDailyLimit());
        System.out.println("Spent ratio: " + Math.round(summary.getSpentRatio() * 100) + "%");
        System.out.println("Category totals: " + summary.getCategoryTotals());
        System.out.println("History:");
        for (Transaction transaction : historyController.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }
}
