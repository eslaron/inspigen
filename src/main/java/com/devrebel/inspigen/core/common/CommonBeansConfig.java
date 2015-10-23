package com.devrebel.inspigen.core.common;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeansConfig {

    @Bean
    public Mapper getDtoMapper() {
        return new DozerBeanMapper();
    }
}