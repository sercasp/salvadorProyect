package ni.gob.inss.barista.model.dao;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Excepción para entidades no encontradas</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public class EntityNotFoundException extends DAOException {
    private static final long serialVersionUID = 1L;

    /**
     * Construye mensaje de error con un mensaje
     * @param id Identificador de la entidad
     */
    public EntityNotFoundException(int id) {
        super("Registro con identificador: " + id + " no encontrado.");
    }

    /**
     * Construye mensaje de error con un mensaje
     * @param id Identificador de la entidad
     */
    public EntityNotFoundException(String id) {
        super("Registro con identificador: " + id + " no encontrado.");
    }
}