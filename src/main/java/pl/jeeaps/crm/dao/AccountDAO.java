/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.model.Account;
import pl.jeeaps.crm.model.Project;
/**
 *
 * @autor Dawid
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class AccountDAO extends HibernateDAO<Account,Long> {
    private static final Logger LOG = Logger.getLogger(AccountDAO.class.getName());
    
    @PersistenceContext(unitName = "PU_MySQL")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public AccountDAO() {
        // woła konstruktor nadklasy - klasy z której dziedziczy
        super(Account.class);
    }
    
    protected void createAccount(Account account){
        LOG.severe("Klasa " + this.getClass().getCanonicalName().toString() +"metoda createAccount(acc)");
        super.create(account);
    } 
    
    public Account findByLoginName(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        
        Root<Account> from = query.from(Account.class);
        
        query = query.select(from);
        
        query = query.where(cb.equal(from.get("login"), login));
        
        TypedQuery<Account> tq = entityManager.createQuery(query);

        return tq.getSingleResult();
    }

    public List<Account> getAllAccounts() {
        return super.findAll();
    }
    
    public Account finById(Long id){
        return super.findById(id);
    }
    
    public List<Account> getSelectedAccountList(List<Account> selectedAccountList){
        List<Account> dbAccountList = new ArrayList<Account>();
        
        for (Account account : selectedAccountList ){
            dbAccountList.add(super.findById(account.getId()));
        }
        return dbAccountList;
    }
}
