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
        this.siguienteEstado=estado;
        this.posFila=posFila;
        this.posCol=posCol;
        this.listaAdy=new LinkedList<Celula>();
    }
    
    public void cargarListaAdy(Celula[][] m){
        //Se obtienen y cargan la lista de vecinos de la celula
        int k;
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
        // Se verifica el estado de cada vecino
        int i=0;
        this.siguienteEstado=this.estado;
        if(this.estado==0){
            int vecinosVivos=0;
            for(Celula cel: this.listaAdy){
                if(cel.obtenerEstadoActual()==1){
                    vecinosVivos++;
                }
                i++;
            }
            if(vecinosVivos==3){
                this.siguienteEstado=1;
            }
        }else{ // la celula está viva
            int vecinosVivos=0;
            for(Celula cel: this.listaAdy){
                if(cel.obtenerEstadoActual()==1){
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
        // Se cambia el estado de la celula
        if(this.estado!=this.siguienteEstado){
            this.estado=this.siguienteEstado;
        }
    }
    
    public int obtenerEstadoActual(){
        // Se obtiene el estado actual
        return this.estado;
    }
    
    public int obtenerEstadoSig(){
        return this.siguienteEstado;
    }
    
    public void imprimirVecinos(){
        for(Celula cel: listaAdy){
            System.out.print(" "+cel.mostrarCelula());
        }
    }
    
    public String mostrarCelula(){
        String cell="\033[36m*";
        if(this.estado==1)
            cell="\033[31m1";
        return cell;
    }
    
    public void darVida(){
        // Metodo usado para dar vida a un conjunto de celulas inciales
        this.estado=1;
        this.siguienteEstado=1;
    }
}
