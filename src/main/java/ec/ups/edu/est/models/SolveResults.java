package ec.ups.edu.est.models;

import java.util.List;

/**
 * Representa el resultado final de un algoritmo de resolución del laberinto.
 */
public class SolveResults {
    private List<Cell> camino;
    private int pasos;
    private long tiempoEjecucion;

    /**
     * Constructor con tiempo medido.
     * @param camino Lista de celdas que conforman el camino.
     * @param pasos Número de pasos realizados.
     * @param tiempoEjecucion Tiempo en milisegundos.
     */
    public SolveResults(List<Cell> camino, int pasos, long tiempoEjecucion) {
        this.camino = camino;
        this.pasos = pasos;
        this.tiempoEjecucion = tiempoEjecucion;
    }

    /**
     * Constructor sin tiempo medido.
     * @param camino Lista de celdas que conforman el camino.
     * @param pasos Número de pasos realizados.
     */
    public SolveResults(List<Cell> camino, int pasos) {
        this.camino = camino;
        this.pasos = pasos;
        this.tiempoEjecucion = 0;
    }

    /**
     * @return Lista de celdas que conforman el camino solucionado.
     */
    public List<Cell> getCamino() {
        return camino;
    }

    /**
     * @return Cantidad de pasos que tomó el algoritmo.
     */
    public int getPasos() {
        return pasos;
    }

    /**
     * @return Tiempo total de ejecución en milisegundos.
     */
    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }
}
