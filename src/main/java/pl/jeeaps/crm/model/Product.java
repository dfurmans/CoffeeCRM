/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.model;

import pl.jeeaps.crm.core.AbstractEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @autor Dawid
 */
@Entity
public class Product extends AbstractEntity implements Serializable{
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    private String description;

    private BigDecimal price;
    
    // Wiele produktów moze nalezec do jednego projektu
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @Override
    protected Object getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return name;
    }
    
            
}
