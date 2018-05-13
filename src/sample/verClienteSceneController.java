package sample;
import javafx.scene.image.Image;
import manejador.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
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
    ChoiceBox<String> cb_Ocupacion;

    @FXML
    ChoiceBox<String> cb_Departamento;

    @FXML
    ChoiceBox<String> cb_Banco;

    @FXML
    ChoiceBox<String> cb_Sucursal;

    @FXML
    ChoiceBox<String> cb_Categoria;

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
        if(tfBuscarUsuario.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Busqueda cliente");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, por favor ingresa el ID de un cliente!");
            alert.showAndWait();
        }else{
            try {
                ResultSet rs = serverSQL.getUserbyID(Integer.parseInt(tfBuscarUsuario.getText()));
                rs.next();

                tfNombre.setText(rs.getString("nombre"));
                tfApellido.setText(rs.getString("apellido"));
                tfImagen.setText(rs.getString("local_image"));
                twitterName.setText(rs.getString("twitter_user"));
                tfTwitterUsername.setText(rs.getString("twitter_user"));
                tfUltimaCompra.setText(rs.getString("ultima_compra"));
                String path1 = rs.getString("twitter_image");
                System.out.println(path1);
                Image img = new Image(path1);
                imagenTwitter.setImage(img);
                String path2 = rs.getString("local_image");
                path2 = "file:"+ path2.replace("\\", "/");
                System.out.println(path2);
                Image img1 = new Image(path2);
                imagenUsuario.setImage(img1);
                String fecha[] = rs.getString("fecha_nacimiento").split("-");
                fechaNacimiento.setValue(LocalDate.of(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2])));
                if(rs.getString("tiene_creditos").equals("t")){
                    creditoSwitch.setSelected (true);
                }
                else{
                    creditoSwitch.setSelected (false);
                }
                tfMontoCredito.setText(rs.getString("cant_creditos"));
                cb_Ocupacion.getSelectionModel().select( Integer.parseInt(rs.getString("id_ocupacio"))-1);
                cb_Banco.getSelectionModel().select( Integer.parseInt(rs.getString("id_ocupacio"))-1);
                cb_Departamento.getSelectionModel().select( Integer.parseInt(rs.getString("id_ocupacio"))-1);
                cb_Categoria.getSelectionModel().select( Integer.parseInt(rs.getString("id_ocupacio"))-1);
                cb_Sucursal.getSelectionModel().select( Integer.parseInt(rs.getString("id_sucursal"))-1);


                scrollPaneFields.setVisible(true);
            }
            catch (Exception e){
                System.out.println(e);
            }
            scrollPaneFields.setVisible(true);
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
        fechaNacimiento.setEditable(false);
        tfTwitterUsername.setEditable(true);
        cb_Ocupacion.setDisable(false);
        cb_Departamento.setDisable(false);
        cb_Banco.setDisable(false);
        cb_Sucursal.setDisable(false);
        cb_Categoria.setDisable(false);
        tfUltimaCompra.setEditable(true);
        creditoSwitch.setDisable(false);
        tfMontoCredito.setEditable(true);
        creditoSwitch.setDisable(false);
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
        creditoSwitch.setDisable(true);
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



    public void fillcb_Sucursal(){
        cb_Sucursal.getItems().addAll(
                serverSQL.getNamesFromCatalog("sucursal")
        );
    }


    public void fillcb_Categoria(){
        cb_Categoria.getItems().addAll(
                serverSQL.getNamesFromCatalog("categoria_producto")

        );
    }

    public void fillcb_Ocupacion(){
        cb_Ocupacion.getItems().addAll(
                serverSQL.getNamesFromCatalog("ocupacion")

        );
    }

    public void fillcb_Banco(){
        cb_Banco.getItems().addAll(
                serverSQL.getNamesFromCatalog("banco")
        );
    }

    public void fillcb_Departamento(){
        cb_Departamento.getItems().addAll(
                serverSQL.getNamesFromCatalog("departamento")
        );
    }
    @FXML
    ScrollPane scrollPaneFields;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPaneFields.setVisible(false);
        fechaNacimiento.setEditable(false);
        creditoSwitch.setDisable(true);
        fillcb_Banco();
        fillcb_Departamento();
        fillcb_Ocupacion();
        fillcb_Sucursal();
        fillcb_Categoria();
    }
}
