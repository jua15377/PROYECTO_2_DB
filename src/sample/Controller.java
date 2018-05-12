package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    Label paneHeader;
    @FXML
    AnchorPane homePane;
    @FXML
    AnchorPane pane;
    @FXML
    BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneHeader.setText("¡Bienvenido!");
    }

    public void setNode(Node node){
        pane.getChildren().clear();


        pane.getChildren().add((Node)node);
    }

    public void createScene(AnchorPane home, String loc) throws IOException{
        home = FXMLLoader.load(getClass().getResource(loc));
        home.setTopAnchor(home, 0.0);
        home.setLeftAnchor(home, 0.0);
        home.setBottomAnchor(home, 0.0);
        home.setRightAnchor(home, 0.0);

        setNode(home);
    }

    public void reportenSceneAction() throws IOException {
        if(!paneHeader.getText().equals("Reportes")){
            System.out.println("Entre a reportes");
            paneHeader.setText("Reportes");
            this.createScene(homePane, "reportesScene.fxml");
        }
    }

    public void verClientesSceneAction() throws IOException{
        if(!paneHeader.getText().equals("Ver cliente")){
            System.out.println("Entre a ver clientes");
            paneHeader.setText("Ver cliente");
            this.createScene(homePane, "verClienteScene.fxml");
        }
    }
    public void agregarClienteSceneAction() throws IOException{
        if(!paneHeader.getText().equals("Agregar cliente")){
            System.out.println("Entre a agregar clientes");
            paneHeader.setText("Agregar cliente");
            this.createScene(homePane, "agregarClienteScene.fxml");
        }
    }
    public void modificarCatalogoSceneAction() throws IOException{
        if(!paneHeader.getText().equals("Modificar catalogo")){
            System.out.println("Entre a modificar catalogos");
            paneHeader.setText("Modificar catalogo");
            this.createScene(homePane, "modificarCatalogoScene.fxml");
        }
    }
    public void verDataAction() throws IOException{
        if(!paneHeader.getText().equals("Ver data")){
            System.out.println("Entre a view data");
            paneHeader.setText("Ver data");
            this.createScene(homePane, "verDataScene.fxml");
        }
    }
}
