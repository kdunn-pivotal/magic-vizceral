package io.pivotal.pde.magicvizceral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class MagicVizceralApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicVizceralApplication.class, args);
	}
}
