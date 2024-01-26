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
 * Interfaz de Servicio para Catálogos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface CatalogoService {
    Catalogo obtener(int id) throws EntityNotFoundException;

    void agregar(Catalogo oCatalogo) throws DAOException, BusinessException;

    void actualizar(Catalogo oCatalogo) throws DAOException;

    void eliminar(Catalogo oCatalogo);

    List<Catalogo> buscar(String refTipoCatalogo, String prBuscar);

    List<TiposCatalogo> obtenerTipoCatalogo();

    List<TiposCatalogo> obtenerTipoCatalogo(Catalogo oCatalogo);
}