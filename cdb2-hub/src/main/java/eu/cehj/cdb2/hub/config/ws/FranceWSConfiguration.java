package eu.cehj.cdb2.hub.config.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import eu.cehj.cdb2.hub.service.search.FranceQueryService;

@Configuration
public class FranceWSConfiguration {

    @Bean
    public Jaxb2Marshaller marshallerFrance() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("eu.cehj.cdb2.hub.service.soap.france");
        return marshaller;
    }

    @Bean
    public FranceQueryService franceQueryService() {
        final FranceQueryService service = new FranceQueryService();
        service.setDefaultUri("http://euro.huissier-justice.fr/annuaire.asmx");
        service.setMarshaller(this.marshallerFrance());
        service.setUnmarshaller(this.marshallerFrance());
        return service;
    }
}
