package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.*;

/**
 * Implementación del algoritmo Breadth-First Search (BFS) para la resolución de laberintos.
 * BFS garantiza encontrar el camino más corto (en cantidad de pasos) desde la celda de inicio
 * hasta la celda de destino, siempre que exista una ruta accesible.
 */
public class MazeSolverBFS implements MazeSolver {

    /**
     * Direcciones válidas de movimiento: derecha, abajo, izquierda, arriba.
     * Estas se expresan como desplazamientos relativos en la matriz.
     */
    private static final int[][] DIRECCIONES = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0}
    };

    /**
     * Resuelve el laberinto aplicando el algoritmo BFS.
     *
     * @param laberinto matriz de estados que representa el laberinto
     * @param inicio celda de partida
     * @param fin celda objetivo
     * @return objeto {@link SolveResults} con el camino encontrado, recorrido completo, pasos y tiempo
     */
    @Override
    public SolveResults resolver(CellState[][] laberinto, Cell inicio, Cell fin) {
        long t0 = System.currentTimeMillis();

        int filas = laberinto.length;
        int columnas = laberinto[0].length;

        int[][] pasosMemo = new int[filas][columnas];
        for (int[] fila : pasosMemo) {
            Arrays.fill(fila, Integer.MAX_VALUE);
        }

        pasosMemo[inicio.getFila()][inicio.getColumna()] = 0;

        Queue<Cell> cola = new LinkedList<>();
        cola.add(inicio);

        Map<Cell, Cell> padre = new HashMap<>();
        List<Cell> recorrido = new ArrayList<>();

        while (!cola.isEmpty()) {
            Cell actual = cola.poll();
            recorrido.add(actual);

            int i = actual.getFila();
            int j = actual.getColumna();

            for (int[] dir : DIRECCIONES) {
                int ni = i + dir[0];
                int nj = j + dir[1];

                if (ni >= 0 && ni < filas && nj >= 0 && nj < columnas &&
                        laberinto[ni][nj] != CellState.WALL) {

                    int nuevoCosto = pasosMemo[i][j] + 1;

                    if (nuevoCosto < pasosMemo[ni][nj]) {
                        pasosMemo[ni][nj] = nuevoCosto;
                        Cell vecino = new Cell(ni, nj, laberinto[ni][nj]);
                        padre.put(vecino, actual);
                        cola.add(vecino);
                    }
                }
            }
        }

        List<Cell> camino = new ArrayList<>();
        int pasos = 0;

        if (pasosMemo[fin.getFila()][fin.getColumna()] != Integer.MAX_VALUE) {
            Cell actual = fin;
            while (actual != null && !actual.equals(inicio)) {
                camino.add(0, actual);
                actual = padre.get(actual);
                pasos++;
            }
            camino.add(0, inicio);
            pasos++;
        }

        long t1 = System.currentTimeMillis();
        long tiempoTotal = t1 - t0;

        return new SolveResults(camino, recorrido, pasos, tiempoTotal);
    }
}
