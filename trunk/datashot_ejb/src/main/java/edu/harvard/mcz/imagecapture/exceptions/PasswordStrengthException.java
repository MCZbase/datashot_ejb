/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.exceptions;

/**
 *
 * @author mole
 */
public class PasswordStrengthException extends Exception {

    /**
     * Creates a new instance of <code>PasswordStrengthException</code> without detail message.
     */
    public PasswordStrengthException() {
    }


    /**
     * Constructs an instance of <code>PasswordStrengthException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PasswordStrengthException(String msg) {
        super(msg);
    }
}
