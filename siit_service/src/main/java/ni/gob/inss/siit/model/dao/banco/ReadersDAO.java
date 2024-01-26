package ni.gob.inss.siit.model.dao.banco;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.siit.model.entity.banco.Readers;

import java.util.List;


public interface ReadersDAO extends BaseGenericDAO<Readers, Integer> {

    List<Readers> listReaders(String prBuscar);

    Readers obtenerReaderPorId(int id) throws Exception;

    void actualizar(Readers readers) throws Exception;

    void guardar(Readers readers) throws Exception;

    void eliminar(int id) throws Exception;

}
