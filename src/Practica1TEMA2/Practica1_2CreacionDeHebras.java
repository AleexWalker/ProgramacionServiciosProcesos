package Practica1TEMA2;

public class Practica1_2CreacionDeHebras {
    public static void main(String[] args) {
        Thread tareaHebra1 = new Thread(new Tarea2(00));
        Thread tareaHebra2 = new Thread(new Tarea2(01));

        tareaHebra1.start();
        tareaHebra2.start();
    }
}

class Tarea2 implements Runnable{
    int numHebra;

    public Tarea2(int numHebra){
        this.numHebra = numHebra;
    }

    public void run(){
        for (int i = 0 ; i < 1000 ; i++)
            System.out.println("Hola soy la hebra numero: " + numHebra);
    }
}
