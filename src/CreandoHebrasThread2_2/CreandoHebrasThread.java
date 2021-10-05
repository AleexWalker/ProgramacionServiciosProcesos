package CreandoHebrasThread2_2;

public class CreandoHebrasThread {
    public static void main(String[] args) {

        Tarea1 tareaHola = new Tarea1();
        Tarea2 tareaAdios = new Tarea2();

        tareaHola.start();
        tareaAdios.start();
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