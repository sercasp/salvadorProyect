package ni.gob.inss.siit.model.dao.util;


import ni.gob.inss.barista.model.dao.BaseGenericDAO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex Benavidez on 09/09/2019.
 * Modificado  by scastillo 12/12/2022
 */
public interface UtilDAO extends BaseGenericDAO<Object, Integer> {

    List<String> obtenerEncabezadoDesdeTipoSql(String nombreTipo);

    Integer obtenerAnioActivo(Integer entidadId, Integer anio);

    String obtenerIp();

    String separarCerosDeNumeros();

    String separarCerosDeNumerosComprobante(Integer aumento, int anio);

    String obtenerRefArea(Integer entidadId);

    Boolean obtenerArriendo(Integer entidadId);

    List<Map<String,Object>> obtenerAutorizacionesPorRoles(Integer usuarioId);

    List<Map<String,Object>> listaDirecciones();

    List<Map<String,Object>> direccionDelUsuario(int usuarioId);

    String numeroLetras(BigDecimal numero, String monedaIso);

    String getAppi(String uri, Object req);
    String convertirNumeroaLetras(Integer prNumero);

    List<Map<String, Object>> cargarDiasFeriados(String prBuscar);

    List<Map<String, Object>> diasFeriados(String prBuscar);

    public Map<String, Object> insertAtty(String prnombre, Date prfecha, Integer prduracion, Integer prdelegacion);

    public Map<String, Object> EliminarRegistroAtty(Integer prRegistro);
}
