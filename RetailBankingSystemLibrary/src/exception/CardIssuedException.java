/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exception;

/**
 *
 * @author jayso
 */
public class CardIssuedException extends Exception {

    /**
     * Creates a new instance of <code>CardIssuedException</code> without detail
     * message.
     */
    public CardIssuedException() {
    }

    /**
     * Constructs an instance of <code>CardIssuedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CardIssuedException(String msg) {
        super(msg);
    }
}
