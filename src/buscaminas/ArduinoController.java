package buscaminas;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Robot;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.AWTException;
import java.awt.event.InputEvent;

public class ArduinoController extends Thread {

    private SerialPort puertoArduino;
    private InputStream input;
    private OutputStream output;
    private boolean correr = true;
    private int x;
    private int y;
    private int posX;
    private int posY;
    SerialPort [] puertosDisponibles = SerialPort.getCommPorts();

    private Robot control;
    private  Matriz matriz;


    public ArduinoController() {

        try {
            this.control = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        this.x = 1;
        this.y = 1;

        this.posX = 500 ;
        this.posY = 500 ;
        this.control.mouseMove(this.posX, this.posY);

        System.out.println("Puertos Disponibles ");
        for (int i = 0; i<puertosDisponibles.length ; i++){
            System.out.println(i + ". " + puertosDisponibles[i].getSystemPortName() + "== " + puertosDisponibles[i].getDescriptivePortName() );
        }

        this.puertoArduino = SerialPort.getCommPort("cu.usbmodem142301");

        // Se define la velocidad de comunicación con el arduino
        this.puertoArduino.setBaudRate(9600);

        if (this.puertoArduino.openPort()) {
            System.out.println("Conexión exitosa con el Arduino");
        }
        else {
            System.out.println("Error al conectarse con el Arduino");
            this.correr = false;
        }

        this.input = this.puertoArduino.getInputStream();
        this.output = this.puertoArduino.getOutputStream();

    }


    @Override
    public void run(){

        while (this.correr){
            try{

                if (this.input.available() > 0){
                    byte[] buffer = new byte[this.input.available()];
                    this.input.read(buffer);
                    String data = new String(buffer);

                    System.out.println("Los datos que se reciben son: " + data);
                    ejecutarAccion(data);
                }
                Thread.sleep(100);
            }
            catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
        }

        this.puertoArduino.closePort();
        System.out.println("Se cerró correctamente la comunicación ");
    }

    public void detenerHilo(){
        this.correr = false;
    }

    public void ejecutarAccion(String data) throws IOException{

        if (data.equals("A")){

            this.control.keyPress(KeyEvent.VK_UP);
            this.control.keyRelease(KeyEvent.VK_UP);

            if (this.y != 1){

                if (this.y > 1 && this.y <= 8){

                    this.y--;

                    this.control.mouseMove(this.posX + 25 * (this.x -1), this.posY + 25 * (this.y-1));

                    if (this.matriz.getMatriz()[this.x - 1][this.y - 1].getBandera()){
                        mensajeSerial("B");
                    }
                    else{
                        mensajeSerial("N");
                    }
                }
            }
            else{

                this.control.mouseMove(700,200);
                this.y--;

                mensajeSerial("N");
            }
        }

        if (data.equals("T")){

            this.control.keyPress(KeyEvent.VK_DOWN);
            this.control.keyRelease(KeyEvent.VK_DOWN);

            if (this.y >= 0 && this.y < 8){

                if (this.y == 0){

                    int n = 0;

                    int dif = this.x-5;

                    while (n < Math.abs(dif)){

                        if ( dif < 0){
                            this.control.keyPress(KeyEvent.VK_LEFT);
                            this.control.keyRelease(KeyEvent.VK_LEFT);
                        }
                        else{
                            this.control.keyPress(KeyEvent.VK_RIGHT);
                            this.control.keyRelease(KeyEvent.VK_RIGHT);

                        }
                        n++;
                    }
                }
                this.y++;
                this.control.mouseMove(this.posX + 25 * (this.x - 1), this.posY + 25 * (this.y - 1));

                if (this.matriz.getMatriz()[this.x - 1][this.y - 1].getBandera()){
                    mensajeSerial("B");
                }
                else{
                    mensajeSerial("N");
                }
            }
        }

        if (data.equals("I")){

            this.control.keyPress(KeyEvent.VK_LEFT);
            this.control.keyRelease(KeyEvent.VK_LEFT);

            if (this.y != 0){

                if (this.x > 1 && this.x <= 8){

                    this.x--;
                    this.control.mouseMove(this.posX + 25 * (this.x - 1), this.posY + 25 * (this.y - 1));

                    if (this.matriz.getMatriz()[this.x - 1][this.y - 1].getBandera()){
                        mensajeSerial("B");
                    }
                    else{
                        mensajeSerial("N");
                    }
                }
            }
        }

        if (data.equals("D")){

            this.control.keyPress(KeyEvent.VK_RIGHT);
            this.control.keyRelease(KeyEvent.VK_RIGHT);

            if(this.y != 0){
                if (this.x >= 1 && this.x < 8){

                    this.x++;
                    this.control.mouseMove(this.posX + 25 * (this.x - 1), this.posY + 25 * (this.y - 1));

                    if (this.matriz.getMatriz()[this.x - 1][this.y -1].getBandera()){
                        mensajeSerial("B");
                    }
                    else{
                        mensajeSerial("N");
                    }
                }
            }
        }

        if (data.equals("L")){
            this.control.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            this.control.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }

        if (data.equals("R")){
            this.control.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            this.control.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }

    }

    public void mensajeSerial(String sms) throws IOException{

        if (!sms.isEmpty()){
            this.output.write(sms.getBytes());

            System.out.println("El mensaque que se envió fue: " +sms);
        }
    }


    public void setMatriz(Matriz matriz){
        this.matriz = matriz;
    }

}
