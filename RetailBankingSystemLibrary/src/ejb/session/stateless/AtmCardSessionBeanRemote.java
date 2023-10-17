/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
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
import javax.ejb.Remote;

/**
 *
 * @author jayso
 */
@Remote
public interface AtmCardSessionBeanRemote {

    public AtmCard retrieveAtmCardById(Long id) throws AtmCardNotFoundException;

    public void changePin(String oldPin, String newPin, Long cardId) throws AtmCardNotFoundException, DuplicatePinException, IncorrectPinException;

    public AtmCard retrieveAtmCardByNumber(String cardNumber) throws AtmCardNotFoundException;
    
     public AtmCard createNewAtmCard(AtmCard newCard, Long customerId, List<Long> accountIds) throws AtmCardNotFoundException, DepositAccountNotFoundException, CustomerNotFoundException, CardIssuedException, DepositAccountMismatchException, UnknownPersistenceException;

    public DepositAccount enquireAvailableBalance(Long atmCardId, String acctNum) throws AtmCardNotFoundException, DepositAccountNotFoundException;
    
    public void deleteOldAtmCard(Long atmCardId) throws AtmCardNotFoundException;
    
}
