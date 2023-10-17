/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.DepositAccount;
import exception.CustomerNotFoundException;
import exception.DepositAccountExistException;
import exception.DepositAccountNotFoundException;
import exception.UnknownPersistenceException;
import javax.ejb.Local;

/**
 *
 * @author jayso
 */
@Local
public interface DepositAccountSessionBeanLocal {

    public DepositAccount retrieveDepositAccountById(Long Id) throws DepositAccountNotFoundException;

    public DepositAccount createNewAccount(DepositAccount depositAccount, Long customerId) throws DepositAccountExistException, CustomerNotFoundException, UnknownPersistenceException;
    
}
