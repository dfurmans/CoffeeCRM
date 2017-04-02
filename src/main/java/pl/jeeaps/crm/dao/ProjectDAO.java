/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.model.Project;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)

public class ProjectDAO extends HibernateDAO<Project, Long>{

    @PersistenceContext(unitName = "PU_MySQL")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    // Konstruktor obiektu
    public ProjectDAO() {
        super(Project.class);
    }
        // Basic CRUD and other 
    public void createIssue(Project project){
        super.create(project);
    }
    
    //getAllProjects
    public List<Project> getAllProjects(){
        return super.findAll();
    }
    
    public Project getById(Long id){
        return super.findById(id);
    }
    
}
