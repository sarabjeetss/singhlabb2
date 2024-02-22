module employee.employeefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens employee.employeefx to javafx.fxml;
    exports employee.employeefx;
    opens employee.employeefx.controller to javafx.fxml;
    opens employee.employeefx.model to javafx.base;
}