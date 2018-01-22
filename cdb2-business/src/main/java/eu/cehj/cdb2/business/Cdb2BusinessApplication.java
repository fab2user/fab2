package eu.cehj.cdb2.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Cdb2BusinessApplication {

    public static void main(final String[] args) {
        SpringApplication.run(Cdb2BusinessApplication.class, args);
    }
}
