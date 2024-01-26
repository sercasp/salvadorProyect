package ni.gob.inss.barista.view.bean.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <b>LINUS</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Descripción</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 20/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */

@WebListener
public class SessionListener implements HttpSessionListener {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ni.gob.inss.barista.view.bean.session.SessionBean oSessionBean = (SessionBean) httpSessionEvent.getSession().getAttribute("sessionBean");
        if (oSessionBean != null) {
            if (oSessionBean.getUsuarioActual() != null) {
                SessionList.remove(oSessionBean.getUsuarioActual().getUsername());
            }
        }
    }
}
