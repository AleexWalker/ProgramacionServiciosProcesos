package RepartoCiclicoYPorBloques;

public class Practica2 {
    public static void main(String[] args) {
        int numeroHebras = 2;
        int numeroProcesos = 24;
        for (int i = 0; i < numeroProcesos; i = i + numeroHebras)
            System.out.println("i = " + i);
    }
}
