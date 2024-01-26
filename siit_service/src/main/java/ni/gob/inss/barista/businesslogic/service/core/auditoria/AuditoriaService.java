package ni.gob.inss.barista.businesslogic.service.core.auditoria;

import ni.gob.inss.barista.businesslogic.service.ServiceException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrailDetail;
import ni.gob.inss.barista.model.entity.seguridad.RecursoUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.io.Serializable;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para obtener auditoría</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/23/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface AuditoriaService {
    Auditoria obtenerAuditoria(Class type, Serializable id) throws ServiceException, EntityNotFoundException;

    Auditoria obtenerAuditoria(Object entity) throws ServiceException, EntityNotFoundException;

    List<AuditTrailDetail> obtenerDetalleAuditoria(AuditTrail oAuditTrail);

    List<RecursoUsuario> obtenerRecursosPorUsuarioEntidad(Usuario oUsuario, Integer entidadId);
}