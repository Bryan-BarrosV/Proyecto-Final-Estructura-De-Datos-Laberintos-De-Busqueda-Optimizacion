package ec.ups.edu.est.dao;

import ec.ups.edu.est.models.AlgorithmResult;

import java.io.IOException;
import java.util.List;

public interface AlgorithmResultDAO {
    void guardar(AlgorithmResult resultado) throws IOException;
    List<AlgorithmResult> listar() throws IOException;
}
