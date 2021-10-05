package Actividad2_2;

public class Actividad2HebraConUnaClase {
    public static void main(String[] args) {
        Tarea1 tareaHola = new Tarea1(0);
        Tarea1 tareaAdios = new Tarea1(1);

        tareaHola.start();
        tareaAdios.start();
    }
}
class Tarea1 extends Thread{
    int idHebra;
    public Tarea1 (int idHebra) {
        this.idHebra = idHebra;
    }
    public void run(){
        if (idHebra == 0) {
            for (int i = 0 ; i < 3 ; i++)
                System.out.println("Hola");
        } else if (idHebra == 1) {
            for (int i = 0 ; i < 3 ; i++)
                System.out.println("Adios");
        }
    }
}
