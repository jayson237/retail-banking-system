/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author jayso
 */
@Entity
public class AtmCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmCardId;

    @Column(nullable = false, unique = true, length = 32)
    private String cardNumber;

    @Column(nullable = false, length = 64)
    private String nameOnCard;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false, length = 64)
    private String pin;

    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "atmCard")
    private List<DepositAccount> depositAccounts;

    public AtmCard() {
        this.depositAccounts = new ArrayList<>();
    }

    public AtmCard(String cardNumber, String nameOnCard, Boolean enabled, String pin) {
        this();
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.enabled = enabled;
        this.pin = pin;
    }

    public Long getAtmCardId() {
        return atmCardId;
    }

    public void setAtmCardId(Long atmCardId) {
        this.atmCardId = atmCardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<DepositAccount> getDepositAccounts() {
        return depositAccounts;
    }

    public void setDepositAccounts(List<DepositAccount> depositAccounts) {
        this.depositAccounts = depositAccounts;
    }

}
