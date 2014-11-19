package org.sobiech.inspigen.core.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.sobiech.inspigen.core.config.DatabaseConfig;
import org.sobiech.inspigen.core.config.WebAppConfig;

public class Initializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { DatabaseConfig.class, SecurityConfig.class, EmailConfig.class, CustomBeansConfig.class, ScheduledTasks.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
    	return new Class<?>[] { ThymeleafConfig.class, WebAppConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}