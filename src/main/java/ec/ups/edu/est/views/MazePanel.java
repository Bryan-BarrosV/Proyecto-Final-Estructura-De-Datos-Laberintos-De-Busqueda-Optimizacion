package ec.ups.edu.est.views;

import javax.swing.*;

import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;

import java.awt.*;

/**
 * Clase que representa el panel donde se visualiza el laberinto completo.
 */
public class MazePanel extends JPanel {

    private final int filas;
    private final int columnas;
    private final JButton[][] celdas;
    // Modo establecido por defecto "WALL" (muro)
    private String modo = "WALL";

    /**
     * Constructor donde inicializo el panel y su matriz de botones.
     * @param filas número de filas del laberinto
     * @param columnas número de columnas del laberinto
     */
    public MazePanel(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        setLayout(new GridLayout(filas, columnas, 2, 2));
        setBackground(new Color(220, 220, 220));
        celdas = new JButton[filas][columnas];
        inicializar();
    }

    /**
     * Método que recorre cada posición de la matriz y crea un bóton.
     * Asigna colores iniciales y un listener que permite cambiar su estado.
     * dependiendo del modo actual (inicio, fin, muro).
     */
    private void inicializar() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton celda = new JButton();
                celda.setBackground(Color.WHITE);
                celda.setFocusPainted(false);
                celda.setOpaque(true);
                celda.setBorderPainted(false);
                celda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                int fi = i, fj = j;
                celda.addActionListener(e -> actualizarCelda(fi, fj));
                celdas[i][j] = celda;
                add(celda);
            }
        }
    }

    /**
     * Permite establecer el modo actual que se usará al hacer clic en una celda.
     * Puede ser START, END o WALL.
     * @param modo nuevo modo seleccionado por el usuario
     */
    public void setModo(String modo) {
        this.modo = modo;
    }

    /**
     * Este método se activa cuando el usuario hace clic en una celda.
     * Cambia el color y estado de la cela según el modo actual.
     */
    private void actualizarCelda(int i, int j) {
        JButton celda = celdas[i][j];
        switch (modo) {
            case "START":
                limpiarColor(CellState.START);
                celda.setBackground(Color.GREEN);
                break;
            case "END":
                limpiarColor(CellState.END);
                celda.setBackground(Color.RED);
                break;
            case "WALL":
                if (celda.getBackground().equals(Color.BLACK)) {
                    celda.setBackground(Color.WHITE);
                } else {
                    celda.setBackground(Color.BLACK);
                }
                break;
        }
    }

    /**
     * Este método limpia cualquier celda que tenga color del tipo indicado.
     * Lo uso para asegurar que solo haya un inicio (verde) o un fin (rojo).
     * @param tipo
     */
    private void limpiarColor(CellState tipo) {
        Color color = tipo == CellState.START ? Color.GREEN : Color.RED;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j].getBackground().equals(color)) {
                    celdas[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    /**
     * Este método limpia todo el laberinto, dejando todas las celdas en blanco
     * Se hace uso de este cuando se presiona el botón "Limpiar".
     */
    public void limpiar() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j].setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Devuelve una matriz con los estados de todas las celdas de los laberintos.
     * basándose en el color actual de cada celda.
     * @return matriz de estados (WALL, START, END, EMPTY)
     */
    public CellState[][] getMatrizEstados() {
        CellState[][] matriz = new CellState[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color color = celdas[i][j].getBackground();
                if (color.equals(Color.BLACK)) {
                    matriz[i][j] = CellState.WALL;
                } else if (color.equals(Color.GREEN)) {
                    matriz[i][j] = CellState.START;
                } else if (color.equals(Color.RED)) {
                    matriz[i][j] = CellState.END;
                } else {
                    matriz[i][j] = CellState.EMPTY;
                }
            }
        }
        return matriz;
    }


    /**
     * Busca la celda que está marcada como inicio (color verde).
     * @return objeto Cell que representa el punto de inicio
     */
    public Cell getInicio() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j].getBackground().equals(Color.GREEN)) {
                    return new Cell(i, j, CellState.START);
                }
            }
        }
        return null;
    }

    /**
     * Busca la celda que está marcada como fin (color rojo).
     * @return objeto Cell que representa el punto de fin
     */
    public Cell getFin() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j].getBackground().equals(Color.RED)) {
                    return new Cell(i, j, CellState.END);
                }
            }
        }
        return null;
    }

    /**
     * Pinta el camino resultante de un algoritmo de búsqueda.
     * @param camino lista de celdas que representan el camino a seguir.
     */
    public void pintarSolucion(java.util.List<Cell> camino) {
        for (Cell celda : camino) {
            int i = celda.getFila();
            int j = celda.getColumna();
            Color actual = celdas[i][j].getBackground();

            if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                celdas[i][j].setBackground(Color.BLUE);
            }
        }
    }

    /**
     * Retorna el color actual de una celda específica.
     * @param i fila
     * @param j columna
     * @return color actual de la celda.
     */
    public Color getCeldaColor(int i, int j) {
        return celdas[i][j].getBackground();
    }

    /**
     * Cambia el color de una celda específica
     * @param i fila
     * @param j columna
     * @param color Color que se quiere establecer
     */
    public void setCeldaColor(int i, int j, Color color) {
        celdas[i][j].setBackground(color);
        celdas[i][j].setOpaque(true);
        celdas[i][j].setBorderPainted(false);
    }
}