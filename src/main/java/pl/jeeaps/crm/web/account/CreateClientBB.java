/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.web.account;
import pl.jeeaps.crm.model.Client;
import pl.jeeaps.crm.controllers.AccountSessionEJB;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.model.enums.AccountBusinessEnumType;
import pl.jeeaps.crm.model.enums.IssuePriority;
import pl.jeeaps.crm.model.enums.IssueType;
import pl.jeeaps.crm.utils.web.ContextUtils;

/**
 *
 * @author Dawid
 */

@RequestScoped
@Named("createClientAccountBB")
public class CreateClientBB implements Serializable{
    private static final Logger LOG = Logger.getLogger(CreateClientBB.class.getName());

    // Wstrzyknji EJB o zasięgu sesji!
    @Inject AccountSessionEJB accountSession;
    // TODO: make flexability
    private Client konto = new Client();

    public CreateClientBB() {
    }

    public Client getKonto() {
        return konto;
    }

    public String createClientAccount(){
        LOG.severe("Tworzone konto " + konto);
        // zapisz tworzonekonto
         return accountSession.createClientAccount(konto);
    }

        // Zwraca tablicę enum IssueType
    public AccountBusinessEnumType[] getAllAccountBusinesstype(){
        // metoda values iteruje po enum
        return konto.getAllAccountBusinessType();
    }
    
    public Map<String, String> getBusinessTypeClientAccount(){
                
        ResourceBundle bundle = ContextUtils.getDefaultBundle();
        Map<String,String> issueBusinessType = new LinkedHashMap<String,String>();
        
        issueBusinessType.put(AccountBusinessEnumType.CORPORATION.toString(), bundle.getString("accountBusinessEnumType.Corporation"));
        issueBusinessType.put(AccountBusinessEnumType.LARGE_CLIENT.toString(), bundle.getString("accountBusinessEnumType.LargeClient"));
        issueBusinessType.put(AccountBusinessEnumType.MEDIUM_CLIENT.toString(), bundle.getString("accountBusinessEnumType.MediumClient"));
        issueBusinessType.put(AccountBusinessEnumType.SMALL_CLIENT.toString(), bundle.getString("accountBusinessEnumType.StandardClient"));
        issueBusinessType.put(AccountBusinessEnumType.STANDARD_CLIENT.toString(), bundle.getString("accountBusinessEnumType.SmallClient"));
        
        return issueBusinessType;
    }
}