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
public class SyntaxComandoException extends Exception {

    /**
     * Creates a new instance of <code>SyntaxComandoException</code> without
     * detail message.
     */
    public SyntaxComandoException() {
    }

    /**
     * Constructs an instance of <code>SyntaxComandoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SyntaxComandoException(String msg) {
        super(msg);
    }
}
