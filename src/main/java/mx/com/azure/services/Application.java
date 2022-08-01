package mx.com.azure.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase principal de la aplicación spring boot.
 *
 * @author adrian.
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "mx.com.azure.services")

public class Application {

    /**
     * Método principal.
     *
     * @param args Argumentos variables.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class);
    }

}
