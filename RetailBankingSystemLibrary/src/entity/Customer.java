/*
To make the identification number and contact number unique
ALTER TABLE Customer
ADD CONSTRAINT UK_IDENTIFICATIONNUMBER UNIQUE (IDENTIFICATIONNUMBER);
TABLE Customer
ADD CONSTRAINT UK_CONTACTNUMBER UNIQUE (CONTACTNUMBER);
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author jayso
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;

    @Column(nullable = false, unique = true, length = 32)
    private String identificationNumber;

    @Column(nullable = false, unique = true, length = 32)
    private String contactNumber;

    @Column(nullable = false, length = 64)
    private String addressLine1;

    @Column(nullable = false, length = 64)
    private String addressLine2;

    @Column(nullable = false, length = 64)
    private String postalCode;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<DepositAccount> depositAccounts;

    @OneToOne(mappedBy = "customer")
    private AtmCard card;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String identificationNumber, String contactNumber, String addressLine1, String addressLine2, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
        this.contactNumber = contactNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<DepositAccount> getDepositAccounts() {
        return depositAccounts;
    }

    public void setDepositAccounts(List<DepositAccount> depositAccounts) {
        this.depositAccounts = depositAccounts;
    }

    public AtmCard getCard() {
        return card;
    }

    public void setCard(AtmCard card) {
        this.card = card;
    }

}
