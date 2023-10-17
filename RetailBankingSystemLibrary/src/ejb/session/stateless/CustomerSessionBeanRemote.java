/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import exception.CustomerNotFoundException;
import exception.CustomerExistException;
import exception.UnknownPersistenceException;
import javax.ejb.Remote;

/**
 *
 * @author jayso
 */
@Remote
public interface CustomerSessionBeanRemote {

    public Customer createNewCustomer(Customer customer) throws CustomerExistException, UnknownPersistenceException;

    public Customer retrieveCustomerById(Long id) throws CustomerNotFoundException;
    
    public Customer checkCredentials(String identificationNumber, String contactNumber) throws CustomerNotFoundException;
    
}
