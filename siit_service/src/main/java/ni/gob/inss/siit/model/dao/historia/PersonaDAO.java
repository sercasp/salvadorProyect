package ni.gob.inss.siit.model.dao.historia;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.siit.model.entity.historia.Persona;

import java.util.List;
import java.util.Map;

/**
 * Creado por  jvillanueva1 el 01/12/2015.
 * Modificado por jvillanueva 02/01/2018.
 */

public interface PersonaDAO extends BaseGenericDAO<Persona, Integer> {

    List buscarPersona(String prBuscar);

    List<Map<String, Object>> buscarPersonaLazy(String buscar,
                                                Integer limite,
                                                Integer pagina);

    List<DivisionPolitica> buscarDepartamentos();

}
