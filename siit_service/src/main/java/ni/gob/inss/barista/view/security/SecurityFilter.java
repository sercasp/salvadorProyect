package ni.gob.inss.barista.view.security;

import ni.gob.inss.barista.view.bean.session.SessionBean;
import ni.gob.inss.barista.view.bean.session.SessionList;
import ni.gob.inss.barista.view.utils.web.NavigationResults;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * Filtro de seguridad</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 * <p>
 * Modificado por jvillanueva 23/10/2023
 */
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String contextPath = ((HttpServletRequest) req).getContextPath();
        String requestUri = ((HttpServletRequest) req).getRequestURI();

        if (getLoggedIn((HttpServletRequest) req)) {
            SessionBean oSessionBean = obtenerSession((HttpServletRequest) req);
            String userName = oSessionBean.getUsuarioActual().getUsername();
            String referencia = oSessionBean.getUsuarioActual().getReferencia();
            String telefono = oSessionBean.getUsuarioActual().getTelefono();
            String nombreCompleto = oSessionBean.getUsuarioActual().obtenerNombreCompleto();
            HttpSession httpSession = ((HttpServletRequest) req).getSession();
            String sessionId = httpSession.getId();
            String EntityToken = ((HttpServletRequest) req).getHeader("EntityToken");
            boolean recargarPagina = false;
            oSessionBean.setUltimaActividad(new Date());

            //Comprobando si tiene más de una sesión activa
            if (SessionList.contains(userName) && SessionList.getSessionId(userName) != sessionId) {
                ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.INVALIDATE_SESSION).forward(req, res);
            } else {
                if (!SessionList.contains(userName)) {
                    SessionList.addUser(userName, referencia, telefono, nombreCompleto, ((HttpServletRequest) req).getSession());
                }

                //Comprobando si la entidad actual a cambiado
                if (oSessionBean.getEntidadActual() != null) {
                    if (EntityToken != null && !EntityToken.equals(oSessionBean.getEntidadActual().getId().toString())) {
                        recargarPagina = true;
                    }
                }

                //Comprobando si la clave es temporal
                if (oSessionBean.getUsuarioActual().getPasswordTemporal()) {
                    ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.CHANGE_PASSWORD_NO_REDIRECT).forward(req, res);
                } else if (recargarPagina) {

                    oSessionBean.setModuloActual(null);
                    StringBuilder sb = new StringBuilder();
                    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><changes>");
                    sb.append("<eval>mostrarMensajeEntidad();</eval>");
                    sb.append("</changes></partial-response>");

                    ((HttpServletResponse) res).setHeader("Cache-Control", "no-cache");
                    ((HttpServletResponse) res).setCharacterEncoding("UTF-8");
                    ((HttpServletResponse) res).setContentType("text/xml");
                    PrintWriter pw = ((HttpServletResponse) res).getWriter();
                    pw.println(sb.toString());
                    pw.flush();
                } else if (oSessionBean.getSoloMovil()) {
                    ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.HOME_MOBILE).forward(req, res);
                } else if (oSessionBean.getSoloWeb()) {
//                    PrintWriter out = res.getWriter();
//                    CharResponseWrapper responseWrapper = new CharResponseWrapper(
//                            (HttpServletResponse) res);
//                    chain.doFilter(req, responseWrapper);
//                    String servletResponse = new String(responseWrapper.toString());
//                    Integer tiempoSesion = ((HttpServletRequest) req).getSession().getMaxInactiveInterval();
//                    if (servletResponse.indexOf("</changes></partial-response>") != -1) {
//                        out.write(servletResponse.replace("</changes></partial-response>",
//                                "<eval>reiniciarContadorSesion('" + tiempoSesion + "');</eval></changes></partial-response>"));
//                    } else if (servletResponse.indexOf("/barista/login.html") == -1) {
//                        out.write(servletResponse.replace("</body>", "<script>reiniciarContadorSesion('" + tiempoSesion + "');</script></body>"));
//                    } else {
//                        out.write(servletResponse);
//                    }
                    ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.HOME).forward(req, res);
                }
                //Menú de selección de sitio
                else if (oSessionBean.getAmbosSitios()) {
                    if (requestUri.equals(contextPath + NavigationResults.HOME_NO_REDIRECT)) {
                        ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.HOME).forward(req, res);
                    } else if (requestUri.equals(contextPath + NavigationResults.HOME_MOBILE_NO_REDIRECT)) {
                        ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.HOME_MOBILE).forward(req, res);
                    } else {
                        ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.MENU_APP).forward(req, res);
                    }
                } else {
                    ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.HOME).forward(req, res);
                }
            }

        } else {

            if (requestUri.split("/").length == 2) {
                ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.LOGIN).forward(req, res);
            } else {
                if (requestUri.split("/")[2].equals("login.html")) {
                    ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.LOGIN).forward(req, res);
                } else {
                    if (req.getParameterMap().size() == 0) {
                        ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.SESSION_EXPIRED).forward(req, res);
                    } else if (req.getParameterMap().get("javax.faces.partial.ajax") != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><changes>");
                        sb.append("<eval>mostrarMensajeSesion();</eval>");
                        sb.append("</changes></partial-response>");
                        ((HttpServletResponse) res).setHeader("Cache-Control", "no-cache");
                        ((HttpServletResponse) res).setCharacterEncoding("UTF-8");
                        ((HttpServletResponse) res).setContentType("text/xml");
                        PrintWriter pw = ((HttpServletResponse) res).getWriter();
                        pw.println(sb.toString());
                        pw.flush();
                    } else if (requestUri.contains("login.html")) {
                        ((HttpServletRequest) req).getRequestDispatcher(NavigationResults.LOGIN).forward(req, res);
                    }
                }
            }
        }
    }

    private boolean getLoggedIn(HttpServletRequest req) {
        SessionBean oSessionBean = (SessionBean) req.getSession().getAttribute("sessionBean");
        return (oSessionBean != null && oSessionBean.getLoggedIn());
    }

    private SessionBean obtenerSession(HttpServletRequest req) {
        return ((SessionBean) req.getSession().getAttribute("sessionBean"));
    }
}