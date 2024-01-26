package org.primefaces.sapphire.component;

import org.primefaces.component.api.Widget;
import org.primefaces.component.menu.AbstractMenu;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.*;

@ListenerFor(sourceClass = SapphireMenu.class, systemEventClass = PostAddToViewEvent.class)
public class SapphireMenu extends AbstractMenu implements Widget,ComponentSystemEventListener {

    public static final String COMPONENT_TYPE = "org.primefaces.component.SapphireMenu";
    public static final String COMPONENT_FAMILY = "org.primefaces.component";
    private static final String DEFAULT_RENDERER = "org.primefaces.component.SapphireMenuRenderer";
    private static final String[] LEGACY_RESOURCES = new String[]{"primefaces.css","jquery/jquery.js","jquery/jquery-plugins.js","primefaces.js"};
    private static final String[] MODERN_RESOURCES = new String[]{"components.css","jquery/jquery.js","jquery/jquery-plugins.js","core.js"};
    
    protected enum PropertyKeys {

        widgetVar, model, style, styleClass, closeDelay;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    public SapphireMenu() {
        setRendererType(DEFAULT_RENDERER);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getWidgetVar() {
        return (String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }

    public void setWidgetVar(String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }

    public org.primefaces.model.menu.MenuModel getModel() {
        return (org.primefaces.model.menu.MenuModel) getStateHelper().eval(PropertyKeys.model, null);
    }

    public void setModel(org.primefaces.model.menu.MenuModel _model) {
        getStateHelper().put(PropertyKeys.model, _model);
    }

    public String getStyle() {
        return (String) getStateHelper().eval(PropertyKeys.style, null);
    }

    public void setStyle(String _style) {
        getStateHelper().put(PropertyKeys.style, _style);
    }

    public String getStyleClass() {
        return (String) getStateHelper().eval(PropertyKeys.styleClass, null);
    }

    public void setStyleClass(String _styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, _styleClass);
    }
    
    public int getCloseDelay() {
		return (Integer) getStateHelper().eval(PropertyKeys.closeDelay, 250);
	}
	public void setCloseDelay(int _closeDelay) {
		getStateHelper().put(PropertyKeys.closeDelay, _closeDelay);
	}

    public String resolveWidgetVar() {
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");

        if (userWidgetVar != null) {
            return userWidgetVar;
        } else {
            return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
        }
    }
    
    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        if(event instanceof PostAddToViewEvent) {
            FacesContext context = getFacesContext();
            UIViewRoot root = context.getViewRoot();
            
            boolean isPrimeConfig;
            try {
                isPrimeConfig = Class.forName("org.primefaces.config.PrimeConfiguration") != null;
            } catch (ClassNotFoundException e) {
                isPrimeConfig = false;
            }

            String[] resources = (isPrimeConfig) ? MODERN_RESOURCES : LEGACY_RESOURCES;

            for(String res : resources) {
                UIComponent component = context.getApplication().createComponent(UIOutput.COMPONENT_TYPE);
                if(res.endsWith("css"))
                    component.setRendererType("javax.faces.resource.Stylesheet");
                else if(res.endsWith("js"))
                    component.setRendererType("javax.faces.resource.Script");

                component.getAttributes().put("library", "primefaces");
                component.getAttributes().put("name", res);

                root.addComponentResource(context, component);
            }
        }
    }
}
