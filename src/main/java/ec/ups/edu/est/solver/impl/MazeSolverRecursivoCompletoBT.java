package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del algoritmo recursivo completo con backtracking.
 * Esta versión explora todas las rutas posibles desde el inicio hasta el fin
 * y selecciona la ruta más corta entre todas las soluciones válidas.
 */
public class MazeSolverRecursivoCompletoBT implements MazeSolver {

    private int filas;
    private int columnas;
    private boolean[][] visitado;
    private List<Cell> mejorRuta;

    /**
     * Ejecuta el algoritmo recursivo con backtracking para encontrar el camino óptimo
     * entre la celda de inicio y la de fin dentro del laberinto.
     *
     * @param laberinto matriz de estados del laberinto
     * @param inicio    celda de inicio
     * @param fin       celda de destino
     * @return objeto {@link SolveResults} con el camino más corto, número de pasos y tiempo
     */
    @Override
    public SolveResults resolver(CellState[][] laberinto, Cell inicio, Cell fin) {
        long inicioTiempo = System.currentTimeMillis();

        filas = laberinto.length;
        columnas = laberinto[0].length;
        visitado = new boolean[filas][columnas];
        mejorRuta = new ArrayList<>();

        List<Cell> rutaActual = new ArrayList<>();
        buscar(laberinto, inicio.getFila(), inicio.getColumna(), fin, rutaActual);

        long finTiempo = System.currentTimeMillis();
        long tiempoTotal = finTiempo - inicioTiempo;

        return new SolveResults(mejorRuta, mejorRuta.size(), tiempoTotal);
    }

    /**
     * Método recursivo que explora todas las rutas posibles. Si se encuentra una ruta más corta,
     * se almacena como la mejor solución hasta el momento. Aplica retroceso para continuar explorando.
     *
     * @param laberinto  matriz de estados del laberinto
     * @param i          fila actual
     * @param j          columna actual
     * @param fin        celda de destino
     * @param rutaActual ruta parcial construida hasta este punto
     */
    private void buscar(CellState[][] laberinto, int i, int j, Cell fin, List<Cell> rutaActual) {
        if (i < 0 || i >= filas || j < 0 || j >= columnas) return;
        if (visitado[i][j] || laberinto[i][j] == CellState.WALL) return;

        visitado[i][j] = true;
        rutaActual.add(new Cell(i, j, laberinto[i][j]));

        if (i == fin.getFila() && j == fin.getColumna()) {
            if (mejorRuta.isEmpty() || rutaActual.size() < mejorRuta.size()) {
                mejorRuta = new ArrayList<>(rutaActual);
            }
        } else {
            buscar(laberinto, i - 1, j, fin, rutaActual);
            buscar(laberinto, i + 1, j, fin, rutaActual);
            buscar(laberinto, i, j - 1, fin, rutaActual);
            buscar(laberinto, i, j + 1, fin, rutaActual);
        }

        rutaActual.remove(rutaActual.size() - 1);
        visitado[i][j] = false;
    }
}
