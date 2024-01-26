package ni.gob.inss.siit.businesslogic.serviceImpl.util;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.catalogos.CatalogoDAO;
import ni.gob.inss.barista.model.dao.catalogos.DivisionPoliticaDAO;
import ni.gob.inss.barista.model.dao.seguridad.ParametroDAO;
import ni.gob.inss.barista.model.dao.seguridad.PermisoDAO;
import ni.gob.inss.barista.model.dao.seguridad.RecursoDAO;
import ni.gob.inss.barista.model.dao.seguridad.UsuarioDAO;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.barista.model.entity.seguridad.Permiso;
import ni.gob.inss.barista.model.entity.seguridad.Recurso;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.siit.businesslogic.service.util.UtilService;
import ni.gob.inss.siit.model.dao.util.UtilDAO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Creado por  abenavidez on 03/01/2018.
 * Modificado por jvillanueva 02/01/2018.
 * Modificado por jvillanueva 11/06/2020.
 * Modificado por jvillanueva 16/07/2020.
 * Modificado por jvillanueva 04/02/2021.
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UtilServiceImpl implements UtilService {
    /**
     * **********************************************************************************
     * DEPENDENCIAS
     * **********************************************************************************
     */
    private final CatalogoDAO oCatalogoDAO;
    private final DivisionPoliticaDAO oDivisionPoliticaDAO;
    private final RecursoDAO oRecursoDAO;
    private final PermisoDAO oPermisoDAO;
    private final UsuarioDAO oUsuarioDAO;
    private final UtilDAO UtilDAO;
    private final ParametroDAO oParametroDAO;

    @Autowired
    public UtilServiceImpl(final CatalogoDAO oCatalogoDAO,
                           final DivisionPoliticaDAO oDivisionPoliticaDAO,
                           final RecursoDAO oRecursoDAO,
                           final PermisoDAO oPermisoDAO,
                           final UsuarioDAO oUsuarioDAO,
                           final UtilDAO UtilDAO,
                           final ParametroDAO oParametroDAO
                           ) {
        this.oCatalogoDAO = oCatalogoDAO;
        this.oDivisionPoliticaDAO = oDivisionPoliticaDAO;
        this.oRecursoDAO = oRecursoDAO;
        this.oPermisoDAO = oPermisoDAO;
        this.oUsuarioDAO = oUsuarioDAO;
        this.UtilDAO = UtilDAO;
        this.oParametroDAO = oParametroDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public static String getRemoteIp() {
        String strRemoteIp = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request != null) {
            strRemoteIp = request.getHeader("X-FORWARDED-FOR");
            if (strRemoteIp == null || "".equals(strRemoteIp)) {
                strRemoteIp = request.getRemoteAddr();
            }
        }
        return strRemoteIp;
    }

    public static String getPublicIp() {
        BufferedReader in = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }

    public static boolean isNumeric(final String str) {
        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);

    }

    @Override
    public Parametro obtenerParametroPorCodigo(String codigo) throws EntityNotFoundException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        oSearch.setMaxResults(1);
        List<Parametro> listParametro = oParametroDAO.search(oSearch);
        return listParametro.size() > 0 ? listParametro.get(0) : null;
    }

    @Override
    public List<Catalogo> obtenerCatalogo(String codigo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("nombre");
        return oCatalogoDAO.search(oSearch);
    }

    @Override
    public DivisionPolitica obtenerPadreDivisionPolitica(Integer divisionPoliticaId) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("id", divisionPoliticaId);
        oSearch.addFilterEqual("pasivo", false);
        oSearch.setMaxResults(1);
        List<DivisionPolitica> listDivisionPolitica = oDivisionPoliticaDAO.search(oSearch);
        return listDivisionPolitica.size() > 0 ? listDivisionPolitica.get(0) : null;
    }

    @Override
    public Catalogo obtenerObjCatalogo(String codigo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        oSearch.addFilterEqual("pasivo", false);
        oSearch.setMaxResults(1);
        List<Catalogo> listCatalogo = oCatalogoDAO.search(oSearch);
        return codigo != null ? listCatalogo.size() > 0 ? listCatalogo.get(0) : null : null;
    }

    @Override
    public List<Catalogo> obtenerCatalogoPorTipoCatalogo(String refTipoCatalogo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", refTipoCatalogo);
        oSearch.addFilterEqual("pasivo", false);
        oSearch.addSortAsc("orden");
        return oCatalogoDAO.search(oSearch);

    }

    @Override
    public Recurso obtenerRecursoReporte(String codigo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("codigo", codigo);
        oSearch.addFilterEqual("tipo", "R");
        oSearch.setMaxResults(1);
        return (Recurso) oRecursoDAO.searchUnique(oSearch);
    }

    @Override
    public Boolean tieneRolUsuario(Usuario usuarioId, Integer rolId) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("rolesByRolId.id", rolId);
        oSearch.addFilterEqual("usuariosByUsuarioId.id", usuarioId);
        oSearch.setMaxResults(1);
        List<Permiso> listPermiso = oPermisoDAO.search(oSearch);
        return listPermiso.size() > 0;
    }

    @Override
    public Usuario obtenerUsuarioPorId(int var1) throws EntityNotFoundException {
        Search oSearch = new Search();
        oSearch.addFilterEqual("id", var1);
        oSearch.setMaxResults(1);
        return (Usuario) oUsuarioDAO.searchUnique(oSearch);
    }

    @Override
    public void collapsingORexpanding(TreeNode n, boolean option) {
        if (n.getChildren().size() == 0) {
            n.setSelected(false);
        } else {
            for (TreeNode s : n.getChildren()) {
                collapsingORexpanding(s, option);
            }
            n.setExpanded(option);
            n.setSelected(false);
        }
    }

    @Override
    public List<Parametro> obtenerListaParametro(String[] parametroCodigo) throws BusinessException, DAOException {
        Search oSearch = new Search();
        oSearch.addFilterIn("codigo", parametroCodigo);
        return oParametroDAO.search(oSearch);
    }

    @Override
    public String getRemoteIpService() {
        String strRemoteIp = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request != null) {
            strRemoteIp = request.getHeader("X-FORWARDED-FOR");
            if (strRemoteIp == null || "".equals(strRemoteIp)) {
                strRemoteIp = request.getRemoteAddr();
            }
        }
        return strRemoteIp;
    }

    public List<Integer> cargarListaAnios(Integer minAnio) {
        List<Integer> listaAnios = new ArrayList();

        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR) + 1;

        for (Integer i = minAnio; i <= year; i++) {
            listaAnios.add(i);
        }
        Comparator<Integer> comparador = Collections.reverseOrder();
        listaAnios.sort(comparador);
        return listaAnios;
    }

    @Override
    public Integer[] getUnmatch(Integer[] array1, Integer[] array2) {
        boolean contains = false;
        List<Integer> results = new ArrayList<>();

        for (Integer anArray1 : array1) {
            for (Integer anArray2 : array2) {
                if (anArray1.equals(anArray2)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                results.add(anArray1);
            } else {
                contains = false;
            }
        }

        Integer[] result = new Integer[results.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = results.get(i);
        }
        return result;
    }

    @Override
    public long countWeekDaysMath(LocalDate start, LocalDate stop) {
        long count;
        final DayOfWeek startW = start.getDayOfWeek();
        final DayOfWeek stopW = stop.getDayOfWeek();
        final long days = ChronoUnit.DAYS.between(start, stop);
        final long daysWithoutWeekends = days - 2 * ((days + startW.getValue()) / 7);

        //adjust for starting and ending on a Sunday:
        count = daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + (stopW == DayOfWeek.SUNDAY ? 1 : 0);
        return count;
    }

    @Override
    public List<String> obtenerEncabezadoDesdeTipoSql(String nombreTipo) throws BusinessException, DAOException {
        return UtilDAO.obtenerEncabezadoDesdeTipoSql(nombreTipo);
    }

    @Override
    public void imprimirExcel(List<HashMap<String, Object>> rows, List<String> listaEncabezado, String nombreArchivo) throws Exception {
        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("data");
        int currentRowNumber = 0;
        Row header = sheet.createRow(currentRowNumber);

        // Create a Font for styling header cells
        Font headerFont = workBook.createFont();
        headerFont.setBold(true);

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workBook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        for (int i = 0; i < listaEncabezado.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(listaEncabezado.get(i));
            headerCell.setCellStyle(headerCellStyle);
        }

        for (HashMap<String, Object> row : rows) {
            currentRowNumber++;
            Row sheetRow = sheet.createRow(currentRowNumber);

            for (int j = 0; j < listaEncabezado.size(); j++) {
                Cell cell = sheetRow.createCell(j);
                if (row.containsKey(listaEncabezado.get(j))) {
                    cell.setCellValue(row.get(listaEncabezado.get(j)) != null ? row.get(listaEncabezado.get(j)).toString() : null);
                }
            }
        }
        // Resize all columns to fit the content size
        for (int i = 0; i < listaEncabezado.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + "_" + System.currentTimeMillis() + ".xlsx");
        response.setHeader("charset", "iso-8859-1");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setStatus(HttpServletResponse.SC_OK);
        workBook.write(response.getOutputStream());
        response.getOutputStream().flush();
        facesContext.responseComplete();
        facesContext.renderResponse();
        workBook.close();
        response.getOutputStream().close();
    }

    @Override
    public Boolean inicializarParametrosProcesoSinInspeccion(String codParametro) throws Exception {
        Boolean result = false;
        List<Parametro> listaParametro = this.obtenerListaParametro(new String[]{codParametro});
        for (Parametro parametro : listaParametro) {
            if (parametro.getCodigo().equals(codParametro))
                result = parametro.getValor().toUpperCase().equals("SI");
        }
        return result;
    }

    @Override
    public Date actualizarFechaTiempo(Date date) {
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, (cal2.get(Calendar.HOUR_OF_DAY)));
        cal.set(Calendar.MINUTE, (cal2.get(Calendar.MINUTE)));
        cal.set(Calendar.SECOND, (cal2.get(Calendar.SECOND)));
        cal.set(Calendar.MILLISECOND, (cal2.get(Calendar.MILLISECOND)));
        return cal.getTime();
    }


    @Override
    public Integer obtenerAnioActivo(Integer entidadId, Integer anio) {
        return UtilDAO.obtenerAnioActivo(entidadId, anio);
    }

    @Override
    public LocalDate obtenerUltimaFechaDeMesyAnio(Integer mes, Integer anio) {

        String fechaString = anio.toString() + "/" + (mes < 10 ? ("0" + mes.toString()) : mes.toString()) + "/01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha = LocalDate.parse(fechaString, formatter);

        return fecha.withDayOfMonth(fecha.getMonth().length(fecha.isLeapYear()));

    }

    @Override
    public String obtenerIp() {
        String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
        String IP_REGEXP = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
        Pattern IP_PATTERN = Pattern.compile(IP_REGEXP);
        String ip = "0.0.0.0";

        Enumeration en = null;
        try {
            en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration ee = ni.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ee.nextElement();
                    if (ia.getHostAddress().matches(IP_REGEXP)) {
                        if (!ia.getHostAddress().substring(0, 5).equals("127.0")) {
                            ip = ia.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public String obtenerRefArea(Integer entidadId){
        return UtilDAO.obtenerRefArea(entidadId);
    }

    @Override
    public Boolean obtenerArriendo(Integer entidadId) {
        return UtilDAO.obtenerArriendo(entidadId);
    }

    @Override
    public List<Map<String,Object>> obtenerAutorizacionesPorRoles(Integer usuarioId){
        return UtilDAO.obtenerAutorizacionesPorRoles(usuarioId);
    }

    @Override
    public List<Map<String,Object>> listaDirecciones(){
        return UtilDAO.listaDirecciones();
    }

    @Override
    public List<Map<String,Object>> direccionDelUsuario(int usuarioId){
        return UtilDAO.direccionDelUsuario(usuarioId);
    }

    @Override
    public String numeroLetras(BigDecimal numero, String monedaIso) throws BusinessException, DAOException {
        return UtilDAO.numeroLetras(numero, monedaIso);
    }

    @Override
    public String getAppi(String uri, Object req)  {
        return UtilDAO.getAppi(uri, req);
    }
    @Override
    public String convertirNumeroaLetras(Integer prNumero) throws BusinessException, DAOException {
        return UtilDAO.convertirNumeroaLetras(prNumero);
    }


    @Override
    public List<Catalogo> buscarDiasFeriados(String refTipoCatalogo) {
        Search oSearch = new Search();
        oSearch.addFilterEqual("refTipoCatalogo", refTipoCatalogo);
        oSearch.addFilterEqual("pasivo", false);
        return oCatalogoDAO.search(oSearch);
    }

    @Override
    public List<Map<String, Object>> cargarDiasFeriados(String prBuscar) {
        return UtilDAO.cargarDiasFeriados(prBuscar);

    }

    @Override
    public Map<String, Object> insertAtty( String prnombre, Date prfecha, Integer prduracion, Integer prdelegacion) {
        return UtilDAO.insertAtty( prnombre, prfecha, prduracion, prdelegacion);
    }

    @Override
    public Map<String, Object> EliminarRegistroAtty(Integer prRegistro) {
        return UtilDAO.EliminarRegistroAtty(prRegistro);
    }

    @Override
    public List<Map<String, Object>> diasFeriados(String prBuscar) {
        return UtilDAO.diasFeriados(prBuscar);

    }
}