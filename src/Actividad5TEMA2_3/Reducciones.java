package Actividad5TEMA2_3;

import java.util.concurrent.atomic.AtomicInteger;

public class Reducciones extends Thread{
    public static void main(String[] args) {
        int numHebras;

        int vector[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29,
                30, 31, 32, 33, 34, 35, 36, 37, 38,
                39, 40, 41};

        numHebras = 4;

        implementacionSecuencial(vector);

        Multiplos m = new Multiplos();
        implementacionCiclica(vector, numHebras, m);

        Multiplos n = new Multiplos();
        implementacionCiclicaReducciones(vector, numHebras, n);
    }

    static void implementacionSecuencial(int[] vectorNumeros) {
        long t1;
        long t2;
        double tt;

        int multiplo2 = 0;
        int multiplo3 = 0;
        int multiplo5 = 0;


        System.out.println("");
        System.out.println("Implementación secuencial.");

        t1 = System.nanoTime();
        //Escribe aquí la implementación secuencial

        for (int i = 0 ; i <vectorNumeros.length ; i++) {
            if (vectorNumeros[i] % 2 == 0)
                multiplo2++;
            if (vectorNumeros[i] % 3 == 0)
                multiplo3++;
            if (vectorNumeros[i] % 5 == 0)
                multiplo5++;
        }

        //Fin de la implementación secuencial
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
        System.out.println("Multiplos de 2: " + multiplo2);
        System.out.println("Multiplos de 3: " + multiplo3);
        System.out.println("Multiplos de 5: " + multiplo5);
    }

    static void implementacionCiclica(int[] vectorNumeros, int numHebras, Multiplos m) {
        long t1;
        long t2;
        double tt;

        System.out.println("");
        System.out.println("Implementación cíclica sin Reducciones.");

        MiHebraCiclica v[] = new MiHebraCiclica[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraCiclica(i, numHebras, vectorNumeros, m);
            v[i].start();
        }

        for (int i = 0; i < numHebras; i++) {
            try {
                v[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo cíclico (seg.):\t\t\t" + tt);
        System.out.println("Multiplos de 2: " + m.resultadoMultiplos2());
        System.out.println("Multiplos de 3: " + m.resultadoMultiplos3());
        System.out.println("Multiplos de 5: " + m.resultadoMultiplos5());
    }

    static class MiHebraCiclica extends Thread {
        int miId;
        int numHebras;
        int vector[];
        Multiplos m;

        public MiHebraCiclica(int i, int numHebras, int[] vectorNumeros, Multiplos m) {
            this.miId = i;
            this.numHebras = numHebras;
            this.vector = vectorNumeros;
            this.m = m;
        }

        public void run() {
            for (int j = miId; j < vector.length; j += numHebras) {
                if (vector[j] % 2 == 0)
                    m.numeroMultiplo2(1);
                if (vector[j] % 3 == 0)
                    m.numeroMultiplo3(1);
                if (vector[j] % 5 == 0)
                    m.numeroMultiplo5(1);
            }
        }
    }

    static void implementacionCiclicaReducciones(int[] vectorNumeros, int numHebras, Multiplos m) {
        long t1;
        long t2;
        double tt;

        System.out.println("");
        System.out.println("Implementación cíclica con Reducciones.");

        MiHebraCiclicaReducciones v[] = new MiHebraCiclicaReducciones[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraCiclicaReducciones(i, numHebras, vectorNumeros, m);
            v[i].start();
        }

        for (int i = 0; i < numHebras; i++) {
            try {
                v[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo cíclico Reducciones (seg.):\t\t\t" + tt);
        System.out.println("Multiplos de 2: " + m.resultadoMultiplos2());
        System.out.println("Multiplos de 3: " + m.resultadoMultiplos3());
        System.out.println("Multiplos de 5: " + m.resultadoMultiplos5());
    }

    static class MiHebraCiclicaReducciones extends Thread {
        int miId;
        int numHebras;
        int vector[];
        Multiplos m;

        public MiHebraCiclicaReducciones(int i, int numHebras, int[] vectorNumeros, Multiplos m) {
            this.miId = i;
            this.numHebras = numHebras;
            this.vector = vectorNumeros;
            this.m = m;
        }

        public void run() {
            int multiplo2 = 0;
            int multiplo3 = 0;
            int multiplo5 = 0;

            for (int j = miId; j < vector.length; j += numHebras) {
                if (vector[j] % 2 == 0)
                    multiplo2++;
                if (vector[j] % 3 == 0)
                    multiplo3++;
                if (vector[j] % 5 == 0)
                    multiplo5++;
            }

            m.multiplo2.addAndGet(multiplo2);
            m.multiplo3.addAndGet(multiplo3);
            m.multiplo5.addAndGet(multiplo5);
        }
    }
}

class Multiplos {
    AtomicInteger multiplo2 = new AtomicInteger();
    AtomicInteger multiplo3 = new AtomicInteger();
    AtomicInteger multiplo5 = new AtomicInteger();

    void numeroMultiplo2(int valorVector) {
        this.multiplo2.addAndGet(valorVector);
    }
    void numeroMultiplo3(int valorVector) {
        this.multiplo3.addAndGet(valorVector);
    }
    void numeroMultiplo5(int valorVector) {
        this.multiplo5.addAndGet(valorVector);
    }

    int resultadoMultiplos2() {return this.multiplo2.get(); }
    int resultadoMultiplos3() {return this.multiplo3.get(); }
    int resultadoMultiplos5() {return this.multiplo5.get(); }
}
