module org.example.program2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.program2 to javafx.fxml;
    exports org.example.program2;
}