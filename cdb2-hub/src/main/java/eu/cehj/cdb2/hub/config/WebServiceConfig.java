package eu.cehj.cdb2.hub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter{

}
