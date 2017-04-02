/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.model.enums;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 * Enum Reprezentuje Role w systemie - ponieważ adnotacja @DiscriminatorColumn/Value zawiodla 
 */
public enum AccountSystemEnumType {

    CLIENT,
    EMPLOYE,
    MANAGER,
    ADMINISTRATOR
    
}
