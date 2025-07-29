package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación recursiva de resolución de laberintos.
 * Realiza una búsqueda profunda desde el punto de inicio hasta el punto de fin,
 * utilizando retroceso (backtracking) cuando encuentra callejones sin salida.
 */
public class MazeSolverRecursivo implements MazeSolver {

    private int filas;
    private int columnas;
    private boolean[][] visitado;
    private List<Cell> camino;
    private boolean encontrado;

    /**
     * Resuelve el laberinto desde una celda de inicio hasta una celda de fin
     * utilizando una búsqueda recursiva simple.
     *
     * @param laberinto matriz que representa los estados de cada celda
     * @param inicio    celda de inicio
     * @param fin       celda de destino
     * @return objeto {@link SolveResults} que contiene el camino, número de pasos y tiempo de ejecución
     */
    @Override
    public SolveResults resolver(CellState[][] laberinto, Cell inicio, Cell fin) {
        long inicioTiempo = System.currentTimeMillis();

        filas = laberinto.length;
        columnas = laberinto[0].length;
        visitado = new boolean[filas][columnas];
        camino = new ArrayList<>();
        encontrado = false;

        buscar(laberinto, inicio.getFila(), inicio.getColumna(), fin);

        long finTiempo = System.currentTimeMillis();
        long tiempoTotal = finTiempo - inicioTiempo;

        return new SolveResults(camino, camino.size(), tiempoTotal);
    }

    /**
     * Método recursivo para explorar el laberinto desde una posición específica.
     * Se detiene al encontrar la celda destino y retrocede si no hay camino válido.
     *
     * @param laberinto matriz del laberinto
     * @param i         fila actual
     * @param j         columna actual
     * @param fin       celda destino
     */
    private void buscar(CellState[][] laberinto, int i, int j, Cell fin) {
        if (i < 0 || i >= filas || j < 0 || j >= columnas ||
                visitado[i][j] || laberinto[i][j] == CellState.WALL || encontrado) {
            return;
        }

        visitado[i][j] = true;
        camino.add(new Cell(i, j, laberinto[i][j]));

        if (i == fin.getFila() && j == fin.getColumna()) {
            encontrado = true;
            return;
        }

        buscar(laberinto, i, j + 1, fin);
        buscar(laberinto, i + 1, j, fin);
        buscar(laberinto, i, j - 1, fin);
        buscar(laberinto, i - 1, j, fin);

        if (!encontrado) {
            camino.remove(camino.size() - 1);
        }
    }
}
