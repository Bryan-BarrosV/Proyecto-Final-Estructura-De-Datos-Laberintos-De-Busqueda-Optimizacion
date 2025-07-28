package ec.ups.edu.est.controllers;

import ec.ups.edu.est.dao.AlgorithmResultDAO;
import ec.ups.edu.est.dao.impl.AlgorithmResultDAOFile;
import ec.ups.edu.est.models.AlgorithmResult;
import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;
import ec.ups.edu.est.solver.impl.*;
import ec.ups.edu.est.views.MazeFrame;
import ec.ups.edu.est.views.MazePanel;
import ec.ups.edu.est.views.ResultadosGuardadosDialog;

import javax.swing.*;
import java.time.LocalDate;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeController implements ActionListener {

    private final MazeFrame frame;
    private final MazePanel mazePanel;
    private final AlgorithmResultDAO dao;
    private final ResultadosGuardadosDialog dialogResultados;

    public MazeController(MazeFrame frame) {
        this.frame = frame;
        this.mazePanel = frame.getMazePanel();
        this.dao = new AlgorithmResultDAOFile("resultados.csv");
        this.dialogResultados = new ResultadosGuardadosDialog(frame);
        frame.registrarEventos(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == frame.getBtnResolver()) {
            resolverLaberinto();
        } else if (src == frame.getBtnPaso()) {
            resolverPasoAPaso();
        } else if (src == frame.getBtnLimpiar()) {
            mazePanel.limpiar();
        } else if (src == frame.getBtnInicio()) {
            mazePanel.setModo("START");
        } else if (src == frame.getBtnFin()) {
            mazePanel.setModo("END");
        } else if (src == frame.getBtnMuro()) {
            mazePanel.setModo("WALL");
        } else if (src == frame.getMenuNuevo()) {
            frame.recrearMaze();
        } else if (src == frame.getMenuResultados()) {
            dialogResultados.setVisible(true);
        } else if (src == frame.getMenuSalir()) {
            System.exit(0);
        } else if (src == frame.getMenuAutores()) {
            String mensaje = """
                Proyecto: Laberinto 
                Autores:
                - Valeria Borja - DianitaB
                - Keyra Carvajal - KeyraCarvajajl
                - Bryan Barros - Bryan-BarrosV
                - Erika Collaguazo - Erika-colla

                ¡Gracias por utilizar nuestra aplicación!
                """;
            JOptionPane.showMessageDialog(frame, mensaje, "Información del Proyecto", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resolverLaberinto() {
        String algoritmo = (String) frame.getComboAlgoritmos().getSelectedItem();
        CellState[][] matriz = mazePanel.getMatrizEstados();
        Cell inicio = mazePanel.getInicio();
        Cell fin = mazePanel.getFin();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(frame, "Debes marcar un punto de inicio (🟢) y uno de fin (🔴).");
            return;
        }

        MazeSolver solver = switch (algoritmo) {
            case "BFS" -> new MazeSolverBFS();
            case "DFS" -> new MazeSolverDFS();
            case "Recursivo" -> new MazeSolverRecursivo();
            case "Recursivo 4D" -> new MazeSolverRecursivoCompleto();
            case "Recursivo 4D + BT" -> new MazeSolverRecursivoCompletoBT();
            case "Backtracking" -> new Backtracking();
            default -> null;
        };

        if (solver == null) {
            JOptionPane.showMessageDialog(frame, "Algoritmo no válido.");
            return;
        }

        long t0 = System.currentTimeMillis();
        SolveResults resultado = solver.resolver(matriz, inicio, fin);
        long t1 = System.currentTimeMillis();
        long tiempoTotal = t1 - t0;

        mazePanel.pintarSolucion(resultado.getCamino());
        JOptionPane.showMessageDialog(frame,
                "✅ Algoritmo completado en " + tiempoTotal + " ms\n🔢 Pasos: " + resultado.getPasos(),
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE);


        AlgorithmResult r = new AlgorithmResult(algoritmo, resultado.getPasos(), tiempoTotal, LocalDate.now().toString());
        dialogResultados.agregarResultado(r);

        try {
            dao.guardar(r);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void resolverPasoAPaso() {
        String algoritmo = (String) frame.getComboAlgoritmos().getSelectedItem();
        CellState[][] matriz = mazePanel.getMatrizEstados();
        Cell inicio = mazePanel.getInicio();
        Cell fin = mazePanel.getFin();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(frame, "Debes marcar un punto de inicio (🟢) y uno de fin (🔴).");
            return;
        }

        MazeSolver solver = switch (algoritmo) {
            case "BFS" -> new MazeSolverBFS();
            case "DFS" -> new MazeSolverDFS();
            case "Recursivo" -> new MazeSolverRecursivo();
            case "Recursivo 4D" -> new MazeSolverRecursivoCompleto();
            case "Recursivo 4D + BT" -> new MazeSolverRecursivoCompletoBT();
            case "Backtracking" -> new Backtracking();
            default -> null;
        };

        if (solver == null) {
            JOptionPane.showMessageDialog(frame, "Algoritmo no válido.");
            return;
        }

        SolveResults resultado = solver.resolver(matriz, inicio, fin);
        java.util.List<Cell> camino = resultado.getCamino();

        SwingWorker<Void, Cell> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (Cell c : camino) {
                    publish(c);
                    Thread.sleep(100);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Cell> chunks) {
                for (Cell celda : chunks) {
                    int i = celda.getFila();
                    int j = celda.getColumna();
                    Color actual = mazePanel.getCeldaColor(i, j);
                    if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                        mazePanel.setCeldaColor(i, j, Color.CYAN);
                    }
                }
            }
        };

        worker.execute();
    }
}
