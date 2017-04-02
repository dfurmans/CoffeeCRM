/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.utils.web.autocomplete;

import pl.jeeaps.crm.model.Issue;
import pl.jeeaps.crm.ejb.IssueEndpoint;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.annotation.PostConstruct;

/**
 * JEEAPS - Coffee CRM
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Named("issueAutocomplete")
public class IssueAutocomplete implements Serializable {

    @EJB
    IssueEndpoint issueEndpoint;
// przechowuje wybrane zadanie 
    private Issue selectedIssue;
// przechowuje wszystkie Issue z bazy danych
    private List<Issue> issues;
    
    public IssueAutocomplete() {
    }

    @PostConstruct
    public void init(){
        issues = issueEndpoint.getAllIssues();
    } 
    
    public List<Issue> complete(String query) {
        //zwroc liste Issuesow zaczynajacych sie na to query!
        List<Issue> suggestions = new ArrayList<Issue>();
        for (Issue issue : issues) {
            if (issue.getName().startsWith(query)) {
                suggestions.add(issue);
            }
        }
        // zwraca liste pasujacych encji! yeah!
        return suggestions;
    }

    public Issue getSelectedIssue() {
        return selectedIssue;
    }

    public void setSelectedIssue(Issue selectedIssue) {
        this.selectedIssue = selectedIssue;
    }
}