package TP2Concurrente;

public class MainClass {

    public static void main(String[] args) {

        char[][] m = new char[30][30];
        

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print("  +");	// Imprime elemento
            }
            System.out.println();	// Imprime salto de línea
        }
    }
}
