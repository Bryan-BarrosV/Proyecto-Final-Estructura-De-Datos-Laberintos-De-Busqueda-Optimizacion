package ec.ups.edu.est.solver;


import ec.ups.edu.est.models.Cell;
import ec.ups.edu.est.models.CellState;
import ec.ups.edu.est.models.SolveResults;

public interface MazeSolver {
    SolveResults resolver(CellState[][] laberinto, Cell inicio, Cell fin);
}
