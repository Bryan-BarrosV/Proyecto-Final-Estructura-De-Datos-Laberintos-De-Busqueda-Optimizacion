package ec.ups.edu.est.controllers;

import ec.ups.edu.est.dao.AlgorithmResultDAO;
import ec.ups.edu.est.dao.impl.AlgorithmResultDAOFile;
import ec.ups.edu.est.models.AlgorithmResult;
import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;
import ec.ups.edu.est.solver.impl.*;
import ec.ups.edu.est.views.MazePanel;
import ec.ups.edu.est.views.ResultsDialog;

import javax.swing.*;
import java.time.LocalDate;

public class MazeController {

    private final MazePanel mazePanel;
    private final JFrame parent;
    private final AlgorithmResultDAO dao;

    public MazeController(MazePanel mazePanel, JFrame parent) {
        this.mazePanel = mazePanel;
        this.parent = parent;
        this.dao = new AlgorithmResultDAOFile("resultados.csv");
    }

    public void resolverLaberinto(String nombreAlgoritmo) {
        CellState[][] matriz = mazePanel.getMatrizEstados();
        Cell inicio = mazePanel.getInicio();
        Cell fin = mazePanel.getFin();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(parent, "Debes marcar un punto de inicio (🟢) y uno de fin (🔴).");
            return;
        }

        MazeSolver solver = switch (nombreAlgoritmo) {
            case "BFS" -> new MazeSolverBFS();
            case "DFS" -> new MazeSolverDFS();
            case "Recursivo" -> new MazeSolverRecursivo();
            case "Recursivo 4D" -> new MazeSolverRecursivoCompleto();
            case "Recursivo 4D + BT" -> new MazeSolverRecursivoCompletoBT();
            case "Backtracking " -> new Backtracking();
            default -> null;
        };

        if (solver == null) {
            JOptionPane.showMessageDialog(parent, "Algoritmo no válido.");
            return;
        }

        long t0 = System.currentTimeMillis();
        SolveResults resultado = solver.resolver(matriz, inicio, fin);
        long t1 = System.currentTimeMillis();
        long tiempoTotal = t1 - t0;

        mazePanel.pintarSolucion(resultado.getCamino());

        new ResultsDialog(parent, resultado.getPasos(), (int) tiempoTotal).setVisible(true);

        try {
            dao.guardar(new AlgorithmResult(nombreAlgoritmo, resultado.getPasos(), tiempoTotal, LocalDate.now().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
