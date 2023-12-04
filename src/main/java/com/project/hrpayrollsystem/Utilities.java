package com.project.hrpayrollsystem;

import javafx.scene.control.Alert;

/**
 * This class provides utility methods for the HR Payroll System.
 */
public class Utilities {
    /**
     * Displays an alert with the specified title and content.
     *
     * @param title   the title of the alert
     * @param content the content of the alert
     */
    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
