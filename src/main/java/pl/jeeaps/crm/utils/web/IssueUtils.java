/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.utils.web;

import java.util.Date;
import pl.jeeaps.crm.core.Event;
import pl.jeeaps.crm.model.Issue;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
public class IssueUtils {

    /**
     * Metoda przepisująca dane 
     */
    public static void dataChange(Issue source, Issue target ){
        if (source == null || target == null) return;
        
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        
        
    }
    
}
