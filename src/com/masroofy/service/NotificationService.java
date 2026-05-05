package com.masroofy.service;

/**
 * Wraps notification behavior. In the prototype it prints to the console; in mobile code it would call local notifications.
 */
public class NotificationService {
    /** Sends a budget threshold alert to the user. */
    public void sendThresholdAlert(double spentRatio) {
        int percent = (int) Math.round(spentRatio * 100);
        System.out.println("ALERT: You have spent " + percent + "% of your budget cycle.");
    }
}
