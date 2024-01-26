package ni.gob.inss.barista.view.bean.backbean.personal.miembro;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.businesslogic.service.personal.MiembroService;
import ni.gob.inss.barista.model.entity.personal.Miembro;
import ni.gob.inss.barista.model.utils.RegExpresion;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.validation.EntityValidationField;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BackBean para pantalla Miembroes</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/28/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class ListaMiembroBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    AuditoriaService oDialogAuditoriaService;
    UploadedFile file;
    private Miembro selectedRow;
    private boolean nuevoRegistro;
    private Integer hfId;
    private String txtIdentificador;
    private String txtPrimerNombre;
    private String txtSegundoNombre;
    private String txtPrimerApellido;
    private String txtSegundoApellido;
    private String cmbSexo;
    private String txtBuscar;
    private boolean rbPasivo;
    private List<Miembro> listaMiembros;
    private String regExpNombresyApellidos;
    private String regExpIdentificador;
    private boolean btnElminiarVisible;
    private boolean btnGuardarVisible;
    private boolean btnAuditoriaVisible;
    private List listaAuditoria;
    private MiembroService oMiembroService;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public ListaMiembroBackBean(MiembroService oMiembroService, AuditoriaService oDialogAuditoriaService) {
        this.oMiembroService = oMiembroService;
        this.oDialogAuditoriaService = oDialogAuditoriaService;
    }

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
        cargarValidaciones();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    private void cargarValidaciones() {
        try {
            regExpNombresyApellidos = RegExpresion.regExpSoloLetras;
            regExpIdentificador = EntityValidationField.getRegExpresionPattern(Miembro.class, "Identificador");
        } catch (NoSuchMethodException e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarValidaciones()", MessagesResults.ERROR_CARGAR_VALIDACION, e);
        }
    }

    public void cargarAuditoria() {
        mostrarMensajeInfo("Entidad no habilitada para auditoría");
//        try {
//            listaAuditoria = new ArrayList<>();
//
//            listaAuditoria.add(oDialogAuditoriaService.obtenerAuditoria(Miembro.class, hfId));
//        }
//        catch (Exception e){
//            mostrarMensajeError(this.getClass().getSimpleName(),"cargarAuditoria()",MessagesResults.ERROR_OBTENER_AUDITORIA, e);
//        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();
        System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String dummyAction() {
        System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());
        return "";
    }

    public void cargarLista() {
        try {
            this.listaMiembros = oMiembroService.buscar(txtBuscar);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargarLista()", MessagesResults.ERROR_OBTENER_LISTA, e);
        }
    }

    public void limpiar() {
        btnElminiarVisible = false;
        btnGuardarVisible = isServidorReplicacion();
        btnAuditoriaVisible = false;
        txtBuscar = "";
        hfId = null;
        txtIdentificador = null;
        txtPrimerNombre = null;
        txtSegundoNombre = null;
        txtPrimerApellido = null;
        txtSegundoApellido = null;
        cmbSexo = null;
        rbPasivo = false;
        nuevoRegistro = true;
    }

    public void editar() {
        if (selectedRow != null) {
            cargar(selectedRow.getId());
        }
    }

    private void cargar(Integer id) {
        try {
            Miembro oMiembro = oMiembroService.obtenerMiembroPorId(id);
            hfId = oMiembro.getId();
            txtIdentificador = oMiembro.getIdentificador();
            txtPrimerNombre = oMiembro.getPrimerNombre();
            txtSegundoNombre = oMiembro.getSegundoNombre();
            txtPrimerApellido = oMiembro.getPrimerApellido();
            txtSegundoApellido = oMiembro.getSegundoApellido();
            cmbSexo = oMiembro.getSexo();
            rbPasivo = oMiembro.getPasivo();
            btnAuditoriaVisible = isVerInfoAuditoria();
            btnElminiarVisible = isServidorReplicacion();
            nuevoRegistro = false;
            this.cargarLista();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').show()");
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "cargar(Integer id)", MessagesResults.ERROR_OBTENER, e);
        }
    }

    public void eliminar() {
        try {
            Miembro oMiembro = oMiembroService.obtenerMiembroPorId(hfId);
            oMiembroService.eliminar(oMiembro);
            limpiar();
            cargarLista();
            PrimeFaces.current().executeScript("PF('wvDlgFormDetalle').hide()");
            mostrarMensajeInfo(MessagesResults.EXITO_ELIMINAR);
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "eliminar()", MessagesResults.ERROR_ELIMINAR, e);
        }
    }

    public void guardar() {
        try {
            Miembro oMiembro;
            if (nuevoRegistro) {
                oMiembro = new Miembro();
                oMiembro.setIdentificador(txtIdentificador);
                oMiembro.setPrimerNombre(txtPrimerNombre);
                oMiembro.setSegundoNombre(txtSegundoNombre);
                oMiembro.setPrimerApellido(txtPrimerApellido);
                oMiembro.setSegundoApellido(txtSegundoApellido);
                oMiembro.setSexo(cmbSexo);
                oMiembro.setCreadoPor(this.getUsuarioActual().getId());
                oMiembro.setCreadoEl(this.getTimeNow());
                oMiembro.setCreadoEnIp(this.getRemoteIp());
                oMiembro.setPasivo(false);
                oMiembroService.agregar(oMiembro);
                mostrarMensajeSuccess(MessagesResults.EXITO_GUARDAR);
            } else {
                oMiembro = oMiembroService.obtenerMiembroPorId(hfId);
                oMiembro.setIdentificador(txtIdentificador);
                oMiembro.setPrimerNombre(txtPrimerNombre);
                oMiembro.setSegundoNombre(txtSegundoNombre);
                oMiembro.setPrimerApellido(txtPrimerApellido);
                oMiembro.setSegundoApellido(txtSegundoApellido);
                oMiembro.setSexo(cmbSexo);
                oMiembro.setPasivo(rbPasivo);
                oMiembro.setModificadoPor(this.getUsuarioActual().getId());
                oMiembro.setModificadoEl(this.getTimeNow());
                oMiembro.setModificadoEnIp(this.getRemoteIp());
                oMiembroService.actualizar(oMiembro);
                mostrarMensajeSuccess(MessagesResults.EXITO_MODIFICAR);
            }
            this.cargar(oMiembro.getId());
        } catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(), "guardar()", MessagesResults.ERROR_GUARDAR, e);
        }
    }
}