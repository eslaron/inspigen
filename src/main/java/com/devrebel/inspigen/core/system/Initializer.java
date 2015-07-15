package com.devrebel.inspigen.core.system;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Klasa inicjalizująca klasy konfiguracyjne
public class Initializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

	//Inicjalizacja klas konfiguracyjnych
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { DatabaseConfig.class, SecurityConfig.class, EmailConfig.class, CustomBeansConfig.class};
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