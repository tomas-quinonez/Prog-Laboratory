package TPConcurrente;

import java.util.concurrent.Callable;

public class TareaSumar implements Callable<Integer> {

    private final int[] arr;
    private final int inicio;
    private final int fin;

    public TareaSumar(int[] arr, int inicio, int fin) {
        this.arr = arr;
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public Integer call() { // se redefine el metodo call() para realizar la suma parcial del arreglo
        int suma = 0;
        for (int i = this.inicio; i <= this.fin; i++) {
            suma += arr[i];
        }
        return suma;
    }
}
