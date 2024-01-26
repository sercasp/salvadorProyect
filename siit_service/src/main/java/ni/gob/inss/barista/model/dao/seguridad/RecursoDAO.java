package ni.gob.inss.barista.model.dao.seguridad;


import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

import java.util.List;


/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Recursos</br>
 *
 * @author JAIRO HELI MENDOZA AGUIRRE
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificación de jvillanueva el 12/07/2023
 * Modificado por jvillanueva el 03/08/2023
 */
public interface RecursoDAO extends BaseGenericDAO<Recurso, Integer> {
    List<Recurso> obtenerRecursosAuditables();

    Recurso buscarPorRecurso(String Recurso);

    List<Miembro> obtenerMiembros();

    List obtenerRecursosAuditablesPorUsuario(int usuarioId);

    List secuenciaCodigo();

    Recurso verificarPermiso(Usuario oUsuario, Entidad oEntidad, String url);

    String obtenerBreadcrum(Integer menuId);
}