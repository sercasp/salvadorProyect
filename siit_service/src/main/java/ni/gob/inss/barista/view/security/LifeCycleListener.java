package ni.gob.inss.barista.view.security;

import ni.gob.inss.barista.view.utils.web.NavigationResults;
import org.apache.log4j.Logger;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Interfaz de acceso a datos para LifeCycleListener</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 5/24/2014
 * @since 1.0 *
 * Modificado por jvillanueva 23/10/2023
 */
public class LifeCycleListener implements PhaseListener {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LifeCycleListener.class);

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext facesCtx = event.getFacesContext();
        ExternalContext extCtx = facesCtx.getExternalContext();
        HttpSession session = (HttpSession) extCtx.getSession(false);

        boolean newSession = (session == null) || (session.isNew());
        boolean postback = !extCtx.getRequestParameterMap().isEmpty();
        boolean timedout = postback && newSession;

        if (timedout) {
            HttpServletRequest request = (HttpServletRequest) extCtx.getRequest();
            String loginURL = request.getContextPath() + NavigationResults.LOGIN_NO_REDIRECT;
            String originalURL = (String) request.getAttribute("javax.servlet.forward.request_uri");

            if (facesCtx.getPartialViewContext().isAjaxRequest()
                    && originalURL != null
                    && loginURL.equals(request.getRequestURI())
                    && !loginURL.equals(originalURL)
                    && !loginURL.equals(request.getContextPath() + "/")) {
                try {
                    facesCtx.getExternalContext().redirect(loginURL);
                } catch (IOException e) {
                    LOGGER.error(e);
                    throw new FacesException(e);
                }
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }
}