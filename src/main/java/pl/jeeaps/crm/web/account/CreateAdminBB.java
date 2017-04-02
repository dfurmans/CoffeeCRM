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
import pl.jeeaps.crm.model.Administrator;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
@Named("createAdminAccountBB")
public class CreateAdminBB implements Serializable {

    // Wstrzyknji EJB o zasięgu sesji!
    @Inject AccountSessionEJB accountSession;
    
    private Administrator konto = new Administrator();

    public CreateAdminBB() {
    }
    
    public String createAdminAccount(){
        return accountSession.createAdminAccount(konto);
    }

    public Administrator getKonto() {
        return konto;
    }

}
