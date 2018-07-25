package eu.cehj.cdb2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class Cdb2HubWebApplication extends WebMvcAutoConfiguration{
    public static void main(final String[] args) {
        SpringApplication.run(Cdb2HubWebApplication.class, args);
    }

    @Bean
    public static ConversionService conversionService() {
        return new DefaultFormattingConversionService();
    }
}