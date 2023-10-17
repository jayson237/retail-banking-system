/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exception;

/**
 *
 * @author jayso
 */
public class CustomerExistException extends Exception {

    /**
     * Creates a new instance of <code>CustomerUsernameExistException</code>
     * without detail message.
     */
    public CustomerExistException() {
    }

    /**
     * Constructs an instance of <code>CustomerUsernameExistException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerExistException(String msg) {
        super(msg);
    }
}
