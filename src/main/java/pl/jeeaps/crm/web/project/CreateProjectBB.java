/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.web.project;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.controllers.ProjectSessionEJB;
import pl.jeeaps.crm.model.Project;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@RequestScoped
@Named("createProjectBB")
public class CreateProjectBB implements Serializable {

    @Inject private ProjectSessionEJB projectSession;
    private Project project;

    public CreateProjectBB() {
        project = new Project();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public String createProject(){
        projectSession.setCreateProject(project);
        projectSession.create();
        return "issueList";
    }
    
       public void getAllProjects(){
           projectSession.getAllProjects();
    }
    
}
