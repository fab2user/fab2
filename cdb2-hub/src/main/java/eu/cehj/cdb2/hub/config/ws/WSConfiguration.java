package eu.cehj.cdb2.hub.config.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.chj.cdb2.common.ObjectFactory;

@Configuration
public class WSConfiguration {

    @Bean
    public ObjectFactory objectFactory() {
        return new ObjectFactory();
    }

}
