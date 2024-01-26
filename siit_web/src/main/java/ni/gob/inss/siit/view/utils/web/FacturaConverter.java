package ni.gob.inss.siit.view.utils.web;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.HashMap;
import java.util.List;

@FacesConverter("FacturaConverter")
public class FacturaConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0 && !("-1".equals(value))) {
            UISelectItems items = (UISelectItems) uiComponent.getChildren().get(1);
            List<HashMap> facturas = (List<HashMap>) items.getValue();
            for (HashMap item : facturas) {
                if (item.get("id").equals(Integer.valueOf(value)))
                    return item;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value != null && !("-1".equals(value))) {
            return String.valueOf(((HashMap) value).get("id"));
        } else {
            return null;
        }
    }
}
