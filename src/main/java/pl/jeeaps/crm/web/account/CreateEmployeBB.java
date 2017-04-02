/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.web.account;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.controllers.AccountSessionEJB;
import pl.jeeaps.crm.model.Employe;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
@Named("createEmployeAccountBB")
public class CreateEmployeBB implements Serializable {

    // Wstrzyknji EJB o zasięgu sesji!
    @Inject AccountSessionEJB accountSession;
    
    private Employe konto = new Employe();

    public CreateEmployeBB() {
    }
    
    public String createEmployeAccount(){
        return accountSession.createEmployeAccount(konto);
    }

    public Employe getKonto() {
        return konto;
    }

    
    
    
}
