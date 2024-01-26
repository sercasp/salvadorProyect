package ni.gob.inss.barista.view.bean.backbean.comps;

import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrail;
import ni.gob.inss.barista.model.entity.auditoria.AuditTrailDetail;
import org.primefaces.event.ToggleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BackBean para componente compuesto de auditoría</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 6/11/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Named
@Scope("request")
public class DialogAuditoriaBackBean {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    final AuditoriaService oAuditoriaService;
    private List<AuditTrailDetail> detalleAuditoria;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public DialogAuditoriaBackBean(AuditoriaService oAuditoriaService) {
        this.oAuditoriaService = oAuditoriaService;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void obtenerDetalleAuditoria(ToggleEvent event) {
        AuditTrail oAuditTrail = (AuditTrail) event.getData();
        detalleAuditoria = oAuditoriaService.obtenerDetalleAuditoria(oAuditTrail);
    }

    public List<AuditTrailDetail> getDetalleAuditoria() {
        return detalleAuditoria;
    }
}