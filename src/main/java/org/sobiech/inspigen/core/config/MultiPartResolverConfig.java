package org.sobiech.inspigen.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Configuration
public class MultiPartResolverConfig {

	 @Bean
	 public CommonsMultipartResolver getCommonsMultipartResolver() {
	    	
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(20971520); // 20MB
	    multipartResolver.setMaxInMemorySize(1048576);	// 1MB
	    
	    return multipartResolver;
	 }
}
