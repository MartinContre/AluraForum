package mx.alura.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class that starts the Alura Forum API application.
 */
@SpringBootApplication
public class AluraForumApi {

	/**
	 * The entry point of the application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AluraForumApi.class, args);
	}

}
