package org.sobiech.inspigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.sobiech.inspigen.dao.DuplicateUserException;
import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.UserService;

import java.security.Principal;


@Controller
public class LinkController {
	
	@Autowired
	UserService userService;
	
	
    @RequestMapping(value = "/")
    public String mainPage() {
        return "home";
    }

    @RequestMapping(value = "/index")
    public String indexPage() {
        return "home";
    }
        
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
    
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody String addUser(@RequestBody User user) throws DuplicateUserException {
    	
    	userService.addUser(user);
    	
    	return "Success";
    }
}
