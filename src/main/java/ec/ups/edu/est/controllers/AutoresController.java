package ec.ups.edu.est.controllers;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.*;

/**
 * Controlador para manejar los clics en los botones de los autores
 * y abrir los respectivos perfiles de GitHub.
 */
public class AutoresController implements ActionListener {

    private final JFrame parent;

    public AutoresController(JFrame parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            switch (comando) {
                case "DianitaB" -> abrirLink("https://github.com/DianitaB");
                case "KeyraCarvajajl" -> abrirLink("https://github.com/KeyraCarvajajl");
                case "Bryan-BarrosV" -> abrirLink("https://github.com/Bryan-BarrosV");
                case "Erika-colla" -> abrirLink("https://github.com/Erika-colla");
                default -> JOptionPane.showMessageDialog(parent, "Autor no reconocido.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "No se pudo abrir el navegador.");
        }
    }

    private void abrirLink(String url) throws Exception {
        Desktop.getDesktop().browse(new URI(url));
    }
}

