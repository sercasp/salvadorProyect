package ni.gob.inss.barista.businesslogic.service.auditoria.usuario;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.auditoria.NavegacionUsuario;
import ni.gob.inss.barista.model.entity.auditoria.SesionUsuario;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

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
public interface AuditoriaUsuarioService {

    Usuario obtenerUsuarioPorId(int id) throws EntityNotFoundException;

    List<Usuario> buscar(String prBuscar);

    List<SesionUsuario> buscarInicioSession(Usuario oUsuario);

    List<NavegacionUsuario> buscarNavegacion(Usuario oUsuario);

    List<AuditTrail> obtenerAuditoriaPorUsuario(Usuario oUsuario, Date fecha, Date fechaFin, String schema, String table);
}