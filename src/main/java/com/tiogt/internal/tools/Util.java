package com.tiogt.internal.tools;

import java.util.ResourceBundle;

public class Util {

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("mensaje");

    public final static String INICIO = RESOURCE.getString("inicio");
    public final static String FIN = RESOURCE.getString("fin");
    public final static String SALTO_LINEA = RESOURCE.getString("salto_linea");
    public final static String ESPACIO = RESOURCE.getString("espacio");
    public final static String ERROR_LECTURA_JSON = RESOURCE.getString("error_lectura");
    public final static String ERRORES_ECONTRADOS = RESOURCE.getString("cantidad_errores");
    public final static String SEPARADOR = RESOURCE.getString("separador");
    public final static String INICIO_ERROR = RESOURCE.getString("inicio_error");
    public final static String LINEA_INICIO = RESOURCE.getString("linea_inicio");
    public final static String LINEA_FIN = RESOURCE.getString("linea_final");

}
