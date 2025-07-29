package ec.ups.edu.est.dao.impl;


import ec.ups.edu.est.dao.AlgorithmResultDAO;
import ec.ups.edu.est.models.AlgorithmResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz AlgorithmResultDAO que permite
 * guardar y recuperar resultados de algoritmos en un archivo CSV.
 */
public class AlgorithmResultDAOFile implements AlgorithmResultDAO {
    /**
     * Ruta del archivo CSV donde se guardarán los resultados.
     */
    private final String rutaArchivo;

    /**
     * Constructor que recibe la ruta del archivo donde se guardarán los datos.
     * Si el archivo no existe, se crea con los encabezados.
     *
     * @param rutaArchivo ruta del archivo CSV.
     */
    public AlgorithmResultDAOFile(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        inicializarArchivo();
    }

    /**
     * Inicializa el archivo si no existe. Crea el encabezado CSV.
     */
    private void inicializarArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
                writer.println("Algoritmo;Pasos;Tiempo(ms);Fecha");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Guarda un resultado de algoritmo al final del archivo CSV.
     *
     * @param resultado Objeto AlgorithmResult a guardar.
     * @throws IOException si ocurre un error de escritura.
     */
    @Override
    public void guardar(AlgorithmResult resultado) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            writer.println(resultado.toString());
        }
    }

    /**
     * Lee todos los resultados desde el archivo CSV.
     *
     * @return Lista de objetos AlgorithmResult.
     * @throws IOException si ocurre un error de lectura.
     */
    @Override
    public List<AlgorithmResult> listar() throws IOException {
        List<AlgorithmResult> resultados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            reader.readLine();
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    String nombre = partes[0];
                    int pasos = Integer.parseInt(partes[1]);
                    long tiempo = Long.parseLong(partes[2]);
                    String fecha = partes[3];
                    resultados.add(new AlgorithmResult(nombre, pasos, tiempo, fecha));
                }
            }
        }
        return resultados;
    }
}
