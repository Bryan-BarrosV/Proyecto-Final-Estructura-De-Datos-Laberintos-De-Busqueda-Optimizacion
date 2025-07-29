package ec.ups.edu.est.solver;


import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;
/**
 * Interfaz funcional para los algoritmos de resolución de laberintos.
 *
 * <p>Define el método base que debe implementar cualquier algoritmo de búsqueda
 * (como BFS, DFS, Backtracking, etc.) que se encargue de encontrar un camino
 * desde una celda de inicio hasta una celda de fin dentro de un laberinto.</p>
 *
 * <p>El laberinto se representa como una matriz de estados de celda
 * ({@link CellState}), y el resultado incluye el camino, la cantidad de pasos
 * y el tiempo de ejecución.</p>
 */
public interface MazeSolver {
    /**
     * Resuelve el laberinto desde una celda de inicio hasta una celda de fin.
     *
     * @param laberinto Matriz que representa el estado de cada celda del laberinto.
     * @param inicio Celda de inicio (🟢) desde donde comienza el algoritmo.
     * @param fin Celda de destino (🔴) que se intenta alcanzar.
     * @return Un objeto {@link SolveResults} que contiene el camino encontrado,
     *         la cantidad de pasos y el tiempo de resolución en milisegundos.
     */
    SolveResults resolver(CellState[][] laberinto, Cell inicio, Cell fin);
}
