package ni.gob.inss.barista.businesslogic.serviceImpl.seguridad;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.seguridad.BloqueoUsuarioService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 04/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class BloqueoUsuarioServiceImpl implements BloqueoUsuarioService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final UsuarioDAO oUsuarioDAO;

    @Autowired
    public BloqueoUsuarioServiceImpl(UsuarioDAO oUsuarioDAO) {
        this.oUsuarioDAO = oUsuarioDAO;
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
    public List<Usuario> buscar(String prBuscar, int tiempoBloqueo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addFilterGreaterThan("intentosFallidos", 0);
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
    public void actualizar(Usuario oUsuario) throws BusinessException, DAOException {
        oUsuario.setNombres(oUsuario.getNombres().toUpperCase().trim());
        oUsuario.setApellidos(oUsuario.getApellidos().toUpperCase().trim());
        if (oUsuario.getReferencia() != null) {
            oUsuario.setReferencia(oUsuario.getReferencia().toUpperCase().trim());
        }
        oUsuarioDAO.update(oUsuario);
    }
}