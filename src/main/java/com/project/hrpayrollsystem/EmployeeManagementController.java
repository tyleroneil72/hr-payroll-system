package com.project.hrpayrollsystem;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The controller class for managing employees in the HR Payroll System.
 * This class handles the UI interactions and logic related to employee management.
 */
public class EmployeeManagementController {
    @FXML
    public Button backButton;
    @FXML
    public Button addEmployee;
    @FXML
    public Button deleteEmployee;
    @FXML
    public Button editEmployee;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> firstNameColumn;
    @FXML
    private TableColumn<Employee, String> lastNameColumn;
    @FXML
    private TableColumn<Employee, String> departmentColumn;
    @FXML
    public TableColumn<Employee, String> wageColumn;
    @FXML
    public TableColumn<Employee, String> hoursColumn;
    @FXML
    public TableColumn<Employee, String> bonusColumn;


    /**
     * Initializes the EmployeeManagementController.
     * Sets up the table columns and binds the table data to the employee list.
     */
    @FXML
    private void initialize() {
        // Set up the table columns
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        wageColumn.setCellValueFactory(cellData -> cellData.getValue().wageProperty());
        hoursColumn.setCellValueFactory(cellData -> cellData.getValue().hoursProperty());
        bonusColumn.setCellValueFactory(cellData -> cellData.getValue().bonusProperty());

        // Bind the table data to the employee list
        employeeTable.setItems(EmployeeManager.getEmployeeList());
    }

    /**
     * Opens a new stage to add an employee.
     * Loads the addEmployee FXML and creates a new stage with the addEmployee scene.
     * Sets the icon for the new stage and sets the title.
     * Closes the current stage if needed.
     */
    @FXML
    private void addEmployeeMethod() {
        try {
            // Load the addEmployee FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/hrpayrollsystem/fxml/addEmployee.fxml"));
            Parent root = loader.load();
            // Create a new scene with the addEmployee scene
            Scene scene = new Scene(root);
            // Create a new stage
            Stage addEmployeeStage = new Stage();
            addEmployeeStage.setScene(scene);
            // Set the icon for the new stage
            Image newIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/hrpayrollsystem/images/square-plus.png")));
            addEmployeeStage.getIcons().add(newIcon);
            addEmployeeStage.setTitle("HR Manager and Payroll System - Add Employee");
            // Show the new stage
            addEmployeeStage.show();
            addEmployeeStage.setResizable(false);
            // Close the current stage if needed
            Stage currentStage = (Stage) employeeTable.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the user clicks on the "Edit Employee" button.
     * It retrieves the selected employee from the employeeTable and opens a new stage
     * with the editEmployee scene, allowing the user to edit the employee's information.
     * If no employee is selected, it displays an alert message.
     *
     * @param actionEvent The action event triggered by clicking the "Edit Employee" button.
     */
    @FXML
    private void editEmployeeMethod(ActionEvent actionEvent) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                // Load the editEmployee FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/hrpayrollsystem/fxml/editEmployee.fxml"));
                Parent root = loader.load();
                // Get the controller of the editEmployee scene
                EditEmployeeController editEmployeeController = loader.getController();
                // Set the selected employee's information in the editEmployee controller
                editEmployeeController.setEmployeeData(selectedEmployee);
                // Create a new scene with the editEmployee scene
                Scene scene = new Scene(root);
                // Create a new stage
                Stage editEmployeeStage = new Stage();
                editEmployeeStage.setScene(scene);
                // Set the icon for the new stage
                Image newIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/hrpayrollsystem/images/square-plus.png")));
                editEmployeeStage.getIcons().add(newIcon);
                editEmployeeStage.setTitle("HR Manager and Payroll System - Edit Employee");

                // Show the new stage
                editEmployeeStage.show();
                // Close the current stage if needed
                Stage currentStage = (Stage) employeeTable.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Utilities.showAlert("Alert", "Please select an employee to edit.");
        }
    }

    /**
     * Navigates back to the main menu.
     */
    @FXML
    private void goBack() {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/hrpayrollsystem/fxml/mainMenu.fxml"));
            Parent root = loader.load();
            // Create a new scene with the main menu
            Scene scene = new Scene(root);
            // Get the current stage
            Stage stage = (Stage) employeeTable.getScene().getWindow();

            Image newIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/hrpayrollsystem/images/home.png")));
            stage.getIcons().clear();
            stage.getIcons().add(newIcon);
            stage.setTitle("HR Manager and Payroll System - Main Menu");
            // Set the new scene on the stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the selected employee from the employee management system.
     * If an employee is selected, it removes the employee from the list, refreshes the table,
     * removes the employee from all departments, and displays a success message.
     * If no employee is selected, it displays an alert message to select an employee.
     */
    @FXML
    private void deleteEmployeeMethod() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            // Remove the selected employee from the list
            EmployeeManager.getEmployeeList().remove(selectedEmployee);
            // Refresh the table
            employeeTable.setItems(FXCollections.observableArrayList(EmployeeManager.getEmployeeList()));
            // Remove employee from department
            EmployeeManager.removeEmployeeFromAllDepartments(selectedEmployee);
            Utilities.showAlert("Alert", "Successfully deleted Employee");
        } else {
            Utilities.showAlert("Alert", "Please select an employee to delete.");
        }
    }
}