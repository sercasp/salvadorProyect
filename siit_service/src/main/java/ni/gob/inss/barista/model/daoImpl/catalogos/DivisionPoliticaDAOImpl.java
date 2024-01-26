package ni.gob.inss.barista.model.daoImpl.catalogos;

import ni.gob.inss.barista.model.dao.catalogos.DivisionPoliticaDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import org.springframework.stereotype.Repository;

@Repository
public class DivisionPoliticaDAOImpl extends BaseGenericDAOImpl<DivisionPolitica, Integer> implements DivisionPoliticaDAO {
    public DivisionPoliticaDAOImpl() {
    }
}
