/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.jeeaps.crm.exception.AbstractApplicationException;

import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;

/**
 * Bezstanowe zairno EJB - zdefiniowane jako LocalBean
 * ZADANIE: Dostęp do danych przez EntityManager tzn. typowe DAO
 * @autor Dawid
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class IssueDAO extends HibernateDAO<Issue, Long> {
    
    @PersistenceContext(unitName = "PU_MySQL")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    // Konstruktor obiektu
    public IssueDAO() {
        super(Issue.class);
    }
    // Basic CRUD and other 
    public void createIssue(Issue issue){
        super.create(issue);
    }
    // TODO !
    public Issue findIssue(Issue issue){
        return super.findById(issue.getId());
    }
    /**
     * Zapisz IssueDAO
     */
    public void updateIssue(Issue issue) throws AbstractApplicationException{
        super.update(issue);
        // wymus natychmiastowy zapis danych do DB
        entityManager.flush();
    }
    @Override
    public List<Issue> findAll(){
        return super.findAll();
    }

    public List<Issue> getMyIssuesList(Long id) {
        TypedQuery<Issue> tq = entityManager.createNamedQuery("Issue.findForAccount", Issue.class);
        tq.setParameter("id", id);
        
        return tq.getResultList();
    }
}