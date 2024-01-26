package ni.gob.inss.barista.model.dao.auditoria;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.Date;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para AuditTrail</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/10/2014
 * @since 1.0 *
 */
public interface AuditTrailDAO extends BaseGenericDAO<AuditTrail,Integer> {

    public List<AuditTrail> obtenerAuditoria(String schema, String table, Object id);

    public List<AuditTrail> obtenerAuditoriaPorUsuario(Usuario oUsuario,Date fecha, Date fechaFin,String schema, String table);

}
