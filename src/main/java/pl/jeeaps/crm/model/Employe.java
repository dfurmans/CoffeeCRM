/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Entity
@Table(name = "employe_data")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value ="EMPLOYE")

public class Employe extends Account implements Serializable {
    
    //Perspektywa 
    @OneToMany(mappedBy = "sellerEmploye")
    private List<Indent> salesList = new ArrayList<Indent>();
    
}
