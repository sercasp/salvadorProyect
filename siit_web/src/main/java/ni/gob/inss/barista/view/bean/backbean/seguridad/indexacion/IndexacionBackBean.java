package ni.gob.inss.barista.view.bean.backbean.seguridad.indexacion;

import lombok.Data;
import ni.gob.inss.barista.businesslogic.service.seguridad.IndexacionService;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.security.SystemSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <b>SPA</b></br>
 * <b>Copyright (c) 2017 MI FAMILIA.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * BackBean para pantalla Indexación, en donde se generan los índices de las diferentes entidades.</br>
 *
 * @author HERNALDO JOSE MAYORGA HERNANDEZ, ENOC EZEQUIEL JIMENEZ MIRANDA
 * @version 1.0, 03/03/2017
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Data
@Named
@Scope("view")
public class IndexacionBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;
    final IndexacionService oIndexacionService;
    private String somEntidad;
    private List<Map<String, Object>> lstEntidades;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    @Inject
    @Autowired
    public IndexacionBackBean(IndexacionService oIndexacionService) {
        this.oIndexacionService = oIndexacionService;
    }

    @PostConstruct
    public void init() throws SystemSecurityException {
        lstEntidades = oIndexacionService.cargarListaEntidades();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void indexarEntidad() {
        try {
            mostrarMensajeInfo("Libre del barista");
            oIndexacionService.indexarEntidad((Class) lstEntidades.stream().filter(a -> a.get("nombre").toString().equals(somEntidad)).collect(Collectors.toList()).get(0).get("id"));
            mostrarMensajeSuccess("Entidad indexada con éxito");
        } catch (Exception e) {
            mostrarMensajeException(this.getClass().getName(), "indexarEntidad()", "Error al indexar: ", e);
        }
    }
}