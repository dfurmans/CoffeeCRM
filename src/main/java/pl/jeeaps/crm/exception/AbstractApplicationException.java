/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.exception;

import javax.ejb.ApplicationException;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */

@ApplicationException(rollback=true)
abstract public class AbstractApplicationException extends Exception {

    public AbstractApplicationException(String message) {
        // woła konstruktor nadklasy
        super(message);
    }

    public AbstractApplicationException(String message, Throwable cause) {
        // woła konstruktor nadklasy
        super(message, cause);
    }
    
}
