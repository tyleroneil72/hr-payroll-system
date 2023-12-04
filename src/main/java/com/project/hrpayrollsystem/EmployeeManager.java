package com.project.hrpayrollsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE_PATH = "./src/main/resources/com/project/hrpayrollsystem/data/Employees.data";

    private static ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    private static Map<String, Department> departmentMap = new HashMap<>();

    static {
        // Initialize the departments
        initializeDepartments();
        // Load employee data on application start
        deserializeEmployeeData();
    }

    /**
     * Initializes the departments in the employee management system.
     * This method adds predefined departments to the departmentMap.
     */
    private static void initializeDepartments() {
        departmentMap.put("Development Team", new Department("Development Team"));
        departmentMap.put("Accounting", new Department("Accounting"));
        departmentMap.put("Human Resources (HR)", new Department("Human Resources (HR)"));
        departmentMap.put("Marketing", new Department("Marketing"));
        departmentMap.put("Sales", new Department("Sales"));
        departmentMap.put("Information Technology (IT)", new Department("Information Technology (IT)"));
        departmentMap.put("Quality Assurance (QA)", new Department("Quality Assurance (QA)"));
        departmentMap.put("Project Management", new Department("Project Management"));
        departmentMap.put("Research and Development (R&D)", new Department("Research and Development (R&D)"));
    }

    /**
     * Retrieves the list of employees.
     *
     * @return the ObservableList of employees
     */
    public static ObservableList<Employee> getEmployeeList() {
        return employeeList;
    }

    /**
     * Adds an employee to a department.
     * If the department exists, the employee is added to it.
     *
     * @param employee       the employee to be added
     * @param departmentName the name of the department
     */
    public static void addEmployeeToDepartment(Employee employee, String departmentName) {
        Department department = departmentMap.get(departmentName);
        // if the department exists
        if (department != null) {
            department.addEmployee(employee);
        }
    }

    /**
     * Removes the given employee from all departments.
     *
     * @param employee the employee to be removed
     */
    public static void removeEmployeeFromAllDepartments(Employee employee) {
        for (Department department : departmentMap.values()) {
            department.removeEmployee(employee);
        }
    }

    /**
     * Serializes the employee data to a file.
     * This method writes the employeeList to a file using serialization.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public static void serializeEmployeeData() {
        // Serialize the employee data to a file
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_PATH))) {
            // Create a serializable list from the employeeList
            List<Employee> serializableList = new ArrayList<>(employeeList);
            // Write the serializable list to the file
            outputStream.writeObject(serializableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes the employee data from a file and updates the employeeList with the deserialized data.
     * If the file doesn't exist, it creates an empty list and saves it.
     */
    @SuppressWarnings("unchecked")
    public static void deserializeEmployeeData() {
        // Check if  the file exists already
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_PATH))) {
            // Read the object from the file
            Object object = inputStream.readObject();
            // Check if the object is an instance of List
            if (object instanceof List) {
                // Update the employeeList with the deserialized data
                employeeList = FXCollections.observableArrayList((List<Employee>) object);
            }
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, create an empty list and save it
            serializeEmployeeData();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}