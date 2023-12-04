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
 * The controller class for editing employee information.
 */
public class EditEmployeeController {
    @FXML
    public TextField wageField;
    @FXML
    public TextField hoursField;
    @FXML
    public Button backButton;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public ComboBox departmentComboBox;
    private Employee employee;

    /**
     * Navigates back to the Employee Management page.
     *
     * @param actionEvent The event triggered by the "Go Back" button.
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

    /**
     * Sets the employee data for the EditEmployeeController.
     * Updates the UI fields with the selected employee's information.
     *
     * @param selectedEmployee The employee object to set the data for.
     */
    public void setEmployeeData(Employee selectedEmployee) {
        this.employee = selectedEmployee;
        firstNameField.setText(selectedEmployee.firstNameProperty().get());
        lastNameField.setText(selectedEmployee.lastNameProperty().get());
        departmentComboBox.setValue(selectedEmployee.departmentProperty().getValue());
        wageField.setText(selectedEmployee.wageProperty().getValue().substring(1)); // Remove dollar sign it will always have
        hoursField.setText(selectedEmployee.hoursProperty().getValue());
    }


    /**
     * Updates the information of the selected employee.
     * If all required fields are filled and contain valid values, the employee's information is updated.
     * Otherwise, an alert is shown indicating the missing or invalid fields.
     *
     * @param actionEvent the action event triggered by the user
     */
    public void editEmployee(ActionEvent actionEvent) {
        if (employee != null) {
            // Update the employee's information
            if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && departmentComboBox.getValue() != null && !wageField.getText().isEmpty() && !hoursField.getText().isEmpty()) {
                try {
                    double tryWage = Double.parseDouble(wageField.getText()); // If this errors then it's not numeric
                    double tryHours = Double.parseDouble(hoursField.getText()); // If this errors then it's not numeric
                    // Cant be a negative number
                    if (tryHours < 0 || tryWage < 0) {
                        throw new NumberFormatException();
                    }

                    employee.firstNameProperty().set(firstNameField.getText());
                    employee.lastNameProperty().set(lastNameField.getText());
                    employee.departmentProperty().set((String) departmentComboBox.getValue());
                    employee.wageProperty().set("$" + wageField.getText());
                    employee.hoursProperty().set(hoursField.getText());
                    // Remove the employee from the old department and add it to the new one
                    EmployeeManager.removeEmployeeFromAllDepartments(employee);
                    EmployeeManager.addEmployeeToDepartment(employee, (String) departmentComboBox.getValue());

                    Utilities.showAlert("Alert", "Successfully edited Employee");
                } catch (NumberFormatException e) {
                    // Handle the case where the wage is not a valid double
                    Utilities.showAlert("Alert", "Please enter a valid positive numeric value.");
                }
            } else {
                Utilities.showAlert("Alert", "All fields are required to submit");
            }
        }
    }
}
