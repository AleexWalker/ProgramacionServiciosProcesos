package Practica2TEMA2VolatileSynchronized;

public class Practica2Bien {
    public static void main (String args[] ) {
        if ( args.length != 1 ) {
            System.out.println ("Error");
            System.exit(-1);
        }

        int numHebras = Integer.parseInt (args [0] );

        miHebra[] v = new miHebra[ numHebras ];
        CuentaIncrementos cuenta = new CuentaIncrementos();

        System.out.println ( "Soy el programa principal y contador vale: " + cuenta.dameContador());

        for ( int i = 0; i < numHebras; i++ ) {
            v[i] = new miHebra(i,cuenta);
        }

        for ( int i = 0; i < numHebras; i++ ) {
            v[i].start();

        }

        for (int i = 0; i < numHebras; i++ ) {
            try{
                v[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();

            }
        }
        System.out.println ("Soy el programa principal y contador vale: " + cuenta.dameContador());
    }
}

class miHebra extends Thread{
    int hebra;
    CuentaIncrementos contador;

    public miHebra(int hebra , CuentaIncrementos contador){
        this.hebra = hebra;
        this.contador = contador;
    }

    public void run(){
        for (int i = 0 ; i < 1000000 ; i++){
            if (i == 0)
                System.out.println("Empieza el contador");
            else if (i == 1000000-1)
                System.out.println("Ha termindo el contador");
            contador.incrementaContador();
        }
    }
}

class CuentaIncrementos {
    volatile long contador = 0;
    synchronized void incrementaContador(){
        contador++;
    }
    synchronized long dameContador(){
        return contador;
    }
}
