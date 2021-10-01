package RepartoCiclicoYPorBloques;

public class Practica3 {
    public static void main(String[] args) {
        int numeroProcesos = 24;
        int numeroHebras = 4;
        int numeroHebra = 2;

        for (int i = (numeroHebra-1) * numeroProcesos / numeroHebras; i < (numeroHebra) * numeroProcesos / numeroHebras; i++)
            System.out.println("i = " + i);
    }
}
