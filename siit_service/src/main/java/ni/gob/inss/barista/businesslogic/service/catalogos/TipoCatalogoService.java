package ni.gob.inss.barista.businesslogic.service.catalogos;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;

import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de Servicio para Tipos de Catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface TipoCatalogoService {

    TiposCatalogo obtener(int id) throws EntityNotFoundException;

    void agregar(TiposCatalogo oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(TiposCatalogo oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(TiposCatalogo oTipoCatalogo) throws BusinessException;

    List<TiposCatalogo> buscar(String prBuscar);

    List<Catalogo> obtenerCatalogos(TiposCatalogo oTipoCatalogo);
}