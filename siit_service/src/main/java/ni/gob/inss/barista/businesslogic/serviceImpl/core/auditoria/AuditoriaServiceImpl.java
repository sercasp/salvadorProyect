package ni.gob.inss.barista.businesslogic.serviceImpl.core.auditoria;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.ServiceException;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.Auditoria;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.model.auditoria.Audit;
import ni.gob.inss.barista.model.auditoria.AuditException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.auditoria.AuditTrailDAO;
import ni.gob.inss.barista.model.dao.auditoria.AuditTrailDetailDAO;
import ni.gob.inss.barista.model.dao.seguridad.RecursoUsuarioDAO;
import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrailDetail;
import ni.gob.inss.barista.model.entity.seguridad.RecursoUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.barista.model.utils.sql.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para AuditoriaService</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/23/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class AuditoriaServiceImpl implements AuditoriaService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    final SqlUtils oSqlUtils;
    final UsuarioDAO oUsuarioDAO;
    final AuditTrailDAO oAuditTrailDAO;
    final AuditTrailDetailDAO oAuditTrailDetailDAO;
    final RecursoUsuarioDAO oRecursoUsuarioDAO;

    @Autowired
    public AuditoriaServiceImpl(SqlUtils oSqlUtils, UsuarioDAO oUsuarioDAO, AuditTrailDAO oAuditTrailDAO, AuditTrailDetailDAO oAuditTrailDetailDAO, RecursoUsuarioDAO oRecursoUsuarioDAO) {
        this.oSqlUtils = oSqlUtils;
        this.oUsuarioDAO = oUsuarioDAO;
        this.oAuditTrailDAO = oAuditTrailDAO;
        this.oAuditTrailDetailDAO = oAuditTrailDetailDAO;
        this.oRecursoUsuarioDAO = oRecursoUsuarioDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Auditoria obtenerAuditoria(Class type, Serializable id) throws ServiceException, EntityNotFoundException {
        try {
            Auditoria oAuditoria = new Auditoria();
            oAuditoria.setMostrarDetalleAuditoria(false);
            Table table = (Table) type.getAnnotation(Table.class);
            Audit audit = (Audit) this.encontarObjetoPorAnotacion(type, Audit.class);
            oAuditoria.setSchema(table.schema());
            oAuditoria.setTabla(table.name());
            oAuditoria.setId(id);
            Object entity = oSqlUtils.findByObject(type, id);

            if (!(entity instanceof AuditableEntity)) {
                throw new ServiceException("La entidad " + type.getSimpleName() + " debe implementar AuditableEntity");
            }
            Method getCreadoPor = type.getMethod("getCreadoPor");
            Method getModificadoPor = type.getMethod("getModificadoPor");
            Method getCreadoEl = type.getMethod("getCreadoEl");
            Method getModificadoEl = type.getMethod("getModificadoEl");

            //Creado por
            Integer usuarioId = ((Usuario) getCreadoPor.invoke(entity)).getId();
            Usuario oUsuarioCreadoPor = oUsuarioDAO.find(usuarioId);
            oAuditoria.setCreadoPor(oUsuarioCreadoPor.obtenerUsernameNombreCompleto());
            //Modificado por
            if (((Usuario) getModificadoPor.invoke(entity)) != null) {
                Integer usuarioModificacionId = ((Usuario) getModificadoPor.invoke(entity)).getId();
                if (usuarioModificacionId != null) {
                    Usuario oUsuarioModificadoPor = oUsuarioDAO.find(usuarioModificacionId);
                    oAuditoria.setModificadoPor(oUsuarioModificadoPor.obtenerUsernameNombreCompleto());
                }
            }
            //Creado el
            oAuditoria.setCreadoEl((Timestamp) getCreadoEl.invoke(entity));
            //Modificado el
            oAuditoria.setModificadoEl((Timestamp) getModificadoEl.invoke(entity));
            if (audit != null) {
                oAuditoria.setMostrarDetalleAuditoria(true);
                //Obteniendo auditoria
                oAuditoria.setAuditTrailList(oAuditTrailDAO.obtenerAuditoria(oAuditoria.getSchema(), oAuditoria.getTabla(), oAuditoria.getId().toString()));
            }
            return oAuditoria;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public Auditoria obtenerAuditoria(Object entity) throws ServiceException, EntityNotFoundException {
        try {
            Auditoria oAuditoria = new Auditoria();
            oAuditoria.setMostrarDetalleAuditoria(false);
            Table table = (Table) entity.getClass().getAnnotation(Table.class);
            Audit audit = (Audit) this.encontarObjetoPorAnotacion(entity.getClass(), Audit.class);

            if (!(entity instanceof AuditableEntity)) {
                throw new ServiceException("La entidad " + entity.getClass().getSimpleName() + " debe implementar AuditableEntity");
            }
            Method getCreadoPor = entity.getClass().getMethod("getCreadoPor");
            Method getModificadoPor = entity.getClass().getMethod("getModificadoPor");
            Method getCreadoEl = entity.getClass().getMethod("getCreadoEl");
            Method getModificadoEl = entity.getClass().getMethod("getModificadoEl");
            //Creado por
            Integer usuarioId = (Integer) getCreadoPor.invoke(entity);
            Usuario oUsuarioCreadoPor = oUsuarioDAO.find(usuarioId);
            oAuditoria.setCreadoPor(oUsuarioCreadoPor.obtenerUsernameNombreCompleto());

            //Modificado por
            Integer usuarioModificacionId = (Integer) getModificadoPor.invoke(entity);
            if (usuarioModificacionId != null) {
                Usuario oUsuarioModificadoPor = oUsuarioDAO.find(usuarioModificacionId);
                oAuditoria.setModificadoPor(oUsuarioModificadoPor.obtenerUsernameNombreCompleto());
            }
            //Creado el
            oAuditoria.setCreadoEl((Timestamp) getCreadoEl.invoke(entity));
            //Modificado el
            oAuditoria.setModificadoEl((Timestamp) getModificadoEl.invoke(entity));
            if (audit != null) {
                oAuditoria.setMostrarDetalleAuditoria(true);
                //Obteniendo auditoria
                oAuditoria.setAuditTrailList(oAuditTrailDAO.obtenerAuditoria(oAuditoria.getSchema(), oAuditoria.getTabla(), oAuditoria.getId().toString()));
            }
            return oAuditoria;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<AuditTrailDetail> obtenerDetalleAuditoria(AuditTrail oAuditTrail) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("auditTrail", oAuditTrail);
        return oAuditTrailDetailDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<RecursoUsuario> obtenerRecursosPorUsuarioEntidad(Usuario oUsuario, Integer entidadId) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("usuarioId", oUsuario.getId());
        oSearch.addFilterEqual("entidadId", entidadId);
        return oRecursoUsuarioDAO.search(oSearch);
    }

    private Object encontarObjetoPorAnotacion(Class<?> pClass, Class<? extends Annotation> pAnnotation) {
        Annotation oObjeto = null;
        try {
            if (pClass.isAnnotationPresent(pAnnotation)) {
                oObjeto = pClass.getAnnotation(pAnnotation);
            } else {
                for (Class e = pClass.getSuperclass(); e != null; e = e.getSuperclass()) {
                    if (e.isAnnotationPresent(pAnnotation)) {
                        oObjeto = e.getAnnotation(pAnnotation);
                        break;
                    }
                }
            }
            return oObjeto;
        } catch (Exception var5) {
            throw new AuditException(var5);
        }
    }
}