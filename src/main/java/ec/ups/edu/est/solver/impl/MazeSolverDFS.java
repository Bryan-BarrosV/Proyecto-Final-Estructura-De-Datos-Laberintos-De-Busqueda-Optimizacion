package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.*;

/**
 * Implementación del algoritmo Depth-First Search (DFS) para la resolución de laberintos.
 * Este algoritmo recorre el laberinto en profundidad para encontrar una ruta desde el punto de inicio
 * hasta el de destino, sin garantizar la ruta más corta.
 */
public class MazeSolverDFS implements MazeSolver {

    private boolean[][] visitado;
    private Map<Cell, Cell> padre;
    private int filas;
    private int columnas;

    /**
     * Resuelve el laberinto utilizando búsqueda en profundidad (DFS).
     *
     * @param laberinto matriz de celdas con sus respectivos estados
     * @param inicio    celda inicial
     * @param fin       celda de destino
     * @return un objeto {@link SolveResults} con el camino encontrado, número de pasos y tiempo de ejecución
     */
    @Override
    public SolveResults resolver(CellState[][] laberinto, Cell inicio, Cell fin) {
        long t0 = System.currentTimeMillis();

        filas = laberinto.length;
        columnas = laberinto[0].length;
        visitado = new boolean[filas][columnas];
        padre = new HashMap<>();

        dfs(laberinto, inicio, fin);

        List<Cell> camino = new ArrayList<>();
        int pasos = 0;

        if (padre.containsKey(fin) || inicio.equals(fin)) {
            for (Cell c = fin; c != null; c = padre.get(c)) {
                camino.add(0, c);
                pasos++;
            }
        }

        long t1 = System.currentTimeMillis();
        long tiempoTotal = t1 - t0;

        return new SolveResults(camino, pasos, tiempoTotal);
    }

    /**
     * Algoritmo DFS recursivo que intenta llegar desde una celda actual hasta la celda de fin.
     *
     * @param laberinto matriz del laberinto
     * @param actual    celda actual a explorar
     * @param fin       celda destino
     * @return true si se encuentra un camino hacia la meta, false en caso contrario
     */
    private boolean dfs(CellState[][] laberinto, Cell actual, Cell fin) {
        int i = actual.getFila();
        int j = actual.getColumna();

        if (i < 0 || i >= filas || j < 0 || j >= columnas) return false;
        if (visitado[i][j] || laberinto[i][j] == CellState.WALL) return false;

        visitado[i][j] = true;

        if (actual.equals(fin)) return true;

        int[][] direcciones = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] dir : direcciones) {
            int ni = i + dir[0];
            int nj = j + dir[1];

            if (ni >= 0 && ni < filas && nj >= 0 && nj < columnas) {
                Cell siguiente = new Cell(ni, nj, laberinto[ni][nj]);
                if (!visitado[ni][nj] && laberinto[ni][nj] != CellState.WALL) {
                    padre.put(siguiente, actual);
                    if (dfs(laberinto, siguiente, fin)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
