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
    }
    
    public void cargarListaAdy(Celula[][] m){
        int k, l;
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
        if(this.posCol-1<0){
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
        this.cargarVecinoDiagSupIzq(m);
        this.cargarVecinoDiagSupDer(m);
        this.cargarVecinoDiagInfIzq(m);
        this.cargarVecinoDiagInfDer(m);
    }
    
    public void cargarVecinoDiagInfIzq(Celula[][] m){
        int f=this.posFila;
        int c=this.posCol;
        if(c-1<0){
            if(f+1>m.length-1){
                this.listaAdy.add(m[0][m[0].length-1]);
            }else{
                this.listaAdy.add(m[f+1][m[0].length-1]);
            }
        }else{
            if(f+1>m.length-1){
                this.listaAdy.add(m[0][c-1]);
            }else{
                this.listaAdy.add(m[f+1][c-1]);
            }
        }
    }
    
    public void cargarVecinoDiagInfDer(Celula[][] m){
        int f=this.posFila;
        int c=this.posCol;
        if(f+1>m.length-1){
            if(c+1>m[0].length-1){
                this.listaAdy.add(m[0][0]);
            }else{
                this.listaAdy.add(m[0][c+1]);
            }
        }else{
            if(c+1>m[0].length-1){
                this.listaAdy.add(m[f+1][0]);
            }else{
                this.listaAdy.add(m[f+1][c+1]);
            }
        }
    }
    
    public void cargarVecinoDiagSupDer(Celula[][] m){
        int f=this.posFila;
        int c=this.posCol;
        if(f-1<0){
            if(c+1>m[0].length-1){
                this.listaAdy.add(m[m.length-1][0]);
            }else{
                this.listaAdy.add(m[m.length-1][c+1]);
            }
        }else{
            if(c+1>m[0].length-1){
                this.listaAdy.add(m[f-1][0]);
            }else{
                this.listaAdy.add(m[f-1][c+1]);
            }
        }
    }
    
    public void cargarVecinoDiagSupIzq(Celula[][] m){
        int f=this.posFila;
        int c=this.posCol;
        if(f-1<0){
            if(c-1<0){
                this.listaAdy.add(m[m.length-1][m[0].length-1]);
            }else{
                this.listaAdy.add(m[m.length-1][c-1]);
            }
        }else{
            if(c-1<0){
                this.listaAdy.add(m[f-1][m[0].length-1]);
            }else{
                this.listaAdy.add(m[f-1][c-1]);
            }
        }
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
                i++;
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
                i++;
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
        char cell='O';
        if(this.estado==1)
            cell='1';
        return cell;
    }
    
    public void darVida(){
        this.estado=1;
    }
}
