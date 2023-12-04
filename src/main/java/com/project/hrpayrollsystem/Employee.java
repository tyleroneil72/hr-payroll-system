package com.project.hrpayrollsystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;

/**
 * The Employee class represents an employee in the HR payroll system.
 * It provides properties and methods to manage employee information.
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 2L;
    private static int id = 1;

    private transient StringProperty employeeID;
    private transient StringProperty firstName;
    private transient StringProperty lastName;
    private transient StringProperty department;
    private transient StringProperty wage;
    private transient StringProperty hours;
    private transient StringProperty bonus;

    public Employee(String firstName, String lastName, String department, double wage, double hours) {
        initializeProperties();
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.department.set(department);
        this.wage.set("$" + Double.toString(wage));
        this.hours.set(Double.toString(hours));
        this.bonus.set("None");
        this.employeeID.set(Integer.toString(id));
        id++;
    }

    private void initializeProperties() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.department = new SimpleStringProperty();
        this.wage = new SimpleStringProperty();
        this.hours = new SimpleStringProperty();
        this.bonus = new SimpleStringProperty();
        this.employeeID = new SimpleStringProperty();
    }
    // Since these are StringProperties, we can use the get() method to get the value of the property and set() to set the value of the property.
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
    public StringProperty employeeIDProperty() { return employeeID; }
    public StringProperty departmentProperty() { return department; }
    public StringProperty wageProperty() { return wage; }
    public StringProperty hoursProperty() { return hours; }
    public StringProperty bonusProperty() { return bonus; }

    /**
     * Writes the object's state to the specified ObjectOutputStream.
     * This method is called by the serialization mechanism.
     *
     * @param out the ObjectOutputStream to write the object's state to
     * @throws IOException if an I/O error occurs while writing the object
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(firstName.get());
        out.writeObject(lastName.get());
        out.writeObject(employeeID.get());
        out.writeObject(department.get());
        out.writeObject(wage.get());
        out.writeObject(hours.get());
        out.writeObject(bonus.get());
    }

    /**
     * Custom deserialization method for the Employee class.
     * Reads the object from the input stream and initializes the properties of the Employee object.
     * 
     * @param in the input stream from which the object is read
     * @throws IOException            if an I/O error occurs while reading the object
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initializeProperties();
        firstName.set((String) in.readObject());
        lastName.set((String) in.readObject());
        employeeID.set((String) in.readObject());
        department.set((String) in.readObject());
        wage.set((String) in.readObject());
        hours.set((String) in.readObject());
        bonus.set((String) in.readObject());
    }

    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }
}
