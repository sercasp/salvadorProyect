package ni.gob.inss.barista.view.bean.backbean.notificacion.usuario;

import lombok.Data;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.bean.session.SessionList;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para Establecimientos</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 23/07/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
@Data
@Named
@Scope("view")
public class UsuarioConectadoBackBean extends BaseBackBean implements Serializable {
    /**
     * ******************************************************************************
     * VARIABLES
     * ******************************************************************************
     **/
    private static final long serialVersionUID = 1L;

    List<Map<String, Object>> listaUsuarios;

    List<Map<String, Object>> listaUsuariosFiltrados;

    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */

    @PostConstruct
    public void init() {
        cargarUsuarios();
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public void cargarUsuarios() {
        //Set<Map.Entry<String, Integer>> productSet = UsuarioNotificacion.getListaUsuario().entrySet();
        listaUsuarios = SessionList.getUserList();
    }
}