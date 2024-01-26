package ni.gob.inss.siit.businesslogic.service.util;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import org.primefaces.model.TreeNode;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creado por  abenavidez el 03/01/2018.
 * modificado por mbarrios el 31/10/2023
 */
public interface UtilService {

    String PATTERN_EMAIL = ("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    Parametro obtenerParametroPorCodigo(String codigo) throws EntityNotFoundException;


    List<Catalogo> obtenerCatalogo(String codigo);

    DivisionPolitica obtenerPadreDivisionPolitica(Integer divisionPoliticaId);

    Catalogo obtenerObjCatalogo(String codigo);

    List<Catalogo> obtenerCatalogoPorTipoCatalogo(String refTipoCatalogo);

    Recurso obtenerRecursoReporte(String codigo);

    Boolean tieneRolUsuario(Usuario usuarioId, Integer rolId);

    Usuario obtenerUsuarioPorId(int var1) throws EntityNotFoundException;

    void collapsingORexpanding(TreeNode n, boolean option);

    List<Parametro> obtenerListaParametro(String[] parametroCodigo) throws BusinessException, DAOException;

    List<Integer> cargarListaAnios(Integer minAnio);

    String getRemoteIpService();

    Integer[] getUnmatch(Integer[] array1, Integer[] array2);

    long countWeekDaysMath(LocalDate start, LocalDate stop);

    List<String> obtenerEncabezadoDesdeTipoSql(String nombreTipo) throws BusinessException, DAOException;

    void imprimirExcel(List<HashMap<String, Object>> rows, List<String> listaEncabezado, String nombreArchivo) throws Exception;

    Boolean inicializarParametrosProcesoSinInspeccion(String parametro) throws Exception;

    Date actualizarFechaTiempo(Date date);

    Integer obtenerAnioActivo(Integer entidadId, Integer anio);

    LocalDate obtenerUltimaFechaDeMesyAnio(Integer mes, Integer anio);

    String obtenerIp();

    String obtenerRefArea(Integer entidadId);

    Boolean obtenerArriendo(Integer entidadId);

    List<Map<String,Object>> obtenerAutorizacionesPorRoles(Integer usuarioId);

    List<Map<String,Object>> listaDirecciones();

    List<Map<String,Object>> direccionDelUsuario(int usuarioId);

    String numeroLetras(BigDecimal numero, String monedaIso) throws BusinessException, DAOException;

    String getAppi(String uri, Object req);
    String convertirNumeroaLetras(Integer prNumero) throws BusinessException, DAOException;


    List<Catalogo> buscarDiasFeriados(String refTipoCatalogo);

    List<Map<String, Object>> cargarDiasFeriados(String buscar);

    List<Map<String, Object>> diasFeriados(String buscar);

    Map<String, Object> insertAtty(String prnombre, Date prfecha, Integer prduracion, Integer prdelegacion);

    Map<String, Object> EliminarRegistroAtty(Integer prRegistro);

}
