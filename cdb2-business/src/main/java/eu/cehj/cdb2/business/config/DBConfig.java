package eu.cehj.cdb2.business.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "eu.cehj.cdb2.entity")
public class DBConfig {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
