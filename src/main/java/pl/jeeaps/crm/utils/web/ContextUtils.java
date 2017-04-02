/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.utils.web;

import java.security.Principal;
import java.util.ResourceBundle;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * JEEAPS - Coffee CRM
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
public class ContextUtils {

    /**
     * Creates a new instance of AttributesUtils
     */
    public ContextUtils() {
    }

    /**
     * Zwraca obiekt FacesContext - kontekst serwletu FacesServlet
     */
    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }
    
    /**
     * Wyszukuje atrybut o zadanej nazwie w kontekście aplikacji
     */
    public static Object getApplicationAttribute(String attributeName) {
        return getContext().getApplicationMap().get(attributeName);
    }

    /**
     * Wyszukuje atrybut o zadanej nazwie w kontekście sesji
     */
    public static Object getSessionAttribute(String attributeName) {
        return getContext().getSessionMap().get(attributeName);
    }

    /**
     * Wyszukuje atrybut o zadanej nazwie w kontekście żądania
     */
    public static Object getRequestAttribute(String attributeName) {
        return getContext().getRequestMap().get(attributeName);
    }

    /**
     * Wyszukuje parametr inicjalizacyjny o zadanej nazwie
     */
    public static String getContextParameter(String paramName) {
        return getContext().getInitParameter(paramName);
    }

    /**
     * Dokonuje zamknięcia bieżącej sesji
     */
    public static void invalidateSession() {
        ((HttpSession) getContext().getSession(true)).invalidate();
    }

    /**
     * Zwraca czas utworzenia biezacej sesji
     *
     */
    public static long getCreationTime() {
        return ((HttpSession) getContext().getSession(true)).getCreationTime();
    }

    public static long getLastAccessedTime() {
        return ((HttpSession) getContext().getSession(true)).getLastAccessedTime();
    }

    /**
     * Zwraca maksymalny czas zycia sesji http
     */
    public static int getSessionLifeTime() {
        return ((HttpSession) getContext().getSession(true)).getMaxInactiveInterval();
    }

    /**
     * Zwraca identyfikator bieżącej sesji
     */
    public static String getSessionID() {
        HttpSession session = (HttpSession) getContext().getSession(true);
        return session.getId();
    }

    /**
     * Pozwala na sprawdzenie czy użytkownk jest w danej roli
     */
    public static boolean checkUserRole(String userRole) {
        return getContext().isUserInRole(userRole);
    }

    /**
     * Zwraca nazwe zalogowanego użytkownika
     */
    public static String getUserName() {
        Principal p = getContext().getUserPrincipal();
        return (null == p ? "Niezalogowany" : p.getName());
    }

    /**
     * Zwraca zasób (ResourceBundle) o ścieżce wskazywanej przez parametr
     * resourceBundle.path
     */
    public static ResourceBundle getDefaultBundle() {
        String bundlePath = getContextParameter("resourceBundle.path");
        if (null == bundlePath) {
            return null;
        } else {
            return ResourceBundle.getBundle(bundlePath, java.util.Locale.getDefault());
        }
    }

}
