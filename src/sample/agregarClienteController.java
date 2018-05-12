package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.control.ToggleSwitch;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class agregarClienteController implements Initializable{

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

    @FXML
    ToggleSwitch creditoSwitch;

    @FXML
    HBox thebox;

    @FXML
    DatePicker fechaNacimiento;


    @FXML
    ChoiceBox cb_Sucursal;

    public void fillcb_Sucursal(){
        cb_Sucursal.getItems().addAll();
    }

    @FXML
    ChoiceBox cb_Categoria;

    public void fillcb_Categoria(){
        cb_Categoria.getItems().addAll();
    }

    @FXML
    ChoiceBox cb_Ocupacion;

    public void fillcb_Ocupacion(){
        cb_Ocupacion.getItems().addAll();
    }

    @FXML
    ChoiceBox cb_Banco;

    public void fillcb_Banco(){
        cb_Banco.getItems().addAll();
    }

    @FXML
    ChoiceBox cb_Departamento;

    public void fillcb_Departamento(){
        cb_Departamento.getItems().addAll();
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
    }

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

        Image img = new Image("file:default.png");
        imagen.setImage(img);
    }
}
