/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.ejb;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.jeeaps.crm.dao.AccountDAO;
import pl.jeeaps.crm.exception.AbstractApplicationException;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.model.Account;
import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.model.Project;

/**
 *
 * @author Dawid
 */
@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountEndpoint {//extends GenericEndpoint<Account, AccountDAO> {

    // Wstrzyknji komponent EJB
    @EJB 
    private AccountDAO accountDAO;
    
    // Wstrzyknji komponent realizujacy funkcje kontekstu aplikacji
    @Resource
    private SessionContext sctx;
    
    public AccountEndpoint() {
        
    }
    public void createAccount(Account account){
        accountDAO.create(account);
    }
    
    public Account findAccoutByLogin(){
        // pobierz nazwe z wstrzyknietego kontekstu aplikacji!
        return accountDAO.findByLoginName(sctx.getCallerPrincipal().getName());
    }
    
    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }
    
    // TODO :: getManagerAccountList
    // getEmployeAccountList
    // getClientAccountList
    
    public Account findById(Long id){
        return accountDAO.findById(id);
    }
    
    public Account mergeAccount(Account account) throws AbstractApplicationException{
        return accountDAO.update(account);
    }
    
    public List<Account> getSelectedAccountList(List<Account> selectedAccountList){

        return accountDAO.getSelectedAccountList(selectedAccountList);
    }
    
   
}
