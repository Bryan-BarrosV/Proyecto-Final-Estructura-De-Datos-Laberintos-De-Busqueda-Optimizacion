package ec.ups.edu.est.views;

import ec.ups.edu.est.controllers.AutoresController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana personalizada para mostrar los autores y sus perfiles de GitHub.
 */
public class AutoresDialog extends JDialog {

    public AutoresDialog(JFrame parent) {
        super(parent, "Autores del Proyecto: ", true);
        setSize(320, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        AutoresController controlador = new AutoresController(parent);

        JTextArea areaIntro = new JTextArea();
        areaIntro.setEditable(false);
        areaIntro.setFont(new Font("Segoe UI", Font.BOLD, 20));
        areaIntro.setText("""
                Proyecto: Laberinto Interactivo
                Autores: 
                """);

        areaIntro.setBackground(getBackground());

        JPanel panelAutores = new JPanel(new GridLayout(4, 2, 10, 10));
        panelAutores.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelAutores.add(new JLabel("• Valeria Borja"));
        panelAutores.add(crearBoton("DianitaB", controlador));

        panelAutores.add(new JLabel("• Keyra Carvajal"));
        panelAutores.add(crearBoton("KeyraCarvajajl", controlador));

        panelAutores.add(new JLabel("• Bryan Barros"));
        panelAutores.add(crearBoton("Bryan-BarrosV", controlador));

        panelAutores.add(new JLabel("• Erika Collaguazo"));
        panelAutores.add(crearBoton("Erika-colla", controlador));

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());

        JPanel panelSur = new JPanel();
        panelSur.add(cerrar);

        add(areaIntro, BorderLayout.NORTH);
        add(panelAutores, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setForeground(Color.BLUE);
        boton.setActionCommand(texto);
        boton.addActionListener(listener);
        return boton;
    }

    public static void mostrar(JFrame parent) {
        new AutoresDialog(parent).setVisible(true);
    }
}
