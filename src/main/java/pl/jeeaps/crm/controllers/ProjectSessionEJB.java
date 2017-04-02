/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.ejb.AccountEndpoint;
import pl.jeeaps.crm.ejb.ProjectEndpoint;
import pl.jeeaps.crm.model.Account;
import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.model.Project;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */

@SessionScoped
@Named("projectSession")
public class ProjectSessionEJB implements Serializable {
    
    // Wstrzyknji Endpoint dla Project
    @EJB
    private ProjectEndpoint projectEndpoint;
    // Wstrzyknji Endpoint dla Account
    @EJB
    private AccountEndpoint accountEndpoint;
    
    @Inject private AccountSessionEJB accountSession;
    
    // reprezentuje tworzony projekt
    private Project createProject;

    private Project selectedProject;
    
    private Project editProject;
    
    public ProjectSessionEJB() {
    }

    public Project getCreateProject() {
        return createProject;
    }

    public void setCreateProject(Project createProject) {
        this.createProject = createProject;
    }
    
    // create project
    public void create(){

        // TODO :: Refactor this!
        createProject.setCreationDate(new Date());
        // realacja pomiedzy Project <-> Account
        System.out.println("To co mamy w secji " + accountSession.getSelectedAccounts()  );
        System.out.println("To co zwrocila nam baza danych " + accountEndpoint.getSelectedAccountList(accountSession.getSelectedAccounts()));
        createProject.setAccounts(accountEndpoint.getSelectedAccountList(accountSession.getSelectedAccounts()));
        //for (Account account:accountEndpoint.getSelectedAccountList(accountSession.getSelectedAccounts()) )
          //  account.setProjects();
        // Zapisz encje do bazy danych
        projectEndpoint.createProject(createProject);
        // wyzeruj referencje po utworzeniu/zapisaniu obiektu do bazy danych
        createProject = null;
        // wyzeruj wczesniej wybrane konta w ziarnie o zasiegu seji!
        accountSession.getSelectedAccounts().clear();
    }
    
    public List<Project> getAllProjects(){
       
        return projectEndpoint.getAllProject();
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Long id) {
        this.selectedProject = projectEndpoint.getById(id);
    }

    public Project getEditProject() {
        return editProject;
    }

    public void setEditProject(Project editProject) {
        this.editProject = editProject;
    }
    
    public String getProjectToEdit(Project selectedProject) {
        // przypisz wybrane zadanie pod ref selectedProject
        editProject = projectEndpoint.getById(selectedProject.getId());
        System.out.println("ProjectSesionEJB = > getProjectToEdit() " + editProject);
        return "projectDetail";
     }
    
    public List<Issue> getAllIssues(){
        return projectEndpoint.getAllIssues();
    }
    
    public List<Account> getAllAssignAccount(){
        return projectEndpoint.getAllAssignAccount();
    }
}
