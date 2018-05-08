package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AgregarClienteSceneController {

    @FXML
    Button addButton;

    public void addButtonAction(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Agregar usuario");
        alert.setHeaderText(null);
        alert.setContentText("Esta seguro de agregar este usuario?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Confirmacion");
            alert1.setHeaderText(null);
            alert1.setContentText("Usuario agregado con exito!");
            alert1.showAndWait();
        }
    }
}
