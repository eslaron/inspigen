package org.sobiech.inspigen.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller						//Oznaczenie jako zwykły kontroler
@RequestMapping("/login")		//Mapowanie zasobu
public class LoginController {

	//Ządanie GET sprawdzające czy użytkownik jest zalogowany
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Boolean isUserLogged() {
 
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    	if (!(auth instanceof AnonymousAuthenticationToken)) {

    	  return true;
    	} else return false;
    }
    
}