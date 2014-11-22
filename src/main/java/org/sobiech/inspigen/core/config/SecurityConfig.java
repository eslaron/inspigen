package org.sobiech.inspigen.core.config;

import javax.sql.DataSource;

import org.sobiech.inspigen.core.config.xauth.XAuthTokenConfigurer;
import org.sobiech.inspigen.core.config.xauth.XAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;


@Configuration
@EnableWebMvcSecurity
@ComponentScan(basePackageClasses=org.sobiech.inspigen.core.services.impl.UserServiceImpl.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       
    	auth
    		.userDetailsService(customUserDetailsService())
    		.and()
    		.authenticationProvider(customAuthenticationProvider());	
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
    	http.csrf().disable();
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
            .antMatchers("/resources/**",
            			 "/partials/**",
            			 "/authenticate",
            			 "/api/v1/accounts/*").permitAll()
            .antMatchers("/dashboard/**").hasAnyRole("ADMIN","MOD","USER")
            .anyRequest().authenticated()
            .and()           
        .formLogin()
        	 .loginPage("/")
             .permitAll()
             .and()
        .logout()
            .permitAll();
    	          
        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(customUserDetailsService());
        http.apply(securityConfigurerAdapter); 
    }
    
    @Bean
    ReflectionSaltSource saltSource() {
		
		ReflectionSaltSource salt = new ReflectionSaltSource();
		salt.setUserPropertyToUse("username");
	
		return salt;		
	}
    
    @Bean
    Md5PasswordEncoder passwordEncoder() {
    	return new Md5PasswordEncoder();
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
    	return new CustomUserDetailsService();
    }   
    
    @Bean
    XAuthTokenFilter xAuthTokenFilter() {
    	return new XAuthTokenFilter(customUserDetailsService());
    }  
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    } 
}