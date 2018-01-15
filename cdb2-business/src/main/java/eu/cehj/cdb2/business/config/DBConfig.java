package eu.cehj.cdb2.business.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "eu.cehj.cdb2.entity")
public class DBConfig {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // Database infos

    //    @Value("#{'${spring.datasource.url}'.trim()}")
    //    private String url;
    //    //
    //    @Value("#{'${spring.datasource.driverClassName}'.trim()}")
    //    private String driverClass;
    //
    //    @Value("#{'${spring.datasource.username}'.trim()}")
    //    private String username;
    //
    //    @Value("#{'${spring.datasource.password}'.trim()}")
    //    private String password;
    //
    //    // Conn. pool
    //
    //    @Value("#{'${hibernate.c3p0.min_size}'.trim()}")
    //    private String poolMinSize;
    //
    //    @Value("#{'${hibernate.c3p0.max_size}'.trim()}")
    //    private String poolMaxSize;
    //
    //    @Value("#{'${hibernate.c3p0.timeout}'.trim()}")
    //    private String poolTimeout;
    //
    //    @Value("#{'${hibernate.c3p0.max_statements}'.trim()}")
    //    private String poolMaxStatements;
    //
    //    @Value("#{'${hibernate.c3p0.idle_test_period}'.trim()}")
    //    private String poolIdleTestPeriod;
    //
    //    @Bean
    //    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    //        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    //        em.setPackagesToScan(new String[] { "eu.cehj.cdb2.entity" });
    //        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    //        em.setJpaVendorAdapter(vendorAdapter);
    //        //        em.setJpaProperties(this.additionalProperties());
    //        return em;
    //    }

}
