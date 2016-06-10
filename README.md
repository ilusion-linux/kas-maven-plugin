# kas-maven-plugin
Analiza un resultado local de sonar-maven-plugin y detiene el proceso de compilado en caso de encontrar errores.

```
 __  ___      ___           _______.
|  |/  /     /   \         /       |
|  '  /     /  ^  \       |   (----`
|    <     /  /_\  \       \   \    
|  .  \   /  _____  \  .----)   |   
|__|\__\ /__/     \__\ |_______/    
                                    
```

## Configuracion
Como primer punto debemos configurar las propiedades necesarias para el plugin de sonar-maven, la configuracion recomendada es la siguiente:
```
<properties>
    <sonar.host.url>http://192.168.99.100:9000</sonar.host.url>
    <sonar.report.export.path>resultado_sonarqb.json</sonar.report.export.path>
    <sonar.analysis.mode>preview</sonar.analysis.mode>
    <sonar.branch>nombre_develop</sonar.branch>
</properties>
```
Luego se procede con la configuracion de los plugins necesarios, este es un ejemplo de configuracion valido:
```
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>sonar-maven-plugin</artifactId>
            <version>3.0.2</version>
            <executions>
                <execution>
                    <id>validate</id>
                    <phase>validate</phase>
                    <goals>
                        <goal>sonar</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        
        <plugin>
            <groupId>com.tiogt.internal.tools</groupId>
            <artifactId>kas-maven-plugin</artifactId>
            <version>0.1.0</version>
            <executions>
                <execution>
                    <id>validate</id>
                    <phase>validate</phase>
                    <goals>
                        <goal>kas</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
</build>
```
Con esta ya estas listo para poder obtener los resultados de sonarqube en tu consola, simplemente ejecuta ```mvn clean install``` y podras emprezar a ver los resultados.