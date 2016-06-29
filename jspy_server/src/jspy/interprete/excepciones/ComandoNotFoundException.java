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
public class ComandoNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ComandoNotFoundException</code> without
     * detail message.
     */
    public ComandoNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ComandoNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ComandoNotFoundException(String msg) {
        super(msg);
    }
}
