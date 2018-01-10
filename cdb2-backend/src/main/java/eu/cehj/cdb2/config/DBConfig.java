package eu.cehj.cdb2.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DBConfig {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // Database infos

    @Value("#{'${spring.datasource.url}'.trim()}")
    private String url;

    @Value("#{'${spring.datasource.driverClassName}'.trim()}")
    private String driverClass;

    @Value("#{'${spring.datasource.username}'.trim()}")
    private String username;

    @Value("#{'${spring.datasource.password}'.trim()}")
    private String password;

    // Conn. pool

    @Value("#{'${hibernate.c3p0.min_size}'.trim()}")
    private String poolMinSize;

    @Value("#{'${hibernate.c3p0.max_size}'.trim()}")
    private String poolMaxSize;

    @Value("#{'${hibernate.c3p0.timeout}'.trim()}")
    private String poolTimeout;

    @Value("#{'${hibernate.c3p0.max_statements}'.trim()}")
    private String poolMaxStatements;

    @Value("#{'${hibernate.c3p0.idle_test_period}'.trim()}")
    private String poolIdleTestPeriod;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan(new String[] { "eu.cehj.cdb2.entity" });
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(this.additionalProperties());
        return em;
    }

    private Properties additionalProperties() {
        final Properties props = new Properties();
        props.setProperty("hibernate.connection.url", this.url);
        props.setProperty("hibernate.connection.driver_class", this.driverClass);
        props.setProperty("hibernate.connection.username", this.username);
        props.setProperty("hibernate.connection.password", this.password);
        props.setProperty("org.hibernate.envers.store_data_at_delete", "true");

        props.setProperty("hibernate.c3p0.min_size", this.poolMinSize);
        props.setProperty("hibernate.c3p0.max_size", this.poolMaxSize);
        props.setProperty("hibernate.c3p0.timeout", this.poolTimeout);
        props.setProperty("hibernate.c3p0.max_statements", this.poolMaxStatements);
        props.setProperty("hibernate.c3p0.idle_test_period", this.poolIdleTestPeriod);

        return props;
    }
}
