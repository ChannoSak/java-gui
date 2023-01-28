module com.gui.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;


    opens com.gui.gui to javafx.fxml;
    exports com.gui.gui;
}