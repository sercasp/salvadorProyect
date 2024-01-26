package ni.gob.inss.barista.model.daoImpl.infraestructura;

import ni.gob.inss.barista.model.dao.infraestructura.EstablecimientoDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.barista.model.entity.infraestructura.Establecimiento;
import org.springframework.stereotype.Repository;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * DESCRIPCIÓN</br>
 *
 * @author JAIRO HELÍ MENDOZA AGUIRRE
 * @version 1.0, 05/08/2014
 * @since 1.0 *
 */
@Repository
public class EstablecimientoDAOImpl extends BaseGenericDAOImpl<Establecimiento, Integer> implements EstablecimientoDAO {
}
