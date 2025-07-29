package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del algoritmo de Backtracking (con memoización) para resolver laberintos.
 * Utiliza búsqueda recursiva para encontrar el camino más corto desde el inicio hasta el fin.
 * Guarda los resultados en una matriz memoizada para evitar cálculos repetidos.
 */
public class Backtracking implements MazeSolver {

    /**
     * Direcciones posibles: arriba, abajo, izquierda, derecha.
     */
    private final int[][] DIRECCIONES = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private int[][] memo;
    private int filas, columnas;

    /**
     * Resuelve el laberinto desde el punto de inicio hasta el punto final usando Backtracking.
     *
     * @param matriz Matriz de estados de celdas del laberinto.
     * @param inicio Celda de inicio.
     * @param fin    Celda de destino.
     * @return Objeto SolveResults que contiene el camino óptimo, número de pasos y tiempo de ejecución.
     */
    @Override
    public SolveResults resolver(CellState[][] matriz, Cell inicio, Cell fin) {
        long inicioTiempo = System.currentTimeMillis();

        filas = matriz.length;
        columnas = matriz[0].length;
        memo = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                memo[i][j] = -1;
            }
        }

        int pasos = dp(matriz, inicio.getFila(), inicio.getColumna(), fin);

        List<Cell> camino = pasos == Integer.MAX_VALUE
                ? new ArrayList<>()
                : reconstruirCamino(matriz, inicio, fin);

        long finTiempo = System.currentTimeMillis();
        long tiempoTotal = finTiempo - inicioTiempo;

        return new SolveResults(camino, camino.size(), tiempoTotal);
    }

    /**
     * Método recursivo que busca el camino más corto desde una celda actual hasta el destino.
     *
     * @param matriz Laberinto con estados.
     * @param i      Fila actual.
     * @param j      Columna actual.
     * @param fin    Celda destino.
     * @return Número mínimo de pasos desde (i, j) hasta fin, o Integer.MAX_VALUE si no hay camino.
     */
    private int dp(CellState[][] matriz, int i, int j, Cell fin) {
        if (i < 0 || j < 0 || i >= filas || j >= columnas || matriz[i][j] == CellState.WALL) {
            return Integer.MAX_VALUE;
        }

        if (i == fin.getFila() && j == fin.getColumna()) {
            return 0;
        }

        // Si ya fue calculado
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        matriz[i][j] = CellState.WALL;
        int minPaso = Integer.MAX_VALUE;

        for (int[] dir : DIRECCIONES) {
            int ni = i + dir[0], nj = j + dir[1];
            int pasos = dp(matriz, ni, nj, fin);
            if (pasos != Integer.MAX_VALUE) {
                minPaso = Math.min(minPaso, 1 + pasos);
            }
        }

        matriz[i][j] = CellState.EMPTY;
        memo[i][j] = minPaso;
        return minPaso;
    }

    /**
     * Reconstruye el camino desde la celda de inicio hasta la de fin
     * utilizando la matriz memo con los valores mínimos de pasos.
     *
     * @param matriz Laberinto con estados.
     * @param inicio Celda de inicio.
     * @param fin    Celda destino.
     * @return Lista con el camino reconstruido desde inicio hasta fin.
     */
    private List<Cell> reconstruirCamino(CellState[][] matriz, Cell inicio, Cell fin) {
        List<Cell> camino = new ArrayList<>();
        int i = inicio.getFila(), j = inicio.getColumna();
        camino.add(new Cell(i, j, matriz[i][j]));

        while (!(i == fin.getFila() && j == fin.getColumna())) {
            int mejor = memo[i][j];
            boolean encontrado = false;

            for (int[] dir : DIRECCIONES) {
                int ni = i + dir[0], nj = j + dir[1];
                if (ni >= 0 && nj >= 0 && ni < filas && nj < columnas && memo[ni][nj] != -1) {
                    if (memo[ni][nj] == mejor - 1) {
                        camino.add(new Cell(ni, nj, matriz[ni][nj]));
                        i = ni;
                        j = nj;
                        encontrado = true;
                        break;
                    }
                }
            }

            if (!encontrado) break;
        }

        return camino;
    }
}
