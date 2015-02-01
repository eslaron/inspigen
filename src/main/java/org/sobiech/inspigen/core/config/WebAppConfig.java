package org.sobiech.inspigen.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//Klasa z ogolną konfiguracją aplikacji
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.sobiech.inspigen"})
public class WebAppConfig extends WebMvcConfigurerAdapter {
    
    //Mapowanie zasobow do scieżki do katalogu webapp/resources
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    //Internacjonalizacja komunikatow
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        return source;
    }
}
