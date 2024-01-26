package ni.gob.inss.barista.view.bean.backbean.home;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.NavegacionService;
import ni.gob.inss.barista.businesslogic.service.core.SeguridadService;
import ni.gob.inss.barista.businesslogic.service.infraestructura.EntidadService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ModuloService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Modulo;
import ni.gob.inss.barista.model.entity.seguridad.SesionModulo;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.session.SessionBean;
import ni.gob.inss.barista.view.errorHandler.ErrBitNotice;
import ni.gob.inss.barista.view.utils.web.FacesUtils;
import ni.gob.inss.barista.view.utils.web.NavigationResults;
import ni.gob.inss.barista.view.utils.web.WebUtils;
import ni.gob.inss.siit.view.utils.web.MessagesResults;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Creado BackBean para pantalla Index
 * * @author Juan Evangelista Fletes Garcia
 *
 * @since 30/04/2013
 * Modificado por jvillanueva el 07/12/2022
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Named
@Scope("view")
public class IndexBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    List<Map<String, Object>> modulos;
    private Logger logger = Logger.getLogger(IndexBackBean.class);
    private String txtNombreModulo;
    private String selectOrder;
    private String regExpBuscar;
    private Modulo moduloSeleccionado;
    private Integer moduloSeleccionadoId;
    private String paginaActual;
    private String claveActual;
    private String claveNueva;
    private String claveConfirmacion;
    private Boolean moduloUnico;
    //Entidades autorizados para este usuario
    private List<Entidad> listaEntidads;
    private String htmlMenu;
    private String htmlCompleto;
    private String htmlMenus;
    private Integer nivel;
    private Boolean htmlMenuVisible;
    private MenuModel model;
    private String breadcrumMenu;
    // nuevo prueba
    private DashboardModel model2;
    private BarChartModel graficoEmpresasTuristicasDelegacionClasificacionActividad;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */

    private SessionBean sessionBean;
    private NavegacionService oNavegacionService;
    private MenuPrincipalBackBean oMenuPrincipalBackBean;
    private ErrBitNotice oErrBitNotice;
    private EntidadService oEntidadService;
    private ModuloService oModuloService;
    private SeguridadService oSeguridadService;

    @Inject
    @Autowired
    public IndexBackBean(@Qualifier("sessionBean") SessionBean sessionBean,
                         NavegacionService oNavegacionService,
                         MenuPrincipalBackBean oMenuPrincipalBackBean,
                         ErrBitNotice oErrBitNotice,
                         EntidadService oEntidadService,
                         ModuloService oModuloService,
                         SeguridadService oSeguridadService) {
        this.sessionBean = sessionBean;
        this.oNavegacionService = oNavegacionService;
        this.oMenuPrincipalBackBean = oMenuPrincipalBackBean;
        this.oErrBitNotice = oErrBitNotice;
        this.oEntidadService = oEntidadService;
        this.oModuloService = oModuloService;
        this.oSeguridadService = oSeguridadService;
    }

    @PostConstruct
    public void init() throws DAOException {
        ///Codigo anexo
        model2 = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        column1.addWidget("estadisticas");
        column2.addWidget("modulosdelsistema");
        model2.addColumn(column1);
        model2.addColumn(column2);
        ///// fin codigo nuevo
        txtNombreModulo = "";
        selectOrder = "nombre";
        regExpBuscar = RegExpresion.regExpNombre;
        moduloUnico = false;
        model = new DefaultMenuModel();

        //Obteniendo lista de entidades por usuario
        this.listaEntidads = this.oEntidadService.obtenerEntidadesPorUsuario(sessionBean.getUsuarioActual().getId());

        if (sessionBean.getEntidadActual() != null) {
            cargarModulos(sessionBean.getEntidadActual().getId());

            if (sessionBean.getModuloActual() != null) {
                moduloSeleccionado = sessionBean.getModuloActual();
                crearMenu(moduloSeleccionado);
            }
        } else {
            //Si solo tiene acceso a una entidad
            if (this.listaEntidads.size() == 1) {
                sessionBean.setEntidadActual(listaEntidads.get(0));
                cargarModulos(sessionBean.getEntidadActual().getId());

                //si solo tiene acceso a un modulo
                if (this.modulos.size() == 1) {
                    moduloUnico = true;
                    moduloSeleccionadoId = (Integer) modulos.get(0).get("id");
                    seleccionarModulo(true);
                }
            }
        }

        if (sessionBean.getPaginaActual() != null) {
            this.paginaActual = sessionBean.getPaginaActual();
            sessionBean.setPaginaActual(null);
        } else {
            if (getMostrarMenu()) {
                this.paginaActual = NavigationResults.BLANK;
                sessionBean.setBreadcrum(null);
            } else {
                this.paginaActual = NavigationResults.MODULOS;
                sessionBean.setBreadcrum(null);
            }
        }
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void crearMenu(Modulo moduloSeleccionado) {
        List<Map<String, Object>> registros;
        try {
            registros = oNavegacionService.obtenerMenuPorUsuarioModulo(sessionBean.getUsuarioActual().getId(),
                    sessionBean.getEntidadActual().getId(), moduloSeleccionado.getId());
            for (Map<String, Object> i : registros) {
                //Agregando menus primer nivel
                if (i.get("parent_id") == null) {
                    //Si tiene hijos agrego un submenu y busco los hijos del submenu
                    //TODO
                    if (tieneHijos(i, registros)) {
                        crearSubMenu(i, registros, null);
                    }
                    //si no tiene hijos agrego un menu
                    else {
                        DefaultMenuItem individual = new DefaultMenuItem(i.get("nombre"));
                        individual.setAjax(false);
                        individual.setOnclick("goToUrl(" + i.get("id") + ")");
                        individual.setStyle("color:#616161");
                        individual.setUpdate("message messageError");
                        //item.setIcon("ui-icon-home");
                        model.addElement(individual);
                    }
                }
            }
        } catch (
                Exception e) {
            logger.error(e);
            oErrBitNotice.setErrorData(this.getClass().getSimpleName() + "#cargarModelo",
                    "/views/index.xhtml", sessionBean.getUsuarioActual(), e);
            oErrBitNotice.sendError();
        }
    }

    private void crearSubMenu(Map<String, Object> menu, List<Map<String, Object>> listaMenus, DefaultSubMenu subMenu) {
        DefaultSubMenu subMenuN = new DefaultSubMenu(menu.get("nombre").toString());
        //subMenuN1.setIcon("ui-icon-home");
        if (subMenu == null) {
            model.addElement(subMenuN);
        } else {
            subMenu.addElement(subMenuN);
        }
        buscarHijos(menu, listaMenus, subMenuN);
    }

    private void buscarHijos(Map<String, Object> menu, List<Map<String, Object>> listaMenus, DefaultSubMenu subMenuN) {
        for (Map<String, Object> i : listaMenus) {
            if (i.get("parent_id") != null) {
                //si es un hijo
                if ((i.get("parent_id").toString()).equals(menu.get("id").toString())) {
                    //comprobar si tiene hijos
                    if (tieneHijos(i, listaMenus)) {
                        crearSubMenu(i, listaMenus, subMenuN);
                    } else {
                        DefaultMenuItem individual = new DefaultMenuItem(i.get("nombre"));
                        individual.setAjax(false);
                        individual.setOnclick("goToUrl(" + i.get("id") + ")");
                        //item.setIcon("ui-icon-home");
                        subMenuN.addElement(individual);
                    }
                }
            }
        }
    }

    private Boolean tieneHijos(Map<String, Object> menu, List<Map<String, Object>> listaMenus) {
        Boolean result = false;
        for (Map<String, Object> i : listaMenus) {
            if (i.get("parent_id") != null) {
                if ((i.get("parent_id")).equals(menu.get("id"))) {
                    result = true;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }


    public void buscarModulos() {
        cargarModulos(sessionBean.getEntidadActual().getId());
    }

    public void selecionarEntidadEvent(ValueChangeEvent event) {
        cargarModulos((Integer) event.getNewValue());
    }

    public void selecionarOrdenEvent(ValueChangeEvent event) {
        selectOrder = (String) event.getNewValue();
        cargarModulos(sessionBean.getEntidadActual().getId());
    }

    private void cargarModulos(Integer oEntidad) {
        if (oEntidad == null) {
            modulos = null;
        } else {
            try {
                modulos = (List<Map<String, Object>>) oNavegacionService.obtenerModulosPorUsuario(sessionBean.getUsuarioActual().getId(), oEntidad, txtNombreModulo, selectOrder);
                sessionBean.setEntidadActual(oEntidadService.obtenerEntidad(oEntidad));
            } catch (Exception e) {
                logger.error(e);
                oErrBitNotice.setErrorData(this.getClass().getSimpleName() + "#cargarModulos(Integer entidadId)",
                        "/views/home/modulos.xhtml", sessionBean.getUsuarioActual(), e);
                oErrBitNotice.sendError();
            }
        }
    }

    public void seleccionarModulo(boolean estado) throws DAOException {
        Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (!estado) {
            moduloSeleccionadoId = Integer.parseInt(requestParamMap.get("id"));
        }
        if (moduloSeleccionadoId != null) {
            moduloSeleccionado = oModuloService.obtenerModuloPorId(moduloSeleccionadoId);
            sessionBean.setModuloActual(moduloSeleccionado);
            //Guardando sesion modulo
            SesionModulo oSesionModulo = new SesionModulo();
            oSesionModulo.setUsuarioId(sessionBean.getUsuarioActual().getId());
            oSesionModulo.setFechaUltimaSesion(WebUtils.nowTimeStamp());
            oSesionModulo.setModulosByModuloId(moduloSeleccionado);
            oSesionModulo.setEntidad(sessionBean.getEntidadActual());
            oNavegacionService.guardarSesionModulo(oSesionModulo);
            this.paginaActual = NavigationResults.BLANK;
            if (moduloSeleccionado.getId() == 47) {
                this.paginaActual = "/web/views/contabilidadauxiliar/listaPagoOnline.xhtml";
            }
            if (moduloSeleccionado.getId() == 54) {
                this.paginaActual = "/web/views/miExpediente/expedienteETView.xhtml";
            }
            if (moduloSeleccionado.getId() == 63) {
                this.paginaActual = "/web/views/registro/listaInspeccion.xhtml";
            }

        } else {
            this.paginaActual = NavigationResults.MODULOS;
        }
        crearMenu(moduloSeleccionado);
        PrimeFaces.current().ajax().update("menuform");
    }

    public void seleccionarMenuModulo() {
        sessionBean.setModuloActual(null);
        sessionBean.setBreadcrum(null);
        moduloSeleccionado = null;
        sessionBean.setPaginaActual(NavigationResults.MODULOS);
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

    public void cambiarClave() {
        try {
            oSeguridadService.cambiarClave(sessionBean.getUsuarioActual(), claveActual, claveNueva, claveConfirmacion, false);
            claveActual = null;
            claveNueva = null;
            claveConfirmacion = null;
            FacesUtils.addInfoMessage("Clave cambiada correctamente");
            PrimeFaces.current().ajax().update("formularioCambiarClave");
            PrimeFaces.current().executeScript("PF('wvDlgCambiarClave').hide()");
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
            claveActual = null;
            claveNueva = null;
            claveConfirmacion = null;
        }
    }

    public void cerrarModalContraseña() {
        claveActual = null;
        claveNueva = null;
        claveConfirmacion = null;
        PrimeFaces.current().ajax().update("formularioCambiarClave");
        PrimeFaces.current().executeScript("PF('wvDlgCambiarClave').hide()");
    }

    public Integer getSessionMaxInactiveInterval() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMaxInactiveInterval();
    }



    private String generarColorGrafico() {
        Random rand = new Random();
        int value = rand.nextInt(38);
        String[] color = {"d50000", "c51162", "aa00ff", "6200ea", "304ffe", "2962ff", "0091ea", "00b8d4", "00bfa5", "00c853", "64dd17", "aeea00", "ffd600", "ffab00", "ff6d00", "dd2c00", "3e2723", "212121", "263238", "ff1744", "f50057", "d500f9", "651fff", "3d5afe", "2979ff", "00b0ff", "00e5ff", "1de9b6", "00e676", "76ff03", "c6ff00", "ffea00", "ffc400", "ff9100", "ff3d00", "4e342e", "424242", "37474f"};
        switch (value) {
            case 1:
                return color[1];
            case 2:
                return color[2];
            case 3:
                return color[3];
            case 4:
                return color[4];
            case 5:
                return color[5];
            case 6:
                return color[6];
            case 7:
                return color[7];
            case 8:
                return color[8];
            case 9:
                return color[9];
            case 10:
                return color[10];
            case 11:
                return color[11];
            case 12:
                return color[12];
            case 13:
                return color[13];
            case 14:
                return color[14];
            case 15:
                return color[15];
            case 16:
                return color[16];
            case 17:
                return color[17];
            case 18:
                return color[18];
            case 19:
                return color[19];
            case 20:
                return color[20];
            case 21:
                return color[21];
            case 22:
                return color[22];
            case 23:
                return color[23];
            case 24:
                return color[24];
            case 25:
                return color[25];
            case 26:
                return color[26];
            case 27:
                return color[27];
            case 28:
                return color[28];
            case 29:
                return color[29];
            case 30:
                return color[30];
            case 31:
                return color[31];
            case 32:
                return color[32];
            case 33:
                return color[33];
            case 34:
                return color[34];
            case 35:
                return color[35];
            case 36:
                return color[36];
            case 37:
                return color[37];
            default:
                return color[0];
        }
    }

    public Boolean getMostrarMenu() {
        boolean mostrarMenu = moduloSeleccionado != null;
        return mostrarMenu;
    }

    public String getEntidadToken() {
        return sessionBean.getEntidadActual() != null ? sessionBean.getEntidadActual().getId().toString() : "";
    }

    public String getUsuarioToken() {
        return sessionBean.getUsuarioActual().getId().toString();
    }

    public String getRegExpClave() {
        return RegExpresion.regExpAlphaNumeric;
    }
}