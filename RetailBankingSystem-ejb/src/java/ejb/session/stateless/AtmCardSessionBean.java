/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import exception.AtmCardNotFoundException;
import exception.CardIssuedException;
import exception.CustomerNotFoundException;
import exception.DepositAccountMismatchException;
import exception.DepositAccountNotFoundException;
import exception.DuplicatePinException;
import exception.IncorrectPinException;
import exception.UnknownPersistenceException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author jayso
 */
@Stateless
public class AtmCardSessionBean implements AtmCardSessionBeanRemote, AtmCardSessionBeanLocal {

    @EJB
    private DepositAccountSessionBeanLocal depositAccountSessionBean;

    @EJB
    private CustomerSessionBeanLocal customerSessionBean;

    @PersistenceContext(unitName = "RetailBankingSystem-ejbPU")
    private EntityManager em;

    @Override
    public AtmCard createNewAtmCard(AtmCard newCard, Long customerId, List<Long> accountIds) throws AtmCardNotFoundException, DepositAccountNotFoundException, CustomerNotFoundException, CardIssuedException, DepositAccountMismatchException, UnknownPersistenceException {
        try {
            Customer customer = customerSessionBean.retrieveCustomerById(customerId);
            if (customer.getCard() != null) {
                throw new CardIssuedException("ATM card has been issued before");
            } else {
                if (isCardNumberExist(newCard.getCardNumber())) {
                    throw new CardIssuedException("A customer with the same card number exists!");
                }

                em.persist(newCard);

                // Set the newCard to customer and vice versa
                customer.setCard(newCard);
                newCard.setCustomer(customer);

                // Set the accounts to the new ATM card
                for (Long id : accountIds) {
                    DepositAccount account = depositAccountSessionBean.retrieveDepositAccountById(id);

                    if (account.getCustomer().equals(customer)) {
                        newCard.getDepositAccounts().add(account);
                        account.setCard(newCard);
                    } else {
                        throw new DepositAccountMismatchException("Deposit account holder is different from ATM card holder");
                    }
                }

                em.flush();
                em.refresh(customer);
                em.refresh(newCard);
                return newCard;
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public void deleteOldAtmCard(Long atmCardId) throws AtmCardNotFoundException {
        AtmCard atm = retrieveAtmCardById(atmCardId);
        atm.getCustomer().setCard(null);

        for (DepositAccount acct : atm.getDepositAccounts()) {
            acct.setCard(null);
        }
        atm.getDepositAccounts().clear();
        em.remove(atm);
    }

    @Override
    public void changePin(String oldPin, String newPin, Long cardId) throws AtmCardNotFoundException, DuplicatePinException, IncorrectPinException {
        AtmCard card = retrieveAtmCardById(cardId);
        if (card.getPin().equals(oldPin)) {
            if (oldPin == newPin) {
                throw new DuplicatePinException("Duplicate PIN detected");
            } else {
                card.setPin(newPin);
            }
        } else {
            throw new IncorrectPinException("Your original PIN does not match");
        }
    }

    @Override
    public AtmCard retrieveAtmCardByNumber(String cardNumber) throws AtmCardNotFoundException {
        Query q = em.createQuery("SELECT c FROM AtmCard c WHERE c.cardNumber = :num");
        q.setParameter("num", cardNumber);
        try {
            return (AtmCard) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public AtmCard retrieveAtmCardById(Long id) throws AtmCardNotFoundException {
        AtmCard card = em.find(AtmCard.class, id);
        if (card != null) {
            return card;
        } else {
            throw new AtmCardNotFoundException("ATM card does not exist!");
        }
    }

    @Override
    public DepositAccount enquireAvailableBalance(Long atmCardId, String acctNum) throws AtmCardNotFoundException, DepositAccountNotFoundException {
        AtmCard card = retrieveAtmCardById(atmCardId);
        DepositAccount acc = null;

        for (DepositAccount account : card.getDepositAccounts()) {
            if (account.getAccountNumber().equals(acctNum)) {
                acc = account;
                break;
            }
        }

        if (acc == null) {
            throw new DepositAccountNotFoundException("Desired Account not found");
        }
        return acc;
    }

    private boolean isCardNumberExist(String cardNumber) throws AtmCardNotFoundException {
        return retrieveAtmCardByNumber(cardNumber) != null;
    }

}
