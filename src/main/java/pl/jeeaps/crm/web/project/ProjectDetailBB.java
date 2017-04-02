/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.web.project;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.controllers.ProjectSessionEJB;
import pl.jeeaps.crm.model.Account;
import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.model.Project;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
// W JSF ManagedProperty
@Named("projectDetailBB")
public class ProjectDetailBB {

    //CDI
    @Inject private ProjectSessionEJB projectSession;
    
    private Project project;
    
    @PostConstruct
    public void init(){
        project = projectSession.getEditProject();
        System.out.println("Projekt w sesji " + projectSession.getEditProject());
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public List<Issue> getAllIssues(){
        return projectSession.getAllIssues();
    }
    
    public List<Account> getAllAssignedAccount(){
        return projectSession.getAllAssignAccount();
    }
}
