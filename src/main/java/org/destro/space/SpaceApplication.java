package org.destro.space;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * Aplicatição principal
 * 
 * @author destro
 */
@RestController
@SpringBootApplication
@ComponentScan({ "org.destro.space" })
public class SpaceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpaceApplication.class, args);
	}
}
