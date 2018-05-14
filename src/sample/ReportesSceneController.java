package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import manejador.ServerSQL;

public class ReportesSceneController {

    ServerSQL serverSQL = new ServerSQL();

    @FXML
    ComboBox cb_reporte;

    @FXML
    TableView table;

    @FXML
    void reporteListener(){

    }

}
