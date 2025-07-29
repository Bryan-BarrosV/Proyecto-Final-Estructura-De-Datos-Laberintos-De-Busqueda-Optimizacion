package ec.ups.edu.est.models;

/**
 * Enumeración de posibles estados para una celda del laberinto.
 */
public enum CellState {
    EMPTY,    // Celda vacía
    WALL,     // Obstáculo
    START,    // Punto A
    END,      // Punto B
    PATH      // Ruta óptima (usada para pintar la solución)


}
