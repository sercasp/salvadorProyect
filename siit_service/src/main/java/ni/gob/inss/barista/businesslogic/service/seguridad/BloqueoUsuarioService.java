package ni.gob.inss.barista.businesslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

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
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface BloqueoUsuarioService {
    Usuario obtenerUsuarioPorId(int id) throws EntityNotFoundException;

    List<Usuario> buscar(String prBuscar, int tiempoBloqueo);

    void actualizar(Usuario oUsuario) throws BusinessException, DAOException;
}