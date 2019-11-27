package TP2Concurrente;

import java.util.LinkedList;

public class Celula {
    
    private int estado;
    private int siguienteEstado;
    private int posFila;
    private int posCol;
    private LinkedList<Celula> listaAdy;
    
    public Celula(Celula[][] m,int estado, int posFila, int posCol) {
        this.estado=estado;
        this.siguienteEstado=0;
        this.posFila=posFila;
        this.posCol=posCol;
        this.listaAdy=new LinkedList<Celula>();
        this.cargarListaAdy(m);
    }
    
    public void cargarListaAdy(Celula[][] m){
        int k=this.posFila, l=this.posCol;
        //vecino superior
        if(this.posFila-1<0){
            k=m.length-1;
        }else{
            k=this.posFila-1;
        }
        this.listaAdy.add(m[k][this.posCol]);
        //
        //vecino inferior
        if(this.posFila+1>m.length-1){
            k=0;
        }else{
            k=this.posFila+1;
        }
        this.listaAdy.add(m[k][this.posCol]);
        //
        //vecino izquierdo
        if(this.posCol-1>0){
            k=m[0].length-1;
        }else{
            k=this.posCol-1;
        }
        this.listaAdy.add(m[this.posFila][k]);
        //
        //vecino derecho
        if(this.posCol+1>m[0].length-1){
            k=0;
        }else{
            k=this.posCol+1;
        }
        this.listaAdy.add(m[this.posFila][k]);
        //
        //vecino diagonal sup izquierdo
        if(this.posFila+1>0 && this.posCol<0){
            k=m.length-1;
            l=m[0].length-1;
        }else{
            k=this.posFila-1;
            l=this.posCol-1;
        }
        this.listaAdy.add(m[k][l]);
        //
        //vecino diag sup derecho
        if(this.posFila-1<0 && this.posCol+1>m.length-1){
            k=m.length-1;
            l=0;
        }else{
            k=this.posFila-1;
            l=this.posCol+1;
        }
        this.listaAdy.add(m[k][l]);
        //
        //vecino diagonal inferior izq
        if(this.posFila+1>m.length-1 && this.posCol-1<0){
            k=0;
            l=m[0].length-1;
        }else{
            k=this.posFila+1;
            l=this.posCol-1;
        }
        this.listaAdy.add(m[k][l]);
        //
        //vecino diagonal inferior derecho
        if(this.posFila+1>m.length-1 && this.posCol+1>m[0].length-1){
            k=0;
            l=0;
        }else{
            k=this.posFila+1;
            l=this.posCol+1;
        }
        this.listaAdy.add(m[k][l]);
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
    
    public char mostrarCelula(){
        char cell='+';
        if(this.estado==1)
            cell='+';
        return cell;
    }
}
