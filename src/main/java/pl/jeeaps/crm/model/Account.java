/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import pl.jeeaps.crm.core.AbstractEntity;

/**
 *
 * @autor D. Furman http://www.jeeaps.pl/autor
 */
@Entity
// Support 3NF in DBMS - Rozwiązuje problem szczegółowości
@SecondaryTable(name="personal_detail")
// Określa strategie dziedziczenia
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ACCOUNT")
public class Account extends AbstractEntity implements Serializable{

    private static final Logger LOG = Logger.getLogger(Account.class.getName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "login", length = 32, nullable = false, unique = true, updatable = false)
    private String login;
    
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    
    // Dwukierunkowa realacja 1 - do - n. Jest to lista zadan, które są utworzone przez to konto 
    @OneToMany(mappedBy = "currentConsigner")
    private List<Issue> consignerIssueAssignedList = new ArrayList<Issue>();
    // Dwukierunkowa realacja 1 - do - n. Jest listą zadań, które są przypisane do tego konta
    @OneToMany(mappedBy = "currentRecipient")
    private List<Issue> recipientIssueAssignedList = new ArrayList<Issue>();
    
    // repreznetuje liste komentarzy zlozonych przez tego uzytkownika 
    // kolumna accoutnId zostanie dodana do tabeli comment
     @OneToMany(mappedBy = "account")
     private List<Comment> comments = new ArrayList<Comment>();
    
    // zapewnia dostęp do kolumy dyskryminatora klas podrzędnych
    // Implementacja Hibernate JPA 2.0 nie przewiduje wsparcia przy Strategi JOINED
    @Column(name = "type",updatable = false)
    private String type;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="lastLoginDate")
    private Date lastLoginDate;
    
    
    //TODO << zaimplementować relację n-do-n (wiele kont może być przypisanych do wielu projektow) 
    // Reprezentuje projekty przypisanie do danego konta
    /* realacja jednokierunkowa oznacza, że w tabeli Project zostanie utworzony
     * klucz obcy o nazwie id_account, który będzie wiązał projekty do danego konta 
    */
    @ManyToMany(mappedBy = "accounts")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Project> projects = new ArrayList<Project>();

    @Column(name = "confirm", nullable = false)
    private boolean confirm;
    
    @Column(name = "active", nullable = false)
    private boolean active;
    
    /*Szczegółowe dane należy umieść w osobnej tabeli SecondaryTable - problem szczegółowości*/
    @Column(table = "personal_detail")
    private String name;
    
    @Column(table = "personal_detail")
    private String surname;
    
    @Column(table = "personal_detail")
    private String email;
    
    @Column(table = "personal_detail")
    private String phoneNumber;
    
    // Konstruktor
    public Account() {
    }

    // Akcesory
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    protected String getBusinessKey() {
        return login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Issue> getConsignerIssueAssignedList() {
        return consignerIssueAssignedList;
    }

    public void setConsignerIssueAssignedList(List<Issue> consignerIssueAssignedList) {
        this.consignerIssueAssignedList = consignerIssueAssignedList;
    }

    public List<Issue> getRecipientIssueAssignedList() {
        return recipientIssueAssignedList;
    }

    public void setRecipientIssueAssignedList(List<Issue> recipientIssueAssignedList) {
        this.recipientIssueAssignedList = recipientIssueAssignedList;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastLogin() {
        return lastLoginDate;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLoginDate = lastLogin;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
   
}