/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.utils.web.autocomplete;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import pl.jeeaps.crm.core.AbstractEntity;

/**
 * JEEAPS - Coffee CRM
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
public class AbstractEntityAutocomplete<T extends AbstractEntity> {

       
    private T selectedEntity;
    
    private List<T> entities;
    
    public AbstractEntityAutocomplete() {
    }
    
    @PostConstruct
    public void init(){
     
    }
    
    public List<T> complete(String query) {
        //zwroc liste Issuesow zaczynajacych sie na to query!
        List<T> suggestions = new ArrayList<T>();
        for (T entity : entities) {
            if (entity.getName().startsWith(query)) {
                suggestions.add(entity);
            }
        }
        // zwraca liste pasujacych encji! yeah!
        return suggestions;
    }


    public T getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(T selectedEntity) {
        this.selectedEntity = selectedEntity;
    }
}
