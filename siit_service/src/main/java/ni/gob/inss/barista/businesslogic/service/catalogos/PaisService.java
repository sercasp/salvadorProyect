package ni.gob.inss.barista.businesslogic.service.catalogos;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Pais;

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
public interface PaisService {
    Pais obtener(int var1) throws EntityNotFoundException;

    void agregar(Pais var1) throws BusinessException, DAOException;

    void actualizar(Pais var1) throws BusinessException, DAOException;

    void eliminar(Pais var1);

    List<Pais> buscar(String var1);
}