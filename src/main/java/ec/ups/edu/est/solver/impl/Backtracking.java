package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;

import java.util.ArrayList;
import java.util.List;

public class Backtracking implements MazeSolver {

    private final int[][] DIRECCIONES = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private int[][] memo;
    private int filas, columnas;

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

        long finTiempo = System.currentTimeMillis(); // ⏱️ Tiempo final
        long tiempoTotal = finTiempo - inicioTiempo;

        return new SolveResults(camino, camino.size(), tiempoTotal);
    }

    private int dp(CellState[][] matriz, int i, int j, Cell fin) {
        if (i < 0 || j < 0 || i >= filas || j >= columnas || matriz[i][j] == CellState.WALL) {
            return Integer.MAX_VALUE;
        }

        if (i == fin.getFila() && j == fin.getColumna()) {
            return 0;
        }

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
