/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Klasa pozwala rejestrować zmianę statusu oraz przebieg tej zmiany
 * @autor Dawid
 */
@MappedSuperclass
public abstract class Historical implements Serializable {

    // for current status 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_status_id")
    private Status status;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Status> statusHistoricalList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> eventHistoricalList;

    public Historical() {

        // należy zainicjalizować identyfikatory o typach kolekcji
        statusHistoricalList = new ArrayList<Status>();
        eventHistoricalList = new ArrayList<Event>();
    }

    public void newStatus(Status newStatus) {
        statusHistoricalList.add(newStatus);
        status = newStatus;
    }

    public void newEvent(Event newEvent) {
        eventHistoricalList.add(newEvent);
    }
    
    /**
     * @return  Zwraca aktualny status dla danego zadania
     */
    public Status getStatus() {
        return status;
    }

    public List<Status> getStatusHistoricalList() {
        return statusHistoricalList;
    }
    
    /**
     * @return Zwraca listę wszystkich wykonywanych manipulacji na tej Encji
     */
    public List<Event> getEventHistoricalList() {
        return eventHistoricalList;
    }
    
}
