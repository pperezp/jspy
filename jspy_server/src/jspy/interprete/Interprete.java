package jspy.interprete;

import java.util.ArrayList;
import java.util.List;
import jspy.interprete.excepciones.ComandoNotFoundException;
import jspy.interprete.excepciones.SyntaxComandoException;
import jspy.interprete.excepciones.SyntaxParametroException;

public class Interprete {

    private final List<Comando> comandos;

    public static enum IdComando {
        msg, help
    }

    public Interprete() {
        comandos = new ArrayList<>();

        Comando msg = new Comando(IdComando.msg, "Comando para enviar un mensaje a un cliente");
        msg.addParametro(new Parametro("-t"));
        msg.addParametro(new Parametro("-a"));

        Comando help = new Comando(IdComando.help, "Comando para desplegar la ayuda del interprete");
        /*Agregando los comandos como parametros de ayuda*/
        for (IdComando id : IdComando.values()) {
            help.addParametro(new Parametro(id.name()));
        }
        /*Agregando los comandos como parametros de ayuda*/

        comandos.add(msg);
        comandos.add(help);
    }

    public IdComando getIdComando(String sentencia) throws SyntaxComandoException, ComandoNotFoundException {
        String nombreComando;
        if (sentencia.charAt(0) == '/') {
            nombreComando = sentencia.split(" ")[0].substring(1).toLowerCase();

            for (Comando c : comandos) {
                if (c.getId().name().equalsIgnoreCase(nombreComando)) {
                    return c.getId();
                }
            }
        }else{
            throw new SyntaxComandoException("Los comandos deben comenzar con /");
        }

        throw new ComandoNotFoundException("\""+nombreComando+"\" no se reconoce como un comando válido");
    }

    public List<String> getParametros(String sentencia) throws SyntaxParametroException {
        List<String> params = new ArrayList<>();

        char[] letras = sentencia.toCharArray();
        
        if(!isCorrecto(sentencia)){
            throw new SyntaxParametroException("Falta una doble comilla (\")");
        }

        String param = "";
        char letraActual;
        int contComilla;
        boolean dentroDeComilla;
        int i = 0;

        while (i < sentencia.length()) {
            contComilla = 0;
            dentroDeComilla = false;
            do {
                letraActual = letras[i];

                do {
                    param += letraActual;
                    i++;

                    if (i != sentencia.length()) {
                        letraActual = letras[i];

                        if (letraActual == '"') {
                            contComilla++;
                            if (contComilla == 1) {
                                dentroDeComilla = true;
                            } else {
                                // es porque contComilla es 2, y quiere decir 
                                // que ya cerramos la comilla
                                dentroDeComilla = false;
                                contComilla = 0;
                            }
                        }
                    } else {
                        break;
                    }
                } while (letraActual != ' ');
            } while (dentroDeComilla);

            params.add(param.trim());
            param = "";
        }

        // elimino el primer parametro que es el nombre
        params.remove(0);
        
        return params;
    }
    
    private boolean isCorrecto(String sentencia){
        int cont = 0;
        
        for(char c : sentencia.toCharArray()){
            if(c == '"'){
                cont++;
            }
        }
        
        return cont % 2 == 0;
    }
    
    private Comando getComando(IdComando id){
        for(Comando c : comandos){
            if(c.getId() == id)
                return c;
        }
        
        return null;
    }
    
    public boolean isParametroOK(IdComando id, String parametro){
        return getComando(id).existeParametro(parametro);
    }
}
