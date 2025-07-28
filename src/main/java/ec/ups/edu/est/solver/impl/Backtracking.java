package ec.ups.edu.est.solver.impl;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;
import ec.ups.edu.est.views.MazePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Backtracking implements MazeSolver {

    private final int[][] DIRECCIONES = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private boolean[][]  visitado;
    private List<Cell> mejorCamino;
    private List<Cell> caminoActual;
    private MazePanel mazePanel;
    private boolean animacion;

    public Backtracking() {
        this.animacion = false;
    }

    public Backtracking(MazePanel panel) {
        this.mazePanel = panel;
        this.animacion = true;
    }

    @Override
    public SolveResults resolver(CellState[][] matriz, Cell inicio, Cell fin) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        visitado = new boolean[filas][columnas];
        mejorCamino = new ArrayList<>();
        caminoActual = new ArrayList<>();

        backtrack(matriz, inicio.getFila(), inicio.getColumna(), fin);

        return new SolveResults(mejorCamino.size(), mejorCamino);
    }

    private void backtrack(CellState[][] matriz, int i, int j, Cell fin) {
        if (i < 0 || j < 0 || i >= matriz.length || j >= matriz[0].length) return;
        if (matriz[i][j] == CellState.WALL || visitado[i][j]) return;

        visitado[i][j] = true;
        Cell actual = new Cell(i, j, matriz[i][j]);
        caminoActual.add(actual);

        if (animacion) pintarCelda(i, j, Color.CYAN);

        if (i == fin.getFila() && j == fin.getColumna()) {
            if (mejorCamino.isEmpty() || caminoActual.size() < mejorCamino.size()) {
                mejorCamino = new ArrayList<>(caminoActual);
            }
        } else {
            for (int[] d : DIRECCIONES) {
                backtrack(matriz, i + d[0], j + d[1], fin);
            }
        }

        caminoActual.remove(caminoActual.size() - 1);
        visitado[i][j] = false;

        if (animacion) pintarCelda(i, j, Color.LIGHT_GRAY);
    }

    private void pintarCelda(int i, int j, Color color) {
        if (mazePanel == null) return;

        SwingUtilities.invokeLater(() -> {
            Color actual = mazePanel.getCeldaColor(i, j);
            if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                mazePanel.setCeldaColor(i, j, color);
            }
        });

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}