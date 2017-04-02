/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.jeeaps.crm.dao.ProjectDAO;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.model.Account;
import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.model.Project;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProjectEndpoint extends GenericEndpoint implements SessionSynchronization{
    
    // Wstrzyknji DAO dla klasy Project
    @EJB
    private ProjectDAO projectDAO;
    
    // Ziarno jest ziarnem typu Stateful << Co to nam daje?
    private Project stateProject;

    public ProjectEndpoint() {
    }
    
    public void createProject(Project project){
        projectDAO.create(project);
    }
    
    public List<Project> getAllProject(){
        return projectDAO.getAllProjects();
    }
    
    public Project getById(Long id){
        stateProject = projectDAO.getById(id);
        return stateProject;
    }
    
    public List<Issue> getAllIssues(){
        //LazyIncjalization EX Fuck oFF!
        return getById(stateProject.getId()).getIssues();
    }
    
    public List<Account> getAllAssignAccount(){
        //LazyIncjalization EX Fuck oFF!
        return getById(stateProject.getId()).getAccounts();
    }
}
