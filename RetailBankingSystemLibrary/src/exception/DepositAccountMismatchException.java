/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exception;

/**
 *
 * @author jayso
 */
public class DepositAccountMismatchException extends Exception {

    /**
     * Creates a new instance of <code>DepositAccountMismatchException</code>
     * without detail message.
     */
    public DepositAccountMismatchException() {
    }

    /**
     * Constructs an instance of <code>DepositAccountMismatchException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DepositAccountMismatchException(String msg) {
        super(msg);
    }
}
