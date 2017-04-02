/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.model;
import pl.jeeaps.crm.model.enums.AccountBusinessEnumType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
@Entity

@Table(name = "client_data")
/* Adnotacja @DiscriminatorValue określa, jaka wartość znajdzie się w kolumnie oznaczonej @DiscriminatorColumn
 * w tabeli reprezentującej nadrzędną klasę encyjną.
 */
@DiscriminatorValue("CLIENT")
public class Client extends Account implements Serializable{
    
    @NotNull
    @Column(name = "nip", unique=true, nullable=false, length=10)
    private String nip;
    
    // Perspektywa :: Lista zamowien zrealizowana przez tego klienta 1 Klient - N zamowien
    @OneToMany(mappedBy = "client")
    private List<Indent> indentList = new ArrayList<Indent>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "accountBusinnesType")
    private AccountBusinessEnumType accountBusinessEnumType;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public AccountBusinessEnumType getAccountBusinessEnumType() {
        return accountBusinessEnumType;
    }

    public void setAccountBusinessEnumType(AccountBusinessEnumType accountBusinessEnumType) {
        this.accountBusinessEnumType = accountBusinessEnumType;
    }
    
    // Zwraca tablicę enum IssueType
    public AccountBusinessEnumType[] getAllAccountBusinessType() {
        // metoda values iteruje po enum
        return AccountBusinessEnumType.values();
    }

}