package jspy.interprete;

import java.util.ArrayList;
import java.util.List;
import jspy.interprete.Interprete.IdComando;

/**
 *
 * @author LAB-315
 */
public class Comando {
    private final IdComando id;
    private final String descripcion;
    private final List<Parametro> parametros;

    /**
     *
     * @param id
     * @param descripcion
     */
    public Comando(IdComando id, String descripcion) {
        this.id = id;
        parametros = new ArrayList<>();
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public IdComando getId() {
        return id;
    }

    /**
     *
     * @param nuevo
     */
    public void addParametro(Parametro nuevo){
        parametros.add(nuevo);
    }
    
    /**
     *
     * @param nombre
     * @return
     */
    public boolean existeParametro(String nombre){
        for(Parametro p: parametros){
            if(p.getNombre().equals(nombre)){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     *
     */
    public void showAyuda(){
        System.out.println("NOMBRE");
        System.out.println("\t"+id+" - "+descripcion);
        System.out.println();
        System.out.println("PARÁMETROS");
        if(!parametros.isEmpty()){
            for(Parametro p : parametros){
                System.out.println("\t"+p.getNombre()+" - "+p.getExplicacion());
            }
        }else{
            System.out.println("\tNo existen parámetros para este comando.");
        }
    }
    
}
