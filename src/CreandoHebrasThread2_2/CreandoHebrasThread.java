package CreandoHebrasThread2_2;

public class CreandoHebrasThread {
    public static void main(String[] args) {

        Tarea1 t1 = new Tarea1();
        Tarea2 t2 = new Tarea2();

        t1.start(); //ojo no pongo t1.run
        t2.start();
    }
}

class Tarea1 extends Thread{
    public void run(){
        for (int i = 0 ; i < 3; i++){
            System.out.println("Hola");
        }
    }
}

class Tarea2 extends Thread {
    public void run(){
        for (int i = 0 ; i < 3; i++){
            System.out.println("Adios");
        }
    }
}