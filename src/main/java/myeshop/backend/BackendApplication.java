
package myeshop.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot para el backend de la aplicación MyEshop.
 *
 * <p>Arranca el contexto de Spring y lanza la aplicación.</p>
 *
 * @author Rafael Robles
 * @version 1.0.0
 */

@SpringBootApplication
public class BackendApplication {

 public static void main(String[] args) {
     SpringApplication.run(BackendApplication.class, args);
 }
}
