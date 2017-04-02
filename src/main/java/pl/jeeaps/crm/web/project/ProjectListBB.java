/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.web.project;

import pl.jeeaps.crm.controllers.ProjectSessionEJB;
import pl.jeeaps.crm.model.Project;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

import java.util.List;
import javax.annotation.PostConstruct;
/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
@Named("projectListBB")
public class ProjectListBB {

    @Inject private ProjectSessionEJB projectSession;
    
    private List<Project> projectList;

    private Project selectedProject;
    
    public ProjectListBB() {
    }
    // metoda zostanie wywolana podczas procesu tworzenia obiektu
    @PostConstruct
    private void init() {
        this.getAllProjects();
    }
    
    public void getAllProjects(){
        projectList = projectSession.getAllProjects();
    }

    public List<Project> getProjects() {
        return projectList;
    }

    public void setProjects(List<Project> projects) {
        this.projectList = projects;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }
    
    public String editProject(){
        System.out.println("Wybrano projekt" + selectedProject);
        projectSession.getProjectToEdit(selectedProject);
        return "projectDetail";
    }
    
}
