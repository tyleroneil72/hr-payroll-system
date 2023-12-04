package com.project.hrpayrollsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a department in the HR payroll system.
 */
public class Department implements Serializable {
    private final String name;
    private final List<Employee> employees;

    /**
     * Constructs a department with the specified name.
     *
     * @param name the name of the department
     */
    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee the employee to be added
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Removes an employee from the department.
     *
     * @param employee the employee to be removed
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    /**
     * Returns a string representation of the department.
     *
     * @return a string representation of the department
     */
    @Override
    public String toString() {
        return name + " " + employees;
    }
}
