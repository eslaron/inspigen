package com.devrebel.powerlib.core.config;

import com.devrebel.powerlib.core.common.CommonBeansConfig;
import com.devrebel.powerlib.core.config.email.EmailConfig;
import com.devrebel.powerlib.core.config.security.SecurityConfig;
import com.devrebel.powerlib.core.config.view.ThymeleafConfig;
import com.devrebel.powerlib.core.persistence.DatabaseConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Klasa inicjalizująca klasy konfiguracyjne
public class Initializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

	//Inicjalizacja klas konfiguracyjnych
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { DatabaseConfig.class, SecurityConfig.class, EmailConfig.class, CommonBeansConfig.class};
    }

    //Inicjalizacja klas konfiguracyjnych związanych z servletami
    @Override
    protected Class<?>[] getServletConfigClasses() {
    	return new Class<?>[] { ThymeleafConfig.class, WebAppConfig.class};
    }

    //Ustawienie domyślnej ścieżki dla servletow
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}