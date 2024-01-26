package ni.gob.inss.barista.model.auditoria;

import ni.gob.inss.barista.model.entity.AuditableEntity;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrailDetail;
import ni.gob.inss.barista.model.utils.ServiceUtils;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Felix Medina
 * @author <a href=mailto:fmedina@inss.gob.ni>fmedina@inss.gob.ni</a>
 * @version 1.0, &nbsp; 20/04/2014
 * Modificado por jvillanueva el 03/08/2023
 */
@Repository
@SuppressWarnings({"rawtypes", "unchecked"})
@Scope("prototype")
public class AuditableEntityInterceptor extends EmptyInterceptor {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private List<AuditTrail> upAuditTrails = new ArrayList<>();
    private List<AuditTrail> inAuditTrails = new ArrayList<>();
    private List<AuditTrail> delAuditTrails = new ArrayList<>();
    private List<Map<String, Object[]>> listaEstado = new ArrayList<>();
    private List<Map<String, String[]>> listaColumnas = new ArrayList<>();
    private Object[] estado;
    private Object entidad;
    private String[] nombreColumnas;
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(AuditableEntityInterceptor.class);

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    final SessionFactory sessionFactory;
    final AuditUtils oAuditUtils;

    @Autowired
    public AuditableEntityInterceptor(SessionFactory sessionFactory, AuditUtils oAuditUtils) {
        this.sessionFactory = sessionFactory;
        this.oAuditUtils = oAuditUtils;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public boolean onSave(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types) {
        estado = state;
        entidad = entity;
        nombreColumnas = propertyNames;
        Class oEntityClass = entity.getClass();
        Audit AuditAnnotation = (Audit) encontarObjetoPorAnotacion(oEntityClass, Audit.class);

        if (entity instanceof AuditTrail || entity instanceof AuditTrailDetail || !(entity instanceof AuditableEntity)
                || AuditAnnotation == null) {
            return false;
        }

        if (AuditAnnotation.insert()) {
            AuditTrail oAuditTrail = new AuditTrail();
            List<AuditTrailDetail> oAuditTrails = new ArrayList<>();
            Table oTableAnnotation = (Table) encontarObjetoPorAnotacion(oEntityClass, Table.class);
            oAuditTrail.setSistema(oAuditUtils.obtenerNombreSistema());
            oAuditTrail.setTabla(oTableAnnotation.name());
            oAuditTrail.setSchema(oTableAnnotation.schema());
            oAuditTrail.setOperacion("insert");
            oAuditTrail.setFilaId("1");
            oAuditTrail.setFecha(ServiceUtils.now());
            oAuditTrail.setUsuario(oAuditUtils.obtenerUsuarioActual());
            inAuditTrails.add(oAuditTrail);
            Map<String, Object[]> mapa = new HashMap<>();
            mapa.put(oTableAnnotation.name(), estado);
            Map<String, String[]> mapaColumnas = new HashMap<>();
            mapaColumnas.put(oTableAnnotation.name(), nombreColumnas);
            listaColumnas.add(mapaColumnas);
            listaEstado.add(mapa);
        }
        return false;
    }

    @Transactional
    public boolean onFlushDirty(Object entity, Serializable id,
                                Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) throws AuditException {
        Class oEntityClass = entity.getClass();
        Audit AuditAnnotation = (Audit) encontarObjetoPorAnotacion(oEntityClass, Audit.class);

        if (entity instanceof AuditTrail
                || entity instanceof AuditTrailDetail
                || !(entity instanceof AuditableEntity)
                || AuditAnnotation == null) {
            return false;
        }

        try {
            if (!AuditAnnotation.update()) {
                return false;
            }
            AuditTrail oAuditTrail = new AuditTrail();
            List<AuditTrailDetail> oAuditTrails = new ArrayList<>();
            Table oTableAnnotation = (Table) encontarObjetoPorAnotacion(oEntityClass, Table.class);
            oAuditTrail.setSistema(oAuditUtils.obtenerNombreSistema());
            oAuditTrail.setTabla(oTableAnnotation.name());
            oAuditTrail.setSchema(oTableAnnotation.schema());
            oAuditTrail.setOperacion("update");
            oAuditTrail.setFilaId(id.toString());
            oAuditTrail.setFecha(ServiceUtils.now());
            oAuditTrail.setUsuario(oAuditUtils.obtenerUsuarioActual());
            oAuditTrail.setHost(WebUtils.getRemoteHost());

            for (int i = 0; i < currentState.length; i++) {
                String oNombreCampo = propertyNames[i];
                if (oNombreCampo.contains("serialVersionUID")) {
                    continue;
                }
                boolean auditarCampo = true;
                for (String exceptField : AuditAnnotation.except()) {
                    if (exceptField.equals(oNombreCampo)) {
                        auditarCampo = false;
                    }
                }

                for (String onlyField : AuditAnnotation.only()) {
                    if (onlyField.equals("")) {
                        continue;
                    }
                    if (!onlyField.equals(oNombreCampo)) {
                        auditarCampo = false;
                    }
                }

                if (!auditarCampo) {
                    continue;
                }
                // Se supervisan los cambios de estado de aquellos objetos que
                // hereden de AuditableEntity, o bien que sean de tipo Date, Number o String.
                if (!(previousState[i] instanceof AuditableEntity
                        || previousState[i] instanceof String
                        || previousState[i] instanceof Long
                        || previousState[i] instanceof BigDecimal
                        || previousState[i] instanceof Integer
                        || previousState[i] instanceof Boolean
                        || previousState[i] instanceof Date)) {
                    continue;
                }

                AuditTrailDetail auditTrailDetail = new AuditTrailDetail();
                if (previousState[i] instanceof AuditableEntity) {
                    AuditableEntity pvAuditableEntity = (AuditableEntity) previousState[i];
                    AuditableEntity csAuditableEntity = (AuditableEntity) currentState[i];
                    Method oMetodo;
                    Character oPrimerChar = oNombreCampo.charAt(0);
                    oPrimerChar = Character.toUpperCase(oPrimerChar);
                    String oCampoSubStr = oNombreCampo.substring(1);
                    String oNombreMetodo = "get" + oPrimerChar + oCampoSubStr;
                    oMetodo = encontrarMetodoPorNombre(oEntityClass, oNombreMetodo);

                    if (oMetodo == null) {
                        continue;
                    }

                    JoinColumn oColumna = oMetodo.getAnnotation(JoinColumn.class);
                    auditTrailDetail.setColumna(oColumna != null ? oColumna
                            .name() : oNombreCampo);
                    if (pvAuditableEntity != null && csAuditableEntity != null
                            && !pvAuditableEntity.getFilaId().equalsIgnoreCase(csAuditableEntity.getFilaId())
                    ) {
                        // Valor anterior
                        auditTrailDetail.setValorAnterior(pvAuditableEntity.getFilaId());
                        // Valor nuevo
                        auditTrailDetail.setValorNuevo(csAuditableEntity.getFilaId());
                        oAuditTrails.add(auditTrailDetail);
                    } else if (csAuditableEntity != null
                            && (pvAuditableEntity == null || (pvAuditableEntity.getFilaId() == null || pvAuditableEntity.getFilaId().isEmpty()))) {
                        // Valor nuevo
                        auditTrailDetail.setValorNuevo(csAuditableEntity.getFilaId());
                        oAuditTrails.add(auditTrailDetail);
                    } else if (pvAuditableEntity != null && (csAuditableEntity == null
                            || (csAuditableEntity.getFilaId() == null || csAuditableEntity.getFilaId().isEmpty()))) {
                        // Valor anterior
                        auditTrailDetail.setValorAnterior(pvAuditableEntity.getFilaId());
                    }
                } else {
                    // Verificamos que el valor de la columna ha sido
                    // actualizado
                    Method oMetodo;
                    Character oPrimerChar = oNombreCampo.charAt(0);
                    oPrimerChar = Character.toUpperCase(oPrimerChar);
                    String oCampoSubStr = oNombreCampo.substring(1);
                    String oNombreMetodo = "get" + oPrimerChar + oCampoSubStr;
                    oMetodo = encontrarMetodoPorNombre(oEntityClass, oNombreMetodo);

                    if (oMetodo == null) {
                        continue;
                    }
                    Column oColumna = oMetodo.getAnnotation(Column.class);
                    auditTrailDetail.setColumna(oColumna != null ? oColumna
                            .name() : oNombreCampo);

                    if (previousState[i] != null && currentState[i] != null
                            && !(previousState[i].equals(currentState[i]))) {
                        // Valor anterior
                        auditTrailDetail
                                .setValorAnterior(previousState[i] == null ? null
                                        : previousState[i].toString());
                        // Valor nuevo
                        auditTrailDetail
                                .setValorNuevo(currentState[i] == null ? null
                                        : currentState[i].toString());
                        oAuditTrails.add(auditTrailDetail);
                    } else if (currentState[i] != null
                            && (previousState[i] == null
                            || (previousState[i].toString() == null || previousState[i].toString().isEmpty()))) {
                        // Valor nuevo
                        auditTrailDetail
                                .setValorNuevo(currentState[i] == null ? null
                                        : currentState[i].toString());
                    } else if (previousState[i] != null
                            && (currentState[i] == null
                            || (currentState[i].toString() == null || currentState[i].toString().isEmpty()))) {
                        // Valor anterior
                        auditTrailDetail
                                .setValorAnterior(previousState[i] == null ? null
                                        : previousState[i].toString());
                    }
                }

            }
            oAuditTrail.setAuditTrailList(oAuditTrails);
            if (upAuditTrails.stream().filter(p -> p.getTabla().equals(oAuditTrail.getTabla())).collect(Collectors.toList()).size() == 0) {
                this.upAuditTrails.add(oAuditTrail);
            }
        } catch (Exception iExcepcion) {
            upAuditTrails.clear();
            logger.error(iExcepcion);
            throw new AuditException(iExcepcion);
        }
        return false;
    }

    @Transactional
    public void onDelete(Object entity, Serializable id, Object[] state,
                         String[] propertyNames, Type[] types) throws AuditException {
        Class oEntityClass = entity.getClass();
        Audit AuditAnnotation = (Audit) encontarObjetoPorAnotacion(oEntityClass, Audit.class);

        if (entity instanceof AuditTrail || entity instanceof AuditTrailDetail || !(entity instanceof AuditableEntity)
                || AuditAnnotation == null) {
            return;
        }

        try {
            if (!AuditAnnotation.delete()) {
                return;
            }
            Table oTableAnnotation;
            AuditTrail oAuditTrail = new AuditTrail();
            List<AuditTrailDetail> oAuditTrails = new ArrayList<>();
            Class oSuperClass = oEntityClass.getSuperclass();
            if (oSuperClass.isAnnotationPresent(Entity.class)) {
                oTableAnnotation = (Table) oSuperClass.getAnnotation(Table.class);
            } else {
                oTableAnnotation = (Table) oEntityClass.getAnnotation(Table.class);
            }
            oAuditTrail.setSistema(oAuditUtils.obtenerNombreSistema());
            oAuditTrail.setTabla(oTableAnnotation.name());
            oAuditTrail.setSchema(oTableAnnotation.schema());
            oAuditTrail.setOperacion("delete");
            oAuditTrail.setFilaId(id.toString());
            oAuditTrail.setFecha(ServiceUtils.now());
            oAuditTrail.setUsuario(oAuditUtils.obtenerUsuarioActual());
            oAuditTrail.setHost(WebUtils.getRemoteHost());

            for (int i = 0; i < state.length; i++) {
                String oNombreCampo = propertyNames[i];
                boolean auditarCampo = true;
                if (oNombreCampo.contains("serialVersionUID")) {
                    continue;
                }
                for (String exceptField : AuditAnnotation.except()) {
                    if (exceptField.equals(oNombreCampo)) {
                        auditarCampo = false;
                    }
                }

                for (String onlyField : AuditAnnotation.only()) {
                    if (onlyField.equals("")) {
                        continue;
                    }
                    if (!onlyField.equals(oNombreCampo)) {
                        auditarCampo = false;
                    }
                }

                if (!auditarCampo) {
                    continue;
                }

                if (!(state[i] instanceof AuditableEntity
                        || state[i] instanceof String
                        || state[i] instanceof Long
                        || state[i] instanceof BigDecimal
                        || state[i] instanceof Integer
                        || state[i] instanceof Boolean
                        || state[i] instanceof Date)) {
                    continue;
                }

                AuditTrailDetail auditTrailDetail = new AuditTrailDetail();
                if (state[i] instanceof AuditableEntity) {
                    AuditableEntity csAuditableEntity = (AuditableEntity) state[i];
                    Character oPrimerChar = oNombreCampo.charAt(0);
                    oPrimerChar = Character.toUpperCase(oPrimerChar);
                    String oCampoSubStr = oNombreCampo.substring(1);
                    String oNombreMetodo = "get" + oPrimerChar + oCampoSubStr;
                    Method oMetodo = encontrarMetodoPorNombre(oEntityClass, oNombreMetodo);

                    if (oMetodo == null) {
                        continue;
                    }

                    JoinColumn oColumna = oMetodo.getAnnotation(JoinColumn.class);
                    auditTrailDetail.setColumna(oColumna != null ? oColumna
                            .name() : oNombreCampo);
                    // Valor a eliminar
                    auditTrailDetail.setValorAnterior(csAuditableEntity == null ? null
                            : csAuditableEntity.getFilaId());
                } else {
                    Character oPrimerChar = oNombreCampo.charAt(0);
                    oPrimerChar = Character.toUpperCase(oPrimerChar);
                    String oCampoSubStr = oNombreCampo.substring(1);
                    String oNombreMetodo = "get" + oPrimerChar + oCampoSubStr;
                    Method oMetodo = encontrarMetodoPorNombre(oEntityClass, oNombreMetodo);

                    if (oMetodo == null) {
                        continue;
                    }

                    Column oColumna = oMetodo.getAnnotation(Column.class);
                    auditTrailDetail.setColumna(oColumna != null ? oColumna
                            .name() : oNombreCampo);
                    // Valor de la fila eliminado
                    auditTrailDetail.setValorAnterior(state[i] == null ? null
                            : state[i].toString());
                }
                oAuditTrails.add(auditTrailDetail);
            }

            oAuditTrail.setAuditTrailList(oAuditTrails);
            this.delAuditTrails.add(oAuditTrail);
        } catch (Exception iExcepcion) {
            delAuditTrails.clear();
            logger.error(iExcepcion);
            throw new AuditException(iExcepcion);
        }
    }

    public void preFlush(Iterator iterator) {

    }

    @Transactional
    public void postFlush(Iterator iterator) {
        if (this.upAuditTrails.isEmpty() && this.inAuditTrails.isEmpty() && this.delAuditTrails.isEmpty()) {
            return;
        }

        while (iterator.hasNext()) {
            Object entity = iterator.next();
            Class oEntityClass = entity.getClass();
            Table oTableAnnotation = (Table) encontarObjetoPorAnotacion(oEntityClass, Table.class);
            if (inAuditTrails.stream().filter(p -> p.getTabla().equals(oTableAnnotation.name())).collect(Collectors.toList()).size() > 0) {
                Audit AuditAnnotation = (Audit) encontarObjetoPorAnotacion(oEntityClass, Audit.class);

                if (!inAuditTrails.isEmpty()) {
                    try {
                        AuditTrail oAuditTrail = new AuditTrail();
                        List<AuditTrailDetail> oAuditTrails = new ArrayList<>();
                        Method method = entity.getClass().getMethod("getId", new Class[0]);
                        Object value = method.invoke(entity, new Object[0]);
                        oAuditTrail.setSistema(oAuditUtils.obtenerNombreSistema());
                        oAuditTrail.setTabla(oTableAnnotation.name());
                        oAuditTrail.setSchema(oTableAnnotation.schema());
                        oAuditTrail.setOperacion("insert");
                        oAuditTrail.setFilaId(value.toString());
                        oAuditTrail.setFecha(ServiceUtils.now());
                        oAuditTrail.setUsuario(oAuditUtils.obtenerUsuarioActual());
                        oAuditTrail.setHost(WebUtils.getRemoteHost());
                        List<Map<String, Object[]>> listaTemporal = listaEstado.stream()
                                .filter(p -> p.entrySet().stream()
                                        .filter(key -> key.getKey().equals(oTableAnnotation.name())).count() > 0)
                                .collect(Collectors.toList());
                        List<Map<String, String[]>> listaTemporalColumnas = listaColumnas.stream()
                                .filter(p -> p.entrySet().stream()
                                        .filter(key -> key.getKey().equals(oTableAnnotation.name())).count() > 0)
                                .collect(Collectors.toList());
                        if (listaTemporal.size() > 0 && listaTemporalColumnas.size() > 0) {
                            this.estado = listaTemporal.get(0).get(oTableAnnotation.name());
                            this.nombreColumnas = listaTemporalColumnas.get(0).get(oTableAnnotation.name());
                            for (int i = 0; i < estado.length; i++) {
                                if (!(estado[i] instanceof AuditableEntity
                                        || estado[i] instanceof String
                                        || estado[i] instanceof Long
                                        || estado[i] instanceof BigDecimal
                                        || estado[i] instanceof Integer
                                        || estado[i] instanceof Boolean
                                        || estado[i] instanceof Date)) {
                                    continue;
                                }

                                /*Asignar el valor del campo, propertyNames[i]*/
                                String oNombreCampo = nombreColumnas[i];
                                boolean auditarCampo = true;

                                if (oNombreCampo.contains("serialVersionUID")) {
                                    continue;
                                }

                                for (String exceptField : AuditAnnotation.except()) {
                                    if (exceptField.equals(oNombreCampo)) {
                                        auditarCampo = false;
                                    }
                                }

                                for (String onlyField : AuditAnnotation.only()) {
                                    if (onlyField.equals("")) {
                                        continue;
                                    }
                                    if (!onlyField.equals(oNombreCampo)) {
                                        auditarCampo = false;
                                    }
                                }

                                if (!auditarCampo) {
                                    continue;
                                }

                                AuditTrailDetail auditTrailDetail = new AuditTrailDetail();
                                if (estado[i] instanceof AuditableEntity) {

                                    AuditableEntity csAuditableEntity = (AuditableEntity) estado[i];
                                    Character oPrimerChar = oNombreCampo.charAt(0);
                                    oPrimerChar = Character.toUpperCase(oPrimerChar);
                                    String oCampoSubStr = oNombreCampo.substring(1);
                                    String oNombreMetodo = "get" + oPrimerChar + oCampoSubStr;
                                    Method oMetodo = encontrarMetodoPorNombre(oEntityClass, oNombreMetodo);

                                    if (oMetodo == null) {
                                        continue;
                                    }

                                    JoinColumn oColumna = oMetodo.getAnnotation(JoinColumn.class);
                                    auditTrailDetail.setColumna(oColumna != null ? oColumna.name() : oNombreCampo);
                                    // Valor nuevo
                                    auditTrailDetail.setValorNuevo(csAuditableEntity == null ? null : csAuditableEntity.getFilaId());
                                    oAuditTrails.add(auditTrailDetail);
                                } else {
                                    Character oPrimerChar = oNombreCampo.charAt(0);
                                    oPrimerChar = Character.toUpperCase(oPrimerChar);
                                    String oCampoSubStr = oNombreCampo.substring(1);
                                    String oNombreMetodo = "get" + oPrimerChar + oCampoSubStr;
                                    Method oMetodo = encontrarMetodoPorNombre(oEntityClass, oNombreMetodo);

                                    if (oMetodo == null) {
                                        continue;
                                    }

                                    Column oColumna = oMetodo.getAnnotation(Column.class);
                                    auditTrailDetail.setColumna(oColumna != null ? oColumna.name() : oNombreCampo);
                                    // Valor nuevo
                                    auditTrailDetail.setValorNuevo(estado[i] == null ? null : estado[i].toString());
                                    oAuditTrails.add(auditTrailDetail);
                                }
                            }

                            oAuditTrail.setAuditTrailList(oAuditTrails);
                            this.inAuditTrails.removeIf(p -> p.getTabla().equals(oAuditTrail.getTabla()));
                            this.inAuditTrails.add(oAuditTrail);
                        }
                    } catch (Exception iExcepcion) {
                        inAuditTrails.clear();
                        logger.error(iExcepcion);
                        throw new AuditException(iExcepcion);
                    }
                }
            }
        }

        Session oSession = sessionFactory.getCurrentSession();
        try {
            for (AuditTrail oAuTrail : this.upAuditTrails) {
                if (oAuTrail.getAuditTrailList().size() == 0) {
                    continue;
                }
                oSession.save(oAuTrail);
                for (AuditTrailDetail oAuDetail : oAuTrail.getAuditTrailList()) {
                    oAuDetail.setAuditTrail(oAuTrail);
                    oSession.save(oAuDetail);
                }
            }

            for (AuditTrail oAuTrail : this.inAuditTrails) {
                if (oAuTrail.getAuditTrailList().size() == 0) {
                    continue;
                }

                oSession.save(oAuTrail);
                for (AuditTrailDetail oAuDetail : oAuTrail.getAuditTrailList()) {
                    oAuDetail.setAuditTrail(oAuTrail);
                    oSession.save(oAuDetail);
                }
            }

            for (AuditTrail oAuTrail : this.delAuditTrails) {
                if (oAuTrail.getAuditTrailList().size() == 0) {
                    continue;
                }

                oSession.save(oAuTrail);
                for (AuditTrailDetail oAuDetail : oAuTrail.getAuditTrailList()) {
                    oAuDetail.setAuditTrail(oAuTrail);
                    oSession.save(oAuDetail);
                }
            }
            //oSession.flush();
        } finally {
            //oSession.close();
            this.upAuditTrails.clear();
            this.inAuditTrails.clear();
            this.delAuditTrails.clear();
            if (listaEstado.size() > 0) {
                listaEstado.clear();
            }

            if (listaColumnas.size() > 0) {
                listaColumnas.clear();
            }
        }
    }

    public void afterTransactionCompletion(Transaction tx) {
        this.upAuditTrails.clear();
        this.inAuditTrails.clear();
        this.delAuditTrails.clear();
    }

    private Method encontrarMetodoPorNombre(Class<?> pClass,
                                            String pNombreMetodo) {
        Method oMetodo = null;
        Method[] oMetodos;
        try {
            Class oClass = pClass;
            while (oMetodo == null && oClass != null) {
                oMetodos = oClass.getDeclaredMethods();
                if (oMetodos != null) {
                    for (Method iMethod : oMetodos) {
                        if (iMethod.getName().equalsIgnoreCase(pNombreMetodo)) {
                            oMetodo = iMethod;
                            break;
                        }
                    }
                }
                oClass = oClass.getSuperclass();
            }
        } catch (Exception e) {
            logger.error(e);
            throw new AuditException(e);
        }
        return oMetodo;
    }

    private Object encontarObjetoPorAnotacion(Class<?> pClass, Class<? extends Annotation> pAnnotation) {
        Object oObjeto = null;
        try {
            if (pClass.isAnnotationPresent(pAnnotation)) {
                oObjeto = pClass.getAnnotation(pAnnotation);
            } else {
                Class oPadre = pClass.getSuperclass();
                while (oPadre != null) {
                    if (oPadre.isAnnotationPresent(pAnnotation)) {
                        oObjeto = oPadre.getAnnotation(pAnnotation);
                        break;
                    }
                    oPadre = oPadre.getSuperclass();
                }
            }
        } catch (Exception e) {
            throw new AuditException(e);
        }
        return oObjeto;
    }
}