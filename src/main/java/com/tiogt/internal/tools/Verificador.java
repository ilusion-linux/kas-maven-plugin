package com.tiogt.internal.tools;

import java.io.File;
import java.io.IOException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class Verificador {

    private final static String PROBLEMAS = "issues";

    private JsonNode resultado;
    private StringBuffer mensaje;

    private final File fichero;
    private final String rutaFichero;
    private final ObjectMapper mapper = new ObjectMapper();

    private int problemas;

    public Verificador(String rutaFichero) throws UtilExcepcion {
        problemas = 0;
        this.rutaFichero = rutaFichero;
        this.fichero = new File(rutaFichero);
        leerResultado();
    }

    public void verificarErrores() throws MojoFailureException {
        problemas = resultado.get(PROBLEMAS).size();

        if (problemas > 0) {
            mensaje = new StringBuffer(Util.ERRORES_ECONTRADOS)
                    .append(problemas);

            throw new MojoFailureException(mensaje.toString());
        }
    }

    public String obtenerErrores() {

        mensaje = new StringBuffer(Util.SEPARADOR)
                .append(Util.SALTO_LINEA);

        for (JsonNode error : resultado.get(PROBLEMAS)) {
            String recurso = error.get("component").getTextValue();
            int inicio = recurso.lastIndexOf(":") + 1;
            recurso = recurso.substring(inicio, recurso.length());

            mensaje.append(Util.INICIO_ERROR)
                    .append(error.get("line").getIntValue())
                    .append(Util.ESPACIO)
                    .append(Util.LINEA_INICIO)
                    .append(error.get("startLine").getIntValue())
                    .append(Util.ESPACIO)
                    .append(Util.LINEA_FIN)
                    .append(error.get("endLine").getIntValue())
                    .append(Util.SALTO_LINEA)
                    .append(error.get("message").getTextValue())
                    .append(Util.SALTO_LINEA)
                    .append(recurso)
                    .append(Util.SALTO_LINEA)
                    .append(Util.SEPARADOR)
                    .append(Util.SALTO_LINEA);
        }

        return mensaje.toString();
    }

    public int getProblemas() {
        return problemas;
    }

    private void leerResultado() throws UtilExcepcion {
        try {
            resultado = mapper.readTree(fichero);
        } catch (IOException error) {
            mensaje = new StringBuffer(Util.ERROR_LECTURA_JSON)
                    .append(rutaFichero).append(Util.SALTO_LINEA)
                    .append(error.getMessage());

            throw new UtilExcepcion(mensaje.toString());
        }
    }
}
