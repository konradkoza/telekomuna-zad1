module p.lodz.kodowanie {
    requires javafx.controls;
    requires javafx.fxml;


    opens p.lodz.kodowanie to javafx.fxml;
    exports p.lodz.kodowanie;
}