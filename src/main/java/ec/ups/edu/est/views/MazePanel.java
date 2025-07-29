package ec.ups.edu.est.views;

import javax.swing.*;
import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import java.awt.*;
import java.util.List;

/**
 * Componente gráfico que representa el panel del laberinto.
 * Permite definir celdas como inicio, fin, muros y visualizar la solución y el recorrido.
 */
public class MazePanel extends JPanel {

    private final int filas;
    private final int columnas;
    private final JButton[][] celdas;
    private String modo = "WALL";

    /**
     * Constructor que inicializa el panel del laberinto.
     * @param filas Número de filas del laberinto.
     * @param columnas Número de columnas del laberinto.
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
     * Inicializa las celdas del laberinto como botones blancos con acción asignada.
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
     * Establece el modo de interacción actual (START, END, WALL).
     * @param modo Tipo de acción a aplicar en celdas al hacer clic.
     */
    public void setModo(String modo) {
        this.modo = modo;
    }

    /**
     * Actualiza una celda según el modo seleccionado.
     * @param i Fila de la celda.
     * @param j Columna de la celda.
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
     * Elimina cualquier celda con el color del tipo indicado.
     * @param tipo Tipo de celda a limpiar (START o END).
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
     * Limpia todo el laberinto, dejando las celdas en blanco.
     */
    public void limpiar() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j].setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Obtiene la matriz de estados actual del laberinto.
     * @return Matriz de estados (CellState[][]).
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
     * Obtiene la celda de inicio (verde).
     * @return Celda de inicio o null si no está definida.
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
     * Obtiene la celda de fin (roja).
     * @return Celda de fin o null si no está definida.
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
     * Pinta el camino final de solución en azul.
     * @param camino Lista de celdas que componen el camino.
     */
    public void pintarSolucion(List<Cell> camino) {
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
     * Pinta el recorrido general en color gris claro.
     * @param recorrido Lista de celdas recorridas.
     */
    public void pintarRecorrido(List<Cell> recorrido) {
        for (Cell celda : recorrido) {
            int i = celda.getFila();
            int j = celda.getColumna();
            Color actual = celdas[i][j].getBackground();
            if (!actual.equals(Color.GREEN) && !actual.equals(Color.RED)) {
                celdas[i][j].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    /**
     * Pinta el recorrido inicial sin sobreescribir el camino final.
     * @param recorrido Lista de celdas recorridas.
     * @param camino Lista de celdas del camino final.
     */
    public void pintarRecorridoInicial(List<Cell> recorrido, List<Cell> camino) {
        for (Cell celda : recorrido) {
            int i = celda.getFila();
            int j = celda.getColumna();

            boolean esDelCamino = false;
            for (Cell c : camino) {
                if (c.getFila() == i && c.getColumna() == j) {
                    esDelCamino = true;
                    break;
                }
            }

            if (!esDelCamino && !getCeldaColor(i, j).equals(Color.GREEN) && !getCeldaColor(i, j).equals(Color.RED)) {
                setCeldaColor(i, j, Color.LIGHT_GRAY);
            }
        }
    }

    /**
     * Obtiene el color actual de una celda específica.
     * @param i Fila de la celda.
     * @param j Columna de la celda.
     * @return Color de la celda.
     */
    public Color getCeldaColor(int i, int j) {
        return celdas[i][j].getBackground();
    }

    /**
     * Establece el color de una celda específica.
     * @param i Fila de la celda.
     * @param j Columna de la celda.
     * @param color Nuevo color a aplicar.
     */
    public void setCeldaColor(int i, int j, Color color) {
        celdas[i][j].setBackground(color);
        celdas[i][j].setOpaque(true);
        celdas[i][j].setBorderPainted(false);
    }
    /**
     * Limpia únicamente la ruta y el recorrido visualizados en el laberinto,
     * manteniendo las celdas de inicio, fin y muros.
     */
    public void limpiarRuta() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color color = celdas[i][j].getBackground();
                if (color.equals(Color.BLUE) || color.equals(Color.LIGHT_GRAY)) {
                    celdas[i][j].setBackground(Color.WHITE);
                }
            }
        }
        repaint();
    }
}
