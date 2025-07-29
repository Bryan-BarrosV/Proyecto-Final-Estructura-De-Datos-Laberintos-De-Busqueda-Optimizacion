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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.time.LocalDate;

/**
 * Controlador principal de la aplicación de laberintos. Se encarga de manejar
 * los eventos desde la interfaz gráfica, resolver el laberinto con el algoritmo seleccionado
 * y gestionar la visualización de resultados y pasos.
 */
public class MazeController implements ActionListener {

    private final MazeFrame frame;
    private final MazePanel mazePanel;
    private final AlgorithmResultDAO dao;
    private final ResultadosGuardadosDialog dialogResultados;
    private List<Cell> caminoPasoAPaso;
    private int pasoActual = 0;

    /**
     * Constructor que inicializa el controlador con la vista principal.
     *
     * @param frame la ventana principal del laberinto
     */
    public MazeController(MazeFrame frame) {
        this.frame = frame;
        this.mazePanel = frame.getMazePanel();
        this.dao = new AlgorithmResultDAOFile("resultados.csv");
        this.dialogResultados = new ResultadosGuardadosDialog(frame);
        frame.registrarEventos(this);
    }

    /**
     * Maneja los eventos de la interfaz gráfica.
     *
     * @param e el evento generado por un componente
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == frame.getBtnResolver()) {
            resolverLaberinto();
        } else if (src == frame.getBtnPaso()) {
            resolverPasoAPaso();
        } else if (src == frame.getBtnLimpiar()) {
            mazePanel.limpiar();
            caminoPasoAPaso = null;
            pasoActual = 0;
        } else if (src == frame.getBtnInicio()) {
            mazePanel.setModo("START");
        } else if (src == frame.getBtnFin()) {
            mazePanel.setModo("END");
        } else if (src == frame.getBtnMuro()) {
            mazePanel.setModo("WALL");
        } else if (src == frame.getMenuNuevo()) {
            frame.recrearMaze();
            caminoPasoAPaso = null;
            pasoActual = 0;
        } else if (src == frame.getMenuResultados()) {
            dialogResultados.setVisible(true);
        } else if (src == frame.getMenuSalir()) {
            System.exit(0);
        }else if (src == frame.getMenuAutores()) {
                ec.ups.edu.est.views.AutoresDialog.mostrar(frame);
            }
    }

    /**
     * Resuelve el laberinto utilizando el algoritmo seleccionado y muestra el recorrido y la solución
     * con animación en la interfaz gráfica.
     */
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

        long t0 = System.nanoTime();
        SolveResults resultado = solver.resolver(matriz, inicio, fin);
        long t1 = System.nanoTime();
        long tiempoTotal = t1 - t0;

        List<Cell> recorrido = resultado.getRecorrido();
        List<Cell> camino = resultado.getCamino();

        final int[] indexRecorrido = {0};
        Timer timerRecorrido = new Timer(100, null);
        timerRecorrido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexRecorrido[0] < recorrido.size()) {
                    Cell celda = recorrido.get(indexRecorrido[0]);
                    int i = celda.getFila();
                    int j = celda.getColumna();
                    Color actual = mazePanel.getCeldaColor(i, j);
                    if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                        mazePanel.setCeldaColor(i, j, Color.LIGHT_GRAY);
                    }
                    indexRecorrido[0]++;
                } else {
                    ((Timer) e.getSource()).stop();

                    final int[] indexCamino = {0};
                    Timer timerCamino = new Timer(100, null);
                    timerCamino.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e2) {
                            if (indexCamino[0] < camino.size()) {
                                Cell celda = camino.get(indexCamino[0]);
                                int i = celda.getFila();
                                int j = celda.getColumna();
                                Color actual = mazePanel.getCeldaColor(i, j);
                                if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                                    mazePanel.setCeldaColor(i, j, Color.BLUE);
                                }
                                indexCamino[0]++;
                            } else {
                                ((Timer) e2.getSource()).stop();

                                JOptionPane.showMessageDialog(frame,
                                        "Algoritmo completado en " + tiempoTotal + " ns\n🔢 Pasos: " + resultado.getPasos(),
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
                        }
                    });
                    timerCamino.start();
                }
            }
        });
        timerRecorrido.start();
    }

    /**
     * Resuelve el laberinto paso a paso mostrando cada celda del camino final con color gris.
     */
    private void resolverPasoAPaso() {
        String algoritmo = (String) frame.getComboAlgoritmos().getSelectedItem();
        CellState[][] matriz = mazePanel.getMatrizEstados();
        Cell inicio = mazePanel.getInicio();
        Cell fin = mazePanel.getFin();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(frame, "Debes marcar un punto de inicio (🟢) y uno de fin (🔴).");
            return;
        }

        if (caminoPasoAPaso == null || pasoActual == 0) {
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
            caminoPasoAPaso = resultado.getCamino();
            pasoActual = 0;
        }

        if (pasoActual < caminoPasoAPaso.size()) {
            Cell celda = caminoPasoAPaso.get(pasoActual);
            int i = celda.getFila();
            int j = celda.getColumna();
            Color actual = mazePanel.getCeldaColor(i, j);
            if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                mazePanel.setCeldaColor(i, j, Color.GRAY);
            }
            pasoActual++;
        } else {
            JOptionPane.showMessageDialog(frame, "Camino completado paso a paso.");
            caminoPasoAPaso = null;
            pasoActual = 0;
        }
    }

    /**
     * Método auxiliar para pintar una lista de celdas de forma animada.
     *
     * @param lista         lista de celdas a pintar
     * @param color         color a aplicar
     * @param cuandoTermine acción a ejecutar cuando finalice la animación
     */
    private void pintarAnimado(List<Cell> lista, Color color, Runnable cuandoTermine) {
        final int[] index = {0};
        Timer timer = new Timer(100, null);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index[0] < lista.size()) {
                    Cell celda = lista.get(index[0]);
                    int i = celda.getFila();
                    int j = celda.getColumna();
                    Color actual = mazePanel.getCeldaColor(i, j);
                    if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                        mazePanel.setCeldaColor(i, j, color);
                    }
                    index[0]++;
                } else {
                    timer.stop();
                    cuandoTermine.run();
                }
            }
        });

        timer.start();
    }
}
