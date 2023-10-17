/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import exception.CustomerNotFoundException;
import exception.DepositAccountExistException;
import exception.DepositAccountNotFoundException;
import exception.UnknownPersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author jayso
 */
@Stateless
public class DepositAccountSessionBean implements DepositAccountSessionBeanRemote, DepositAccountSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBean;

    @PersistenceContext(unitName = "RetailBankingSystem-ejbPU")
    private EntityManager em;

    @Override
    public DepositAccount createNewAccount(DepositAccount depositAccount, Long customerId) throws DepositAccountExistException, CustomerNotFoundException, UnknownPersistenceException {
        try {
            Customer customer = customerSessionBean.retrieveCustomerById(customerId);
            em.persist(depositAccount);
            depositAccount.setCustomer(customer);
            customer.getDepositAccounts().add(depositAccount);
            em.flush();
            em.refresh(depositAccount);
            return depositAccount;
            
        } catch (PersistenceException ex) {
            if (isDatabaseException(ex) && isIntegrityConstraintViolationException(ex)) {
                throw new DepositAccountExistException("Deposit Account with the same account number already exists!");
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public DepositAccount retrieveDepositAccountById(Long Id) throws DepositAccountNotFoundException {
        DepositAccount depositAccount = em.find(DepositAccount.class, Id);
        if (depositAccount != null) {
            return depositAccount;
        } else {
            throw new DepositAccountNotFoundException("Deposit Account does not exist");
        }
    }

    private boolean isDatabaseException(PersistenceException ex) {
        return ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException");
    }

    private boolean isIntegrityConstraintViolationException(PersistenceException ex) {
        return ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException;
    }
}
