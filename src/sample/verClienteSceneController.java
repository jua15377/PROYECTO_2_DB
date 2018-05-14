package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import manejador.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class verClienteSceneController implements Initializable{
    private ServerSQL serverSQL = new ServerSQL();
    private int id;
    //Textfield que contiene el nombre del cliente
    ObservableList<HBox> items = FXCollections.observableArrayList ();

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
    Label twitter_id;

    @FXML
    Label twitter_descripcion;

    @FXML
    Label twitter_locacion;

    @FXML
    Label cant_followers;

    @FXML
    Label cant_tweets;

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
                ServerSQL newServerSQL = new ServerSQL();
                ResultSet rs = newServerSQL.getUserbyID(Integer.parseInt(tfBuscarUsuario.getText()));
                rs.next();
                //carga de lo archivos
                this.id = Integer.parseInt(rs.getString("id"));
                tfNombre.setText(rs.getString("nombre"));
                tfApellido.setText(rs.getString("apellido"));
                tfImagen.setText(rs.getString("limage"));
                twitterName.setText(rs.getString("twuser"));
                tfTwitterUsername.setText(rs.getString("twuser"));
                tfUltimaCompra.setText(rs.getString("ucompra"));

                imagenTwitter.setImage(null);
                String path1 = rs.getString("twimage");
                //System.out.println(path1);
                if(!path1.isEmpty()){
                    Image img = new Image(path1);
                    imagenTwitter.setImage(img);

                }

                imagenUsuario.setImage(null);
                String path2 = rs.getString("limage");
                path2 = "file:"+ path2.replace("\\", "/");
                //System.out.println(path2);
                if(!path2.isEmpty()) {
                    Image img1 = new Image(path2);
                    imagenUsuario.setImage(img1);
                }

                String fecha[] = rs.getString("fdate").split("-");
                fechaNacimiento.setValue(LocalDate.of(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2])));
                if(rs.getString("tiene_creditos").equals("t")){
                    creditoSwitch.setSelected (true);
                }
                else{
                    creditoSwitch.setSelected (false);
                }
                tfMontoCredito.setText(rs.getString("creditos"));
                cb_Ocupacion.getSelectionModel().select(rs.getString("ocupacion"));
                cb_Banco.getSelectionModel().select( rs.getString("banco"));
                cb_Departamento.getSelectionModel().select(rs.getString("departamento"));
                cb_Categoria.getSelectionModel().select( rs.getString("categoria"));
                cb_Sucursal.getSelectionModel().select( rs.getString("sucursal"));
                scrollPaneFields.setVisible(true);

                //para valores dinamicos
                JSONParser parser = new JSONParser();

                try {

                    Object obj = parser.parse(rs.getString("otros"));
                    JSONObject jsonObject = (JSONObject) obj;
                    // loop array
                    for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                        String key = (String) iterator.next();
                        String value = (String) jsonObject.get(key);
                        regnerarCamposDinamicos(key,value);
                    }

                }
                catch (Exception e){
                    System.out.println(e);
                }
                rs.close();
                newServerSQL.closeConnectionToServer();

                //poner lo de los tweets


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
        fieldsList.setEditable(true);
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
        fieldsList.setEditable(false);
        fieldsList.setDisable(true);


        //obtener datos y hacer update en la base de datos
        ServerSQL newServerSQL = new ServerSQL();
        String nombre = tfNombre.getText();
        String apeliido = tfApellido.getText();
        //fecha de nacimieto
        Date date = Date.valueOf(fechaNacimiento.getValue().toString());
        String twitterUser;
        if (tfTwitterUsername.getText().contains("@")){
            twitterUser = tfTwitterUsername.getText().replace("@","");
        }
        else {
            twitterUser = tfTwitterUsername.getText();
        }
        String rutaimagenLocal = tfImagen.getText();
        String rutaTwitter;
        if(!tfTwitterUsername.getText().isEmpty()) {
            ConnectionToTwitter connectionToTwitter = new ConnectionToTwitter();
            rutaTwitter = connectionToTwitter.getUserImageLink(twitterUser);
        }
        else{
            rutaTwitter = "";
        }
        // verificar que esto no sea null
        String depto = cb_Departamento.getSelectionModel().getSelectedItem();
        String ocupacion = cb_Ocupacion.getSelectionModel().getSelectedItem();
        String banco = cb_Banco.getSelectionModel().getSelectedItem();
        String sucursal = cb_Sucursal.getSelectionModel().getSelectedItem();
        String categoria = cb_Categoria.getSelectionModel().getSelectedItem();
        double ultcompra;
        if(!tfUltimaCompra.getText().isEmpty()) {
            ultcompra = Double.parseDouble(tfUltimaCompra.getText());
        }
        else {
            ultcompra = 0;
        }
        boolean haveCredito= creditoSwitch.isSelected();
        double cantCredito;
        if(!tfMontoCredito.getText().isEmpty()) {
            cantCredito = Double.parseDouble(tfMontoCredito.getText());
        }
        else {
            cantCredito = 0;
        }
        //loop para campos dinamicos
        //aca se leeran multiples campos
        //se crea el json
        JSONObject obj = new JSONObject();

        items = fieldsList.getItems();
        for (HBox h:items) {
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


        //hacer update en la bae de datos
        newServerSQL.updateCliente (nombre,apeliido,date, twitterUser,rutaimagenLocal,rutaTwitter, depto, ocupacion, banco, sucursal, categoria , ultcompra,haveCredito, cantCredito, this.id, obj);
        newServerSQL.closeConnectionToServer();

        //insertando twetes en el servidor
        if(!tfTwitterUsername.getText().isEmpty()) {
            ConnectionToTwitter connectionToTwitter = new ConnectionToTwitter();
            connectionToTwitter.updateTweets(tfTwitterUsername.getText());
        }


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
            // se elimina de la base de datos
            serverSQL.deleteUserbyID(this.id);

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
    Button nuevoCampoButton;

    @FXML
    void nuevoCampoButtonAction(){

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

    public void regnerarCamposDinamicos(String campo, String data){
        fieldsList.setMinHeight(fieldsList.getHeight()+73);
        Label s = new Label();
        Font f = new Font(16);
        s.setFont(f);
        VBox vbox = new VBox();
        vbox.setMaxWidth(200);
        vbox.setPrefWidth(200);
        s.setText(campo + ": ");
        vbox.getChildren().add(s);
        HBox hbox = new HBox();
        hbox.setStyle("-fx-padding: 10 0 20 0;");
        TextField myTextField = new TextField();
        myTextField.setMaxWidth(267);
        myTextField.setPrefWidth(267);;
        myTextField.setFont(f);
        myTextField.setText(data);
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(myTextField);
        fieldsList.getItems().add(hbox);

    }



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
