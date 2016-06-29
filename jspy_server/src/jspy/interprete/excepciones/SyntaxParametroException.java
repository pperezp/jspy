/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jspy.interprete.excepciones;

/**
 *
 * @author pperezp
 */
public class SyntaxParametroException extends Exception {

    /**
     * Creates a new instance of <code>SyntaxParametroException</code> without
     * detail message.
     */
    public SyntaxParametroException() {
    }

    /**
     * Constructs an instance of <code>SyntaxParametroException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SyntaxParametroException(String msg) {
        super(msg);
    }
}
