package jspy.interprete;

public class Parametro {
    private final String nombre;
    private final String explicacion;

    public Parametro(String nombre, String explicacion) {
        this.nombre = nombre;
        this.explicacion = explicacion;
    }
    
    public Parametro(String nombre) {
        this.nombre = nombre;
        this.explicacion = null;
    }

    public String getNombre() {
        return nombre;
    }

    public String getExplicacion() {
        return explicacion;
    }
}
