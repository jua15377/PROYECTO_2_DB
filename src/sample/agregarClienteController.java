package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.controlsfx.control.ToggleSwitch;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.*;
import manejador.*;
import org.json.simple.JSONObject;

import javax.xml.crypto.Data;

public class agregarClienteController implements Initializable{
    private ServerSQL  serverSQL = new ServerSQL();
    private HashMap<String,String> camposExtras = new HashMap<>();
    @FXML
    Button addButton;
    @FXML
    Button addField;

    @FXML
    Button loadButton;

    @FXML
    TextField tfNombre;

    @FXML
    TextField tfApellido;

    @FXML
    TextField tfImagen;

    @FXML
    TextField tfTwitterUsername;

    @FXML
    TextField tfUltimaCompra;

    @FXML
    TextField tfMontoCredito;

    @FXML
    ListView<HBox> fieldsList;

    ObservableList<HBox> items = FXCollections.observableArrayList ();

    @FXML
    ToggleSwitch creditoSwitch;

    @FXML
    HBox thebox;

    @FXML
    DatePicker fechaNacimiento;


    @FXML
    ChoiceBox<String> cb_Sucursal;

    public void fillcb_Sucursal(){
        cb_Sucursal.getItems().addAll(
                serverSQL.getNamesFromCatalog("sucursal")
        );
    }

    @FXML
    ChoiceBox<String> cb_Categoria;

    public void fillcb_Categoria(){
        cb_Categoria.getItems().addAll(
                serverSQL.getNamesFromCatalog("categoria_producto")

        );
    }

    @FXML
    ChoiceBox<String> cb_Ocupacion;

    public void fillcb_Ocupacion(){
        cb_Ocupacion.getItems().addAll(
               serverSQL.getNamesFromCatalog("ocupacion")

        );
    }

    @FXML
    ChoiceBox<String> cb_Banco;

    public void fillcb_Banco(){
        cb_Banco.getItems().addAll(
                serverSQL.getNamesFromCatalog("banco")
        );
    }

    @FXML
    ChoiceBox<String> cb_Departamento;

    public void fillcb_Departamento(){
        cb_Departamento.getItems().addAll(
                serverSQL.getNamesFromCatalog("departamento")
        );
    }

    @FXML
    ImageView imagen;

