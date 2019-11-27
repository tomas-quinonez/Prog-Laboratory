package TP2Concurrente;

import java.util.LinkedList;

public class Celula {
    
    private int estado;
    private int siguienteEstado;
    private int posFila;
    private int posCol;
    private LinkedList<Celula> listaAdy;
    
    public Celula(Celula[][] matriz,int estado, int posFila, int posCol) {
        this.estado=estado;
        this.siguienteEstado=0;
        this.posFila=posFila;
        this.posCol=posCol;
        this.listaAdy=new LinkedList<Celula>();
        this.cargarListaAdy(matriz);
    }
    
    public void cargarListaAdy(Celula[][] m){
        
    }
    
    public void verificarVecinos(){
        int i=0;
        this.siguienteEstado=this.estado;
        if(this.estado==0){
            int vecinosVivos=0;
            while(i<this.listaAdy.size() && vecinosVivos<=3){
                if(this.listaAdy.get(i).obtenerEstadoActual()==1){
                    vecinosVivos++;
                }
            }
            if(vecinosVivos==3){
                this.siguienteEstado=1;
            }
        }else{ // la celula está viva
            int vecinosVivos=0;
            while(i<this.listaAdy.size() && vecinosVivos<=3){
                if(this.listaAdy.get(i).obtenerEstadoActual()==1){
                    vecinosVivos++;
                }
            }
            if(vecinosVivos!=2 && vecinosVivos!=3){
                this.siguienteEstado=0;
            }
        }
    }
    
    public void cambiarEstado(){
        if(this.estado!=this.siguienteEstado){
            this.estado=this.siguienteEstado;
        }
    }
    
    public int obtenerEstadoActual(){
        return this.estado;
    }
}
