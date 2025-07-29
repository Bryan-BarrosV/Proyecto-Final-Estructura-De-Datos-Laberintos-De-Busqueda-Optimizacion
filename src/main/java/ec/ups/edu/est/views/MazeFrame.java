package ec.ups.edu.est.views;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación del laberinto interactivo.
 * Esta clase contiene el panel del laberinto, los controles de selección de algoritmos
 * y los botones para ejecutar, limpiar y configurar el laberinto.
 */
public class MazeFrame extends JFrame {

    private MazePanel mazePanel;
    private JComboBox<String> comboAlgoritmos;
    private JButton btnResolver, btnPaso, btnLimpiar;
    private JButton btnInicio, btnFin, btnMuro;
    private JMenuItem menuNuevo, menuResultados, menuSalir, menuAutores;

    /**
     * Constructor principal que inicializa la ventana y sus componentes.
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
     * Crea la barra de menú con las opciones de archivo y ayuda.
     * @return JMenuBar con menús y elementos configurados.
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
        menuAutores = new JMenuItem("Acerca de los autores");
        menuAyuda.add(menuAutores);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        return menuBar;
    }

    private int filas, columnas;

    /**
     * Solicita al usuario las dimensiones del laberinto mediante diálogos.
     */
    private void pedirDimensiones() {
        filas = Integer.parseInt(JOptionPane.showInputDialog("Filas del laberinto:"));
        columnas = Integer.parseInt(JOptionPane.showInputDialog("Columnas del laberinto:"));
    }

    /**
     * Vuelve a crear el panel del laberinto con nuevas dimensiones proporcionadas por el usuario.
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
     * Crea el panel del laberinto basado en las dimensiones especificadas.
     */
    private void crearMazePanel() {
        mazePanel = new MazePanel(filas, columnas);
        JScrollPane scroll = new JScrollPane(mazePanel);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Crea el panel lateral con botones para definir el punto de inicio, fin y muros.
     * @return JPanel configurado con botones laterales.
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
     * Crea el panel inferior con los botones de acción y selección de algoritmo.
     * @return JPanel configurado con botones de acción.
     */
    private JPanel crearPanelInferior() {
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        inferior.setBackground(new Color(235, 232, 222));

        comboAlgoritmos = new JComboBox<>(new String[]{
                "Recursivo","Recursivo Completo","Recursivo Completo BT", "BFS", "DFS",   "Backtracking"
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
     * Crea un botón personalizado con estilo plano.
     * @param texto Texto del botón.
     * @param fondo Color de fondo del botón.
     * @return JButton estilizado.
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
     * Registra los eventos del controlador para todos los botones y menús.
     * @param controlador ActionListener que actuará como controlador de eventos.
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


    /**
     * Retorna el panel del laberinto que contiene las celdas.
     * @return El objeto MazePanel actual.
     */
    public MazePanel getMazePanel() {
        return mazePanel;
    }

    /**
     * Retorna el combo box con las opciones de algoritmos disponibles.
     * @return JComboBox de selección de algoritmo.
     */
    public JComboBox<String> getComboAlgoritmos() {
        return comboAlgoritmos;
    }

    /**
     * Retorna el botón para ejecutar la resolución del laberinto.
     * @return JButton para resolver el laberinto.
     */
    public JButton getBtnResolver() {
        return btnResolver;
    }

    /**
     * Retorna el botón para ejecutar el algoritmo paso a paso.
     * @return JButton para ejecución paso a paso.
     */
    public JButton getBtnPaso() {
        return btnPaso;
    }

    /**
     * Retorna el botón para limpiar el laberinto.
     * @return JButton para limpiar las celdas del laberinto.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Retorna el botón que permite seleccionar el punto de inicio.
     * @return JButton para establecer la celda de inicio.
     */
    public JButton getBtnInicio() {
        return btnInicio;
    }

    /**
     * Retorna el botón que permite seleccionar el punto de fin.
     * @return JButton para establecer la celda de fin.
     */
    public JButton getBtnFin() {
        return btnFin;
    }

    /**
     * Retorna el botón que permite activar/desactivar muros en el laberinto.
     * @return JButton para modificar celdas como muros.
     */
    public JButton getBtnMuro() {
        return btnMuro;
    }

    /**
     * Retorna el ítem de menú para agregar un nuevo laberinto.
     * @return JMenuItem correspondiente a "Agregar nuevo laberinto".
     */
    public JMenuItem getMenuNuevo() {
        return menuNuevo;
    }

    /**
     * Retorna el ítem de menú para ver los resultados guardados.
     * @return JMenuItem correspondiente a "Ver tiempos de ejecución".
     */
    public JMenuItem getMenuResultados() {
        return menuResultados;
    }

    /**
     * Retorna el ítem de menú para salir de la aplicación.
     * @return JMenuItem correspondiente a "Salir".
     */
    public JMenuItem getMenuSalir() {
        return menuSalir;
    }

    /**
     * Retorna el ítem de menú que muestra información sobre los autores.
     * @return JMenuItem correspondiente a "Acerca de los autores".
     */
    public JMenuItem getMenuAutores() {
        return menuAutores;
    }
}