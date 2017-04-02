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
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Entity
@Table(name = "administrator_data")
@DiscriminatorValue(value = "ADMINISTRATOR")
public class Administrator extends Account implements Serializable{


    
}
