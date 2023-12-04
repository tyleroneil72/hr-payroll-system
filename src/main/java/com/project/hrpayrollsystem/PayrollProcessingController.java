package com.project.hrpayrollsystem;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The PayrollProcessingController class is responsible for controlling the payroll processing UI.
 * It initializes the UI components, handles user interactions, and updates the UI based on user input.
 * The controller calculates and displays the total salary, tax, deductions, and bonus for the selected employee.
 * It also updates the pie chart based on the employee's bonus, if any.
 */
public class PayrollProcessingController implements Initializable {

    @FXML
    public Button backButton;
    @FXML
    public PieChart moneyDistributionChart;
    @FXML
    public Label chartLabel;
    @FXML
    private ComboBox<Employee> employeeComboBox;
    @FXML
    private VBox vBox;
    @FXML
    private Label totalSalaryLabel;
    @FXML
    private Label taxRateLabel;
    @FXML
    private Label taxesLabel;
    @FXML
    private Label deductionsLabel;
    @FXML
    private ComboBox<String> bonusComboBox;
    @FXML
    private Label bonusesLabel;

    /**
     * Initializes the PayrollProcessingController.
     * This method is called when the corresponding FXML file is loaded.
     * It sets up the initial state of the UI components and handles event listeners.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the ComboBox with the list of employees
        employeeComboBox.setItems(EmployeeManager.getEmployeeList());

        // Set a custom cell factory to display only the first and last names of the employees
        employeeComboBox.setCellFactory(param -> new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                if (empty || employee == null) {
                    setText(null);
                } else {
                    // Display only the first and last names of the employees
                    setText(employee.firstNameProperty().get() + " " + employee.lastNameProperty().get());
                }
            }
        });

        // Initialize bonusComboBox with options
        bonusComboBox.setItems(FXCollections.observableArrayList("None", "$1000", "$5000", "$10000"));

        // Handle the event when an employee is selected in the ComboBox
        employeeComboBox.setOnAction(event -> onEmployeeSelected());

        // Handle bonus selection event
        bonusComboBox.setOnAction(this::onBonusSelected);

        // Initially hide UI elements related to salary, tax, deductions, and bonus
        totalSalaryLabel.setVisible(false);
        taxRateLabel.setVisible(false);
        taxesLabel.setVisible(false);
        deductionsLabel.setVisible(false);
        bonusComboBox.setVisible(false);
        bonusesLabel.setVisible(false);

        // Anchor the VBox to the edges of the AnchorPane
        AnchorPane.setTopAnchor(vBox, 0.0);
        AnchorPane.setBottomAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
    }

    /**
     * Handles the event when an employee is selected in the ComboBox.
     * Toggles the visibility of various UI elements based on whether an employee is selected.
     * Calculates and displays the total salary, tax, deductions, etc. for the selected employee.
     * Updates the pie chart based on the employee's bonus, if any.
     * Hides other UI elements when no employee is selected.
     */
    private void onEmployeeSelected() {
        // Handle the event when an employee is selected in the ComboBox
        Employee selectedEmployee = employeeComboBox.getValue();

        // Toggle visibility based on whether an employee is selected
        boolean employeeSelected = selectedEmployee != null;
        totalSalaryLabel.setVisible(employeeSelected);
        taxRateLabel.setVisible(employeeSelected);
        taxesLabel.setVisible(employeeSelected);
        deductionsLabel.setVisible(employeeSelected);
        bonusComboBox.setVisible(employeeSelected);
        bonusesLabel.setVisible(employeeSelected);
        chartLabel.setVisible(employeeSelected);

        if (employeeSelected) {
            // Calculate and display salary, tax, deductions, etc.
            double totalSalary = Double.parseDouble(selectedEmployee.wageProperty().get().substring(1))
                    * Double.parseDouble(selectedEmployee.hoursProperty().get());

            // If the employee has a bonus already then add it to the total salary
            if (!selectedEmployee.bonusProperty().get().equals("None")) {
                double bonus = getBonusAmount(selectedEmployee.bonusProperty().get());
                totalSalary += bonus;
            }
            totalSalaryLabel.setText("Total Salary: $" + String.format("%.2f", totalSalary));

            double taxRate = getTaxRate(totalSalary);
            double taxes = totalSalary * taxRate;
            double deductions = getDeductions(totalSalary);

            taxRateLabel.setText("Tax Rate: " + String.format("%.2f%%", taxRate * 100));
            taxesLabel.setText("Taxes: $" + String.format("%.2f", taxes));
            deductionsLabel.setText("Deductions: $" + String.format("%.2f", deductions));
            bonusesLabel.setText("Bonus: ");
            if (selectedEmployee.bonusProperty().get().equals("None")){
                updatePieChart(totalSalary, taxes, deductions);
            } else {
                updatePieChart(totalSalary, taxes, deductions, selectedEmployee.bonusProperty().get());
            }
        }
        // Ensure other UI elements are hidden when no employee is selected
        else {
            totalSalaryLabel.setText("");
            taxRateLabel.setText("");
            taxesLabel.setText("");
            deductionsLabel.setText("");
            bonusesLabel.setText("");
        }
    }

