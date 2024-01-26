package ni.gob.inss.barista.model.dao;

import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz común de acceso a datos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */
public interface BaseGenericDAO<T, ID extends Serializable> {
    T find(ID id) throws EntityNotFoundException;

    <T> T[] find(Serializable... ids);

    T save(Object entity) throws DAOException;

    void save(Object... entities) throws DAOException;

    T saveUpper(Object entity) throws DAOException;

    void saveUpper(Object... entities) throws DAOException;

    void updateUpper(Object entity) throws DAOException;

    void update(Object entity) throws DAOException;

    boolean remove(Object entity);

    void remove(Object... entities);

    boolean removeId(Serializable id);

    void removeId(Serializable... ids);

    List<T> findAll();

    boolean isAttached(Object entity);

    void refresh(Object... entities);

    void flush();

    List search(ISearch search);

    int count(ISearch search);

    int count();

    SearchResult searchAndCount(ISearch search);

    SearchResult lazySearch(int first, int pageSize);

    SearchResult lazySearch(int first, int pageSize, String sortField,
                            String sortOrder, Map<String, Object> filters);

    T searchUnique(ISearch search);

    List<T> fullTextSearch(String valor, String... campos);
}