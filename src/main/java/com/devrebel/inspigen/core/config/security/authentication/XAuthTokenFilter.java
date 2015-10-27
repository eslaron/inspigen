package com.devrebel.inspigen.core.config.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Klasa definiująca nowy filtr do autoryzacji za pomocą tokena
 * 
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 * @author Sebastian Sobiech (sebastian.sobiech@gmail.com)
 */
public class XAuthTokenFilter extends GenericFilterBean {

    private final UserDetailsService detailsService;
    private final TokenUtils tokenUtils = new TokenUtils();
    private String xAuthTokenHeaderName = "x-auth-token";

    public XAuthTokenFilter(UserDetailsService userDetailsService) {
        this.detailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String authToken = httpServletRequest.getHeader(this.xAuthTokenHeaderName);

            validateTokenIfNotEmpty(authToken);
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void validateTokenIfNotEmpty(String authToken) {
        if (StringUtils.hasText(authToken)) {
            String username = this.tokenUtils.getUserNameFromToken(authToken);
            UserDetails details = this.detailsService.loadUserByUsername(username);

            setTokenIfValidated(authToken, details);
        }
    }

    private void setTokenIfValidated(String authToken, UserDetails details) {
        if (this.tokenUtils.validateToken(authToken, details)) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}