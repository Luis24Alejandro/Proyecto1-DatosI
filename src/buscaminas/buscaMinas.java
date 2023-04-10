package buscaminas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;




public class buscaMinas extends Application {
    @Override
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
    public static void main(String[] args) {
        launch(args);

    }
}