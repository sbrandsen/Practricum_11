module com.sb.practricum_11 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sb.practricum_11 to javafx.fxml;
    opens com.sb.practricum_11.ui.controllers to javafx.fxml;
    exports com.sb.practricum_11;
}