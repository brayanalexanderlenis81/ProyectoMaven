module co.edu.poli.parcial {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.poli.parcial to javafx.fxml;
    opens co.edu.poli.parcial.model to javafx.base; // <-- importante para la TableView
    exports co.edu.poli.parcial;
}
