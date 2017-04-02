/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.model.Comment;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class CommentDAO extends HibernateDAO<Comment, Long> {

    @PersistenceContext(unitName = "PU_MySQL")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public CommentDAO() {
        super(Comment.class);
    }
    
    public void createComment(Comment comment){
        super.create(comment);
    }
    
    
}
