package ec.ups.edu.est;

import javax.swing.*;

import ec.ups.edu.est.controllers.MazeController;
import ec.ups.edu.est.views.MazeFrame;


public class MazeAPP {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MazeFrame vista = new MazeFrame();
            new MazeController(vista);
        });
    }
}

