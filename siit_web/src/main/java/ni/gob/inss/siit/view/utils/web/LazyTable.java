package ni.gob.inss.siit.view.utils.web;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hilario Antonio Campos Silva
 * @version 1.0
 * @since 21/02/2018
 * <h1>Clase Generica para crear instancias de tipo Lazy Data Model</h1>
 * <p>Tipos soportados: **Clases de Entidades** y **HashMap < String, Object >**</p>
 */
public class LazyTable<T> {
    private String nombreLlaveRegistro;
    private LazyDataModel<T> lista;
    private LazyTableCallable<List<T>> obtenerRegistros;
    private LazyTableCallable<Integer> obtenerConteoRegistros;

    /******************************************************************************************************************
     * Constructor
     * ****************************************************************************************************************
     * @param nombreLlaveRegistro Nombre de la llave para cada registro
     * @param obtenerRegistros Metodo que obtendra la lista del tipo de dato generico
     * @param obtenerConteoRegistros Metodo que obtendra la cantidad total de registros
     *****************************************************************************************************************/
    public LazyTable(String nombreLlaveRegistro, LazyTableCallable<List<T>> obtenerRegistros, LazyTableCallable<Integer> obtenerConteoRegistros) {
        try {
            this.nombreLlaveRegistro = nombreLlaveRegistro;
            this.obtenerRegistros = obtenerRegistros;
            this.obtenerConteoRegistros = obtenerConteoRegistros;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*****************************************************************************************************************
     * Inicializa el objeto
     * @return Retorna una instancia de tipo lazy data model con la implementacion correspondiente al tipo generico
     *****************************************************************************************************************/
    public LazyDataModel<T> initLazy() {
        lista = new LazyDataModel<T>() {
            public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                try {
                    lista.setRowCount(obtenerConteoRegistros.call(first, pageSize));
                    return obtenerRegistros.call(first, pageSize);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public T getRowData(String rowKey) {
                try {
                    final Field[] field = new Field[1];
                    return ((List<T>) lista.getWrappedData()).stream()
                            .filter(x -> {
                                try {
                                    if (x.getClass().getName().indexOf("Map") < 0) {
                                        field[0] = x.getClass().getDeclaredField(nombreLlaveRegistro);
                                        if (!field[0].isAccessible())
                                            field[0].setAccessible(true);
                                        return field[0].get(x).toString().equals(rowKey);
                                    } else {
                                        return ((HashMap) x).get(nombreLlaveRegistro).toString().equals(rowKey);
                                    }
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                    return false;
                                } catch (NoSuchFieldException e) {
                                    e.printStackTrace();
                                    return false;
                                }
                            }).findFirst().get();

                } catch (Exception e) {
                    return null;
                }
            }

            public T getRowKey(T filaseleccionada) {
                return filaseleccionada;
            }
        };
        return lista;
    }

}

