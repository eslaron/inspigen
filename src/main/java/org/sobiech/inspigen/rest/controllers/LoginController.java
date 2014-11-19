package org.sobiech.inspigen.rest.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Boolean isUserLogged() {
 
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    	if (!(auth instanceof AnonymousAuthenticationToken)) {

    	  return true;
    	} else return false;
    }
    
}