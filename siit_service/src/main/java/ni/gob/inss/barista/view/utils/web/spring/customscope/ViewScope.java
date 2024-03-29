package ni.gob.inss.barista.view.utils.web.spring.customscope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Implements the JSF View Scope for use by Spring. This class is registered as a Spring bean with the CustomScopeConfigurer.
 * Modificado por jvillanueva el 08/08/2023
 */
public class ViewScope implements Scope {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if (FacesContext.getCurrentInstance().getViewRoot() != null) {
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            if (viewMap.containsKey(name)) {
                return viewMap.get(name);
            } else {
                Object object = objectFactory.getObject();
                viewMap.put(name, object);
                return object;
            }
        } else {
            return null;
        }
    }

    public Object remove(String name) {
        if (FacesContext.getCurrentInstance().getViewRoot() != null) {
            return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
        } else {
            return null;
        }
    }

    public void registerDestructionCallback(String name, Runnable callback) {
        // Do nothing
    }

    public Object resolveContextualObject(String key) {
        return null;
    }

    public String getConversationId() {
        return null;
    }
}