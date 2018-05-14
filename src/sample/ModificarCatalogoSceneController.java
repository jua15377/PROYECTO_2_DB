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

import java.net.URL;
import java.util.ResourceBundle;

public class ModificarCatalogoSceneController implements Initializable{

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
    void nuevoButtonAction(){
        ObservableList<Catalogo> data = table.getItems();
        table.getItems().add(new Catalogo(""+data.size(), "nuevo"));
    }

    void fillCatalogos(){
        cb_Catalogo.getItems().addAll("Hola", "Adios", "Ste", "Men");
    }

    @FXML
    void catalogoListener(){
        if(!cb_Catalogo.getSelectionModel().getSelectedItem().toString().equals(currentCatalog)){
            table.setEditable(true);
            //table.getColumns().clear();
            currentCatalog = cb_Catalogo.getSelectionModel().getSelectedItem().toString();
            table.setVisible(true);
            //System.out.println("Entre aca");
            TableColumn columnaID = new TableColumn("ID");
            TableColumn columnaNombre = new TableColumn("Nombre");
            table.getColumns().addAll(columnaID, columnaNombre);

            final ObservableList<Catalogo> data = FXCollections.observableArrayList();

            for(int i = 0; i<2 ;i++){
                data.add(new Catalogo(""+i, "hola"));
            }
            columnaID.setCellValueFactory(new PropertyValueFactory<Catalogo,String>("id"));
            //columnaID.setCellFactory(TextFieldTableCell.forTableColumn());
            /*columnaID.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                @Override
                public void handle(TableColumn.CellEditEvent event) {
                    ((Catalogo) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setId(event.getNewValue().toString());
                }
            });*/

            columnaNombre.setCellValueFactory(new PropertyValueFactory<Catalogo,String>("Nombre"));
            columnaNombre.setCellFactory(TextFieldTableCell.forTableColumn());
            columnaNombre.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                @Override
                public void handle(TableColumn.CellEditEvent event) {
                    ((Catalogo) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setNombre(event.getNewValue().toString());
                }
            });
            table.setItems(data);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setVisible(false);
        fillCatalogos();
        table.getColumns().clear();
    }
}
