package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import manejador.ServerSQL;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class VerDataSceneController implements Initializable{

    ServerSQL serverSQL = new ServerSQL();

    @FXML
    ComboBox cb_tabla;

    @FXML
    ComboBox<String> cb_filtro;

    @FXML
    ComboBox cb_orden;

    @FXML
    Button buscarButton;

    @FXML
    TableView table;

    @FXML
    void buscarButtonAction(){
        //llenar aca la tabla
        table.setVisible(true);
        ResultSet rs = serverSQL.viewDataFromTable(cb_tabla.getSelectionModel().getSelectedItem().toString(), cb_filtro.getSelectionModel().getSelectedItem().toString(), cb_orden.getSelectionModel().getSelectedItem().toString());

    }

    @FXML
    void fillCBtabla(){
        cb_tabla.getItems().add("cliente");
        cb_tabla.getItems().add("deudor");
        cb_tabla.getItems().add("cliente_destacado");
    }

    @FXML
    void fillCBfiltro(){
        cb_filtro.getItems().add("-None-");
        System.out.println(cb_tabla.getSelectionModel().getSelectedItem().toString());
        cb_filtro.getItems().addAll(serverSQL.viewColumnsFromTable(cb_tabla.getSelectionModel().getSelectedItem().toString()));
    }

    @FXML
    void fillCBOrden(){
        cb_orden.getItems().add("-None-");
        cb_orden.getItems().add("Ascendente");
        cb_orden.getItems().add("Descendente");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setVisible(false);
        fillCBtabla();
        fillCBOrden();
    }
}
