package com.tiogt.internal.tools;

import java.util.Locale;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Analiza un resultado local de sonar-maven-plugin y detiene el proceso de
 * compilado en caso de encontrar errores,
 */
@Mojo(name = "kas", aggregator = true)
public class KasMojo extends AbstractMojo {

    private Verificador verificador;

    /**
     * Directorio de resultados de sonar. Este generalmente se encuentra dentro
     * de la carpeta target.
     */
    @Parameter(property = "sonar.target", defaultValue = "target/sonar/")
    protected String targetSonar;

    /**
     * Fichero con el resultado del analisis realizado de forma local por el
     * plugin sonar-maven-plugin.
     */
    @Parameter(property = "sonar.report.export.path")
    protected String resultadoSonar;

    /**
     * Variable para saber si se desea para el ciclo de compilacion en caso de
     * encontrar problemas.
     */
    @Parameter(property = "sonar.compile.break")
    protected boolean paraCompilacion;

    /**
     * Variable para saber si se desea para el ciclo de compilacion en caso de
     * encontrar problemas.
     */
    @Parameter(property = "sonar.local.verbose")
    protected boolean mostratDetalle;

    /**
     * Variable util en projectos donde se tiene una cantidad inicial de errores
     * pero ne se desea afectar el build hasta corregirlos, pero no se desea que
     * el numero de errores cresca.
     */
    @Parameter(property = "sonar.error.initial", defaultValue = "0")
    protected int erroresIniciales;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(Util.INICIO);

        try {
            final String ruta = targetSonar + resultadoSonar;
            verificador = new Verificador(ruta);
            verificador.verificarErrores();
        } catch (UtilExcepcion error) {
            getLog().error(error.getMessage());
            throw new MojoExecutionException(error.getMessage());
        } catch (MojoFailureException error) {
            getLog().info(error.getMessage());

            if (mostratDetalle) {
                getLog().info(verificador.obtenerErrores());
            }

            if (paraCompilacion && verificador.getProblemas() > erroresIniciales) {
                throw error;
            }
        }

        getLog().info(Util.FIN);
    }
}
