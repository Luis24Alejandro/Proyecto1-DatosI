package buscaminas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

/**
 * Clase menú que controla la interfaz del menú
 *
 */
public class menuController implements Initializable{

    @FXML
    private Button btnDummy;

    @FXML
    private Button btnAdvanced;


    @Override
    /**
     * Método que inicia la ventanda
     */

    public void initialize(URL url, ResourceBundle rb){
        // TODO
    }

    @FXML
    /**
     * Método que se activa al precionar el botón dummy
     */
    private void newDummy(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Dummy.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    /**
     * Método que se activa al precionar el botón Advanced
     *
     */
    private void newAdvanced(ActionEvent event) throws Exception{
     Stage stage = new Stage();
     Parent root = FXMLLoader.load(getClass().getResource("Advanced.fxml"));
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.setResizable(false);
     stage.show();

    }

}
