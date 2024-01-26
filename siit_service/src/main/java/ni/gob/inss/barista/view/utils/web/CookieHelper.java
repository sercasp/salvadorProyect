package ni.gob.inss.barista.view.utils.web;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>LINUS</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * CookieHelper</br>
 *
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 29/08/2014
 * @since 1.0 *
 * Modificado por jvillanueva el 08/08/2023
 */
public abstract class CookieHelper {
    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    public static void setCookie(String name, String value, int expiry) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (Cookie userCooky : userCookies) {
                if (userCooky.getName().equals(name)) {
                    cookie = userCooky;
                    break;
                }
            }
        }
        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath(request.getContextPath());
        }
        cookie.setMaxAge(expiry);
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    public static Cookie getCookie(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (Cookie userCooky : userCookies) {
                if (userCooky.getName().equals(name)) {
                    cookie = userCooky;
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void removeCookie(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (Cookie userCooky : userCookies) {
                if (userCooky.getName().equals(name)) {
                    cookie = userCooky;
                    break;
                }
            }
        }
        if (cookie != null) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.addCookie(cookie);
        }
    }
}