package buscaminas;

public class pilaPistas {

    private int maxSize = 6;

    private Object[] pilaArray;

    private int top;

    public pilaPistas(){
        this.pilaArray = new Object[maxSize];
        this.top = -1;
    }

    public void push(Object newObject) {
        if (top < maxSize ){
            this.pilaArray[++top] = newObject;
        }
        else{
            System.out.println("La pila está llena");
        }
    }

    public Object pop() {
        return this.pilaArray[top--];
    }

    public Object peek() {

       try{
           return this.pilaArray[top];
        }
        catch (ArrayIndexOutOfBoundsException exception){
            return null;
        }
    }


}













