package eu.cehj.cdb2.web.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import eu.cehj.cdb2.web.security.CsrfHeaderFilter;

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
				"/localisation"
				).permitAll()
		.antMatchers(HttpMethod.POST, "/api/**", "/user", "/logout", "/v2/**").hasAnyAuthority("USER", "ADMIN", "SUPER_USER")
		.antMatchers(HttpMethod.PUT, "/api/**", "/user", "/logout", "/v2/**").hasAnyAuthority("USER", "ADMIN", "SUPER_USER")
		.antMatchers(HttpMethod.DELETE, "/api/**", "/user", "/logout", "/v2/**").hasAnyAuthority("USER", "ADMIN", "SUPER_USER")
		.antMatchers("/api/**", "/user", "/logout", "/v2/**").authenticated()
		//        .antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
		.and()
		.cors()
		.and()
		.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
		.csrf().csrfTokenRepository(this.csrfTokenRepository());
	}

	private CsrfTokenRepository csrfTokenRepository() {
		final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
