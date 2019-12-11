package TP2Concurrente;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloSubmundo implements Runnable {

    private int inicioSubMatriz;
    private int finSubMatriz;
    private Celula[][] m;
    private CyclicBarrier esperarCheckeoEstado;

    public HiloSubmundo(Celula[][] matriz, int inicio, int fin, CyclicBarrier estado) {
        this.inicioSubMatriz = inicio;
        this.finSubMatriz = fin;
        this.m = matriz;
        this.esperarCheckeoEstado = estado;
    }
    
    public void mostrarPos(){
        System.out.println(this.inicioSubMatriz+" "+this.finSubMatriz);
    }

    @Override
    public void run() {
        int i, j;
        // Se verifica el estado de cada celula del submundo correspondiente
        for (i = 0; i < this.m.length; i++) {
            for (j = this.inicioSubMatriz; j < this.finSubMatriz; j++) {
                this.m[i][j].verificarVecinos();
            }
        }

        try {
            // Se espera a que los otros hilos verifiquen el estado de las celulas de sus submundos
            this.esperarCheckeoEstado.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloSubmundo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(HiloSubmundo.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Se cambia el estado de todas las celulas correspondiente del primer submundo
        for (i = 0; i < this.m.length; i++) {
            for (j = this.inicioSubMatriz; j < this.finSubMatriz; j++) {
                this.m[i][j].cambiarEstado();
            }
        }
    }
}
