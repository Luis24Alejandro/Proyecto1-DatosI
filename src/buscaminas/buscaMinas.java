package buscaminas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;


/**
 * Esta es la clase main que inicia el juego al abrir el fxml
 * @author luis
 */
public class buscaMinas extends Application {
    @Override
    /**
     * Metodo start que abre el FXML del menú
     */
    public void start(Stage primaryStage) throws Exception {
        //Declara el FXML
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        //Declara la escena a abrir
        Scene scene = new Scene(root);
        //Se ejecuta el abrir la escena
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    /**
     * Metodo main
     * @param args
     */
    public static void main(String[] args) {
        launch(args);

    }
}