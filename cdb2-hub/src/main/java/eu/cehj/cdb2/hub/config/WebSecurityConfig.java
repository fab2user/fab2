package eu.cehj.cdb2.hub.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ImportResource(locations = {"${security.config.location}"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
		.httpBasic()
		.and()
		.logout()
		.and()
		.authorizeRequests()
		.antMatchers(
				"/static/**",
				"/css/**",
				"/images/**",
				"/js/**",
				"/webjars/**",
				"/public/**",
				"/localisation",
				"/api/search/bailiff"
				).permitAll()
		.antMatchers("/api/**", "/user", "/logout").authenticated()
		.and()
		// .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
		// .csrf().csrfTokenRepository(this.csrfTokenRepository());
		.csrf().disable();  // Disable CSRF check. Temporarily ???
	}

	private CsrfTokenRepository csrfTokenRepository() {
		final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
