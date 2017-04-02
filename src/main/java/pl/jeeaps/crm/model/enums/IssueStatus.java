/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.jeeaps.crm.model.enums;

import pl.jeeaps.crm.core.Status;

/**
 * JEEAPS - Coffee CRM
 * @author Dawid Furman www.jeeaps.pl/autor
 */
public enum IssueStatus {

    OPEN("open","OPEN"),
    REOPEN("reopen","REOPEN"),
    CLOSE("close","CLOSE"),
    IN_PROGRESS("inprogress","INPROGRESS"),
    CANCELED("canceled","CANCELED"),
    INCOMPLETE("incomplite","INCOMPLETE");

    private Status status;
    
    // Konstruktor dla Enum
    IssueStatus(String name, String desc){
        // kontruktor jednocześnie tworzy obiekt reprezentujący status !
        status = new Status(name, desc);
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IssueStatus{" + "status=" + status + '}';
    }

}