package org.sobiech.inspigen.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
    protected final Log logger = LogFactory.getLog(this.getClass());

    protected CustomAuthenticationSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, 
    		final HttpServletResponse response, 
    		final Authentication authentication) throws IOException {
    
    	String targetUrl="";
    	
    	final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
            	targetUrl = "user";
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
            	targetUrl = "admin";
                break;
            }
        }
        response.sendRedirect(targetUrl);
    }
}