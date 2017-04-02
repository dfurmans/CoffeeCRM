/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.interceptors;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
public interface EntityInterceptorPoint {
   
    /*
     * Operacje do wykonania przez zapisem do bazy danych
     * Zarządca encji wywoluje odpowiednie metody
     */
   @PrePersist 
   public void doPrePersistOperation();
   
   /*
    * Operacje do wykonania przez aktualizacją stanu encji w bazie danych
    */
   @PreUpdate
   public void doPreUpdateOperation();

   /*
    * Wykonywana po odczytaniu encji  z bazy danych
    * 
    */
   @PostLoad
   public void doPostLoadOperation();

}
