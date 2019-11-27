package TP2Concurrente;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloSubmundo implements Runnable{
    
    private int rowLength;
    private int heightLength;
    private Celula[][] m;
    private CyclicBarrier esperarCheckeoEstado;
    private CyclicBarrier esperarCambioEstado;
    
    public HiloSubmundo(Celula[][] matriz, int rowLength, int heightLength, CyclicBarrier estado, CyclicBarrier cambio){
        this.rowLength=rowLength;
        this.heightLength=heightLength;
        this.m=matriz;
        this.esperarCheckeoEstado=estado;
        this.esperarCambioEstado=cambio;
    }
    
    public void run(){
        int i, j;
        while(true){
            
            for(i=this.rowLength; i<this.m.length;i++){
                for(j=this.rowLength; j<10; j++){
                    this.m[i][j].verificarVecinos();
                }
            }
            
            try {
                this.esperarCheckeoEstado.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloSubmundo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(HiloSubmundo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(i=this.rowLength; i<this.m.length;i++){
                for(j=this.rowLength; j<10; j++){
                    this.m[i][j].cambiarEstado();
                }
            }
            
            try {
                this.esperarCambioEstado.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloSubmundo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(HiloSubmundo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
