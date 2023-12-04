package com.project.hrpayrollsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * The main class for the HR Manager and Payroll System.
 * This class extends the Application class and serves as the entry point for the application.
 */
public class MainMenu extends Application {

    /**
     * The main method that launches the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method that initializes and displays the main menu of the application.
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws Exception If an error occurs during the initialization of the main menu.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main menu FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/hrpayrollsystem/fxml/mainMenu.fxml")));

        // Create a scene with the main menu as the root
        Scene scene = new Scene(root);

        // Set the application icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/hrpayrollsystem/images/home.png")));
        primaryStage.getIcons().add(icon);

        // Set the title of the application
        primaryStage.setTitle("HR Manager and Payroll System - Main Menu");

        // Set the scene and make the stage not resizable
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Show the primary stage
        primaryStage.show();

        // Set up a shutdown hook to save employee data on application exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> EmployeeManager.serializeEmployeeData()));

        // Load employee data on application start
        EmployeeManager.deserializeEmployeeData();
    }
}