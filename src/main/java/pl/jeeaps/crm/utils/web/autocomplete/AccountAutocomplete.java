/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.utils.web.autocomplete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import pl.jeeaps.crm.ejb.AccountEndpoint;
import pl.jeeaps.crm.model.Account;

/**
 * JEEAPS - Coffee CRM
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Named("accountAutocomplete")
public class AccountAutocomplete implements Serializable {

    @EJB
    AccountEndpoint accountEndpoint;
    // dla pojedynczego wyboru
    private Account selectedAccount;
    
    private List<Account> selectedAccounts;
    
    // do utwrzenia podpowiedzi
    private List<Account> accounts;
    
    public AccountAutocomplete() {
    }

    @PostConstruct
    public void init() {
        accounts = accountEndpoint.getAllAccounts();
    }

    public List<Account> complete(String query) {
        //zwroc liste Issuesow zaczynajacych sie na query przekazane w argumencie!
        List<Account> suggestions = new ArrayList<Account>();
        for (Account account : accounts) {
            if (account.getName().startsWith(query)) {
                suggestions.add(account);
            }
        }
        // zwraca liste pasujacych encji! yeah!
        return suggestions;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public List<Account> getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(List<Account> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }
    
    
}
