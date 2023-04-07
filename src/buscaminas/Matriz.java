package buscaminas;

public class Matriz {
    int size = 8;
    private Recuadro[][] matriz;

    public Matriz(int x){
        matriz = new Recuadro[this.size][this.size];
        crearMatriz(x);
    }
    public void crearMatriz(int x){
        int cont = 0;
        while(cont < x){
            for(int i = 0; i < 8; i++){
               for(int j = 0; j < 8; j++){
                   int r = (int)(Math.random()*5+1);
                   if(r > 1) {
                       if (this.matriz[i][j] == null) {
                           this.matriz[i][j] = new Recuadro(false);
                       }
                   }
                   else{
                       if(cont < x){
                           this.matriz[i][j] = new Recuadro(true);
                           cont++;
                       }
                       if(this.matriz[i][j] == null){
                               this.matriz[i][j] = new Recuadro(false);
                       }
                   }

               }
            }
        }
    }

    public boolean Fin(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!this.matriz[i][j].getAbierta()){
                    if(!this.matriz[i][j].getState()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Recuadro[][] getMatriz() {
        return this.matriz;
    }
    public void setMatriz(Recuadro[][] matriz){
        this.matriz = matriz;
    }
}
