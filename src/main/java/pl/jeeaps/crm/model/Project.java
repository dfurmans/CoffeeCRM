/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import pl.jeeaps.crm.core.AbstractEntity;

/**
 *
 * @autor Dawid
 */
@Entity
public class Project extends AbstractEntity implements Serializable {
    // TODO - asocjacja rekurencyjna - project <-> subproject

    @Id
    @GeneratedValue
    private Long id;
    // Reprezentuje rodzica
    @ManyToOne
    @JoinColumn(name = "root_project_id")
    private Project rootProject;
    // Reprezentuje podzadania w ramach tego projektu
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "rootProject")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Project> subProjects = new HashSet<Project>();
    /* Reprezentuje zadania przypisane do projektu
     * Jednokierunkowa realzacja 1-do-n 
     * Należy zaincjalizować wartość - w tym przypadku utworzyć referencje 
     */
    
    /**
     * Dwukierunkowa realacja pomiedzy klasami PROJECT < - > ISSUE
     */
    @OneToMany(mappedBy ="project")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Issue> issues = new ArrayList<>();
    
    /**
     * Dwikierunkowa realacja n-do-m. Reprezentuje liste uzytkownikow przypisanych do projektu
     */
    
    @ManyToMany
    @JoinTable(name = "account_project", joinColumns = {
        @JoinColumn(name = "project_id")}, inverseJoinColumns = {
        @JoinColumn(name = "account_id")})
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    List<Account> accounts = new ArrayList<Account>();
    
    // Atrybuty biznesowe 
    private String name;
    
    private String description;

    public Project getRootProject() {
        return rootProject;
    }

    public void setRootProject(Project rootProject) {
        this.rootProject = rootProject;
    }

    public Set<Project> getSubProject() {
        return subProjects;
    }

    public void setSubProject(Set<Project> subProject) {
        this.subProjects = subProject;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Konstruktor obiektu
    public Project() {
        
    }

    // Kod rusztujący
    public void addSubProject(Project subProject) {

        if (subProject.getRootProject() != null) {
            removeIfSubProjectAlredyExist(subProject);
        }
        subProject.setRootProject(this);
        subProjects.add(subProject);
    }

    protected void removeIfSubProjectAlredyExist(Project subProject) {

        subProject.getRootProject().getSubProject().remove(subProject);
    }

    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    protected String getBusinessKey() {
        return name;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + '}';
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
}
