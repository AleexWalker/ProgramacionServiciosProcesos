package Actividad1TEMA2_3;

public class ActividadREDUCCIONES extends Thread {
    public static void main(String args[]) {
        int numHebras;
        double vector[] = {12.0, 15.1, 8.0, 4.2, 9.0, 7.3, 6.4, 1.2, 3.4, 9.1, 18.4, 0.5, 24.8, 42.5, 31.8, 54.2, 1.2, 8.8, 7.7, 5.5, 4.4, 3.3, 2.2, 2.4};
        Acumula a = new Acumula();
        numHebras = 4;

        implementacionSecuencial(vector, a);
        a = new Acumula();

        implementacionCiclica(vector, numHebras, a);
    }

    static void implementacionSecuencial(double[] vectorNumeros, Acumula a) {
        long t1;
        long t2;
        double tt;
        double sumatorio = 0;

        System.out.println("");
        System.out.println("Implementación secuencial.");

        t1 = System.nanoTime();
        //Escribe aquí la implementación secuencial

        for (int i = 0 ; i <vectorNumeros.length ; i++) {
            sumatorio += vectorNumeros[i];
        }

        //Fin de la implementación secuencial
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
        System.out.println("Suma total del vector: " + sumatorio);

    }

    static void implementacionCiclica(double[] vectorNumeros, int numHebras, Acumula a) {
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
        System.out.println("Suma total del vector: " + a.dameResultado());
    }

    static class MiHebraCiclica extends Thread {
        int miId;
        int numHebras;
        double vector[];
        Acumula a;

        public MiHebraCiclica(int i, int numHebras, double[] vectorNumeros, Acumula a) {
            this.miId = i;
            this.numHebras = numHebras;
            this.vector = vectorNumeros;
            this.a = a;
        }

        public void run() {
            double sumatorio = 0;

            for (int j = miId; j < vector.length; j += numHebras) {
                sumatorio += vector[j];
            }
            a.acumulaValor(sumatorio);
        }
    }
}

class Acumula {
    double suma = 0.0;

    synchronized void acumulaValor ( double valor ) {
        this.suma += valor;
    }

    synchronized double dameResultado() {
        return this.suma;
    }
}



