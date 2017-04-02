/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import pl.jeeaps.crm.core.AbstractEntityWithHistorical;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Entity
public class Indent extends AbstractEntityWithHistorical implements Serializable{

    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    
    @ManyToOne
    @JoinColumn(name="employeId")
    private Employe sellerEmploye;
    
    @OneToMany(mappedBy = "indent", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch= FetchType.EAGER)
    private List<IndentItem> indentItemList = new ArrayList<IndentItem>();
    
    @Override
    protected Object getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object getBusinessKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
