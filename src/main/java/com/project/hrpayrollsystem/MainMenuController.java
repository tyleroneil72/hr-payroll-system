package com.project.hrpayrollsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

/**
 * The controller class for the main menu of the HR Payroll System.
 * This class handles the actions and events related to the main menu buttons.
 */
public class MainMenuController {

    @FXML
    private Button employeeManagementButton;

    /**
     * Opens the Employee Management section.
     * Loads the Employee Management FXML and sets it as the scene for the current stage.
     * Sets the stage title, icon, and disables resizing.
     * If an IOException occurs during loading, it prints the stack trace.
     */
    @FXML
    private void openEmployeeManagement() {
        // Handle the action to open the Employee Management section
        try {
            // Load the Employee Management FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/hrpayrollsystem/fxml/employeeManagement.fxml"));
            Parent employeeManagementRoot = loader.load();

            Stage stage = (Stage) employeeManagementButton.getScene().getWindow();
            Image newIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/hrpayrollsystem/images/employees.png")));
            stage.getIcons().clear();
            stage.getIcons().add(newIcon);
            stage.setResizable(false);
            stage.setTitle("HR Manager and Payroll System - Employee Management");
            // Create a new scene for the Employee Management page
            Scene employeeManagementScene = new Scene(employeeManagementRoot);
            // Get the stage from the button and set the new scene
            stage.setScene(employeeManagementScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Payroll Processing section.
     * If there are no employees available, an alert is shown.
     * Loads the Payroll Processing FXML and sets it as the scene for the stage.
     * Sets the stage title and icon.
     * The stage is not resizable.
     */
    @FXML
    private void openPayrollProcessing() {
        // Handle the action to open the Payroll Processing section
        try {
            if (EmployeeManager.getEmployeeList().isEmpty()) {
                Utilities.showAlert("Payroll", "No Employees Available!");
                return;
            }
            // Load the Payroll Processing FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/hrpayrollsystem/fxml/payrollProcessing.fxml"));
            Parent employeeManagementRoot = loader.load();

            Stage stage = (Stage) employeeManagementButton.getScene().getWindow();
            Image newIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/hrpayrollsystem/images/usd-circle.png")));
            stage.getIcons().clear();
            stage.getIcons().add(newIcon);
            stage.setResizable(false);
            stage.setTitle("HR Manager and Payroll System - Payroll Processing and Reports");
            // Create a new scene for the Employee Management page
            Scene employeeManagementScene = new Scene(employeeManagementRoot);
            // Get the stage from the button and set the new scene
            stage.setScene(employeeManagementScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the application.
     * Closes the current stage and displays a thank you message.
     */
    @FXML
    private void exitApplication() {
        Utilities.showAlert("Exiting...","Thank You For Using Our Application");
        Stage stage = (Stage) employeeManagementButton.getScene().getWindow();
        stage.close();
    }
}