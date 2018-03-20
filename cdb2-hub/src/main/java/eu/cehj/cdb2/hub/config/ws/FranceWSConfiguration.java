package eu.cehj.cdb2.hub.config.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import eu.cehj.cdb2.hub.service.FranceSearchService;

@Configuration
public class FranceWSConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("eu.cehj.cdb2.hub.service.soap.france");
        return marshaller;
    }

    @Bean
    public FranceSearchService franceSearchService(final Jaxb2Marshaller marshaller) {
        final FranceSearchService service = new FranceSearchService();
        service.setDefaultUri("http://euro.huissier-justice.fr/annuaire.asmx");
        service.setMarshaller(marshaller);
        service.setUnmarshaller(marshaller);
        return service;
    }
}
