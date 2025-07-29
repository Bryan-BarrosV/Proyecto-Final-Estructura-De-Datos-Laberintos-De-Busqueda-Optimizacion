package ec.ups.edu.est.models;

/**
 * Representa una celda dentro del laberinto, con coordenadas y estado.
 */
public class Cell {
    private int fila;
    private int columna;
    private CellState estado;

    /**
     * Constructor para una celda del laberinto.
     *
     * @param fila    Fila donde se encuentra la celda.
     * @param columna Columna donde se encuentra la celda.
     * @param estado  Estado actual de la celda.
     */
    public Cell(int fila, int columna, CellState estado) {
        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
    }

    /**
     * @return Fila de la celda.
     */
    public int getFila() {
        return fila;
    }

    /**
     * @return Columna de la celda.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @return Estado de la celda.
     */
    public CellState getEstado() {
        return estado;
    }

    /**
     * @param estado Nuevo estado de la celda.
     */
    public void setEstado(CellState estado) {
        this.estado = estado;
    }

    /**
     * Compara si otra celda está en la misma posición.
     * @param obj Objeto a comparar.
     * @return true si están en la misma fila y columna.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell)) return false;
        Cell otra = (Cell) obj;
        return this.fila == otra.fila && this.columna == otra.columna;
    }

    /**
     * Genera un hash basado en la posición de la celda.
     * @return Hash único para la celda.
     */
    @Override
    public int hashCode() {
        return fila * 31 + columna;
    }
}
