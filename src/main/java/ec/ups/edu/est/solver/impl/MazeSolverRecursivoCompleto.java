package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del algoritmo de resolución de laberintos utilizando
 * búsqueda recursiva completa. Explora todas las direcciones posibles
 * y utiliza retroceso para encontrar el camino correcto.
 */
public class MazeSolverRecursivoCompleto implements MazeSolver {

    private int filas;
    private int columnas;
    private boolean[][] visitado;
    private List<Cell> camino;
    private boolean encontrado;

    /**
     * Ejecuta el algoritmo de búsqueda recursiva para encontrar el camino
     * entre el punto de inicio y el punto final en el laberinto.
     *
     * @param laberinto matriz de estados del laberinto
     * @param inicio    celda de inicio
     * @param fin       celda de destino
     * @return objeto {@link SolveResults} con el camino, pasos y tiempo de ejecución
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
     * Método recursivo que explora el laberinto en profundidad. Si encuentra
     * el destino, marca el camino correcto. Si no, retrocede para seguir buscando.
     *
     * @param laberinto matriz de estados del laberinto
     * @param i         fila actual
     * @param j         columna actual
     * @param fin       celda objetivo
     */
    private void buscar(CellState[][] laberinto, int i, int j, Cell fin) {
        if (i < 0 || i >= filas || j < 0 || j >= columnas) return;
        if (visitado[i][j] || laberinto[i][j] == CellState.WALL || encontrado) return;

        visitado[i][j] = true;
        camino.add(new Cell(i, j, laberinto[i][j]));

        if (i == fin.getFila() && j == fin.getColumna()) {
            encontrado = true;
            return;
        }

        buscar(laberinto, i - 1, j, fin);
        buscar(laberinto, i + 1, j, fin);
        buscar(laberinto, i, j - 1, fin);
        buscar(laberinto, i, j + 1, fin);

        if (!encontrado) {
            camino.remove(camino.size() - 1);
        }
    }
}
