package eu.cehj.cdb2.hub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import eu.cehj.cdb2.hub.utils.ParamsInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig  extends WebMvcConfigurerAdapter{
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        // Register guest interceptor with single path pattern
        registry.addInterceptor(new ParamsInterceptor()).addPathPatterns("/api/bailiff/search");
    }
}

