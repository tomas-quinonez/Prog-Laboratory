package TP2Concurrente;
// version 11/12/19

import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mundo {

    public static Celula[][] m;
    public static Scanner sc;

    public static void main(String[] args) {

        // Se carga la matriz con las dimensiones especificadas
        inicializarMatriz();

        // Se carga la matriz inicial
        cargarMatriz();

        // Cada celula en el mundo carga sus correspondientes vecinos
        cargarCelulasVecinas();

        // Se le da vida a un grupo de celulas iniciales
        System.out.println("Eliga un grupo de celulas inciales vivas");
        int op = sc.nextInt();
        switch(op){
            case 1: darVidaCelulasIniciales(); break;
            case 2: darVidaCelulasIniciales2(); break;
            case 3: darVidaCelulasIniciales3(); break;
            case 4: darVidaCelulasIniciales4(); break;
        }
        
        imprimirMundo();
        System.out.println();

        int cantTareas = 3;
        if (m[0].length % 3 > 0) {
            cantTareas++;
        }

        // Barrera usada para que los hilos esperen al chequeo de las celulas de su submundo
        CyclicBarrier esperarVerificacion = new CyclicBarrier(cantTareas);

        ExecutorService poolThreads = Executors.newFixedThreadPool(4);

        // Se crea un pool de threads para resolver un submundo del mundo total
        LinkedList<HiloSubmundo> listaTareas = new LinkedList();
        int i;
        int posInicio = 0;
        int posFin = m[0].length / 3;
        for (i = 0; i < cantTareas; i++) {
            HiloSubmundo tarea = new HiloSubmundo(m, posInicio, posFin, esperarVerificacion);
            listaTareas.add(tarea);
            posInicio = posFin;
            if (i == cantTareas - 2 && m[0].length % 3 >0) {
                posFin = m[0].length;
            } else {
                posFin += m[0].length / 3;
            }
        }

        // En cada iteración los hilos resuelven su submundo corresponde y al final se imprime el mundo
        while (true) {
            for (HiloSubmundo hilo : listaTareas) {
                poolThreads.execute(hilo);
            }
            
            imprimirMundo();
            System.out.println();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mundo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void inicializarMatriz() {
        sc = new Scanner(System.in);
        int cantFilas, cantCols;
        System.out.println("Ingrese las dimensiones de la matriz (Filas x Columnas)");
        cantFilas = sc.nextInt();
        cantCols = sc.nextInt();
        m = new Celula[cantFilas][cantCols];
    }

    public static void imprimirMundo() {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print("  "+m[i][j].mostrarCelula());
            }
            System.out.println();	// Imprime salto de línea
        }
    }

    public static void cargarMatriz() {
        // Se carga la matriz inicial con las celulas
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                m[i][j] = new Celula(m, 0, i, j);
            }
        }
    }

    public static void darVidaCelulasIniciales() {
        // Se da vida a un conjunto de celulas para comenzar a poblar el mundo
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
    
    public static void darVidaCelulasIniciales2(){
        m[12][12].darVida();
        m[12][11].darVida();
        m[12][13].darVida();
        m[13][13].darVida();
        m[13][11].darVida();
        m[14][12].darVida();
        m[11][12].darVida();
    }
    
    public static void darVidaCelulasIniciales3(){
        m[12][12].darVida();
        m[13][13].darVida();
        m[14][13].darVida();
        m[14][12].darVida();
        m[14][11].darVida();
    }
    
    public static void darVidaCelulasIniciales4(){
        m[15][10].darVida();
        m[15][11].darVida();
        m[15][12].darVida();
        m[15][13].darVida();
        m[15][14].darVida();
        m[15][15].darVida();
        m[15][16].darVida();
        m[15][17].darVida();
        m[15][18].darVida();
        m[15][19].darVida();
    }

    public static void cargarCelulasVecinas() {
        // Cada celula del mundo carga sus vecinos
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j].cargarListaAdy(m);
            }
        }
    }
}
