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
@Table(name="core_status")
public class Status implements Serializable{
  
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private String description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date entryToThisStatus;
    
    public Status() {
        entryToThisStatus = new Date();
    }

    public Status(String name, String description) {
        this(); // << woła konstrukotr bezparametrowy
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getEntryToThisStatus() {
        return entryToThisStatus;
    }

    @Override
    public String toString() {
        return "Status{" + "id=" + id + ", name=" + name + ", description=" + description + ", entryToThisStatus=" + entryToThisStatus + '}';
    }
    
    
}
