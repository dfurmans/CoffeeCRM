/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.utils.web.autocomplete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import pl.jeeaps.crm.ejb.ProjectEndpoint;
import pl.jeeaps.crm.model.Project;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Named("projectAutocomplete")
public class ProjectAutocomplete implements Serializable{

    @EJB 
    ProjectEndpoint projectEndpoint;
            
    private Project selectedProject;
    
    private List<Project> projects = new ArrayList<Project>();
    
    public ProjectAutocomplete() {
    }
    
    @PostConstruct
    public void init(){
        projects = projectEndpoint.getAllProject();
    }
    
     public List<Project> complete(String query) {
        //zwroc liste Issuesow zaczynajacych sie na to query!
        List<Project> suggestions = new ArrayList<Project>();
        for (Project project : projects) {
            if (project.getName().startsWith(query)) {
                suggestions.add(project);
            }
        }
        // zwraca liste pasujacych encji! yeah!
        return suggestions;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }
    
}