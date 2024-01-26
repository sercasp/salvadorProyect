package ni.gob.inss.barista.view.bean.session;

import ni.gob.inss.barista.businesslogic.service.infraestructura.EntidadService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Converter para entidad</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 18/06/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@Named
public class EntidadConverter implements Converter{

    @Autowired
    EntidadService oEntidadService;

    private static Logger logger = Logger.getLogger(EntidadConverter.class);

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String entidadId) {
        Entidad oEntidad = null;
        try {
            oEntidad =  oEntidadService.obtenerEntidad(Integer.parseInt(entidadId));
        } catch (EntityNotFoundException e) {
            logger.error(e);
        }
        return oEntidad;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object oEntidad) {
        if(oEntidad!=null)
            return  ((Entidad)oEntidad).getId().toString();
        else
            return "";
    }
}
