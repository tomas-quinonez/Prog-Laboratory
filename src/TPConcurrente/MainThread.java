package TPConcurrente;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainThread {

    public static void main(String[] args) {
        int length;
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la longitud del arreglo.");
        length = sc.nextInt();
        int[] arr = new int[length];
        cargarArreglo(arr);

        // pool de threads con 2 hilos fijos
        ThreadPoolExecutor exe = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        // lista de objetos de tipo Callable<Integer>
        Collection colTareas = new LinkedList<Callable<Integer>>();

        System.out.println("Ingrese en cuantas partes dividirá al arreglo.");
        int cantTareas = sc.nextInt();
        int cantDivs = arr.length / cantTareas;

        int inicio = 0, fin = cantDivs;

        for (int i = 0; i < cantTareas; i++) { // se crean los hilos sumadores
            colTareas.add(new TareaSumar(arr, inicio, fin - 1));
            inicio = fin;
            fin = fin + cantDivs;
        }

        // tarea extra en caso de division de tareas no entera
        colTareas.add(new TareaSumar(arr, fin - cantDivs, arr.length - 1));

        try {
            List colFutures = exe.invokeAll(colTareas); // lista de objetos future con las sumas parciales
            int total = sumaTotal(colFutures); // se suman los resultados de los hilos sumadores
            exe.shutdown();
            System.out.println(Arrays.toString(arr));
            System.out.println("Suma total del arreglo hecha con los hilos: " + total);
            System.out.println("Suma total verdadera: " + sumar(arr));
        } catch (InterruptedException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // metodo para cargar el arreglo con numeros random
    public static void cargarArreglo(int[] arr) {
        Random ran = new Random();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = ran.nextInt(11) + 1;
        }

    }

    // suma de todos las sumas parciales de los hilos sumadores
    public static int sumaTotal(List lista) throws ExecutionException, InterruptedException {

        int sumaTotal = 0;
        while (!lista.isEmpty()) {
            sumaTotal += ((int) ((Future) lista.get(0)).get());
            lista.remove(0);
        }
        return sumaTotal;
    }

    // suma total verdadera de los elementos del arrreglo
    public static int sumar(int[] arr) {
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += arr[i];
        }
        return total;
    }
}
