/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import exception.CustomerNotFoundException;
import exception.CustomerExistException;
import exception.UnknownPersistenceException;
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
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RetailBankingSystem-ejbPU")
    private EntityManager em;

    @Override
    public Customer createNewCustomer(Customer customer) throws CustomerExistException, UnknownPersistenceException {
        try {   
            em.persist(customer);
            em.flush();
            return customer;
        } catch (PersistenceException ex) {
            if (isDatabaseException(ex) && isSQLIntegrityConstraintViolation(ex)) {
                throw new CustomerExistException("The entered identification and/or contact number already exists");
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Customer retrieveCustomerById(Long id) throws CustomerNotFoundException {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException("Customer does not exist!");
        }
    }
    
    @Override
    public Customer checkCredentials(String identificationNumber, String contactNumber) throws CustomerNotFoundException {
        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.identificationNumber = :id AND c.contactNumber = :contact");
        q.setParameter("id", identificationNumber);
        q.setParameter("contact", contactNumber);
        
        try {
            return (Customer) q.getSingleResult();
        } catch (Exception ex) {
            throw new CustomerNotFoundException("An error occured, please make sure you have provided correct details");
        }
    }

    private boolean isDatabaseException(PersistenceException ex) {
        return ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException");
    }

    private boolean isSQLIntegrityConstraintViolation(PersistenceException ex) {
        return ex.getCause() != null && ex.getCause().getCause() != null
                && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException");
    }
}
