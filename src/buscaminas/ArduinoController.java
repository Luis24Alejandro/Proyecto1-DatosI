package buscaminas;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Robot;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.AWTException;

public class ArduinoController extends Thread {

    private SerialPort puertoArduino;
    private InputStream input;
    private OutputStream outout;
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








    }



}
