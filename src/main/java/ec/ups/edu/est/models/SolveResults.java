package ec.ups.edu.est.models;

import java.util.List;

public class SolveResults {
    private List<Cell> camino;
    private int pasos;
    private long tiempoEjecucion;

    public SolveResults(List<Cell> camino, int pasos, long tiempoEjecucion) {
        this.camino = camino;
        this.pasos = pasos;
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public SolveResults(List<Cell> camino, int pasos) {
        this.camino = camino;
        this.pasos = pasos;
        this.tiempoEjecucion = 0;
    }

    public List<Cell> getCamino() {
        return camino;
    }

    public int getPasos() {
        return pasos;
    }

    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }
}
