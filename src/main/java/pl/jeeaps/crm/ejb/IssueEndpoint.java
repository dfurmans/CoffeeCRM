/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.ejb;

import java.util.Date;
import java.util.List;
import pl.jeeaps.crm.dao.IssueDAO;
import pl.jeeaps.crm.model.Issue;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.jeeaps.crm.core.Event;
import pl.jeeaps.crm.core.Status;
import pl.jeeaps.crm.exception.AbstractApplicationException;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.utils.web.ContextUtils;
import pl.jeeaps.crm.utils.web.IssueUtils;

/**
 * Stanowe ziarno EJB - zawiera stan pomiędzy kolejnymi wyolaniami Wstrzykiwane
 * jest tutaj IssueDAO dzięki czemu mamy dostęp do danych z DB Stan jest
 * reprezentowany za pomoca referencji o identyfikatorze stateIssue
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class IssueEndpoint extends GenericEndpoint implements SessionSynchronization {

    // Wstrzyknji DAO - referencja dostarczona przez kontener EJB
    @EJB
    private IssueDAO issueDAO;
    
    @EJB
    private AccountEndpoint accountEndpoint;
    
    private Issue stateIssue;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    // Konstruktor
    public IssueEndpoint() {
    }

    public void createIssue(Issue issue) {
        issueDAO.create(issue);
    }
    // getAllIssues

    public List<Issue> getAllIssues() {
        return issueDAO.findAll();
    }
    // Get Issue to Edit! Semantyka kodu !

    public Issue getIssueToEdit(Issue issue) {
        // metoda zwraca wyszukuje i zwraca obiekt podlaczony do kontekstu trwalosci
        stateIssue = issueDAO.findIssue(issue);
        return stateIssue;
    }

    /**
     * Zapisz zadanie po edycji
     */
    public void saveIssueAfterEdit(Issue issue) throws AbstractApplicationException {
        //Zarejestruj nowe Zdarzenie dla tego zadania (Zdarzenie edycji) 
        Event newEvent = new Event("Edycja zadania przez " + ContextUtils.getUserName(), new Date());
        
        // protect to LazyInicjalization Exception ... after 10 days ...
        try {
            stateIssue.getAllEvent().add(newEvent);
        } catch (Exception ex) {
            stateIssue = getIssueToEdit(issue);
            stateIssue.getAllEvent().add(newEvent);
        }
        
        // zaktualizuj dane 
        IssueUtils.dataChange(issue, stateIssue);

        issueDAO.updateIssue(stateIssue);

        // skasuj referencje do edytowanego zadania poprzez pisanie null
        stateIssue = null;
    }

    public void updateIssue(Issue issue) throws AbstractApplicationException {
        //System.out.println("Aktualizuje zadanie " + issue);
        issueDAO.updateIssue(issue);
    }
    
    public List<Event> getAllEvent(){
        // it is any possible to make better?! 
        //LazyInicializationException FuckOFF!
        return getIssueToEdit(stateIssue).getAllEvent(); 
    }
    
    public List<Status> getAllStatus(){
        // it is any possible to make better?! 
        //LazyInicializationException FuckOFF!
        return getIssueToEdit(stateIssue).getAllStatus(); 
    }
    
    public Issue getById(Long id){
        return issueDAO.findById(id);
    }

    public List<Issue> getMyIssues() {
        return issueDAO.getMyIssuesList(accountEndpoint.findAccoutByLogin().getId());
    }

}