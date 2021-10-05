package Practica1TEMA2;

public class Practica1CreacionDeHebras {
    public static void main(String[] args) {
        Tarea1 tareaHebra1 = new Tarea1(00);
        Tarea1 tareaHebra2 = new Tarea1(01);

        tareaHebra1.start();
        tareaHebra2.start();
    }
}

class Tarea1 extends Thread{
    int numHebra;

    public Tarea1(int numHebra) {
        this.numHebra = numHebra;
    }

    public void run(){
        for (int i = 0 ; i < 1000 ; i++)
            System.out.println("Hola soy la hebra Thread numero: " + numHebra);
    }
}
