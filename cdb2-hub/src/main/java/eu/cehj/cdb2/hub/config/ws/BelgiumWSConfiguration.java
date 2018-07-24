package eu.cehj.cdb2.hub.config.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import eu.cehj.cdb2.hub.service.search.BelgiumSearchService;

@Configuration
public class BelgiumWSConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        // FIXME: Put into properties
        marshaller.setContextPath("https.www_nkcn_cia");

        return marshaller;
    }

    @Bean
    public BelgiumSearchService belgiumSearchService() {
        final BelgiumSearchService service = new BelgiumSearchService();
        // FIXME: Put into properties
        service.setDefaultUri("https://www.nkcn-cia.be/LnxNKCNGDWHDJConsult/GDWHDJConsult.svc");
        service.setMarshaller(this.marshaller());
        service.setUnmarshaller(this.marshaller());
        return service;
    }
}
