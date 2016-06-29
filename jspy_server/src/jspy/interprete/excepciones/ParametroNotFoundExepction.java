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
public class ParametroNotFoundExepction extends Exception {

    /**
     * Creates a new instance of <code>ParametroNotFoundExepction</code> without
     * detail message.
     */
    public ParametroNotFoundExepction() {
    }

    /**
     * Constructs an instance of <code>ParametroNotFoundExepction</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ParametroNotFoundExepction(String msg) {
        super(msg);
    }
}
