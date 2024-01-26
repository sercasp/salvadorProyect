package ni.gob.inss.barista.model.daoImpl;

import com.googlecode.genericdao.search.*;
import com.googlecode.genericdao.search.hibernate.HibernateMetadataUtil;
import com.googlecode.genericdao.search.hibernate.HibernateSearchProcessor;
import ni.gob.inss.barista.annotations.Datasource;
import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Implementación común de de acceso a datos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 03/08/2023
 */

public class BaseGenericDAOImpl<T, ID extends Serializable> implements BaseGenericDAO<T, ID> {
    private static final ResultTransformer ARRAY_RESULT_TRANSFORMER = new ResultTransformer() {
        private static final long serialVersionUID = 1L;

        public List transformList(List collection) {
            return collection;
        }

        public Object transformTuple(Object[] tuple, String[] aliases) {
            return tuple;
        }
    };

    public SessionFactory sessionFactory;
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    Logger logger = Logger.getLogger(BaseGenericDAOImpl.class);
    ApplicationContext applicationContext;
    private Class<T> entityClass;
    private HibernateSearchProcessor searchProcessor;
    private HibernateMetadataUtil metadataUtil;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public BaseGenericDAOImpl(ApplicationContext applicationContext, SessionFactory sessionFactory) {
        this.applicationContext = applicationContext;
        this.sessionFactory = sessionFactory;
    }

