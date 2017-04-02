/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.core;

import java.util.Date;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.Version;

/**
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@MappedSuperclass
public abstract class AbstractEntityWithHistorical extends Historical{

    @Version
    private Integer version;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;
    
    protected abstract Object getId();
    protected abstract Object getBusinessKey();
    
    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + getId() + ", key=" + getBusinessKey() + /*", version=" + wersja +*/ "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        
        if(this.getClass().isAssignableFrom(obj.getClass())) {
            return this.getBusinessKey().equals(((AbstractEntityWithHistorical)obj).getBusinessKey());
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode(); 
    }
    
    public Status getCurrentStatus(){
        return super.getStatus();
    }
}
