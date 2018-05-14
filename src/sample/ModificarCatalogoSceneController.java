package sample;

//import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import manejador.ServerSQL;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificarCatalogoSceneController implements Initializable{

    ServerSQL serverSQL = new ServerSQL();
    String currentCatalog;
    @FXML
    ComboBox cb_Catalogo;

    @FXML
    TableView table;

    @FXML
    Button guardarButton;

    @FXML
    void guardarButtonAction(){
        ObservableList<Catalogo> data = table.getItems();
        for (Catalogo c:data) {
            System.out.println(c.getId() + ", " + c.getNombre());
        }
    }

    @FXML
    Button nuevoButton;

    @FXML
    void nuevoButtonAction() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("Nuevo campo");
        dialog.setTitle("Nuevo campo");
        dialog.setContentText("Ingrese el nuevo valor:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){

            ObservableList<Catalogo> data = table.getItems();
            String datos = "("+data.size() +", '" + result.get() + "')";
            serverSQL.insertCatalog(currentCatalog, datos);
            //table.getItems().add(new Catalogo(""+data.size(), "nuevo"));
            refresh();
            catalogoListener();
            //verificar que no exista, usuario anadido con exito

            //System.out.println(datos);
            //System.out.println("Your name: " + result.get());

        }
        //catalogoListener();

        // The Java 8 way to get the response value (with lambda expression).
        //result.ifPresent(name -> System.out.println("Your name: " + name));

        //ObservableList<Catalogo> data = table.getItems();

    }

    public void refresh(){
        table.getItems().clear();
    }


    void fillCatalogos(){
        cb_Catalogo.getItems().addAll("ocupacion", "banco", "sucursal", "categoria_producto");
    }

    @FXML

    public void catalogoListener() throws SQLException {
        ArrayList<String> ids =new ArrayList<>();
        guardarButton.setDisable(false);
        nuevoButton.setDisable(false);
        //if(!cb_Catalogo.getSelectionModel().getSelectedItem().toString().equals(currentCatalog)){
            //quita las columnas actuales de la tabla
            table.getColumns().clear();
            table.setEditable(true);
            //llamando al query que ejecuta la busqueda
            ResultSet rs = serverSQL.viewDataFromCatalogo(cb_Catalogo.getSelectionModel().getSelectedItem().toString());

            currentCatalog = cb_Catalogo.getSelectionModel().getSelectedItem().toString();

            //obteniendo la metadata del query ejecutado
            ResultSetMetaData meta = null;
            try {
                meta = rs.getMetaData();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //agregrando las columnas correspondientes a la tabla
            for (int i=1; i <= meta.getColumnCount(); i++) {
                    TableColumn tableColumn = new TableColumn(meta.getColumnName(i));
                    table.getColumns().add(tableColumn);
            }

            final ObservableList<Banco> dataBanco = FXCollections.observableArrayList();
            final ObservableList<Ocupacion> dataOcupacion = FXCollections.observableArrayList();
            final ObservableList<Categoria> dataCategoria = FXCollections.observableArrayList();
            final ObservableList<Sucursal> dataSucursal = FXCollections.observableArrayList();

            try {
                if(currentCatalog.equals("banco")){
                    while(rs.next()){
                        dataBanco.add(new Banco(rs.getString("id_banco"), rs.getString("banco")));
                        ids.add(rs.getString("id_banco")+","+ rs.getString("banco"));
                    }
                    ObservableList<TableColumn> t = table.getColumns();
                    for (TableColumn tableColumn:t) {
                        tableColumn.setCellValueFactory(new PropertyValueFactory<Banco,String>(tableColumn.getText()));
                        if(!tableColumn.getText().contains("id")){
                            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent event) {
                                    String[] parts = null;
                                    for (String s:ids) {
                                        System.out.println(s);
                                        if(s.contains(event.getOldValue().toString())){
                                            parts = s.split(",");
                                        }
                                    }
                                    System.out.println("Yo soy el nuevoa "+parts[0]);
                                    serverSQL.updateCatalog(currentCatalog, tableColumn.getText(), event.getNewValue().toString(), "id_banco", parts[0]);
                                }
                            });
                        }
                    }
                    table.setItems(dataBanco);
                }else if(currentCatalog.equals("categoria_producto")){
                    //si escoge el catalogo de categoria_producto
                    while(rs.next()){
                        dataCategoria.add(new Categoria(rs.getString("id_categoria"), rs.getString("nombre_cat")));
                        ids.add(rs.getString("id_categoria")+","+ rs.getString("nombre_cat"));
                    }
                    ObservableList<TableColumn> t = table.getColumns();
                    for (TableColumn tableColumn:t) {
                        tableColumn.setCellValueFactory(new PropertyValueFactory<Categoria,String>(tableColumn.getText()));
                        if(!tableColumn.getText().contains("id")){
                            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent event) {
                                    String[] parts = null;
                                    for (String s:ids) {
                                        System.out.println(s);
                                        if(s.contains(event.getOldValue().toString())){
                                            parts = s.split(",");
                                        }
                                    }
                                    System.out.println("Yo soy el nuevoa "+parts[0]);
                                    serverSQL.updateCatalog(currentCatalog, tableColumn.getText(), event.getNewValue().toString(), "id_categoria", parts[0]);
                                }
                            });
                        }
                    }
                    table.setItems(dataCategoria);
                }else if(currentCatalog.equals("ocupacion")){
                    while(rs.next()){
                        Ocupacion c = new Ocupacion(rs.getString("id_ocupacion"), rs.getString("nombre_ocupacion"));
                        dataOcupacion.add(c);
                        ids.add(rs.getString("id_ocupacion")+","+ rs.getString("nombre_ocupacion"));
                    }
                    ObservableList<TableColumn> t = table.getColumns();
                    for (TableColumn tableColumn:t) {

                        tableColumn.setCellValueFactory(new PropertyValueFactory<Ocupacion,String>(tableColumn.getText()));
                        if(!tableColumn.getText().contains("id")){
                            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent event) {
                                    String[] parts = null;
                                    for (String s:ids) {
                                        System.out.println(s);
                                        if(s.contains(event.getOldValue().toString())){
                                            parts = s.split(",");
                                        }
                                    }
                                    System.out.println("Yo soy el nuevoa "+parts[0]);
                                    serverSQL.updateCatalog(currentCatalog, tableColumn.getText(), event.getNewValue().toString(), "id_ocupacion", parts[0]);
                                }
                            });
                        }
                    }
                    table.setItems(dataOcupacion);
                }else if(currentCatalog.equals("sucursal")){
                    while(rs.next()){
                        dataSucursal.add(new Sucursal(rs.getString("id_sucursal"), rs.getString("nombre_sucursal")));
                        ids.add(rs.getString("id_sucursal")+","+ rs.getString("nombre_sucursal"));
                    }
                    ObservableList<TableColumn> t = table.getColumns();
                    for (TableColumn tableColumn:t) {
                        tableColumn.setCellValueFactory(new PropertyValueFactory<Sucursal,String>(tableColumn.getText()));
                        if(!tableColumn.getText().contains("id")){
                            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent event) {
                                    String[] parts = null;
                                    for (String s:ids) {
                                        System.out.println(s);
                                        if(s.contains(event.getOldValue().toString())){
                                            parts = s.split(",");
                                        }
                                    }
                                    System.out.println("Yo soy el nuevoa "+parts[0]);
                                    serverSQL.updateCatalog(currentCatalog, tableColumn.getText(), event.getNewValue().toString(), "id_sucursal", parts[0]);
                                }
                            });
                        }
                    }
                    table.setItems(dataSucursal);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            table.setVisible(true);
        //}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setVisible(false);
        fillCatalogos();
        guardarButton.setDisable(true);
        nuevoButton.setDisable(true);
        table.getColumns().clear();
    }
}
