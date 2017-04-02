/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.model.notify;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import pl.jeeaps.crm.utils.web.ContextUtils;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Named("messagesController")
public class MessagesController {
    
public void addInfo(ActionEvent actionEvent, String summary, String detail) {  
        ContextUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,summary, detail));  
    }  
  
    public void addWarn(ActionEvent actionEvent, String summary, String detail) {  
        ContextUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,summary, detail));  
    }  
  
    public void addError(ActionEvent actionEvent, String summary, String detail) {  
        ContextUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,summary, detail));  
    }  
  
    public void addFatal(ActionEvent actionEvent, String summary, String detail) {  
        ContextUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,summary, detail));  
    }  
}  
