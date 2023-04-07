package buscaminas;

public class Lista {

    private int size;

    private Nodo head;

    public Lista(){
        this.size = 0;
        this.head = null;
    }

    public void setHead(Nodo nodo){
        this.head=nodo;
    }

    public void setSize(int size){
        this.size = size;
    }

    public Nodo getHead(){
        return this.head;
    }

    public int getSize(){
        return this.size;
    }

    public void agregar(Nodo nodo){
        if(this.head == null){
            this.head = nodo;
        }
        else{
            Nodo aux = this.head;
            while(aux.getNext() != null){
                aux = aux.getNext();
            }
            aux.setNext(nodo);
        }
        this.size++;
    }
    public void delete(Nodo nodo){
        if(this.head == nodo){
            this.head = nodo.getNext();
        }
        else{
            Nodo aux = this.head;
            while(aux.getNext() != nodo){
                aux = aux.getNext();
            }
            aux.setNext(aux.getNext().getNext());
        }
        this.size--;
    }
    public Nodo buscar(int i, int j){
        Nodo aux = this.head;
        while (aux != null){
            if(aux.getI() == i && aux.getJ() == j){
                return aux;
            }
            else{
                aux = aux.getNext();
            }
        }
        return null;
    }

    public void deleteLista() {
        this.head = null;
        this.size = 0;
    }




}
