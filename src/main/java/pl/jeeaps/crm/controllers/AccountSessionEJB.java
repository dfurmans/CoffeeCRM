/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.controllers;
import pl.jeeaps.crm.ejb.AccountEndpoint;
import pl.jeeaps.crm.model.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import pl.jeeaps.crm.model.Administrator;
import pl.jeeaps.crm.model.Client;
import pl.jeeaps.crm.model.Employe;
import pl.jeeaps.crm.model.Manager;
import pl.jeeaps.crm.model.enums.AccountSystemEnumType;
import pl.jeeaps.crm.utils.web.ContextUtils;
/**
 *
 * @author Dawid
 */
@SessionScoped
@Named("accountSession")
public class AccountSessionEJB implements Serializable{
    private static final Logger LOG = Logger.getLogger(AccountSessionEJB.class.getName());

    @EJB
    private AccountEndpoint accountEndpoint;
    
    private Account selectedAccount;
    
    private Client createClientAccount;
    
    private Employe createEmployeAccount;
    
    private Manager createMenagerAccount;
    
    private Administrator createAdminAccount;
    
    private List<Account> selectedAccounts;
    
    // Konstruktor
    public AccountSessionEJB() {
        selectedAccounts = new ArrayList<Account>();
    }
    
    public String logOut(){
        ContextUtils.invalidateSession();
        return "logOut";
    }
    
    public String showMyLoginName(){
        return ContextUtils.getUserName(); 
    }
    
    public boolean isLogin(){
        return this.showMyLoginName().equals("Niezalogowany") ? false : true;
    }
    
    public void updateLastLoginDate(){
        System.out.println("Proboje ustawic nowa date zalogowania");
        if (isLogin()){
            System.out.println("Ustawiam nowa date zalogowania");
            getMyAccount().setLastLogin(new Date());
        }else {
            System.out.println("Duspko");}
    }

    public String createClientAccount(Client client) {
        createClientAccount = client;  
        createClientAccount.setType(AccountSystemEnumType.CLIENT.name());
        createClientAccount.setCreationDate(new Date());
        accountEndpoint.createAccount(createClientAccount);
        // wyzeruj referencje dla tego identyfikatora
        createClientAccount = null;
        return "accountList";
    }

     public String createEmployeAccount(Employe employe) {
        createEmployeAccount = employe;  
        createEmployeAccount.setType(AccountSystemEnumType.EMPLOYE.name());
        createEmployeAccount.setCreationDate(new Date());
        accountEndpoint.createAccount(createEmployeAccount);
        // wyzeruj referencje dla tego identyfikatora
        createEmployeAccount = null;
        return "accountList";
    }
     
     public String createManagerAccount(Manager manager) {
        createMenagerAccount = manager;  
        createMenagerAccount.setType(AccountSystemEnumType.MANAGER.name());
        createMenagerAccount.setCreationDate(new Date());
        accountEndpoint.createAccount(createMenagerAccount);
        // wyzeruj referencje dla tego identyfikatora
        createMenagerAccount = null;
        return "accountList";
    }
    
     public String createAdminAccount(Administrator admin) {
        createAdminAccount = admin;  
        createAdminAccount.setType(AccountSystemEnumType.ADMINISTRATOR.name());
        createAdminAccount.setCreationDate(new Date());
        accountEndpoint.createAccount(createAdminAccount);
        // wyzeruj referencje dla tego identyfikatora
        createAdminAccount = null;
        return "accountList";
    }
     
     public Account getMyAccount(){
         return accountEndpoint.findAccoutByLogin();
     }
     
     public List<Account> getAllAccounts(){
         return accountEndpoint.getAllAccounts();
     }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Long id) {
        //System.out.println("Ustawiam obiekt " + accountEndpoint.findById(id));
        this.selectedAccount = accountEndpoint.findById(id);
    }

    public List<Account> getSelectedAccounts() {
        //System.out.println("Wybrane konta" + selectedAccounts);
        return selectedAccounts;
    }

    public void setSelectedAccounts(List<Account> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }

    public void addSelectedAccount(Long id) {
        this.selectedAccounts.add(accountEndpoint.findById(id));
    }


}