    /**
     * Updates the pie chart with the given total salary, taxes, and deductions.
     *
     * @param totalSalary  the total salary amount
     * @param taxes        the taxes amount
     * @param deductions   the deductions amount
     */
    private void updatePieChart(double totalSalary, double taxes, double deductions) {
        moneyDistributionChart.getData().clear(); // Clear existing data

        // Create PieChart data points
        PieChart.Data salaryData = new PieChart.Data("Salary", totalSalary);
        PieChart.Data taxesData = new PieChart.Data("Taxes", taxes);
        PieChart.Data deductionsData = new PieChart.Data("Deductions", deductions);

        // Add data points to the PieChart
        moneyDistributionChart.getData().addAll(salaryData, taxesData, deductionsData);
    }

    /**
     * Updates the pie chart with the given total salary, taxes, deductions, and bonus.
     * Clears existing data and adds new data points to the pie chart.
     *
     * @param totalSalary  the total salary amount
     * @param taxes        the taxes amount
     * @param deductions   the deductions amount
     * @param bonus        the bonus amount as a string
     */
    private void updatePieChart(double totalSalary, double taxes, double deductions, String bonus) {
        moneyDistributionChart.getData().clear(); // Clear existing data

        // Create PieChart data points
        PieChart.Data salaryData = new PieChart.Data("Salary", totalSalary);
        PieChart.Data taxesData = new PieChart.Data("Taxes", taxes);
        PieChart.Data deductionsData = new PieChart.Data("Deductions", deductions);
        PieChart.Data bonusData = new PieChart.Data("Bonus", getBonusAmount(bonus));

        // Add data points to the PieChart
        moneyDistributionChart.getData().addAll(salaryData, taxesData, deductionsData, bonusData);
    }

    /**
     * Handles the event when a bonus is selected in the ComboBox.
     * Updates the employee's bonus, total salary, taxes, deductions, and pie chart accordingly.
     *
     * @param event The action event triggered by selecting a bonus in the ComboBox.
     */
    @FXML
    private void onBonusSelected(ActionEvent event) {
        // Handle the event when a bonus is selected in the ComboBox
        Employee selectedEmployee = employeeComboBox.getValue();
        String selectedBonus = bonusComboBox.getValue();

        if (selectedEmployee != null && selectedBonus != null) {
            // Get the current bonus and remove its impact on the total salary
            String currentBonus = selectedEmployee.bonusProperty().get();
            double bonusAmount = getBonusAmount(currentBonus);
            double totalSalaryBeforeBonus = calculateTotalSalary(selectedEmployee);

            // Set the new bonus based on the user's selection
            selectedEmployee.bonusProperty().set(selectedBonus);

            // Calculate the new total salary after applying the new bonus
            double totalSalaryAfterBonus = calculateTotalSalary(selectedEmployee);

            // Adjust the total salary on the screen
            totalSalaryLabel.setText("Total Salary: $" + String.format("%.2f", totalSalaryAfterBonus));

            // Recalculate taxes and deductions based on the new total salary
            double taxRate = getTaxRate(totalSalaryAfterBonus);
            double taxes = totalSalaryAfterBonus * taxRate;
            double deductions = getDeductions(totalSalaryAfterBonus);
            updatePieChart(totalSalaryAfterBonus, taxes, deductions, selectedBonus);
            // Display the updated tax and deduction information
            taxRateLabel.setText("Tax Rate: " + String.format("%.2f%%", taxRate * 100));
            taxesLabel.setText("Taxes: $" + String.format("%.2f", taxes));
            deductionsLabel.setText("Deductions: $" + String.format("%.2f", deductions));
        }
    }

    /**
     * Calculates the total salary for an employee.
     * The total salary is calculated based on the employee's wage, hours worked, and bonus.
     *
     * @param employee the employee for whom the total salary is calculated
     * @return the total salary for the employee
     */
    private double calculateTotalSalary(Employee employee) {
        // Calculate total salary based on wage, hours worked, and bonus
        double baseSalary = Double.parseDouble(employee.wageProperty().get().substring(1)) * Double.parseDouble(employee.hoursProperty().get());
        double bonusAmount = getBonusAmount(employee.bonusProperty().get());
        return baseSalary + bonusAmount;
    }

    /**
     * Returns the bonus amount based on the given bonus code.
     *
     * @param bonus the bonus code
     * @return the bonus amount
     */
    private double getBonusAmount(String bonus) {
        return switch (bonus) {
            case "$1000" -> 1000.0;
            case "$5000" -> 5000.0;
            case "$10000" -> 10000.0;
            default -> 0.0; // No bonus
        };
    }

    /**
     * Calculates the tax rate based on the total salary.
     *
     * @param totalSalary the total salary of an employee
     * @return the tax rate applicable to the total salary
     */
    private double getTaxRate(double totalSalary) {
        if (totalSalary <= 50000) {
            // 10% tax rate for income up to $50,000
            return 0.10;
        } else if (totalSalary <= 100000) {
            // 20% tax rate for income between $50,001 and $100,000
            return 0.20;
        } else {
            // 30% tax rate for income above $100,000
            return 0.30;
        }
    }

    /**
     * Calculates the deductions based on the total salary.
     * The deductions are calculated as 2% of the total salary.
     *
     * @param totalSalary the total salary
     * @return the deductions amount
     */
    private double getDeductions(double totalSalary) {
        // 2% deductions of the total salary
        return 0.02 * totalSalary;
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
            // Get the current stage from any control in the scene
            Stage stage = (Stage) vBox.getScene().getWindow();  // Assuming vBox is a part of the current scene

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
}