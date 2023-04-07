package buscaminas;

public class Recuadro {

    private boolean abierta;

    private boolean bandera;

    private boolean mina;

    public Recuadro(boolean state){
        this.abierta = false;
        this.bandera = false;
        this.mina = state;
    }

    public void setAbierta(){
        this.abierta = true;
    }

    public void setBandera(boolean bandera){
        this.bandera = bandera;
    }


    public boolean getAbierta(){
        return this.abierta;
    }


    public boolean getBandera(){
        return this.bandera;
    }

    public boolean getState(){
        return this.mina;
    }


    public int getMina(){
        if(this.mina){
            return 10;
        }
        else{
            return 0;
        }
    }




}
