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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;

//Klasa konfigurująca zabezpieczenia
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses=org.sobiech.inspigen.core.services.impl.UserServiceImpl.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	/* Dołączenie beanow klas CustomUserDetailsService i CustomAuthenticationProvider
	do globalnej konfiguracji */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       
    	auth
    		.userDetailsService(customUserDetailsService())
    		.and()
    		.authenticationProvider(customAuthenticationProvider());	
    }
    
    //Konfiguracja dostępu do ścieżek, rol uzytkownikow, sciezki logowania i autoryzacji poprzez token
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
    	//Wyłączamy CSRF
    	http.csrf().disable();
    	
    	//Konfigurujemy sesję, aby była bezstanowa
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    	//Konigurujemy dostęp do ścieżek
        http.authorizeRequests()
            .antMatchers("/resources/**",
            			 "/partials/**",
            			 "/authenticate",
            			 "/api/v1/accounts",
            			 "/api/v1/accounts/*").permitAll()
             //Udostępniamy sciężkę użytkownikom o należącym do jednej z grup			 
            .antMatchers("/dashboard/**").hasAnyRole("ADMIN","MOD","USER")
            .anyRequest().authenticated()
            .and()  
        //Konfigurujemy ścieżkę logowania    
        .formLogin()
        	 .loginPage("/")
             .permitAll()
             .and()
        //Konfigurujemy wylogowywanie się     
        .logout()
            .permitAll();
    	
        //Dodajemy autentykację poprzez token
        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(customUserDetailsService());
        http.apply(securityConfigurerAdapter); 
    }
    
    //Bean wspomagający szyfrowanie hasła
    @Bean
    ReflectionSaltSource saltSource() {
		
		ReflectionSaltSource salt = new ReflectionSaltSource();
		salt.setUserPropertyToUse("username");
	
		return salt;		
	}
    
    //Bean szyfrujący hasło
    @Bean
    Md5PasswordEncoder passwordEncoder() {
    	return new Md5PasswordEncoder();
    } 
    
    //Bean z konfiguracją i instancją klasy CustomDaoAuthenticationProvider
    @Bean
    CustomDaoAuthenticationProvider customAuthenticationProvider() {
    	
    	CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
    	
    	provider.setPasswordEncoder(passwordEncoder());
    	provider.setSaltSource(saltSource());
    	provider.setUserDetailsService(customUserDetailsService());
    	
    	return provider;
    }
    
    //Bean z instancją klasy CustomUserDetailsService
    @Bean
    CustomUserDetailsService customUserDetailsService() { 	
    	return new CustomUserDetailsService();
    }   
    
    //Bean z instancją nowego filtru
    @Bean
    XAuthTokenFilter xAuthTokenFilter() {
    	return new XAuthTokenFilter(customUserDetailsService());
    }  
    
    //Bean nadpisujący domyślny menadżer autentykacji
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    } 
}