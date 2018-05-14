package sample;

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
    void guardarButtonAction() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("Dato a eliminar");
        dialog.setTitle("Eliminar dato");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingrese el ID del dato a eliminar:");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            boolean exists = false;
            if(currentCatalog.equals("banco")){
                ObservableList<Banco> data = table.getItems();
                for (Banco b:data) {
                    if(b.getId_banco().equals(result.get())){
                        exists = true;
                    }
                }
                if(exists == true){
                    serverSQL.deleteFromCatalog(currentCatalog, "id_banco", result.get());
                }
            }else if(currentCatalog.equals("categoria_producto")){
                ObservableList<Categoria> data = table.getItems();
                for (Categoria c:data) {
                    if(c.getId_categoria().equals(result.get())){
                        exists = true;
                    }
                }
                if(exists == true){
                    serverSQL.deleteFromCatalog(currentCatalog, "id_categoria", result.get());
                }
            }else if(currentCatalog.equals("ocupacion")){
                ObservableList<Ocupacion> data = table.getItems();
                for (Ocupacion o:data) {
                    if(o.getId_ocupacion().equals(result.get())){
                        exists = true;
                    }
                }
                if(exists == true){
                    serverSQL.deleteFromCatalog(currentCatalog, "id_ocupacion", result.get());
                }
            }else if(currentCatalog.equals("sucursal")){
                ObservableList<Sucursal> data = table.getItems();
                for (Sucursal s:data) {
                    if(s.getId_sucursal().equals(result.get())){
                        exists = true;
                    }
                }
                if(exists == true){
                    serverSQL.deleteFromCatalog(currentCatalog, "id_sucursal", result.get());
                }
            }
            if(exists==false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No existe ese elemento dentro del catalogo.");

                alert.showAndWait();
            }else{
                refresh();
                catalogoListener();
            }
        }
    }

    @FXML
    Button nuevoButton;

    @FXML
    void nuevoButtonAction() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("Nuevo dato");
        dialog.setTitle("Nuevo dato");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingrese el nuevo valor:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(currentCatalog.equals("banco")){
                ObservableList<Banco> data = table.getItems();
                int id = 0;
                for (Banco b:data) {
                    if(Integer.parseInt(b.getId_banco()) >= id){
                        id = Integer.parseInt(b.getId_banco());
                    }
                }
                id++;
                String datos = "("+id+", '" + result.get() + "')";
                serverSQL.insertCatalog(currentCatalog, datos);
            }else if(currentCatalog.equals("categoria_producto")){
                ObservableList<Categoria> data = table.getItems();
                int id = 0;
                for (Categoria b:data) {
                    if(Integer.parseInt(b.getId_categoria()) >= id){
                        id = Integer.parseInt(b.getId_categoria());
                    }
                }
                id++;
                String datos = "("+id +", '" + result.get() + "')";
                serverSQL.insertCatalog(currentCatalog, datos);
            }else if(currentCatalog.equals("ocupacion")){
                ObservableList<Ocupacion> data = table.getItems();
                int id = 0;
                for (Ocupacion b:data) {
                    if(Integer.parseInt(b.getId_ocupacion()) >= id){
                        id = Integer.parseInt(b.getId_ocupacion());
                    }
                }
                id++;
                String datos = "("+id +", '" + result.get() + "')";
                serverSQL.insertCatalog(currentCatalog, datos);
            }else if(currentCatalog.equals("sucursal")){
                ObservableList<Sucursal> data = table.getItems();
                int id = 0;
                for (Sucursal b:data) {
                    if(Integer.parseInt(b.getId_sucursal()) >= id){
                        id = Integer.parseInt(b.getId_sucursal());
                    }
                }
                id++;
                String datos = "("+id +", '" + result.get() + "')";
                serverSQL.insertCatalog(currentCatalog, datos);
            }
            refresh();
            catalogoListener();
        }
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
                        if(!rs.getString("id_banco").equals("0")){
                            dataBanco.add(new Banco(rs.getString("id_banco"), rs.getString("banco")));
                            ids.add(rs.getString("id_banco")+","+ rs.getString("banco"));
                        }
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
                        if(!rs.getString("id_categoria").equals("0")){
                            dataCategoria.add(new Categoria(rs.getString("id_categoria"), rs.getString("nombre_cat")));
                            ids.add(rs.getString("id_categoria")+","+ rs.getString("nombre_cat"));
                        }
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
                        if(!rs.getString("id_ocupacion").equals("0")){
                            Ocupacion c = new Ocupacion(rs.getString("id_ocupacion"), rs.getString("nombre_ocupacion"));
                            dataOcupacion.add(c);
                            ids.add(rs.getString("id_ocupacion")+","+ rs.getString("nombre_ocupacion"));
                        }
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
                        if(!rs.getString("id_sucursal").equals("0")){
                            dataSucursal.add(new Sucursal(rs.getString("id_sucursal"), rs.getString("nombre_sucursal")));
                            ids.add(rs.getString("id_sucursal")+","+ rs.getString("nombre_sucursal"));
                        }
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
