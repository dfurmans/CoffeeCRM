/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import pl.jeeaps.crm.ejb.IssueEndpoint;
import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.model.enums.IssueStatus;
import pl.jeeaps.crm.core.Event;
import pl.jeeaps.crm.core.Status;
import pl.jeeaps.crm.model.Comment;
import pl.jeeaps.crm.exception.AbstractApplicationException;
import pl.jeeaps.crm.ejb.AccountEndpoint;
import pl.jeeaps.crm.ejb.ProjectEndpoint;
import pl.jeeaps.crm.model.Project;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Ziarno EJB (z wyrzkoystaniem CDI) o zasięgu sesji 
 * Bazuje na ziarnie EJB Endpoint (tam jest przechowywany stan!) - należy go pobrać! 
 * @author Dawid Furman www.jeeaps.pl/autor
 */
//@Stateful
//@SessionScoped
//@LocalBean
@Named("issueSession")
@SessionScoped
public class IssueSessionEJB implements Serializable{
    private static final Logger LOG = Logger.getLogger(IssueSessionEJB.class.getName());
    // Wstrzyknięcie Endpointa dla klasy Issue
    @EJB 
    private IssueEndpoint issueEndpoint;
    
    @EJB
    private ProjectEndpoint projectEndpoint;
    
    @EJB 
    private AccountEndpoint accountEndpoint;
    
    // Wstrzyknji ref do prjectSessionEJB
    @Inject private ProjectSessionEJB projectSession;
    
   // 
    @Inject private AccountSessionEJB accountSession;
    
    // reprezentuje tworzone zadanie 
   
    private Issue createIssue;
    
    // reprezentuje edytowane zadanie
    
    private Issue editIssue;
    
    private Issue selectedIssue;
    
    // publicy kontruktor obiektu 
    
    public IssueSessionEJB() {
    }
    
    // zapewnia dostęp do twrzonego zadania
    
    public Issue getCreateIssue() {
        return createIssue;
    }

    // zapewnia ustawinie tworzonego zadania
    
    public void setCreateIssue(Issue createIssue) {
        this.createIssue = createIssue;
    }
 
    public void setProjectToCreateIssue(Project project){
       this.createIssue.setProject(project);
    }
    
    // zapewnia dostęp do aktualnie edytowanego zadania
    public Issue getEditIssue() {
        return editIssue;
    }

    public void setEditIssue(Issue editIssue) {
        this.editIssue = editIssue;
    }
    
    // Create
    public void createIssue(){
        /* Twrzone zadanie przekazujemy do EJB Issue Endpoint, dzięki czemu mamy 
         jasno określoną odpowiedzialność poszczególnych ziaren! */
        createIssue.setIssueStatus(IssueStatus.OPEN); 
        // zarejestruj nowy status dla tej klasy  ! TODO - 
        createIssue.newStatus(new Status("Status zadania", "Opis zadania"));
        // zarejstruj nowy event dla tej klasy !
        createIssue.newEvent(new Event("Utworzono nowe zadanie", new Date()));
        createIssue.setCreationDate(new Date());
        /*PROJECT <-> ISSUE*/
        //metoda przyjmuje w argumencie zwrocony obiekt, ktory jest zarzadzany przez kontekst trwalosci
        // TODO << co z druga strona relacji?!
        createIssue.setProject(projectEndpoint.getById(projectSession.getSelectedProject().getId()));
        // pobierz wyszystkie issue dla tego zadania i dodaj nowe zadanie
       // System.out.println("Wybrany" + projectEndpoint.getById(projectSession.getSelectedProject().getId()));
       // System.out.println(projectEndpoint.getById(projectSession.getSelectedProject().getId()).getIssues());
        /*ACCOUNT_Recipient <-> ISSUE*/
        createIssue.setCurrentRecipient(accountEndpoint.findById(accountSession.getSelectedAccount().getId()));
        // TODO << co z druga strona relacji?!
        /*ACCOUNT_Consigner <-> ISSUE*/
        System.out.println("Tworca tego zadania jest " + accountEndpoint.findAccoutByLogin());
        createIssue.setCurrentConsigner(accountEndpoint.findAccoutByLogin());
        
        issueEndpoint.createIssue(createIssue);
        //createIssue.getIssueStatus().setStatus(null);
        // Po zapisaniu Issue do bazy danych należy wyczyścić referencję - ponieważ EJB jest o zasięgu Session
        createIssue=null;
    }
    // GetAll issues 
    public List<Issue> getAllIssues(){
        return issueEndpoint.getAllIssues();
    }
    
    /**
     * Metoda tworzy referencje do obiektu editIssue (zadanie wybrane z widoku)
     * @param issue - reprezentuje wybrane zadanie z widoku
     * @return - zwraca wybrane zadanie w trybie do edycji - przekierowuje do nawigacji "issueDetail"
     */
    public String getIssueToEdit(Issue issue){
        // Przypisz zadanie edytowane pod referencje editIssue
        editIssue = issueEndpoint.getIssueToEdit(issue);
        return "issueDetail";
    }
    /**
     * Metoda dodaje komentarz do edytowanego zadania
     * @param comment 
     */
    public void addCommentEditIssue(Comment comment) throws AbstractApplicationException{
        // Refactor - > pobierz wszystkie komentarze dla tego zadania i dopiero dodaj
        //editIssue.addNewComment(comment); UWAGA !! działamy na referencji editIssue!
        editIssue.getComments().add(comment);
        // ZAPIS OBIEKTU comment PO POWIAZANIU REALACJI POWINNA ZAPEWNIAC OPERACJA KASKADOWA TYPU PERSIST! 
        //zaktualizuj stan encji klasy Issue
        issueEndpoint.updateIssue(editIssue);
    }
    
    /**
     * Metoda dodaje podzadanie - pobiera Zadanie z EJB o zasiegu sesji - aktlanie posiadamy referencje do edytowanego zadnia 
     */
    public void createSubissue(Issue createSubIssue) throws AbstractApplicationException{
        /* ustaw rodzica dla nowo tworzonego zadania na zadanie edytowane, innymi slowy utworz podazanie dla tego zadania
        korzystam z metdy rusztujacej addSubIssue dzieki czemu zmniejszam ryzyko popelnienia bledy i ilosc kodu niezbednego do zwiazania R
        */
        editIssue.addSubIssue(createSubIssue);
        // zapisz gotowy obiekt TODO << ZREALIZUJE TEN PUNK POPRZEZ OPERACJE KASKADOWE!
       // issueEndpoint.createIssue(createSubIssue);
        //zaktualizuj stan encji klasy Issue
        issueEndpoint.updateIssue(editIssue);
    }
    
    /**
     * Metoda zapisujaca stan encji Issue po przeprowadzonej edycji 
     * @param issue
     * @return Regula nawigacji: issueList
     */
     
    public String saveIssueAfterEdit() throws AbstractApplicationException{
        
        issueEndpoint.saveIssueAfterEdit(editIssue);
        return "issueList";
    }

    public List<Event> getAllEvent(){
        return issueEndpoint.getAllEvent();
    }
    
    public List<Status> getAllStatus(){
        return issueEndpoint.getAllStatus();
    }

    public Issue getSelectedIssue() {
        return selectedIssue;
    }

    public void setSelectedIssue(Long id) {
        this.selectedIssue = issueEndpoint.getById(id);
    }
    
    public List<Issue> getMyIssueList(){
        return issueEndpoint.getMyIssues();
    }

}
