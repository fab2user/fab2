package eu.cehj.cdb2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;

@SpringBootApplication()
public class Cdb2HubWebApplication extends WebMvcAutoConfiguration{
    public static void main(final String[] args) {
        SpringApplication.run(Cdb2HubWebApplication.class, args);
    }
}