package ni.gob.inss.barista.businesslogic.service.infraestructura;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.infraestructura.Establecimiento;

import java.util.List;


/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para EntidadService</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface EntidadService {

    List<Entidad> obtenerEntidadesPorUsuario(Integer usuarioId);

    Entidad obtenerEntidad(Integer entidadId) throws EntityNotFoundException;

    Entidad obtenerEntidadPorId(int id) throws EntityNotFoundException;

    Entidad obtenerEntidadPorCodigo(Integer codigo) throws BusinessException;

    void agregar(Entidad oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Entidad oTipoCatalogo) throws BusinessException, DAOException;

    List<Entidad> buscar(String prBuscar);

    List<Establecimiento> obtenerEstablecimientos();

    List<Entidad> obtenerEntidadesCombo(int id);

    List<Entidad> obtenerEntidadesCombo();
}