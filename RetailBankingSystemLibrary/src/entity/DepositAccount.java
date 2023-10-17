/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enumeration.DepositAccountType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author jayso
 */
@Entity
public class DepositAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountId;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private DepositAccountType accountType;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal availableBalance;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal holdBalance;

    @Column(nullable = false)
    private Boolean enabled;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne
    private AtmCard atmCard;
    
    @OneToMany (mappedBy = "depositAccount", fetch = FetchType.EAGER)
    private List<DepositAccountTransaction> transactons;

    public DepositAccount() {
    }

    public DepositAccount(String accountNumber, DepositAccountType accountType, BigDecimal availableBalance, BigDecimal holdBalance, Boolean enabled) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.availableBalance = availableBalance;
        this.holdBalance = holdBalance;
        this.enabled = enabled;
    }

    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public DepositAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(DepositAccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(BigDecimal holdBalance) {
        this.holdBalance = holdBalance;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AtmCard getCard() {
        return atmCard;
    }

    public void setCard(AtmCard card) {
        this.atmCard = card;
    }

    public List<DepositAccountTransaction> getTransactons() {
        return transactons;
    }

    public void setTransactons(List<DepositAccountTransaction> transactons) {
        this.transactons = transactons;
    }

}
