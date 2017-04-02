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
import pl.jeeaps.crm.controllers.AccountSessionEJB;
import pl.jeeaps.crm.model.Account;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */

@Named("accountConventer")
public class AccountConventer implements Converter{

    @Inject AccountSessionEJB accountSession;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        accountSession.setSelectedAccount(Long.valueOf(value));
        
        return accountSession.getSelectedAccount();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Account) value).getId().toString();
    }

}
