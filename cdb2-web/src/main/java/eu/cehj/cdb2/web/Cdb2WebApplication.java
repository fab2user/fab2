package eu.cehj.cdb2.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Cdb2WebApplication extends WebMvcAutoConfiguration{

	public static void main(String[] args) {
		SpringApplication.run(Cdb2WebApplication.class, args);
	}
}
