package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manejador.ServerSQL;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VerDataSceneController implements Initializable{

    ServerSQL serverSQL = new ServerSQL();

    @FXML
    ComboBox cb_tabla;

    @FXML
    ComboBox<String> cb_filtro;

    @FXML
    ComboBox<String> cb_orden;

    @FXML
    Button buscarButton;

    @FXML
    TableView table;

    @FXML
    void buscarButtonAction(){
        if(cb_tabla.getSelectionModel().getSelectedIndex()!= -1){
            //Hacer aqui que pas si no ha seleccionado ninguna tabla
            //table.setVisible(true);
            String tabla = cb_tabla.getSelectionModel().getSelectedItem().toString();
            String filtro;
            if(cb_filtro.getSelectionModel().getSelectedIndex() == -1){
                filtro = "-None-";
            }else{
                filtro = cb_filtro.getSelectionModel().getSelectedItem();
            }
            String orden;
            if(cb_orden.getSelectionModel().getSelectedIndex() == -1){
                orden = "-None-";
            }else{
                orden = cb_orden.getSelectionModel().getSelectedItem();
            }
            ResultSet rs = serverSQL.viewDataFromTable(tabla, filtro, orden);
            table.getColumns().clear();
            ObservableList<String> columnas = cb_filtro.getItems();
            for (String s:columnas) {
                if(!s.equals("-None-")){
                    TableColumn tableColumn = new TableColumn(s);
                    table.getColumns().add(tableColumn);
                }
            }
            final ObservableList<Cliente> dataCliente = FXCollections.observableArrayList();
            final ObservableList<Deudor> dataDeudor = FXCollections.observableArrayList();
            final ObservableList<Cliente_destacado> dataClienteDestacado = FXCollections.observableArrayList();
            table.setVisible(true);
            try {
                if(tabla.equals("cliente")){
                    while(rs.next()){
                        dataCliente.add(new Cliente(rs.getString("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("fecha_nacimiento"), rs.getString("twitter_user"), rs.getString("twitter_image"), rs.getString("local_image"), rs.getString("id_departamento"), rs.getString("id_ocupacio"), rs.getString("id_banco"), rs.getString("sucursal_favorita"), rs.getString("id_categoria_favorita"), rs.getString("ultima_compra"), rs.getString("tiene_creditos"), rs.getString("cant_creditos"), rs.getString("otros")));
                    }
                    ObservableList<TableColumn> t = table.getColumns();
                    for (TableColumn tableColumn:t) {
                        tableColumn.setCellValueFactory(new PropertyValueFactory<Cliente,String>(tableColumn.getText()));
                    }
                    table.setItems(dataCliente);
                }else if(tabla.equals("deudor")){
                    while(rs.next()){
                        dataDeudor.add(new Deudor(rs.getString("id"), rs.getString("cliente_id"), rs.getString("cant_credito")));
                    }
                    ObservableList<TableColumn> t = table.getColumns();
                    for (TableColumn tableColumn:t) {
                        System.out.println("Yo soy el nombre de la columna" + tableColumn.getText());
                        tableColumn.setCellValueFactory(new PropertyValueFactory<Deudor,String>(tableColumn.getText()));
                    }
                    table.setItems(dataDeudor);
                }else if(tabla.equals("cliente_destacado")){
                    while(rs.next()){
                        dataClienteDestacado.add(new Cliente_destacado(rs.getString("id"), rs.getString("cliente_id"), rs.getString("ultima_compra")));
                    }
                    ObservableList<TableColumn> t = table.getColumns();
                    for (TableColumn tableColumn:t) {
                        System.out.println(tableColumn.getText());
                        tableColumn.setCellValueFactory(new PropertyValueFactory<Cliente_destacado,String>(tableColumn.getText()));
                    }
                    table.setItems(dataClienteDestacado);
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, no se ha seleccionado ninguna tabla!");

            alert.showAndWait();
        }
    }

    @FXML
    void fillCBtabla(){
        cb_tabla.getItems().add("cliente");
        cb_tabla.getItems().add("deudor");
        cb_tabla.getItems().add("cliente_destacado");
    }

    @FXML
    void fillCBfiltro(){
        cb_filtro.getItems().clear();
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
