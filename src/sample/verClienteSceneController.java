package sample;
import manejador.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class verClienteSceneController implements Initializable{
    private ServerSQL serverSQL = new ServerSQL();
    //Textfield que contiene el nombre del cliente
    @FXML
    TextField tfNombre;

    //Textfield que contiene el apellido del cliente
    @FXML
    TextField tfApellido;

    //Textfield que contiene la ruta de la imagen del perfil del cliente
    @FXML
    TextField tfImagen;

    //Date picker que permite al usuario elegir una fecha de nacimiento.
    @FXML
    DatePicker fechaNacimiento;

    //Textfield que el nombre de usuario de twitter del cliente.
    @FXML
    TextField tfTwitterUsername;

    @FXML
    ChoiceBox cb_Ocupacion;

    @FXML
    ChoiceBox cb_Departamento;

    @FXML
    ChoiceBox cb_Banco;

    @FXML
    ChoiceBox cb_Sucursal;

    @FXML
    ChoiceBox cb_Categoria;

    @FXML
    TextField tfUltimaCompra;

    @FXML
    ToggleSwitch creditoSwitch;

    @FXML
    TextField tfMontoCredito;

    @FXML
    ListView fieldsList;

    @FXML
    ListView tweetsList;

    @FXML
    Label twitterName;


    @FXML
    ImageView imagenUsuario;

    @FXML
    ImageView imagenTwitter;

    //Boton "Cargar" con el que se carga un URL de la imagen de perfil
    @FXML
    Button loadButton;

    /*
     * Metodo que implementa el FileChooser para seleecionar una imagen
     */
    @FXML
    void loadButtonAction(){
        //Hacer aqui lo del boton de cargar
    }

    //Textfield que contiene el usuario que se quiere buscar
    @FXML
    TextField tfBuscarUsuario;

    //Boton "Buscar" que realiza la accionde busqueda y carga de los datos de un usuario
    @FXML
    Button buscarButton;

    /*
     *  Metodo que implementa la accion de busqueda y carga de los datos de un usuario.
     */
    @FXML
    void buscarButtonAction(){
        //hacer aqui lo que busca el usuario y setea los valores
        try {
            ResultSet rs = serverSQL.getUserbyID(Integer.parseInt(tfBuscarUsuario.getText()));
            rs.next();
            tfNombre.setText(rs.getString("nombre"));
            tfApellido.setText(rs.getString("apellido"));
            tfImagen.setText(rs.getString("local_image"));
            twitterName.setText(rs.getString("twitter_user"));
            tfTwitterUsername.setText(rs.getString("twitter_user"));
            tfUltimaCompra.setText(rs.getString("ultima_compra"));


            scrollPaneFields.setVisible(true);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    //Boton "Modificar" que habilita que los textfields con los daros del usuario sean editables
    @FXML
    Button modificarButton;

    /*
     * Metodo que contiene la accion de habilitar los elementos para que el usuario pueda editar los campos.
     */
    @FXML
    void modificarButtonAction(){
        //habilitar los componentes
        guardarButton.setDisable(false);
        modificarButton.setDisable(true);
        tfNombre.setEditable(true);
        tfApellido.setEditable(true);
        tfImagen.setEditable(true);
        loadButton.setDisable(false);
        fechaNacimiento.setEditable(true);
        tfTwitterUsername.setEditable(true);
        cb_Ocupacion.setDisable(false);
        cb_Departamento.setDisable(false);
        cb_Banco.setDisable(false);
        cb_Sucursal.setDisable(false);
        cb_Categoria.setDisable(false);
        tfUltimaCompra.setEditable(true);
        creditoSwitch.setDisable(false);
        tfMontoCredito.setEditable(true);
        fechaNacimiento.setDisable(false);
        //for de los campos variables

    }

    //Boton "guardar" que guarda los datos del usuario en la base de datos.
    @FXML
    Button guardarButton;

    /*
     * Metodo que realiza el guardado de datos del usuario.
     */
    @FXML
    void guardarButtonAction(){
        //guardar los cambios hechos sobre un cliente
        guardarButton.setDisable(true);
        modificarButton.setDisable(false);
        tfNombre.setEditable(false);
        tfApellido.setEditable(false);
        tfImagen.setEditable(false);
        loadButton.setDisable(true);
        fechaNacimiento.setEditable(false);
        tfTwitterUsername.setEditable(false);
        cb_Ocupacion.setDisable(true);
        cb_Departamento.setDisable(true);
        cb_Banco.setDisable(true);
        cb_Sucursal.setDisable(true);
        cb_Categoria.setDisable(true);
        tfUltimaCompra.setEditable(false);
        creditoSwitch.setDisable(true);
        tfMontoCredito.setEditable(false);
        fechaNacimiento.setDisable(true);
    }

    //Boton "Eliminar" que permite eliminar un cliente
    @FXML
    Button eliminarButton;

    /*
     * Metodo para eliminar un cliente
     */
    @FXML
    void eliminarButtonAction(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar usuario");
        alert.setHeaderText(null);
        alert.setContentText("Esta seguro de eliminar este usuario?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Confirmacion");
            alert1.setHeaderText(null);
            alert1.setContentText("Usuario eliminado con exito!");
            alert1.showAndWait();
            scrollPaneFields.setVisible(false);
            //set scrollpanel visible false.
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
    ScrollPane scrollPaneFields;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPaneFields.setVisible(false);
        fechaNacimiento.setEditable(false);
        fechaNacimiento.setDisable(true);
    }
}
