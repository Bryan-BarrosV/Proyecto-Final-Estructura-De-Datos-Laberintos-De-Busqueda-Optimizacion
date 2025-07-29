package ec.ups.edu.est.models;

import java.util.List;

/**
 * Clase que representa los resultados de la resolución de un laberinto.
 * Incluye el camino encontrado, el recorrido completo, el número de pasos
 * y el tiempo de ejecución del algoritmo.
 */
public class SolveResults {

    /** Camino final encontrado por el algoritmo (de inicio a fin). */
    private List<Cell> camino;

    /** Recorrido completo realizado por el algoritmo, incluyendo exploración. */
    private List<Cell> recorrido;

    /** Número total de pasos del camino final. */
    private int pasos;

    /** Tiempo de ejecución en nanosegundos. */
    private long tiempoEjecucion;

    /**
     * Constructor completo.
     *
     * @param camino           lista de celdas que forman el camino final
     * @param recorrido        lista de celdas exploradas durante la resolución
     * @param pasos            cantidad de pasos en el camino
     * @param tiempoEjecucion  tiempo de ejecución del algoritmo (ns)
     */
    public SolveResults(List<Cell> camino, List<Cell> recorrido, int pasos, long tiempoEjecucion) {
        this.camino = camino;
        this.recorrido = recorrido;
        this.pasos = pasos;
        this.tiempoEjecucion = tiempoEjecucion;
    }

    /**
     * Constructor que usa el mismo listado para camino y recorrido.
     *
     * @param camino           lista de celdas del camino (también recorrido)
     * @param pasos            cantidad de pasos
     * @param tiempoEjecucion  tiempo de ejecución
     */
    public SolveResults(List<Cell> camino, int pasos, long tiempoEjecucion) {
        this(camino, camino, pasos, tiempoEjecucion);
    }

    /**
     * Constructor con tiempo en 0 por defecto.
     *
     * @param camino lista de celdas del camino
     * @param pasos  cantidad de pasos
     */
    public SolveResults(List<Cell> camino, int pasos) {
        this(camino, camino, pasos, 0);
    }

    /**
     * Obtiene el camino final desde el inicio hasta el fin.
     *
     * @return lista de celdas del camino
     */
    public List<Cell> getCamino() {
        return camino;
    }

    /**
     * Obtiene el recorrido completo realizado por el algoritmo.
     *
     * @return lista de celdas recorridas
     */
    public List<Cell> getRecorrido() {
        return recorrido;
    }

    /**
     * Obtiene la cantidad total de pasos del camino.
     *
     * @return número de pasos
     */
    public int getPasos() {
        return pasos;
    }

    /**
     * Obtiene el tiempo de ejecución del algoritmo en nanosegundos.
     *
     * @return tiempo de ejecución
     */
    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }
}