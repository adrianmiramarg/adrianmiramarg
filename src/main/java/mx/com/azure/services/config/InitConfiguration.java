/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.azure.services.config;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Clase principal de configuración inicial del microservicio.
 * 
 * @author adrian
 */
@Configuration
public class InitConfiguration {
    
    final static Logger logger = LoggerFactory.getLogger(InitConfiguration.class);
    
    @PostConstruct
    public void init(){
        logger.info("STARTUP");
    }
}
