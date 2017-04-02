/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.jeeaps.crm.dao.CommentDAO;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.model.Comment;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CommentEndpoint {
    
    // Wstrzyknji DAO
    @EJB
    private CommentDAO commentDAO;
    
    private Comment commentState;

    public CommentEndpoint() {
    }
    
    public void createComment(Comment comment){
        commentDAO.create(comment);
    }
    
    
}
