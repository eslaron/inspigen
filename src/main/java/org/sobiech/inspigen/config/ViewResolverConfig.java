package org.sobiech.inspigen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

@Configuration
public class ViewResolverConfig {
	
	@Bean
	public VelocityViewResolver viewResolver() {
		VelocityViewResolver viewResolver = new VelocityViewResolver();
		viewResolver.setCache(true);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".html");
		viewResolver.setContentType("text/html; charset=utf-8");
		viewResolver.setExposeSpringMacroHelpers(true);
		
		return viewResolver;
	}

	@Bean
	public VelocityConfig velocityConfig() {
		VelocityConfigurer config = new VelocityConfigurer();
		config.setResourceLoaderPath("/WEB-INF/views/");
		
		return config;	
	}
}


