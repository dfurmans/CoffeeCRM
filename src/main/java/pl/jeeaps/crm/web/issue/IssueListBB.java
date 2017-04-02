/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.web.issue;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.controllers.IssueSessionEJB;
import pl.jeeaps.crm.model.Issue;

/**
 * JEEAPS - Coffee CRM
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
@Named("issueListBB")
public class IssueListBB {

    // Wstryzknji ref do issueSessionEJB
    @Inject private IssueSessionEJB issueSession;
// Reprezentuje całą listę zadań
    private List<Issue> issuesList;
// Repreznetuje 'zaznaczone' zadania w widoku
    private Issue selectedIssue;

    // domyslny konstruktor bezparametrowy

    public IssueListBB() {
    }
// Metoda wykonywana tuz po konstruktorze!

    @PostConstruct
    private void init() {
        this.getAllIssues();
    }

// Standardowe metody dostepowe
    public List<Issue> getIssuesList() {
        return issuesList;
    }

    public void getAllIssues() {
        issuesList = issueSession.getAllIssues();
    }

    public void setSelectedIssue(Issue selectedIssue) {
        this.selectedIssue = selectedIssue;
    }

    public Issue getSelectedIssue() {
        return selectedIssue;
    }
    
    /**
     * 
     * @return Metoda przekierowuje na szczegoly wybranego zadania
     * issueSession.getIssueToEdit(selectedIssue) - oczekuje na odebranie wybranego zadania
     */
    public String editIssue() {
        // obiekt selsectedIssue musi trafic w stan ządzany (manage)
        // aktualnie selectedIssue jest detached (odłączony od kontektsu trwalosci) 
        issueSession.getIssueToEdit(selectedIssue);
        return "issueDetail";
    }
//    TODO: Refactor to another Class 

    public void issueListener(ActionEvent ae) {
        String issueID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("issueID").toString();
        Long id = Long.parseLong(issueID);
        //issueSession.getIssueToEdit(id);
    }

    public List<Issue> getIssueAssigneToMe(){
    
        return issueSession.getMyIssueList();
    }
}
