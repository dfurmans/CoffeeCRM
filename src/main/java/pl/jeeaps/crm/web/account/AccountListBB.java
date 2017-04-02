/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.web.account;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.controllers.AccountSessionEJB;
import pl.jeeaps.crm.model.Account;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
@Named("accountListBB")
public class AccountListBB {

    @Inject private AccountSessionEJB accountSession;
    
    private List<Account> accountList;

    public AccountListBB() {
    }
    
        // metoda zostanie wywolana podczas procesu tworzenia obiektu
    @PostConstruct
    private void init() {
        System.out.println("Pobieram wszystkie konta! ");
        this.getAllAccount();
    }
    
    public void getAllAccount(){
        accountList = accountSession.getAllAccounts();
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
    
}