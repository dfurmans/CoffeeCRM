/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.model;

// Own my own
import pl.jeeaps.crm.core.AbstractEntityWithHistorical;
import pl.jeeaps.crm.core.Event;
import pl.jeeaps.crm.core.Status;
import pl.jeeaps.crm.model.enums.IssueType;
import pl.jeeaps.crm.model.enums.IssueStatus;
import pl.jeeaps.crm.model.enums.IssuePriority;

// JPA Lib
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// JavaSE
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import org.hibernate.annotations.Cascade;

/**
 * JEEAPS - Coffee CRM
 * Klasa reprezentujaca zadanie w systemie - 'esencja systemu'
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@NamedQueries({
    @NamedQuery(name="Issue.findForAccount", query="SELECT i FROM Issue i WHERE i.currentRecipient.id=:id")
})
@Entity
public class Issue extends AbstractEntityWithHistorical implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    /*Acocjacja rekurencyjna typu 1 - do - n
     Reprezentuje rodzica/korzeń */
    @ManyToOne
    @JoinColumn(name = "root_issue_id")
    private Issue rootIssue;
    /* SubIssue - Hibernate wymaga interfejsów dla atrybutów będących kolekcjami
     * Obowiązanek zainicjalizowania kolekcji - restrykcja natrzucona przez JPA 
     * HashSet nie dopuszacza powtórzeń w kolekcji 
     * mappedBy << wskazujemy, że ralacja będzie dwukierunkowa
     * 
     * UWAGA - drzewo obiektów jest ladowane w trybie EAGER!
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rootIssue", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Issue> subIssues = new ArrayList<Issue>();
    // Atrybuty biznesowe
    private String name;
    // Opis zadania
    private String description;
    /*Zakładam, że w jedym czasie może być tylko 1 odbiorca i 1 nadawca zadania*/
    // Nadawca zadania - osoba zakladajaca zadanie - pole wskazujące konto, które jest przypisane do tego zadania
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "currentAssignedConsignerId")
    private Account currentConsigner;
    // Odbiorca zadanie - osoba do ktorej zadanie jest przypisanie - pole wskazujace konto które jest odbiorca tego zadania
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currentAccountRecipientId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Account currentRecipient;
    /* Reprezentuje wszystkie komentarze dla tego zadania
     Czytaj: 1 obiekt klasy Issues moze posiadac wiele wystawpien klasy Comment - z tego powodu jestesmy po stronie One
     kolumna issueId zostanie dodana do tabeli comment; 
     * Wlasciwosci:
     1. fetch.EGER - Pobieranie obiektu Issue automatycznie tworzy wlaściwą referencje do comments dzieki trybowi EAGER (chronimy przed LIE)
     2. CascadeType.PERSIST - przy dodaniu komentarza do zadania zostanie automatycznie utrwalona encja comment
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "issueId")
    private List<Comment> comments = new ArrayList<Comment>();
    
    /**
     * Dwukierunkowa realacja pomiedzy klasami Issue  < - >  Project 
     */
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name="id_project")
    private Project project;
    
    // Perspektywa TODO: Pole Reprezentujace zalacznik do zadania(Blob)
    
    @Enumerated(EnumType.STRING)
    private IssueType issueType;
    
    @Enumerated(EnumType.STRING)
    private IssuePriority issuePriority;
    
    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date closeDate;

    /*Akcesory - metody dostępowe musza być publiczne - tylko wtedy stanowią 
      część zewnętrznego interfejsu klasy dzięki czemu mogą być używane przez logikę aplikacji
     Metoda setId bylaby razacym bledem dla kasy encyjnej!
     */
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Object getBusinessKey() {
        return name;
    }

    public Issue getRootIssue() {
        return rootIssue;
    }

    public void setRootIssue(Issue parentIssue) {
        this.rootIssue = parentIssue;
    }

    public List<Issue> getSubIssues() {
        return subIssues;
    }

    /**
     * @return Zwraca listę wszystkich zdarzen dla tego zadnia
     */
    public List<Event> getAllEvent() {
        return getEventHistoricalList();
    }

    public void addNewEvent(Event event) {
        newEvent(event);
    }

    /**
     * Zwraca listę zmiany wszystkich zadania dla tego statusu
     *
     * @param
     */
    public List<Status> getAllStatus() {
        return getStatusHistoricalList();
    }

    public void addNewStatus(Status status) {
        newStatus(status);
    }

    /* Wiązanie SubIssusów do ParentIssues powinno odbywać się tylko i wyłącznie
     * za pomocą metody this.addSubIssue(Issue subissue) - dzięki niej mamy pewność, 
     * że są wykonane wszystkie niezbędne opracje do dowiązania.
     * Z tego powodu metoda this.setSubIssues posiada chroniony kwalifikator dostępu. 
     */
    protected void setSubIssues(List<Issue> subIssues) {
        this.subIssues = subIssues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getCurrentConsigner() {
        return currentConsigner;
    }

    public void setCurrentConsigner(Account currentConsigner) {
        this.currentConsigner = currentConsigner;
    }

    public Account getCurrentRecipient() {
        return currentRecipient;
    }

    public void setCurrentRecipient(Account currentRecipient) {
        this.currentRecipient = currentRecipient;
    }

    public IssuePriority getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(IssuePriority issuePriority) {
        this.issuePriority = issuePriority;
    }

    /* Pomocnicza metoda - wiążąca asocjację rekurencyjną 
     * Jest to tzw. Kod rusztujący (Scaffoding code)
     */
    public void addSubIssue(Issue subIssue) {

        // sprawdź czy subIssue posiada rodzicza
        if (subIssue.getRootIssue() != null) {
            System.out.println("Rodzic nie byl pusty");
            removeSubIssueIfAlredyExist(subIssue);
        }
        System.out.println("Ustaw głowny/rodzica dla subIssue ");
        subIssue.setRootIssue(this);
        System.out.println("Do kolekcji subIssue dodane nowy subIssue");
        subIssues.add(subIssue);
    }

    protected void removeSubIssueIfAlredyExist(Issue subIssue) {
        subIssue.getRootIssue().getSubIssues().remove(subIssue);
    }

    // Zwraca tablicę enum IssueType
    public IssueType[] getAllIssueType() {
        // metoda values iteruje po enum
        return IssueType.values();
    }

    /**
     * @return Zwraca listę kryteriów zadania po względem pilnosci realizacji
     */
    public IssuePriority[] getAllIssuePriority() {
        return IssuePriority.values();
    }
    // Zwraca tablice enum IssueStatus

    public IssueStatus[] getAllIssueStatus() {
        return IssueStatus.values();
    }

    /**
     * @return Zwraca listę wszystkich komentarzy dla danego zadania
     */
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    // pozwala na pobranie aktualnego statusu dla tego zadania
    @Override
    public Status getCurrentStatus() {
        return super.getCurrentStatus();
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
    
}