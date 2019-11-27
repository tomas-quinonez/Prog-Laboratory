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
            
            cargarMatriz(m);
            
            cargarCelulasVecinas(m);
            
            
            darVidaCelulasIniciales(m);
            imprimirMundo(m);
            
            ExecutorService poolThreads=Executors.newFixedThreadPool(3);
            poolThreads.execute(new HiloSubmundo(m, 0, 10, esperarVerificacion, esperarCambioEstado));
            poolThreads.execute(new HiloSubmundo(m, 10, 20, esperarVerificacion, esperarCambioEstado));
            poolThreads.execute(new HiloSubmundo(m, 20, 30, esperarVerificacion, esperarCambioEstado));
            
            while(true){
                esperarCambioEstado.await();
                imprimirMundo(m);
                System.out.println();
                Thread.sleep(2000);
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
                    System.out.print("  "+mundo[i][j].mostrarCelula());	// Imprime elemento
                }
                System.out.println();	// Imprime salto de línea
            }
    }
    
    public static void cargarMatriz(Celula[][] m) {
        for(int i=0; i<m.length; i++){
            for(int j=0; j< m[i].length; j++){
                m[i][j]= new Celula(m, 0, i, j);
            }
        }
    }
    
    public static void darVidaCelulasIniciales(Celula[][] m) {
        m[12][12].darVida();
        m[13][12].darVida();
        m[14][12].darVida();
        m[15][12].darVida();
        m[16][12].darVida();
        m[12][14].darVida();
        m[16][14].darVida();
        m[12][16].darVida();
        m[13][16].darVida();
        m[14][16].darVida();
        m[15][16].darVida();
        m[16][16].darVida();
    }
    
    public static void cargarCelulasVecinas(Celula[][] m){
        for(int i=0; i<m.length; i++){
            for(int j=0; j< m[i].length; j++){
                m[i][j].cargarListaAdy(m);
            }
        }
    }
}
