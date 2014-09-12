package org.sobiech.inspigen.config;


import javax.sql.DataSource;

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
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


@Configuration
@EnableWebMvcSecurity
@ComponentScan(basePackageClasses=org.sobiech.inspigen.service.UserServiceImpl.class)
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
    	
        http
        	.csrf().disable()
            .authorizeRequests()
                .antMatchers("/resources/**", "/signup", "/forgotPassword", "/resetPassword","/newPassword").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                 .permitAll()
                 .and()
                 .rememberMe().tokenRepository(persistentTokenRepository())
                 .tokenValiditySeconds(1209600)
                 .and()            
            .logout()
                .permitAll();
    }
    
    @Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
    
    @Bean
	public SavedRequestAwareAuthenticationSuccessHandler 
                savedRequestAwareAuthenticationSuccessHandler() {
 
               SavedRequestAwareAuthenticationSuccessHandler auth 
                    = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
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