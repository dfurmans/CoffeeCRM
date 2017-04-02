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
import pl.jeeaps.crm.model.Manager;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
@Named("createManagerBB")
public class CreateManagerBB implements Serializable {

    // Wstrzyknji EJB o zasięgu sesji!
    @Inject AccountSessionEJB accountSession;

    private Manager konto = new Manager();

    public CreateManagerBB() {
    }
    
    public String createManagerAccount(){
        return accountSession.createManagerAccount(konto);
    }

    public Manager getKonto() {
        return konto;
    }

    
}
