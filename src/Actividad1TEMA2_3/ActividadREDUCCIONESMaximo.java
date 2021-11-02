package Actividad1TEMA2_3;

import java.util.concurrent.atomic.AtomicLong;

public class ActividadREDUCCIONESMaximo extends Thread {
    public static void main(String args[]) {
        int numHebras;
        double vector[] = {12.0, 15.1, 8.0, 4.2, 9.0, 7.3, 6.4, 1.2, 3.4, 9.1, 18.4, 0.5, 24.8, 42.5, 31.8, 54.2, 1.2, 8.8, 7.7, 5.5, 4.4, 3.3, 2.2, 2.4};
        Maximo a = new Maximo();
        numHebras = 4;

        implementacionSecuencial(vector, a);
        a = new Maximo();

        implementacionCiclica(vector, numHebras, a);
    }

    static void implementacionSecuencial(double[] vectorNumeros, Maximo a) {
        long t1;
        long t2;
        double tt;
        double sumatorio = 0;
        double maximoVector = vectorNumeros[0];

        System.out.println("");
        System.out.println("Implementación secuencial.");

        t1 = System.nanoTime();
        //Escribe aquí la implementación secuencial

        for (int i = 0 ; i <vectorNumeros.length ; i++) {
            sumatorio += vectorNumeros[i];
            if (vectorNumeros[i] > maximoVector) {
                maximoVector = vectorNumeros[i];
            }
        }

        //Fin de la implementación secuencial
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
        System.out.println("Suma total del vector: " + sumatorio);
        System.out.println("Maximo del vector: " + maximoVector);
    }

    static void implementacionCiclica(double[] vectorNumeros, int numHebras, Maximo a) {
        long t1;
        long t2;
        double tt;

        System.out.println("");
        System.out.println("Implementación cíclica.");

        MiHebraCiclica v[] = new MiHebraCiclica[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraCiclica(i, numHebras, vectorNumeros, a);
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
        System.out.println("Maximo de VECTOR: " + a.dameResultado());
    }

    static class MiHebraCiclica extends Thread {
        int miId;
        int numHebras;
        double vector[];
        Maximo a;

        public MiHebraCiclica(int i, int numHebras, double[] vectorNumeros, Maximo a) {
            this.miId = i;
            this.numHebras = numHebras;
            this.vector = vectorNumeros;
            this.a = a;
        }

        public void run() {
            double maximoVector = vector[0];

            for (int j = miId; j < vector.length; j += numHebras) {
                if (vector[j] > maximoVector) {
                    a.acumulaValor(vector[j]);
                }
            }
        }
    }
}

class Maximo {
    double maximo = 0;

    synchronized void acumulaValor ( double valor ) {
        this.maximo = valor;
    }

    synchronized double dameResultado() {
        return this.maximo;
    }
}

