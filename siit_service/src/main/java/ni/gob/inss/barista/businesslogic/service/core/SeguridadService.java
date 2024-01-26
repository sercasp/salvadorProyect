package ni.gob.inss.barista.businesslogic.service.core;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.auditoria.NavegacionUsuario;
import ni.gob.inss.barista.model.entity.auditoria.SesionUsuario;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Seguridad</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/2/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface SeguridadService {
    Usuario login(String username, String password, int tiempoBloqueoSesion, int intentosBloqueoSesion, String ipSesion) throws BusinessException, DAOException;

    List<Recurso> obtenerRecursosAuditables();

    List obtenerRecursosAuditablesPorUsuario(int usuarioId);

    boolean esRecursoAuditable(String contextPath, String requestUri);

    void auditarInicioSesion(SesionUsuario oSesionUsuario) throws DAOException;

    void auditarNavegacion(NavegacionUsuario oNavegacionUsuario) throws DAOException;

    String generarPassword();

    void cambiarClave(Usuario oUsuario, String clave, String claveConfirmacion, Boolean claveTemporal) throws BusinessException, DAOException;

    void cambiarClave(Usuario oUsuario, String claveActual, String clave, String claveConfirmacion, Boolean claveTemporal) throws BusinessException, DAOException;

    Recurso verificarPermiso(Usuario oUsuario, Entidad oEntidad, String url);

    String encode(String plaintext);

    String obtenerBreadcrum(Integer menuId);
}