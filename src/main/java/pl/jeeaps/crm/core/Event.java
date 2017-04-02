/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @autor Dawid
 */
@Entity
@Table(name = "core_event")
public class Event implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String message;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date eventDate;

    // konstruktor bezparametrowy
    public Event() {
    }

    public Event(String message, Date eventDate) {
        this.message = message;
        this.eventDate = eventDate;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
