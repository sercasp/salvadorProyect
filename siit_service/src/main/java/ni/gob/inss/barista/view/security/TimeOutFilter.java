package ni.gob.inss.barista.view.security;

import ni.gob.inss.barista.businesslogic.service.util.ServiceUtil;
import ni.gob.inss.barista.view.bean.session.SessionBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Filtro para expirar la sesión en peticiones push</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 10/09/2014
 * @since 1.0 *
 * Modificado por jvillanueva 23/10/2023
 */


@WebFilter(servletNames = "Push Servlet", asyncSupported = true)
public class TimeOutFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        SessionBean oSessionBean = obtenerSession((HttpServletRequest) req);

        if (getLoggedIn((HttpServletRequest) req)) {
            Long segundos = ServiceUtil.diffSeconds(new Date(), oSessionBean.getUltimaActividad());

            if (segundos >= session.getMaxInactiveInterval()) {
                session.invalidate();
            }
        }
        chain.doFilter(req, res);
    }

    private boolean getLoggedIn(HttpServletRequest req) {
        SessionBean oSessionBean = (SessionBean) req.getSession().getAttribute("sessionBean");
        return (oSessionBean != null && oSessionBean.getLoggedIn());
    }

    private SessionBean obtenerSession(HttpServletRequest req) {
        return ((SessionBean) req.getSession().getAttribute("sessionBean"));
    }
}