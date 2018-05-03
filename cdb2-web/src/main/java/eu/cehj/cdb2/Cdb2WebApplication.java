package eu.cehj.cdb2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import eu.cehj.cdb2.web.config.BailiffImportConfig;

@SpringBootApplication
@EnableConfigurationProperties(BailiffImportConfig.class)
public class Cdb2WebApplication extends WebMvcAutoConfiguration{

    public static void main(final String[] args) {
        SpringApplication.run(Cdb2WebApplication.class, args);
    }

}