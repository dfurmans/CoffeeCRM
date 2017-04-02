/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import pl.jeeaps.crm.core.AbstractEntity;

/**
 * JEEAPS - Coffee CRM
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Entity
public class Comment extends AbstractEntity implements Serializable {
    

    @Id
    @GeneratedValue
    private Long id;
    
    private String commentText;

    @ManyToOne
    @JoinColumn(name="accountId")
    private Account account;
    
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    
    @Override
    protected Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return commentText;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String getName() {
        return commentText;
    }
    
}
