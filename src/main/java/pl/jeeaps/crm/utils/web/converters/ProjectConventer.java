/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.utils.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import pl.jeeaps.crm.controllers.ProjectSessionEJB;
import pl.jeeaps.crm.model.Project;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */

//@FacesConverter(forClass = Project.class, value = "projectConventer" )
@Named("projectConventer")
public class ProjectConventer implements Converter {

    @Inject private ProjectSessionEJB projectSession;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // ustawiamy obiekt w ziarnie o zasiegu sesji
        projectSession.setSelectedProject(Long.valueOf(value));
        return projectSession.getSelectedProject();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Project) value).getId().toString();
        
    }

}
