package ec.ups.edu.est;

import javax.swing.*;

import ec.ups.edu.est.controllers.MazeController;
import ec.ups.edu.est.views.MazeFrame;

/**
 * Clase principal de la aplicación del laberinto.
 *
 * <p>
 * Esta clase se encarga de iniciar la interfaz gráfica del sistema
 * utilizando Swing. Instancia la vista principal {@link MazeFrame}
 * y la conecta con su correspondiente controlador {@link MazeController}.
 * </p>
 *
 * <p>
 * Se utiliza {@code SwingUtilities.invokeLater} para asegurar que
 * la GUI se cree y maneje correctamente en el hilo de eventos de Swing.
 * </p>
 *
 * @author TuNombre
 * @version 1.0
 */
public class MazeAPP {
    /**
     * Método principal que lanza la aplicación.
     *
     * @param args los argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MazeFrame vista = new MazeFrame();
            new MazeController(vista);
        });
    }
}

