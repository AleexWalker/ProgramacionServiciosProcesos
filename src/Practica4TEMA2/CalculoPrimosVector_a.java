package Practica4TEMA2;

import java.util.concurrent.atomic.AtomicLong;

public class CalculoPrimosVector_a extends Thread{
    public static void main(String args[]) {
        int numHebras;
        CuentaIncrementos c = new CuentaIncrementos();
        long vectorNumeros[] = {
                200000033L, 200000039L, 200000051L, 200000069L,
                200000081L, 200000083L, 200000089L, 200000093L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
        };


        numHebras = 4;

        implementacionSecuencial(vectorNumeros);
        c = new CuentaIncrementos();
        implementacionCiclica(vectorNumeros, numHebras, c);
        c = new CuentaIncrementos();
        implementacionBloques(vectorNumeros, numHebras, c);


    }

    static void implementacionSecuencial(long[] vectorNumeros) {
        long t1;
        long t2;
        double tt;
        CuentaIncrementos c = new CuentaIncrementos();

        System.out.println("");
        System.out.println("Implementación secuencial.");

        t1 = System.nanoTime();
        //Escribe aquí la implementación secuencial

        for (long numero : vectorNumeros ) {
            if (esPrimo(numero)){
                System.out.println(numero + " ");
                c.incrementaNumIncrementos();
            }
        }

        //Fin de la implementación secuencial
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
        System.out.println("¡Hay " + c.dameNumIncrementos() + " numeros primos!");
    }

    static void implementacionCiclica(long[] vectorNumeros, int numHebras, CuentaIncrementos c) {
        long t1;
        long t2;
        double tt;


        System.out.println("");
        System.out.println("Implementación cíclica.");

        MiHebraCiclica v[] = new MiHebraCiclica[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraCiclica(i, numHebras, vectorNumeros, c);
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
        System.out.println("¡Hay " + c.dameNumIncrementos() + " numeros primos!");
    }

    static class MiHebraCiclica extends Thread {
        private int idHebra;
        private long [] longitudVector;
        private int numHebras;
        CuentaIncrementos c;

        public MiHebraCiclica(int i, int numHebras, long[] vectorNumeros , CuentaIncrementos c) {
            this.numHebras = numHebras;
            this.longitudVector = vectorNumeros;
            this.idHebra = i;
            this.c = c;
        }

        public void run () {
            for (int j = idHebra ; j < longitudVector.length ; j += numHebras){
                if (esPrimo(longitudVector[j])) {
                    System.out.println(longitudVector[j] + " ");
                    c.incrementaNumIncrementos();
                }
            }
        }
    }

//------------------------------------------------------------------------------------------------------------

    static void implementacionBloques(long[] vectorNumeros, int numHebras, CuentaIncrementos c) {

        long t1;
        long t2;
        double tt;

        System.out.println("");
        System.out.println("Implementación por bloques.");

        MiHebraBloques v[] = new MiHebraBloques[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraBloques(i, numHebras, vectorNumeros, c);
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

        System.out.println("Tiempo Bloques (seg.):\t\t\t" + tt);
        System.out.println("¡Hay " + c.dameNumIncrementos() + " numeros primos!");
    }

    static class MiHebraBloques extends Thread {
        private int idHebra;
        private long [] longitudVector;
        private int numHebras;
        CuentaIncrementos c;

        public MiHebraBloques(int i, int numHebras, long[] vectorNumeros, CuentaIncrementos c) {
            this.numHebras = numHebras;
            this.longitudVector = vectorNumeros;
            this.idHebra = i;
            this.c = c;
        }

        public void run () {
            int Tam = (longitudVector.length + numHebras - 1)/numHebras;
            int ini = idHebra * Tam;
            int fin = Math.min(longitudVector.length , (idHebra + 1) * Tam);
            for (int i = ini ; i < fin ; i++){
                if (esPrimo(longitudVector[i])) {
                    System.out.println(longitudVector[i] + " ");
                    c.incrementaNumIncrementos();
                }
            }
        }
    }

    static boolean esPrimo( long num ) {
        boolean primo;
        if( num < 2 ) {
            primo = false;
        } else {
            primo = true;
            long i = 2;
            while( ( i < num )&&( primo ) ) {
                primo = ( num % i != 0 );
                i++;
            }
        }
        return( primo );
    }
}

class CuentaIncrementos {
    AtomicLong numIncrementos = new AtomicLong(0);

    void incrementaNumIncrementos() {
        numIncrementos.incrementAndGet();
    }

    AtomicLong dameNumIncrementos() {
        return (numIncrementos);
    }
}