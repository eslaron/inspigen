package org.sobiech.inspigen.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;


@Configuration
@EnableWebMvcSecurity
@ComponentScan(basePackageClasses=org.sobiech.inspigen.service.UserServiceImpl.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       
    	auth
    		.userDetailsService(customUserDetailsService())
    		.and()
    		.authenticationProvider(customAuthenticationProvider());	
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http
        	.csrf().disable()
            .authorizeRequests()
                .antMatchers("/resources/**", "/signup").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                 .permitAll()
                 .and()
                 .rememberMe()
                 .and()            
            .logout()
                .permitAll();
    }
    
    @Bean
    ReflectionSaltSource saltSource() {
		
		ReflectionSaltSource salt = new ReflectionSaltSource();
		salt.setUserPropertyToUse("username");
		
		return salt;		
	}
    
    @Bean
    Md5PasswordEncoder passwordEncoder() {
    	
    	Md5PasswordEncoder encoder = new Md5PasswordEncoder();
    	
    	return encoder;
    } 
    
    @Bean
    CustomDaoAuthenticationProvider customAuthenticationProvider() {
    	
    	CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
    	
    	provider.setPasswordEncoder(passwordEncoder());
    	provider.setSaltSource(saltSource());
    	provider.setUserDetailsService(customUserDetailsService());
    	
    	return provider;
    }
    
    @Bean
    CustomUserDetailsService customUserDetailsService() {
    	CustomUserDetailsService service = new CustomUserDetailsService();
    	
    	return service;
    }   
}