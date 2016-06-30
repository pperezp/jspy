package jspy.interprete;

/**
 *
 * @author LAB-315
 */
public class Parametro {
    private final String nombre;
    private final String explicacion;

    /**
     *
     * @param nombre
     * @param explicacion
     */
    public Parametro(String nombre, String explicacion) {
        this.nombre = nombre;
        this.explicacion = explicacion;
    }
    
    /**
     *
     * @param nombre
     */
    public Parametro(String nombre) {
        this.nombre = nombre;
        this.explicacion = null;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public String getExplicacion() {
        return explicacion;
    }
}
