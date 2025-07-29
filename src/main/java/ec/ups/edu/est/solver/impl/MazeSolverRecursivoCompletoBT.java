package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.ArrayList;
import java.util.List;

public class MazeSolverRecursivoCompletoBT implements MazeSolver {

    private int filas;
    private int columnas;
    private boolean[][] visitado;
    private List<Cell> mejorRuta;

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
