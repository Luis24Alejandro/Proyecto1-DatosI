package buscaminas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javax.swing.Timer;
import java.awt.*;
import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.paint.Color;
import java.awt.AWTException;

public class DummyController implements Initializable {

    private int time = 0;

    private boolean perdida = false;

    private int minas = 10;

    private int minasFaltantes = this.minas;

    private int turno = 0;

    private Matriz matriz;

    private pilaPistas pistas = new pilaPistas();

    @FXML
    private GridPane gpMatriz;

    @FXML
    private Label lbTiempo;

    @FXML
    private Label lbMinas;

    @FXML
    private Label lbGanar;

    @FXML
    private Label lbPistas;


    private Timer tiempo;

    private ArduinoController control;



    @Override
    public void initialize(URL url, ResourceBundle rb){

        this.lbMinas.setText(String.valueOf(this.minas));

        this.matriz = new Matriz(this.minas);

        this.tiempo = new Timer(1000,(java.awt.event.ActionEvent e) -> {
            this.time++;
            Platform.runLater(()->this.lbTiempo.setText(String.valueOf(this.time)));
        });
        this.tiempo.start();

        inicio();

        this.control = new ArduinoController();
        this.control.start();
        this.control.setMatriz(this.matriz);

    }


    public void inicio(){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

                Button btn = new Button("");
                btn.setPrefSize(41,41);

                int a = i;
                int b = j;

                btn.setId("boton");

                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton() == MouseButton.PRIMARY){

                            btn.setVisible(false);

                            verRecuadro(a, b, true, 0);
                        }
                        if(event.getButton() == MouseButton.SECONDARY){

                            btn.setVisible(false);

                            try {
                                estadoBoton(a, b, false);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });

