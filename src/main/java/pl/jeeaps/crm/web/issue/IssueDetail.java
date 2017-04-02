/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.web.issue;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.controllers.AccountSessionEJB;
import pl.jeeaps.crm.controllers.IssueSessionEJB;
import pl.jeeaps.crm.core.Event;
import pl.jeeaps.crm.core.Status;
import pl.jeeaps.crm.exception.AbstractApplicationException;
import pl.jeeaps.crm.model.Comment;
import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.model.enums.IssuePriority;
import pl.jeeaps.crm.model.enums.IssueStatus;
import pl.jeeaps.crm.model.enums.IssueType;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
// W JSF ManagedProperty
@Named("detailIssueBB")
public class IssueDetail {


  // Wstrzyknji ref do issueSessionEJB
    @Inject private IssueSessionEJB issueSession;
   
   // Wstrzyknji ref do accountSessionEJB
    @Inject private AccountSessionEJB accountSession;
    
    private Issue issue;
    
    private Comment comment;
    // metoda zostanie wywolana po utworzeniu obiektu
    /**
     * Metoda wywolywana przez konetener EJB w chwili tuż po utworzenia pelnowartosciowego obiektu
     * Wykorzystuje adnotacje @PostConstruct
     */
    @PostConstruct
    private void init() {
       // pobierz edytowane zadanie z obiektu zarzadzanego (w JFS ManagedProperty)
        issue = issueSession.getEditIssue();
       // ochrona przez NPE - tworzymy ref dla nowo dodawnwago obiektu 
        comment = new Comment();
    }
    
    public IssueDetail() {
    }

    public Issue getIssue() {
        return issue;
    }
    
    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    public List<Issue> getAllSubIssues(){
        return issue.getSubIssues();
    }
    
    public IssuePriority[] getAllPriority(){
        return issue.getAllIssuePriority();
    }
    
    public IssueType[] getAllIssueType(){
        return issue.getAllIssueType();
    }
    
    public IssueStatus[] getAllIssueStatus(){
        return issue.getAllIssueStatus();
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    
    /**
     * Zapisuje stan po edycji zadania
     * @return Zwraca regułę nawigacji
     */
    public String saveAfterEdit() throws AbstractApplicationException{
        return issueSession.saveIssueAfterEdit();
    }
    
    public String getCurrentStatus(){
        return issue.getCurrentStatus().toString();
    }
    
    /**
     * 
     * @return Liste (ArrayList) obiektow klasy COMMENT związanych z tym wystąpieniem encji ISSUE 
     */
    public List<Comment> getAllComments(){
        return issue.getComments();
    }
    /**
     * Pobiera liste wszystkich encji EVENT dla tego zadania
     * @return Zwraca Liste (ArrayList) wszystkich obiektów klasy EVENT związanych z tym wystąpieniem encji ISSUE
     */
    public List<Event> getAllEvent(){
        return issueSession.getAllEvent();
    }
    
    /**
     * Pobiera liste wszystkich encji SATUS dla tego zadania
     * @return Kolekcje w postaci Listy statusow dla danego zadania
     */
    public List<Status> getAllStatus(){
        return issueSession.getAllStatus();
    }
    
    /**
     * Metoda dodaje komentarz do aktualnie edytowanego zadania
     * @param comment Reprezentuje dodawny komentarz
     */
    public String addCommentToEditIssue() throws AbstractApplicationException{
        // TODO::Remove
        System.out.println("Probuje dodac nowy komentarz do zadania " + comment);
        // ustaw godzinę dodawnia komentarza
        comment.setCreationDate(new Date());
        comment.setAccount(accountSession.getMyAccount());
        // UWAGA! możemy wykonać bezpośrednio metodę addCommentEditIssue na obiekcie issueSession ponieważ w nim jest gotowa referencja w identyfikatorze editIssue:)
        issueSession.addCommentEditIssue(comment);
        // Ponownie pobierz (zaktualizowane zadanie do edycji)
        return issueSession.getIssueToEdit(issue);
        
    }
    
    
}