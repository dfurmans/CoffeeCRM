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
import pl.jeeaps.crm.controllers.IssueSessionEJB;
import pl.jeeaps.crm.model.Issue;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */

@Named("issueConventer")
public class IssueConventer implements Converter{
    
    @Inject
    IssueSessionEJB issueSession;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // wybrany obiekt ustawiamy obiekt w ziarnie o zasiegu sesji
        issueSession.setSelectedIssue(Long.valueOf(value));
            System.out.println("Wybrano  " + issueSession.getSelectedIssue());
        return issueSession.getSelectedIssue();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Issue) value).getId().toString();
    }

}
