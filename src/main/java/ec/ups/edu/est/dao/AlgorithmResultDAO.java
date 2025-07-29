package ec.ups.edu.est.dao;

import ec.ups.edu.est.models.AlgorithmResult;

import java.io.IOException;
import java.util.List;

/**
 * Interfaz DAO para acceder a los resultados de ejecución de algoritmos.
 * Define métodos para guardar y listar resultados desde una fuente de datos.
 */
public interface AlgorithmResultDAO {
    /**
     * Guarda un resultado de algoritmo.
     *
     * @param resultado el resultado a guardar.
     * @throws IOException si ocurre un error durante la escritura.
     */
    void guardar(AlgorithmResult resultado) throws IOException;

    /**
     * Lista todos los resultados almacenados.
     *
     * @return una lista de resultados.
     * @throws IOException si ocurre un error durante la lectura.
     */
    List<AlgorithmResult> listar() throws IOException;
}
