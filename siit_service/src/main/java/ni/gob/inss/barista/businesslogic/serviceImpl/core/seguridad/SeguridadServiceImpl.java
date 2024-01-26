package ni.gob.inss.barista.businesslogic.serviceImpl.core.seguridad;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.util.ServiceUtil;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.auditoria.NavegacionUsuarioDAO;
import ni.gob.inss.barista.model.dao.auditoria.SesionUsuarioDAO;
import ni.gob.inss.barista.model.dao.seguridad.RecursoDAO;
import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.entity.auditoria.NavegacionUsuario;
import ni.gob.inss.barista.model.entity.auditoria.SesionUsuario;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación Interfaz de Seguridad</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/2/2014
 * @since 1.0 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class SeguridadServiceImpl implements SeguridadService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final UsuarioDAO oUsuarioDAO;
    private final RecursoDAO oRecursoDAO;
    private final SesionUsuarioDAO oSesionUsuarioDAO;
    private final NavegacionUsuarioDAO oNavegacionUsuarioDAO;
    private final SecurityUtils oSecurityUtils;

    @Autowired
    public SeguridadServiceImpl(UsuarioDAO oUsuarioDAO,
                                SecurityUtils oSecurityUtils,
                                NavegacionUsuarioDAO oNavegacionUsuarioDAO,
                                SesionUsuarioDAO oSesionUsuarioDAO,
                                RecursoDAO oRecursoDAO) {
        this.oUsuarioDAO = oUsuarioDAO;
        this.oSecurityUtils = oSecurityUtils;
        this.oNavegacionUsuarioDAO = oNavegacionUsuarioDAO;
        this.oSesionUsuarioDAO = oSesionUsuarioDAO;
        this.oRecursoDAO = oRecursoDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    @Override
    public Usuario login(String username, String password, int tiempoBloqueoSesion, int intentosBloqueoSesion, String ipSesion)
            throws BusinessException, DAOException {
        Usuario oUsuario = this.oUsuarioDAO.buscarPorUsuario(username);

        if (oUsuario != null) {
            if (oUsuario.getPasivo()) {
                throw new BusinessException("Usuario inactivo");
            }

            //verificando si el usuario esta bloqueado
            if (oUsuario.getFechaBloqueo() != null) {
                long nanos = 60000 * tiempoBloqueoSesion;
                Timestamp now = ServiceUtil.nowTimeStamp();
                Timestamp fechaBloqueo = new Timestamp(oUsuario.getFechaBloqueo().getTime() + nanos);

                if (now.before(fechaBloqueo)) {
                    oUsuario = null;
                    long diferenciaMinutos = ServiceUtil.diffMinutes(fechaBloqueo, now);
                    throw new BusinessException("Usuario Bloquedo por intentos fallidos, debe esperar aproximadamente "
                            + String.valueOf(diferenciaMinutos) + " minutos");
                }
            }

            //Verificando contraseña
            if (!oSecurityUtils.checkpw(password, oUsuario.getPassword())) {
                //Registrando intentos de login
                oUsuario.setIntentosFallidos(oUsuario.getIntentosFallidos() + 1);
                if (oUsuario.getIntentosFallidos() == intentosBloqueoSesion) {
                    oUsuario.setFechaBloqueo(ServiceUtil.nowTimeStamp());
                }
                oUsuarioDAO.update(oUsuario);
                oUsuario = null;
            } else {
                oUsuario.setIntentosFallidos(0);
                oUsuario.setFechaBloqueo(null);
                //Guardando datos de última session
                oUsuario.setSesionActual(ServiceUtil.nowTimeStamp());
                oUsuario.setIpActual(ipSesion);
                oUsuario.setCantidadInicioSesion(oUsuario.getCantidadInicioSesion() + 1);
                oUsuarioDAO.update(oUsuario);
            }
        }

        if (oUsuario == null) {
            throw new BusinessException("Usuario o contraseña incorrecta");
        }
        return oUsuario;
    }

    @Transactional
    @Override
    public List<Recurso> obtenerRecursosAuditables() {
        return oRecursoDAO.obtenerRecursosAuditables();
    }

    @Override
    @Transactional
    public List obtenerRecursosAuditablesPorUsuario(int usuarioId) {
        return oRecursoDAO.obtenerRecursosAuditablesPorUsuario(usuarioId);
    }

    @Transactional
    @Override
    public boolean esRecursoAuditable(String contextPath, String requestUri) {
        Search oSearch = new Search();
        oSearch.addFilterIn("tipo", "P", "M");
        oSearch.addFilterEqual("auditable", true);
        oSearch.addFilterEqual("concat('" + contextPath + "',url)", requestUri.replaceAll(".html", ".xhtml"));
        List<Recurso> listaRecursos = oRecursoDAO.search(oSearch);
        return listaRecursos.size() > 0;
    }

    @Transactional
    @Override
    public void auditarInicioSesion(SesionUsuario oSesionUsuario) throws DAOException {
        oSesionUsuarioDAO.save(oSesionUsuario);
    }

    @Transactional
    @Override
    public void auditarNavegacion(NavegacionUsuario oNavegacionUsuario) throws DAOException {
        oNavegacionUsuarioDAO.save(oNavegacionUsuario);
    }

    @Override
    public String generarPassword() {
        return oSecurityUtils.generateKey();
    }

    @Transactional
    @Override
    public void cambiarClave(Usuario oUsuario, String clave, String claveConfirmacion, Boolean claveTemporal) throws BusinessException, DAOException {

        if (clave == null || clave.equals("")) {
            throw new BusinessException("Ingrese la clave");
        }

        if (clave.length() < 6) {
            throw new BusinessException("La clave debe contener al menos 6 caracteres");
        }

        if (!clave.equals(claveConfirmacion)) {
            throw new BusinessException("La clave y la confirmación de la clave no coinciden");
        }

        if (oSecurityUtils.checkpw(clave, oUsuario.getPassword())) {
            throw new BusinessException("La clave debe ser diferente a la clave actual");
        }
        oUsuario.setPassword(oSecurityUtils.encode(clave));
        oUsuarioDAO.update(oUsuario);
    }

    @Transactional
    @Override
    public void cambiarClave(Usuario oUsuario, String claveActual, String clave, String claveConfirmacion, Boolean claveTemporal) throws BusinessException, DAOException {
        if (!oSecurityUtils.checkpw(claveActual, oUsuario.getPassword())) {
            throw new BusinessException("Clave actual incorrecta");
        }
        cambiarClave(oUsuario, clave, claveConfirmacion, claveTemporal);
    }

    @Transactional
    @Override
    public Recurso verificarPermiso(Usuario oUsuario, Entidad oEntidad, String url) {
        return oRecursoDAO.verificarPermiso(oUsuario, oEntidad, url);
    }

    @Override
    public String encode(String plaintext) {
        return oSecurityUtils.encode(plaintext);
    }

    @Transactional
    @Override
    public String obtenerBreadcrum(Integer menuId) {
        return oRecursoDAO.obtenerBreadcrum(menuId);
    }
}