package ni.gob.inss.barista.businesslogic.serviceImpl.auditoria.usuario;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.auditoria.usuario.AuditoriaUsuarioService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.auditoria.AuditTrailDAO;
import ni.gob.inss.barista.model.dao.auditoria.NavegacionUsuarioDAO;
import ni.gob.inss.barista.model.dao.auditoria.SesionUsuarioDAO;
import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.auditoria.NavegacionUsuario;
import ni.gob.inss.barista.model.entity.auditoria.SesionUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 08/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
@Service
public class AuditoriaUsuarioServiceImpl implements AuditoriaUsuarioService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final UsuarioDAO oUsuarioDAO;
    private final SesionUsuarioDAO oSesionUsuarioDAO;
    private final NavegacionUsuarioDAO oNavegacionUsuarioDAO;
    private final AuditTrailDAO oAuditTrailDAO;

    @Autowired
    public AuditoriaUsuarioServiceImpl(UsuarioDAO oUsuarioDAO, SesionUsuarioDAO oSesionUsuarioDAO, NavegacionUsuarioDAO oNavegacionUsuarioDAO, AuditTrailDAO oAuditTrailDAO) {
        this.oUsuarioDAO = oUsuarioDAO;
        this.oSesionUsuarioDAO = oSesionUsuarioDAO;
        this.oNavegacionUsuarioDAO = oNavegacionUsuarioDAO;
        this.oAuditTrailDAO = oAuditTrailDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Usuario obtenerUsuarioPorId(int id) throws EntityNotFoundException {
        return oUsuarioDAO.find(id);
    }

    @Transactional
    @Override
    public List<Usuario> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterOr(
                Filter.ilike("username", "%" + prBuscar + "%"),
                Filter.ilike("nombres", "%" + prBuscar + "%"),
                Filter.ilike("apellidos", "%" + prBuscar + "%"),
                Filter.ilike("referencia", "%" + prBuscar + "%")
        );
        oSearch.addSort("username", false);
        return oUsuarioDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<SesionUsuario> buscarInicioSession(Usuario oUsuario) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("usuarioId", oUsuario.getId());
        oSearch.addSortDesc("fechaSesion");
        return oSesionUsuarioDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<NavegacionUsuario> buscarNavegacion(Usuario oUsuario) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("usuarioId", oUsuario.getId());
        oSearch.addSortDesc("fechaNavegacion");
        return oNavegacionUsuarioDAO.search(oSearch);
    }

    @Transactional
    @Override
    public List<AuditTrail> obtenerAuditoriaPorUsuario(Usuario oUsuario, Date fecha, Date fechaFin, String schema, String table) {
        return oAuditTrailDAO.obtenerAuditoriaPorUsuario(oUsuario, fecha, fechaFin, schema, table);
    }
}