    public BaseGenericDAOImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @PostConstruct
    public void init() {
        try {
            searchProcessor = HibernateSearchProcessor.getInstanceForSessionFactory(sessionFactory);
            metadataUtil = HibernateMetadataUtil.getInstanceForSessionFactory(sessionFactory);
            if (this.getClass().getAnnotation(Datasource.class) != null) {
                Object obj = this.getClass().getAnnotation(Datasource.class);
                Method oMethod = obj.getClass().getMethod("nombre", new Class[0]);
                String datasource = oMethod.invoke(obj, null).toString();
                this.sessionFactory = (SessionFactory) applicationContext.getBean(datasource);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Error al iniciar BaseGenericDAO"));
        }
    }

    @Override
    public T find(Serializable id) throws EntityNotFoundException {
        return findById((Integer) id);
    }

    private T findById(Integer id) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root).where(builder.equal(root.get("id"), id));
        return getSession().createQuery(query).getSingleResult();
    }

    @Override
    public <T> T[] find(Serializable... ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery((Class<T>) entityClass);
        Root<T> root = criteria.from((Class<T>) entityClass);
        Expression<T> expression = root.get("id");
        Predicate predicate = expression.in(ids);
        criteria.select(root).where(predicate);
        List<T> lista = getSession().createQuery(criteria).getResultList();
        Object[] retVal = (Object[]) Array.newInstance(entityClass, ids.length);
        for (Object entity : lista) {
            for (int i = 0; i < ids.length; i++) {
                retVal[i] = entity;
                break;
            }
        }
        return (T[]) retVal;
    }

    @Override
    public T save(Object entity) throws DAOException {
        try {
            toTrim(entity);
            return (T) this.getSession().save(entity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void save(Object... entities) throws DAOException {
        for (Object entity : entities) {
            if (entity != null) {
                save(entity);
            }
        }
    }

    @Override
    public T saveUpper(Object entity) throws DAOException {
        try {
            toUpperCaseTrim(entity);
            return (T) this.getSession().save(entity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e);
            throw new DAOException(e);
        }

    }

    @Override
    public void saveUpper(Object... entities) throws DAOException {
        for (Object entity : entities) {
            if (entity != null) {
                saveUpper(entity);
            }
        }
    }

    @Override
    public void updateUpper(Object entity) throws DAOException {
        try {
            toUpperCaseTrim(entity);
            this.getSession().merge(entity);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error(e);
            throw new DAOException(e);

        }
    }

    @Override
    public void update(Object entity) throws DAOException {
        try {
            toTrim(entity);
            this.getSession().merge(entity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public boolean remove(Object entity) {
        if (entity != null) {
            this.getSession().delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public void remove(Object... entities) {
        for (Object entity : entities) {
            if (entity != null) {
                remove(entity);
            }
        }
    }

    @Override
    public boolean removeId(Serializable id) {
        if (id != null) {
            Object entity = getSession().get(entityClass, id);
            if (entity != null) {
                remove(entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeId(Serializable... ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        Expression<T> expression = root.get("id");
        Predicate predicate = expression.in(ids);
        criteria.select(root).where(predicate);
        List<T> lista = getSession().createQuery(criteria).getResultList();
        for (Object entity : lista) {
            if (entity != null) {
                remove(entity);
            }
        }
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return this.getSession().createQuery(query).list();
    }

    @Override
    public boolean isAttached(Object entity) {
        return this.getSession().contains(entity);
    }

    @Override
    public void refresh(Object... entities) {
        for (Object entity : entities) {
            if (entity != null) {
                this.getSession().refresh(entities);
            }
        }
    }

    @Override
    public void flush() {
        this.getSession().flush();
    }

    /**
     * Same as <code>_search(ISearch)</code> except that it uses the specified
     * search class instead of getting it from the search object. Also, if the search
     * object has a different search class than what is specified, an exception
     * is thrown.
     */
    @Override
    public List search(ISearch search) {
        if (search == null)
            throw new NullPointerException("Search is null.");
        if (search.getSearchClass() != null && !search.getSearchClass().equals(entityClass))
            throw new IllegalArgumentException("Search class does not match expected type: " + entityClass.getName());
        return this.buscar(entityClass, search);
    }

    /**
     * Same as <code>_count(ISearch)</code> except that it uses the specified
     * search class instead of getting it from the search object. Also, if the search
     * object has a different search class than what is specified, an exception
     * is thrown.
     */
    @Override
    public int count(ISearch search) {
        if (search == null)
            throw new NullPointerException("Search is null.");
        if (search.getSearchClass() != null && !search.getSearchClass().equals(entityClass))
            throw new IllegalArgumentException("Search class does not match expected type: " + entityClass.getName());

        return this.buscar(entityClass, search).size();
    }

    /**
     * Returns the number of instances of this class in the datastore.
     */
    @Override
    public int count() {
        List counts = getSession().createQuery("select count(_it_) from " + metadataUtil.get(entityClass).getEntityName() + " _it_").list();
        int sum = 0;
        for (Object count : counts) {
            sum += ((Long) count).intValue();
        }
        return sum;
    }

    /**
     * Same as <code>_searchAndCount(ISearch)</code> except that it uses the specified
     * search class instead of getting it from the search object. Also, if the search
     * object has a different search class than what is specified, an exception
     * is thrown.
     */
    @Override
    public SearchResult searchAndCount(ISearch search) {
        if (search == null)
            throw new NullPointerException("Search is null.");
        if (search.getSearchClass() != null && !search.getSearchClass().equals(entityClass))
            throw new IllegalArgumentException("Search class does not match expected type: " + entityClass.getName());

        SearchResult result = new SearchResult();
        result.setResult(this.buscar(entityClass, search));
        if (search.getMaxResults() > 0) {
            result.setTotalCount(result.getResult().size());
        } else {
            result.setTotalCount(result.getResult().size() + SearchUtil.calcFirstResult(search));
        }
        return result;
    }

    @Deprecated
    @Override
    public SearchResult lazySearch(int first, int pageSize) {
        List<T> list = null;
        int totalCount;
        SearchResult searchResult = new SearchResult();
        Search search = new Search(this.entityClass);
        totalCount = searchProcessor.count(getSession(), search);
        searchResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            search.setFirstResult(first);
            search.setMaxResults(pageSize);
            list = searchProcessor.search(getSession(), search);
        }

        if (list == null) {
            list = new ArrayList<T>();
        }
        searchResult.setResult(list);
        return searchResult;
    }

    @Deprecated
    @Override
    public SearchResult lazySearch(int first, int pageSize,
                                   String sortField, String sortOrder,
                                   Map<String, Object> filters) {
        List<T> list = null;
        int totalCount;
        SearchResult searchResult = new SearchResult();
        Search search = new Search(this.entityClass);

        if (filters != null && filters.isEmpty() == false) {
            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                String filterProperty = it.next();
                Object filterValue = filters.get(filterProperty);
                if (filterValue instanceof String) {
                    search.addFilterILike(filterProperty, "%" + (filterValue.toString()) + "%");
                } else if (filterValue instanceof String[]) {
                    String[] arrayFilterValue = (String[]) filterValue;
                    List<String> listFilterValue = Arrays.asList(arrayFilterValue);
                    search.addFilterIn(filterProperty, listFilterValue);
                } else {
                    search.addFilterEqual(filterProperty, filterValue);
                }
            }
        }
        totalCount = searchProcessor.count(getSession(), search);
        searchResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            if (StringUtils.isBlank(sortField) == false
                    && StringUtils.isBlank(sortOrder) == false) {
                if (sortOrder.toLowerCase().contains("asc")) {
                    search.addSortAsc(sortField);
                }
            }
            search.setFirstResult(first);
            search.setMaxResults(pageSize);
            list = searchProcessor.search(getSession(), search);
        }

        if (list == null) {
            list = new ArrayList<T>();
        }
        searchResult.setResult(list);
        return searchResult;
    }

    /**
     * Same as <code>_searchUnique(ISearch)</code> except that it uses the specified
     * search class instead of getting it from the search object. Also, if the search
     * object has a different search class than what is specified, an exception
     * is thrown.
     */
    @Override
    public T searchUnique(ISearch search) {
        if (search == null)
            throw new NullPointerException("Search is null.");
        if (search.getSearchClass() != null && !search.getSearchClass().equals(entityClass))
            throw new IllegalArgumentException("Search class does not match expected type: " + entityClass.getName());
        return this.buscarUnico(entityClass, search);
    }

    @Override
    public List<T> fullTextSearch(String valor, String... campos) {
        List<T> resultado;
        if (!valor.equals("")) {
            FullTextSession text = org.hibernate.search.Search.getFullTextSession(getSession());
            QueryBuilder qb = text.getSearchFactory()
                    .buildQueryBuilder().forEntity(entityClass).get();
            org.apache.lucene.search.Query luceneQuery = qb
                    .keyword().boostedTo(3)
                    .onFields(campos)
                    .matching(valor)
                    .createQuery();
            Query jpaQuery = text.createFullTextQuery(luceneQuery, entityClass);
            resultado = jpaQuery.list();
        } else {
            resultado = this.findAll();
        }
        return resultado;
    }

    private void toUpperCaseTrim(Object entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        /*Eliminando especios en blanco y pasando a mayúsculas*/
        Field[] fields = entity.getClass().getDeclaredFields();
        String methodList = getMethodsNameByEntity(entity);
        if (methodList == null) {
            return;
        }
        for (Field f : fields) {
            String field = String.valueOf(f.getName().charAt(0)).toUpperCase() + f.getName().substring(1);
            if (f.getType() == String.class
                    && methodList.contains("get" + field)
                    && methodList.contains("set" + field)) {
                Method getter = entity.getClass().getDeclaredMethod("get" + field);
                Method setter = entity.getClass().getDeclaredMethod("set" + field, new Class[]{String.class});
                Object value = getter.invoke(entity, new Object[0]);
                if (value != null) {
                    setter.invoke(entity, ((String) value).trim().toUpperCase());
                }
            }
        }
        /*Fin Eliminando especios en blanco y pasando a mayúsculas*/
    }

    private void toTrim(Object entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        /*Eliminando especios en blanco*/
        Field[] fields = entity.getClass().getDeclaredFields();
        String methodList = getMethodsNameByEntity(entity);

        if (methodList.isEmpty()) {
            return;
        }
        for (Field f : fields) {
            String field = String.valueOf(f.getName().charAt(0)).toUpperCase() + f.getName().substring(1);
            if (f.getType() == String.class
                    && methodList.contains("get" + field)
                    && methodList.contains("set" + field)) {
                Method getter = entity.getClass().getDeclaredMethod("get" + field);
                Method setter = entity.getClass().getDeclaredMethod("set" + field, new Class[]{String.class});
                Object value = getter.invoke(entity, new Object[0]);
                if (value != null) {
                    setter.invoke(entity, ((String) value).trim());
                }
            }
        }
        /*Fin Eliminando especios en blanco*/
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private String getMethodsNameByEntity(Object entity) {
        String methodList = "";
        Method[] methods = entity.getClass().getDeclaredMethods();
        if (methods != null) {
            methodList = Arrays.toString(methods);
        }
        return methodList;
    }

    private List buscar(Class<?> searchClass, ISearch search) {
        if (searchClass != null && search != null) {
            ArrayList paramList = new ArrayList();
            String hql = searchProcessor.generateQL(searchClass, search, paramList);
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            this.addParams(query, paramList);
            this.addPaging(query, search);
            this.addResultMode(query, search);
            return query.list();
        } else {
            return null;
        }
    }

    private T buscarUnico(Class<?> searchClass, ISearch search) {
        if (search == null) {
            return null;
        } else {
            ArrayList paramList = new ArrayList();
            String hql = searchProcessor.generateQL(searchClass, search, paramList);
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            this.addParams(query, paramList);
            this.addPaging(query, search);
            this.addResultMode(query, search);
            return (T) query.uniqueResult();
        }
    }

    private void addParams(Query query, List<Object> params) {
        StringBuilder debug = null;
        int i = 1;
        for (Object o : params) {
            if (logger.isDebugEnabled()) {
                if (debug == null)
                    debug = new StringBuilder();
                else
                    debug.append("\n\t");
                debug.append("p");
                debug.append(i);
                debug.append(": ");
                debug.append(InternalUtil.paramDisplayString(o));
            }
            if (o instanceof Collection) {
                query.setParameterList("p" + Integer.toString(i++), (Collection) o);
            } else if (o instanceof Object[]) {
                query.setParameterList("p" + Integer.toString(i++), (Object[]) o);
            } else {
                query.setParameter("p" + Integer.toString(i++), o);
            }
        }
        if (debug != null && debug.length() != 0) {
            logger.debug(debug.toString());
        }
    }

    private void addPaging(Query query, ISearch search) {
        int firstResult = SearchUtil.calcFirstResult(search);
        if (firstResult > 0) {
            query.setFirstResult(firstResult);
        }
        if (search.getMaxResults() > 0) {
            query.setMaxResults(search.getMaxResults());
        }
    }

    private void addResultMode(Query query, ISearch search) {
        int resultMode = search.getResultMode();
        if (resultMode == ISearch.RESULT_AUTO) {
            int count = 0;
            Iterator<com.googlecode.genericdao.search.Field> fieldItr = search.getFields().iterator();
            while (fieldItr.hasNext()) {
                com.googlecode.genericdao.search.Field field = fieldItr.next();
                if (field.getKey() != null && !field.getKey().equals("")) {
                    resultMode = ISearch.RESULT_MAP;
                    break;
                }
                count++;
            }
            if (resultMode == ISearch.RESULT_AUTO) {
                if (count > 1)
                    resultMode = ISearch.RESULT_ARRAY;
                else
                    resultMode = ISearch.RESULT_SINGLE;
            }
        }

        switch (resultMode) {
            case ISearch.RESULT_ARRAY:
                ((QueryImpl) query).setResultTransformer(ARRAY_RESULT_TRANSFORMER);
                break;
            case ISearch.RESULT_LIST:
                ((QueryImpl) query).setResultTransformer(Transformers.TO_LIST);
                break;
            case ISearch.RESULT_MAP:
                List<String> keyList = new ArrayList<String>();
                Iterator<com.googlecode.genericdao.search.Field> fieldItr = search.getFields().iterator();
                while (fieldItr.hasNext()) {
                    com.googlecode.genericdao.search.Field field = fieldItr.next();
                    if (field.getKey() != null && !field.getKey().equals("")) {
                        keyList.add(field.getKey());
                    } else {
                        keyList.add(field.getProperty());
                    }
                }
                ((QueryImpl) query).setResultTransformer(new MapResultTransformer(keyList.toArray(new String[0])));
                break;
            default: // ISearch.RESULT_SINGLE
                break;
        }
    }

    private static class MapResultTransformer implements ResultTransformer {
        private static final long serialVersionUID = 1L;
        private String[] keys;

        public MapResultTransformer(String[] keys) {
            this.keys = keys;
        }

        public List transformList(List collection) {
            return collection;
        }

        public Object transformTuple(Object[] tuple, String[] aliases) {
            HashMap map = new HashMap();

            for (int i = 0; i < this.keys.length; ++i) {
                String key = this.keys[i];
                if (key != null) {
                    map.put(key, tuple[i]);
                }
            }
            return map;
        }
    }
}