                gpMatriz.add(btn, i, j);
            }
        }
    }




    public void estadoBoton(int a, int b, boolean estado) throws IOException {

        if (estado){
            this.minasFaltantes++;

            this.control.mensajeSerial("N");
        }
        else{

            this.minasFaltantes--;

            this.control.mensajeSerial("B");
        }
        lbMinas.setText(String.valueOf(this.minasFaltantes));

        for(final Node node : this.gpMatriz.getChildren()){
            if(GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node)!=null){

                if(GridPane.getColumnIndex(node) == a && GridPane.getRowIndex(node) == b){

                    node.setVisible(false);

                    Button btn = new Button();

                    if(estado){
                        this.matriz.getMatriz()[a][b].setBandera(false);

                        btn.setText(" ");
                        btn.setPrefSize(41,41);

                        btn.setId("boton");

                        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                if(event.getButton() == MouseButton.PRIMARY){

                                    btn.setVisible(false);
                                    verRecuadro(a, b, true, 0);
                                }
                                if(event.getButton() == MouseButton.SECONDARY){

                                    btn.setVisible(false);
                                    try {
                                        estadoBoton(a, b, false);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        });
                    }
                    else{
                        this.matriz.getMatriz()[a][b].setBandera(true);

                        Image image = new Image("Bandera5.PNG");
                        ImageView imageView = new ImageView(image);

                        btn.setId("bandera");

                        btn.setGraphic(imageView);

                        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if(event.getButton() == MouseButton.SECONDARY){

                                    btn.setVisible(false);

                                    try {
                                        estadoBoton(a, b, true);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        });

                    }
                    this.gpMatriz.add(btn, a, b);
                    break;
                }
            }
        }
    }


    private void verRecuadro(int a, int b, boolean game, int jugador){

        if(game && jugador == 0){
            this.turno++;
        }
        if(this.turno == 5 && game){

            addPista();
            this.turno = 0;
        }

        Label lb = new Label(" ");

        int x = this.matriz.getMatriz()[a][b].getMina();

        boolean abierta = !this.matriz.getMatriz()[a][b].getAbierta();

        if(x == 10 && abierta){

            for(final Node node : this.gpMatriz.getChildren()){

                if(GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null){

                    if(GridPane.getColumnIndex(node) == a && GridPane.getRowIndex(node) == b){

                        this.tiempo.stop();

                        node.setDisable(true);

                        if(!this.matriz.getMatriz()[a][b].getBandera() && jugador == 0){
                            lb.setText("M");
                        }
                        else{
                            lb.setText("O");
                        }

                        lb.setFont(new Font("Arial",12));
                        lb.setAlignment(Pos.CENTER);

                        if(game){

                            if(jugador==0){

                                lb.setTextFill(Color.BLUE);

                                lbGanar.setText("Usted perdió");
                            }else{

                                lb.setTextFill(Color.RED);

                                lbGanar.setText("Usted ganó");
                            }
                        }

                        this.gpMatriz.add(lb, a, b);
                        GridPane.setHalignment(lb, HPos.CENTER);

                        this.matriz.getMatriz()[a][b].setAbierta();

                        this.perdida = true;

                        for(int i = 0; i < 8; i++){
                            for(int j = 0; j < 8; j++){

                                if(game){

                                    for(final Node node2 : this.gpMatriz.getChildren()){

                                        if(GridPane.getColumnIndex(node2) != null && GridPane.getRowIndex(node2) != null){

                                            if(node2 instanceof Button){

                                                node2.setVisible(false);
                                            }
                                        }
                                    }

                                    verRecuadro(i, j, false, jugador);
                                }
                            }
                        }

                        break;
                    }
                }
            }

        }
        else{

            if(abierta){

                try{
                    if(this.matriz.getMatriz()[a - 1][b - 1].getState()){
                        //en caso de ser 1 se le suma
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }
                try{
                    if(this.matriz.getMatriz()[a - 1][b].getState()){
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }
                try{
                    if(this.matriz.getMatriz()[a - 1][b + 1].getState()){
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }
                try{
                    if(this.matriz.getMatriz()[a][b - 1].getState()){
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }
                try{
                    if(this.matriz.getMatriz()[a][b + 1].getState()){
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }
                try{
                    if(this.matriz.getMatriz()[a + 1][b - 1].getState()){
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }
                try{
                    if(this.matriz.getMatriz()[a + 1][b].getState()){
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }
                try{
                    if(this.matriz.getMatriz()[a + 1][b + 1].getState()){
                        x++;
                    }
                }catch(ArrayIndexOutOfBoundsException exception){
                }

                for(final Node node : this.gpMatriz.getChildren()){

                    if(GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null){

                        if(GridPane.getColumnIndex(node) == a && GridPane.getRowIndex(node) == b){

                            node.setVisible(false);

                            for(final Node node2 : this.gpMatriz.getChildren()){

                                if(GridPane.getColumnIndex(node2) != null && GridPane.getRowIndex(node2) != null){

                                    if(GridPane.getColumnIndex(node2) == a && GridPane.getRowIndex(node2) == b){

                                        if(node2 instanceof Button){
                                            if(node2.getId().equalsIgnoreCase("bandera")){

                                                this.minasFaltantes++;
                                            }

                                            this.lbMinas.setText(String.valueOf(this.minasFaltantes));

                                            node2.setVisible(false);
                                        }

                                    }
                                }
                            }

                            node.setVisible(false);

                            this.matriz.getMatriz()[a][b].setAbierta();

                            if(x == 0){

                                if(game){
                                    lb.setText("J");
                                }else{

                                    lb.setText("");
                                }
                            }
                            else{

                                lb.setText(String.valueOf(x));
                            }

                            lb.setAlignment(Pos.CENTER);
                            lb.setFont(new Font("Arial",12));

                            if(game){
                                if(jugador == 0){
                                    lb.setTextFill(Color.BLUE);
                                }
                                else{
                                    lb.setTextFill(Color.RED);
                                }
                            }

                            this.gpMatriz.add(lb, a, b);
                            GridPane.setHalignment(lb, HPos.CENTER);

                            if(x == 0){

                                for(int i = (a - 1); i <= (a + 1); i++){
                                    for(int j = (b - 1); j <= (b + 1);j ++){
                                        try{

                                            verRecuadro(i,j,false,0);
                                        }
                                        catch(ArrayIndexOutOfBoundsException exception){

                                        }
                                    }
                                }
                            }
                            //se rompe el ciclo una vez encontado el elemento de la casilla
                            break;
                        }
                    }
                }
            }
        }
        if(this.matriz.Fin() && !this.perdida){

            this.tiempo.stop();

            lbGanar.setText("Usted ganó");

            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){

                    if(this.matriz.getMatriz()[i][j].getState()){

                        lb = new Label("x");

                        lb.setAlignment(Pos.CENTER);
                        lb.setFont(new Font("Arial",12));
                        this.gpMatriz.add(lb, i, j);
                        GridPane.setHalignment(lb, HPos.CENTER);
                    }
                }
            }

            for(final Node node : this.gpMatriz.getChildren()){

                if(GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null){

                    if(node instanceof  Button){

                        node.setVisible(false);
                    }
                }
            }
        }
        if(!this.perdida && jugador == 0 && game){
            turnoPC();
        }

    }

    @FXML
    private void sugerencia(ActionEvent event){

        if(this.pistas.peek( ) == null){

            this.lbPistas.setText("Sin pistas");
        }
        else{
            this.lbPistas.setText((String) this.pistas.peek());
            this.pistas.pop();
        }
    }

    public void addPista() {

        boolean enPila = false;

        while(!enPila){

            int i = (int)(Math.random()*8);
            int j = (int)(Math.random()*8);

            if(!this.matriz.getMatriz()[i][j].getAbierta() && !this.matriz.getMatriz()[i][j].getState()){

                this.pistas.push("Pista: El recuadoro " + String.valueOf(i + 1) + " ," + String.valueOf( j + 1) + " es seguro de jugar." );

                enPila = true;
            }
        }
    }


    public void turnoPC(){


        boolean movimiento = false;

        while(!movimiento){

            int i = (int)(Math.random()*8);
            int j = (int)(Math.random()*8);

            if(!this.matriz.getMatriz()[i][j].getAbierta()){

                verRecuadro(i, j, true, 1);

                movimiento = true;
            }
        }
    }
}
