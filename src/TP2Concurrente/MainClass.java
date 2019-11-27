package TP2Concurrente;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass {

    public static void main(String[] args) {

        try {
            Celula[][] m = new Celula[30][30];
            
            CyclicBarrier esperarVerificacion = new CyclicBarrier(3);
            CyclicBarrier esperarCambioEstado = new CyclicBarrier(4);
            
            ExecutorService poolThreads=Executors.newFixedThreadPool(3);
            poolThreads.execute(new HiloSubmundo(m, 0, 9, esperarVerificacion, esperarCambioEstado));
            poolThreads.execute(new HiloSubmundo(m, 10, 19, esperarVerificacion, esperarCambioEstado));
            poolThreads.execute(new HiloSubmundo(m, 20, 29, esperarVerificacion, esperarCambioEstado));
            
            while(true){
                esperarCambioEstado.await();
                imprimirMundo(m);
            }    
        } catch (InterruptedException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void imprimirMundo(Celula[][] mundo){
        for (int i = 0; i < mundo.length; i++) {
                for (int j = 0; j < mundo[i].length; j++) {
                    System.out.print("  +");	// Imprime elemento
                }
                System.out.println();	// Imprime salto de línea
            }
    }
}
