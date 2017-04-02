/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.utils.web;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Named("utilBean")
@SessionScoped
public class UtilBean implements Serializable  {
    
    public UtilBean() {
    }
    
    /**
     * Sprawdza czy użytkownik jest w danej roli
     * @param userRole
     * @return Boolean
     */
    
    public boolean checkUserRole(String userRole){
        return ContextUtils.checkUserRole(userRole);
    }
    
    /**
     * Zwraca informację na temat roli na jaką jest zalogowany użytkownik
     */
    
    public String getUserRole(){
        return ContextUtils.getUserName();
    }
    /**
     * Zamyka sesję HTTP (odwołanie do klasy narzędziowej ContextUtils).
     */
    public void closeSession() {
        ContextUtils.invalidateSession();
    }

    /**
     * Zwraca identyfikator sesji HTTP (odwołanie do klasy narzędziowej ContextUtils).
     */
    public String getSessionID() {
        return ContextUtils.getSessionID();
    }
    
    /**
     * Zwrca czas utworzenia sesji
     */
    public long getSessionCreatedTime(){
        return ContextUtils.getCreationTime();
    }
    
    /**
     * Zwraca czas zwiazany z cyklem zycia sesji
     */
    
    public long getLastAccessedIntervalTimeRequest(){
        return ContextUtils.getLastAccessedTime();
    }
    
    /**
     * Zwraca maksymalny czas zycia sesji http
     */
    
    public int getMaxLifeSessionTime(){
        return ContextUtils.getSessionLifeTime();
    }
    /** 
     * Zwraca opisy liczników (wynik wywołania metody toString()) we wszystkich zasięgach. 
     */
    public String getCountersDescription() {
        StringBuilder sb = new StringBuilder(new Date().toString()).append("\n");
        sb.append("Licznik w zasięgu żądania: ").append(ContextUtils.getRequestAttribute("counterBeanRequestScope")).append("\n");
        sb.append("Licznik w zasięgu sesji: ").append(ContextUtils.getSessionAttribute("counterBeanSessionScope")).append("\n");
        sb.append("Licznik w zasięgu aplikacji: ").append(ContextUtils.getApplicationAttribute("counterBeanApplicationScope")).append("\n");
        return sb.toString();
    }

    /** 
     * Zwraca wartość parametru application.description zgodnie z wartością parametru skonfigurowanego w deskryptorze web.xml. 
     */
    public String getApplicationDescriptionParam() {
        return ContextUtils.getContextParameter("application.description");
    }
    
}
