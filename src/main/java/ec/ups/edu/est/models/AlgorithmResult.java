package ec.ups.edu.est.models;

/**
 * Representa el resultado de la ejecución de un algoritmo de resolución del laberinto.
 */
public class AlgorithmResult {

    private String nombreAlgoritmo;
    private int pasos;
    private long tiempoEjecucion;
    private String fecha;
    /**
     * Constructor que inicializa todos los campos del resultado.
     *
     * @param nombreAlgoritmo Nombre del algoritmo utilizado.
     * @param pasos Número de pasos o celdas recorridas.
     * @param tiempoEjecucion Tiempo de ejecución en milisegundos.
     * @param fecha Fecha de la ejecución.
     */
    public AlgorithmResult(String nombreAlgoritmo, int pasos, long tiempoEjecucion, String fecha) {
        this.nombreAlgoritmo = nombreAlgoritmo;
        this.pasos = pasos;
        this.tiempoEjecucion = tiempoEjecucion;
        this.fecha = fecha;
    }

    /**
     * @return Nombre del algoritmo.
     */
    public String getNombreAlgoritmo() {
        return nombreAlgoritmo;
    }

    /**
     * @param nombreAlgoritmo Nombre del algoritmo.
     */
    public void setNombreAlgoritmo(String nombreAlgoritmo) {
        this.nombreAlgoritmo = nombreAlgoritmo;
    }

    /**
     * @return Número de pasos ejecutados por el algoritmo.
     */
    public int getPasos() {
        return pasos;
    }

    /**
     * @param pasos Número de pasos.
     */
    public void setPasos(int pasos) {
        this.pasos = pasos;
    }

    /**
     * @return Tiempo de ejecución en milisegundos.
     */
    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    /**
     * @param tiempoEjecucion Tiempo en milisegundos.
     */
    public void setTiempoEjecucion(long tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    /**
     * @return Fecha de ejecución del algoritmo.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha Fecha de ejecución.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Convierte el resultado a una representación en texto separada por punto y coma.
     * @return Resultado como cadena.
     */
    @Override
    public String toString() {
        return nombreAlgoritmo + ";" + pasos + ";" + tiempoEjecucion + ";" + fecha;
    }
}
