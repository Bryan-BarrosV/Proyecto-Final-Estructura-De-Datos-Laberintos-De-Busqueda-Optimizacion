package ec.ups.edu.est.views;

import ec.ups.edu.est.controllers.MazeController;
import ec.ups.edu.est.models.AlgorithmResult;
import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
import ec.ups.edu.est.solver.MazeSolver;
import ec.ups.edu.est.solver.impl.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MazeFrame extends JFrame {

    private MazePanel mazePanel;
    private MazeController controlador;
    private JComboBox<String> comboAlgoritmos;
    private JButton btnResolver, btnPaso, btnLimpiar;
    private ResultadosGuardadosDialog dialogResultados;


    public MazeFrame() {
        setTitle("Laberinto  Interactivo");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(250, 248, 240));
        getContentPane().setBackground(new Color(250, 248, 240));
        setJMenuBar(crearMenu());

        dialogResultados = new ResultadosGuardadosDialog(this);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dialogResultados.guardarResultadosEnCSV("resultados.csv");
            }
        });


        pedirDimensiones();
        crearMazePanel();
        add(crearPanelLateral(), BorderLayout.WEST);
        add(crearPanelInferior(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem nuevoLaberintoItem = new JMenuItem("Agregar nuevo laberinto");
        nuevoLaberintoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filasStr = JOptionPane.showInputDialog(MazeFrame.this, "Ingrese número de filas:");
                String columnasStr = JOptionPane.showInputDialog(MazeFrame.this, "Ingrese número de columnas:");
                try {
                    int nuevasFilas = Integer.parseInt(filasStr);
                    int nuevasColumnas = Integer.parseInt(columnasStr);

                    getContentPane().removeAll();

                    filas = nuevasFilas;
                    columnas = nuevasColumnas;

                    crearMazePanel();
                    add(crearPanelLateral(), BorderLayout.WEST);
                    add(crearPanelInferior(), BorderLayout.SOUTH);

                    JScrollPane scroll = new JScrollPane(mazePanel);
                    scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    add(scroll, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MazeFrame.this, "Dimensiones inválidas.");
                }
            }

        });

        JMenuItem verResultadosItem = new JMenuItem("Ver tiempos de ejecución");
        verResultadosItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialogResultados.setVisible(true);
            }
        });


        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuArchivo.add(nuevoLaberintoItem);
        menuArchivo.add(verResultadosItem);
        menuArchivo.addSeparator();
        menuArchivo.add(salirItem);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem autoresItem = new JMenuItem("Acerca de los autores");
        autoresItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mensaje = """
                Proyecto: Laberinto 
                Autores:
                - Valeria Borja - DianitaB
                - Keyra Carvajal - KeyraCarvajajl
                - Bryan Barros - Bryan-BarrosV
                - Erika Collaguazo - Erika-colla

                ¡Gracias por utilizar nuestra aplicación!
                """;
                JOptionPane.showMessageDialog(MazeFrame.this, mensaje, "Información del Proyecto", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuAyuda.add(autoresItem);
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        return menuBar;
    }




    private int filas, columnas;

    private void pedirDimensiones() {
        filas = Integer.parseInt(JOptionPane.showInputDialog("Filas del laberinto:"));
        columnas = Integer.parseInt(JOptionPane.showInputDialog("Columnas del laberinto:"));
    }

    private void crearMazePanel() {
        mazePanel = new MazePanel(filas, columnas);
        controlador = new MazeController(mazePanel, this);
        JScrollPane scroll = new JScrollPane(mazePanel);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel crearPanelLateral() {
        JPanel lateral = new JPanel();
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setPreferredSize(new Dimension(180, 0));

        lateral.setBackground(new Color(240, 235, 220));
        lateral.setBackground(new Color(240, 235, 220));
        lateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel lbl = new JLabel("");
        lbl.setFont(new Font("Verdana", Font.BOLD, 16));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnInicio = crearBotonPlano("Set Start", new Color(34, 139, 34));
        JButton btnFin = crearBotonPlano("Set End", new Color(178, 34, 34));
        JButton btnMuro = crearBotonPlano("Toggle Wall", Color.DARK_GRAY);

        btnInicio.addActionListener(e -> mazePanel.setModo("START"));
        btnFin.addActionListener(e -> mazePanel.setModo("END"));
        btnMuro.addActionListener(e -> mazePanel.setModo("WALL"));

        lateral.add(lbl);
        lateral.add(Box.createVerticalStrut(20));
        lateral.add(btnInicio);
        lateral.add(Box.createVerticalStrut(10));
        lateral.add(btnFin);
        lateral.add(Box.createVerticalStrut(10));
        lateral.add(btnMuro);

        return lateral;
    }

    private JPanel crearPanelInferior() {
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        inferior.setBackground(new Color(235, 232, 222));

        comboAlgoritmos = new JComboBox<>(new String[]{
                "Recursivo", "BFS", "DFS", "Recursivo 4D", "Recursivo 4D + BT", "Backtracking"
        });
        comboAlgoritmos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComboBox<String> combo = new JComboBox<>(new String[]{"Recursivo", "Recursivo Completo", "Recursivo Completo BT", "BFS", "DFS","Backtracking"});
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JButton btnResolver = crearBotonPlano("Resolver", new Color(60, 179, 113));
        JButton btnPaso = crearBotonPlano("Paso a paso", new Color(255, 165, 0));
        JButton btnLimpiar = crearBotonPlano("Limpiar", new Color(105, 105, 105));

        btnResolver = crearBotonPlano("Resolver", new Color(60, 179, 113));
        btnPaso = crearBotonPlano("Paso a paso", new Color(255, 165, 0));
        btnLimpiar = crearBotonPlano("Limpiar", new Color(105, 105, 105));

        btnResolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String algoritmo = (String) comboAlgoritmos.getSelectedItem();
                CellState[][] matriz = mazePanel.getMatrizEstados();
                Cell inicio = mazePanel.getInicio();
                Cell fin = mazePanel.getFin();

                if (inicio == null || fin == null) {
                    JOptionPane.showMessageDialog(MazeFrame.this, "Debes marcar un punto de inicio (🟢) y uno de fin (🔴).");
                    return;
                }

                MazeSolver solver = switch (algoritmo) {
                    case "BFS" -> new MazeSolverBFS();
                    case "DFS" -> new MazeSolverDFS();
                    case "Recursivo" -> new MazeSolverRecursivo();
                    case "Recursivo 4D" -> new MazeSolverRecursivoCompleto();
                    case "Recursivo 4D + BT" -> new MazeSolverRecursivoCompletoBT();
                    default -> null;
                };

                if (solver == null) {
                    JOptionPane.showMessageDialog(MazeFrame.this, "Algoritmo no válido.");
                    return;
                }

                SolveResults res = solver.resolver(matriz, inicio, fin);
                mazePanel.pintarSolucion(res.getCamino());

                String fecha = java.time.LocalDateTime.now().toString();
                AlgorithmResult r = new AlgorithmResult(algoritmo, res.getCamino().size(), res.getTiempoEjecucion(), fecha);
                dialogResultados.agregarResultado(r);
            }
        });


        btnLimpiar.addActionListener(e -> mazePanel.limpiar());

        btnPaso.addActionListener(e -> {
            String algoritmo = (String) comboAlgoritmos.getSelectedItem();
            CellState[][] matriz = mazePanel.getMatrizEstados();
            Cell inicio = mazePanel.getInicio();
            Cell fin = mazePanel.getFin();

            if (inicio == null || fin == null) {
                JOptionPane.showMessageDialog(this, "Debes marcar un punto de inicio (🟢) y uno de fin (🔴).");
                return;
            }

            MazeSolver solver = switch (algoritmo) {
                case "BFS" -> new MazeSolverBFS();
                case "DFS" -> new MazeSolverDFS();
                case "Recursivo" -> new MazeSolverRecursivo();
                case "Recursivo 4D" -> new MazeSolverRecursivoCompleto();
                case "Recursivo 4D + BT" -> new MazeSolverRecursivoCompletoBT();
                default -> null;
            };

            if (solver == null) {
                JOptionPane.showMessageDialog(this, "Algoritmo no válido.");
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
        });

        inferior.add(new JLabel("Algoritmo:"));
        inferior.add(comboAlgoritmos);
        inferior.add(btnResolver);
        inferior.add(btnPaso);
        inferior.add(btnLimpiar);

        return inferior;
    }

    private JButton crearBotonPlano(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setBackground(fondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setEnabled(true);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MazeFrame::new);
    }
}
