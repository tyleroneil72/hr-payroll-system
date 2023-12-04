module com.example.hrpayrollsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.hrpayrollsystem to javafx.fxml;
    exports com.project.hrpayrollsystem;
}