    @FXML
    void loadButtonAction(){
        FileChooser fileChooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpg"));
        fileChooser.setTitle("Select your profile picture");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile!=null) {
            selectedFile.getAbsoluteFile();
            tfImagen.setText(selectedFile.getAbsolutePath());
            Image im = new Image(selectedFile.toURI().toString());
            imagen.setImage(im);
        }
    }

    @FXML
    void creditoSwitchListener(){
        if(creditoSwitch.isSelected()){
            tfMontoCredito.setDisable(false);
        }else{
            tfMontoCredito.setDisable(true);
        }
    }

    @FXML
    void addFieldAction(){
        fieldsList.setMinHeight(fieldsList.getHeight()+73);
        Label s = new Label();
        Font f = new Font(16);
        s.setFont(f);
        VBox vbox = new VBox();
        vbox.setMaxWidth(200);
        vbox.setPrefWidth(200);
        s.setText(nameNewField() + ": ");
        vbox.getChildren().add(s);
        HBox hbox = new HBox();
        hbox.setStyle("-fx-padding: 10 0 20 0;");
        TextField myTextField = new TextField();
        myTextField.setMaxWidth(267);
        myTextField.setPrefWidth(267);;
        myTextField.setFont(f);
        myTextField.setPromptText("Nuevo campo");
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(myTextField);
        fieldsList.getItems().add(hbox);
        camposExtras.put(s.getText(),myTextField.getText());
    }

    public void addButtonAction(){
        if(tfNombre.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Houston, tenemos un problema...");
            alert.setContentText("Porfavor, ingresa el nombre del cliente.");

            alert.showAndWait();
        }else if(fechaNacimiento.getEditor().getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Houston, tenemos un problema...");
            alert.setContentText("Porfavor, ingresa la fecha de nacimiento del cliente.");
            alert.showAndWait();
        }else
        if(cb_Categoria.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Houston, tenemos un problema...");
            alert.setContentText("Porfavor, selecciona una categoria.");

            alert.showAndWait();
        }else if(cb_Sucursal.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Houston, tenemos un problema...");
            alert.setContentText("Porfavor, selecciona una sucursal.");

            alert.showAndWait();
        }else if(cb_Banco.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Houston, tenemos un problema...");
            alert.setContentText("Porfavor, selecciona un banco.");

            alert.showAndWait();
        }else if(cb_Departamento.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Houston, tenemos un problema...");
            alert.setContentText("Porfavor, selecciona un departamento.");

            alert.showAndWait();
        }else if(cb_Ocupacion.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Houston, tenemos un problema...");
            alert.setContentText("Porfavor, selecciona una ocupacion.");

            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Agregar usuario");
            alert.setHeaderText(null);
            alert.setContentText("Esta seguro de agregar este usuario?");


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                // check here for special characters
                ServerSQL newServerSQL = new ServerSQL();
                String nombre = tfNombre.getText();
                String apeliido = tfApellido.getText();

                Date date = Date.valueOf(fechaNacimiento.getValue().toString());

                String twitterUser;
                if (tfTwitterUsername.getText().contains("@")) {
                    twitterUser = tfTwitterUsername.getText().replace("@", "");
                } else {
                    twitterUser = tfTwitterUsername.getText();
                }
                String rutaimagenLocal = tfImagen.getText();
                String rutaTwitter;
                if (!tfTwitterUsername.getText().isEmpty()) {
                    ConnectionToTwitter connectionToTwitter = new ConnectionToTwitter();
                    rutaTwitter = connectionToTwitter.getUserImageLink(twitterUser);
                } else {
                    rutaTwitter = "";
                }

                // verificar que esto no sea null

                String depto = cb_Departamento.getSelectionModel().getSelectedItem();
                String ocupacion = cb_Ocupacion.getSelectionModel().getSelectedItem();
                String banco = cb_Banco.getSelectionModel().getSelectedItem();
                String sucursal = cb_Sucursal.getSelectionModel().getSelectedItem();
                String categoria = cb_Categoria.getSelectionModel().getSelectedItem();
                double ultcompra;
                if (!tfUltimaCompra.getText().isEmpty()) {
                    ultcompra = Double.parseDouble(tfUltimaCompra.getText());
                } else {
                    ultcompra = 0;
                }
                boolean haveCredito = creditoSwitch.isSelected();
                double cantCredito;
                if (!tfMontoCredito.getText().isEmpty()) {
                    cantCredito = Double.parseDouble(tfMontoCredito.getText());
                } else {
                    cantCredito = 0;
                }
                //aca se leeran multiples campos
                //se crea el json
                JSONObject obj = new JSONObject();

                items = fieldsList.getItems();
                for (HBox h : items) {
                    VBox v = (VBox) h.getChildren().get(0);
                    Label label = (Label) v.getChildren().get(0);
                    String nombreCampo = label.getText();

                    //Obtiene el nombre del nuevo campo
                    nombreCampo = nombreCampo.replace(": ", "");
                    TextField textField = (TextField) h.getChildren().get(1);

                    //Obtiene el contenido del textfield
                    String contenidoCampo = textField.getText();
                    obj.put(nombreCampo, contenidoCampo);
                }
                if (!camposExtras.isEmpty()) {
                    for (Map.Entry<String, String> s : camposExtras.entrySet()) {

                    }
                }
                //inserta en la bae de datos
                newServerSQL.insertCliente(nombre, apeliido, date, twitterUser, rutaimagenLocal, rutaTwitter, depto, ocupacion, banco, sucursal, categoria, ultcompra, haveCredito, cantCredito, obj);
                newServerSQL.closeConnectionToServer();
                //buscar tweets
                //insertando twetes en el servidor
                if (!tfTwitterUsername.getText().isEmpty()) {
                    ConnectionToTwitter connectionToTwitter = new ConnectionToTwitter();
                    connectionToTwitter.getTweetsFromUserAndInsertOnMongo(tfTwitterUsername.getText());
                }

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Confirmacion");
                alert1.setHeaderText(null);
                alert1.setContentText("Usuario agregado con exito!");
                alert1.showAndWait();

                tfNombre.setText("");
                tfApellido.setText("");
                tfImagen.setText("");
                fechaNacimiento.setValue(null);
                tfTwitterUsername.setText("");
                cb_Ocupacion.getSelectionModel().clearSelection();
                cb_Departamento.getSelectionModel().clearSelection();
                cb_Banco.getSelectionModel().clearSelection();
                cb_Sucursal.getSelectionModel().clearSelection();
                cb_Categoria.getSelectionModel().clearSelection();
                tfUltimaCompra.setText("");
                creditoSwitch.setSelected(false);
                tfMontoCredito.setText("");
                fieldsList.getItems().clear();
                fieldsList.setMinHeight(2);
            }
        }
    }

    public String nameNewField(){
        TextInputDialog dialog = new TextInputDialog("Nombre del nuevo campo");
        dialog.setTitle("Creacion de un nuevo campo");
        dialog.setHeaderText(null);
        dialog.setContentText("Porfavor ingrese el nombre del nuevo campo:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            //System.out.println("Your name: " + result.get());
            return result.get();
        }
        return result.get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillcb_Banco();
        fillcb_Departamento();
        fillcb_Ocupacion();
        fillcb_Sucursal();
        fillcb_Categoria();
        String path1 = System.getProperty("user.dir") + "/resources/default.png";
        path1 = "file:"+ path1.replace("\\", "/");
        System.out.println(path1);
        Image img = new Image(path1);
        imagen.setImage(img);
        fechaNacimiento.getEditor().setDisable(true);
    }
}
