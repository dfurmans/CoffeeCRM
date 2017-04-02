/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.web.main;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import pl.jeeaps.crm.dao.IssueDAO;
import pl.jeeaps.crm.ejb.IssueEndpoint;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Named("mainPage")
public class MainPage implements Serializable {

    @EJB
    IssueEndpoint issueEndpoint;
    
    public MainPage() {
    }
    
    
    
    
}
