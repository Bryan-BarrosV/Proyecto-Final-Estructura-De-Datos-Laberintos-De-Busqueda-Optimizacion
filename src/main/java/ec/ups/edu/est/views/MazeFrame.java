package ec.ups.edu.est.views;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa la ventana principal de la aplicación del laberinto.
 * Contiene la interfaz gráfica para configurar, resolver y limpiar el laberinto
 */
public class MazeFrame extends JFrame {
    // Panel principal donde se dibuja el laberinto
    private MazePanel mazePanel;
    // Controles de interacción.
    private JComboBox<String> comboAlgoritmos;
    private JButton btnResolver, btnPaso, btnLimpiar;
    private JButton btnInicio, btnFin, btnMuro;

    // Ítems del menú
    private JMenuItem menuNuevo, menuResultados, menuSalir, menuAutores;


    /**
     * Constructor que configura la ventana prinicipal del laberinto.
     * Establece el diseño, colores, menús y componentes gráficos.
     */
    public MazeFrame() {
        setTitle("Laberinto Interactivo");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(250, 248, 240));
        setJMenuBar(crearMenu());

        pedirDimensiones();
        crearMazePanel();
        add(crearPanelLateral(), BorderLayout.WEST);
        add(crearPanelInferior(), BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Crea y configura la barra de menús superior
     * @return JMenuBar con opciones de Archivo y Ayuda.
     */
    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        menuNuevo = new JMenuItem("Agregar nuevo laberinto");
        menuResultados = new JMenuItem("Ver tiempos de ejecución");
        menuSalir = new JMenuItem("Salir");
        menuArchivo.add(menuNuevo);
        menuArchivo.add(menuResultados);
        menuArchivo.addSeparator();
        menuArchivo.add(menuSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        menuAutores = new JMenuItem("Acerca de");
        menuAyuda.add(menuAutores);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        return menuBar;
    }

    private int filas, columnas;

    /**
     * Solicita al usuario el número de filas y columnas del laberinto al iniciar la aplicación.
     */
    private void pedirDimensiones() {
        filas = Integer.parseInt(JOptionPane.showInputDialog("Filas del laberinto:"));
        columnas = Integer.parseInt(JOptionPane.showInputDialog("Columnas del laberinto:"));
    }

    /**
     * Permite refedinir las dimensiones del laberinto y reconstruye toda la interfaz gráfica.
     */
    public void recrearMaze() {
        String filasStr = JOptionPane.showInputDialog(this, "Ingrese número de filas:");
        String columnasStr = JOptionPane.showInputDialog(this, "Ingrese número de columnas:");
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
            registrarEventos(new ec.ups.edu.est.controllers.MazeController(this));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Dimensiones inválidas.");
        }
    }

    /**
     * Crea y agrega el panel del laberinto.
     */
    private void crearMazePanel() {
        mazePanel = new MazePanel(filas, columnas);
        JScrollPane scroll = new JScrollPane(mazePanel);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Crea el panel lateral con botones para seleccionar el punto de inicio, fin y muros.
     * @return JPanel con los botones correspondientes
     */
    private JPanel crearPanelLateral() {
        JPanel lateral = new JPanel();
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setPreferredSize(new Dimension(180, 0));
        lateral.setBackground(new Color(240, 235, 220));
        lateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        btnInicio = crearBotonPlano("Set Start", new Color(34, 139, 34));
        btnFin = crearBotonPlano("Set End", new Color(178, 34, 34));
        btnMuro = crearBotonPlano("Toggle Wall", Color.DARK_GRAY);

        lateral.add(Box.createVerticalStrut(20));
        lateral.add(btnInicio);
        lateral.add(Box.createVerticalStrut(10));
        lateral.add(btnFin);
        lateral.add(Box.createVerticalStrut(10));
        lateral.add(btnMuro);

        return lateral;
    }

    /**
     * Crea el panel inferior con la selección de algoritmo y botones de control.
     * @return JPanel con el comboBox y botones.
     */
    private JPanel crearPanelInferior() {
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        inferior.setBackground(new Color(235, 232, 222));

        comboAlgoritmos = new JComboBox<>(new String[]{
                "Recursivo","Recursivo Completo", "Recursivo Completo BT","BFS", "DFS", "Backtracking"
        });
        comboAlgoritmos.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnResolver = crearBotonPlano("Resolver", new Color(60, 179, 113));
        btnPaso = crearBotonPlano("Paso a paso", new Color(255, 165, 0));
        btnLimpiar = crearBotonPlano("Limpiar", new Color(105, 105, 105));

        inferior.add(new JLabel("Algoritmo:"));
        inferior.add(comboAlgoritmos);
        inferior.add(btnResolver);
        inferior.add(btnPaso);
        inferior.add(btnLimpiar);

        return inferior;
    }

    /**
     * Método auxiliar para crear botones personalizados sin bordes y con fondo de color.
     * @param texto Texto del botón.
     * @param fondo Color de fondo del botón.
     * @return JButton personalizado.
     */
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

    /**
     * Registra todos los eventos para los botones y elementos del menú.
     * @param controlador Clase que implementa ActionListener.
     */
    public void registrarEventos(java.awt.event.ActionListener controlador) {
        btnResolver.addActionListener(controlador);
        btnPaso.addActionListener(controlador);
        btnLimpiar.addActionListener(controlador);
        btnInicio.addActionListener(controlador);
        btnFin.addActionListener(controlador);
        btnMuro.addActionListener(controlador);
        menuNuevo.addActionListener(controlador);
        menuResultados.addActionListener(controlador);
        menuSalir.addActionListener(controlador);
        menuAutores.addActionListener(controlador);
    }

    // Métodos getter para los distintos componentes
    public MazePanel getMazePanel() {
        return mazePanel;
    }

    public JComboBox<String> getComboAlgoritmos() {
        return comboAlgoritmos;
    }

    public JButton getBtnResolver() {
        return btnResolver;
    }

    public JButton getBtnPaso() {
        return btnPaso;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnInicio() {
        return btnInicio;
    }

    public JButton getBtnFin() {
        return btnFin;
    }

    public JButton getBtnMuro() {
        return btnMuro;
    }

    public JMenuItem getMenuNuevo() {
        return menuNuevo;
    }

    public JMenuItem getMenuResultados() {
        return menuResultados;
    }

    public JMenuItem getMenuSalir() {
        return menuSalir;
    }

    public JMenuItem getMenuAutores() {
        return menuAutores;
    }
}