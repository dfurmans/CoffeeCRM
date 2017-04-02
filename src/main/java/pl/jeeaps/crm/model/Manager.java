/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Entity
@Table(name = "manager_data")
@DiscriminatorValue(value = "MANAGER")

public class Manager extends Account implements Serializable{
    
    private String pops;
    
    
}
