package com.project.hrpayrollsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The controller class for adding an employee.
 * This class handles the user interface and logic for adding a new employee to the system.
 */
public class AddEmployeeController {
    @FXML
    public Button backButton;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField wageField;
    @FXML
    public TextField hoursField;
    @FXML
    private ComboBox<String> departmentComboBox;

    /**
     * Saves the employee information entered by the user.
     * Validates the input fields and adds the employee to the department and employee list if all fields are valid.
     * Displays appropriate alerts for invalid input or missing fields.
     *
     * @param actionEvent The action event triggered by the user.
     */
    public void saveEmployee(ActionEvent actionEvent) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String departmentName = departmentComboBox.getValue(); // Get the selected value
        String wageText = wageField.getText();
        String hoursText = hoursField.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty() && departmentName != null && !wageText.isEmpty() && !hoursText.isEmpty()) {
            try {
                // Try to convert the salary text to a double
                double wage = Double.parseDouble(wageText);
                double hours = Double.parseDouble(hoursText);
                // Cant be a negative number
                if (hours < 0 || wage < 0) {
                    throw new NumberFormatException();
                }

                // Create a new employee
                Employee newEmployee = new Employee(firstName, lastName, departmentName, wage, hours);
                EmployeeManager.addEmployeeToDepartment(newEmployee, departmentName); // Add employee to the department
                // Add the new employee to the list
                EmployeeManager.getEmployeeList().add(newEmployee);

                // Clear the input fields after adding employee
                firstNameField.clear();
                lastNameField.clear();
                departmentComboBox.getSelectionModel().clearSelection();
                wageField.clear();
                hoursField.clear();
                Utilities.showAlert("Alert", "Successfully added Employee");
            } catch (NumberFormatException e) {
                // Handle the case where the salary is not a valid double
                Utilities.showAlert("Alert", "Please enter a valid positive numeric value.");
            }
        } else {
            // Display an alert or message indicating that all fields are required
            Utilities.showAlert("Alert", "All fields are required to submit");
        }
    }

    /**
     * Navigates back to the Employee Management page.
     * This method is called when the "Go Back" button is clicked.
     * It loads the Employee Management FXML, sets the stage title,
     * and creates a new scene for the Employee Management page.
     * 
     * @param actionEvent the action event triggered by the button click
     */
    public void goBack(ActionEvent actionEvent) {
        try {
            // Load the Employee Management FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/hrpayrollsystem/fxml/employeeManagement.fxml"));
            Parent employeeManagementRoot = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            Image newIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/hrpayrollsystem/images/employees.png")));
            stage.getIcons().clear();
            stage.getIcons().add(newIcon);
            stage.setTitle("HR Manager and Payroll System - Employee Management");
            // Create a new scene for the Employee Management page
            Scene employeeManagementScene = new Scene(employeeManagementRoot);
            // Get the stage from the button and set the new scene
            stage.setScene(employeeManagementScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
