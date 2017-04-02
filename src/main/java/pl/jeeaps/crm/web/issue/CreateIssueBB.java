/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.web.issue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import pl.jeeaps.crm.controllers.IssueSessionEJB;

//import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.jeeaps.crm.controllers.AccountSessionEJB;
import pl.jeeaps.crm.controllers.ProjectSessionEJB;
import pl.jeeaps.crm.exception.AbstractApplicationException;
import pl.jeeaps.crm.model.Account;
import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.model.Project;
import pl.jeeaps.crm.model.enums.IssuePriority;
import pl.jeeaps.crm.model.enums.IssueType;
import pl.jeeaps.crm.utils.web.ContextUtils;

/**
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */

@RequestScoped
@Named("createIssueBB")
public class CreateIssueBB implements Serializable{
    
    // Wstrzyknji ref do issueSessionEJB
    @Inject private IssueSessionEJB issueSession;

    // Wstrzyknji ref do prjectSessionEJB
    @Inject private ProjectSessionEJB projectSession;
    
    //Wstrzyknji ref do accountSessionEJB
    @Inject private AccountSessionEJB accountSession;
    
    private Issue issue;
    
    private Project selectedProject;
    
    private String inputloginName;
    
    private Account selectedAccount;
    
    public CreateIssueBB() {
        issue = new Issue();
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    // Zwraca tablicę enum IssueType
    public IssueType[] getAllIssueType(){
        // metoda values iteruje po enum
        return issue.getAllIssueType();
    }
    // Zwraca tablicę enum IssuePriority
    public IssuePriority[] getAllIssuePriority(){
        return issue.getAllIssuePriority();
    }
    
     public String createIssue(){
         
         // przekaż tworzone zadanie za pomocą setCreateIssue do issueSession
        issueSession.setCreateIssue(issue);
         // zapisz tworzone zadanie
        issueSession.createIssue();
        // Przekieruj na liste zadan
        return "issueList";
    
    }
    
    /**
     * Pozwala na utworzenie podzadania(subissuse)
     * @param Do metody przekazujemy aktualnie edytowane zadanie << pobieramy je z EJB o zasiegu sesji
     */
     public String createSubIssue() throws AbstractApplicationException{
         //System.out.println("To co mamy w sesji " + issueSession.toString());
         // bazuje na aktualnie edytowanym zadaniu << posiadamy je w EJB o zasiegu sesji
         issueSession.createSubissue(issue);
         return issueSession.getIssueToEdit(issue.getRootIssue());
     }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        // dołącza wybrany obiekt do konstekstu trwalosci
        projectSession.setSelectedProject(selectedProject.getId());
        // pobieramy przylaczony do kontekstu trwalosci obiekt
        this.selectedProject = projectSession.getSelectedProject();
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public List<Account> completeAccount(String query){
        System.out.println("Wykonuje metode AccountAutoComplete");
        System.out.println("Parametr" + query);
        ArrayList<Account> account = new ArrayList<Account>();
        account.add(new Account());
        return account;
    }

    public String getInputloginName() {
        return inputloginName;
    }

    public void setInputloginName(String inputloginName) {
        this.inputloginName = inputloginName;
    }
    
    public Map<String, String> getIssueType(){
        
        ResourceBundle bundle = ContextUtils.getDefaultBundle();
        Map<String,String> issueType = new LinkedHashMap<String,String>();
        
        issueType.put(IssueType.BUG.toString(), bundle.getString("issue.type.Bug"));
        issueType.put(IssueType.FEATURE.toString(), bundle.getString("issue.type.Feature"));
        issueType.put(IssueType.IMPROVEMENT.toString(), bundle.getString("issue.type.Improvement"));
        issueType.put(IssueType.MEETING.toString(), bundle.getString("issue.type.Meeting"));
        issueType.put(IssueType.ORDER.toString(), bundle.getString("issue.type.Order"));
        issueType.put(IssueType.TASK.toString(), bundle.getString("issue.type.Task"));
        
        return issueType;
    }
    
    public Map<String, String> getIssuePriority(){
        
        ResourceBundle bundle = ContextUtils.getDefaultBundle();
        Map<String,String> issuePriority = new LinkedHashMap<String,String>();
        
        issuePriority.put(IssuePriority.CRITICAL.toString(), bundle.getString("issue.priority.Critical"));
        issuePriority.put(IssuePriority.MAJOR.toString(), bundle.getString("issue.priority.Major"));
        issuePriority.put(IssuePriority.MINOR.toString(), bundle.getString("issue.priority.Minor"));
        issuePriority.put(IssuePriority.TRIVAL.toString(), bundle.getString("issue.priority.Trivial"));
        
        return issuePriority;
    
    }
    
}
