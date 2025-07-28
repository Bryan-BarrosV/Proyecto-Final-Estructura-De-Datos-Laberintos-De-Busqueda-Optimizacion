package ec.ups.edu.est;

import javax.swing.*;

import ec.ups.edu.est.views.MazeFrame;

public class MazeAPP {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(MazeFrame::new);
    }
}
