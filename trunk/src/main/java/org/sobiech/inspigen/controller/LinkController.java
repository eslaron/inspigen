package org.sobiech.inspigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sobiech.inspigen.dao.DuplicateUserException;
import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.EmailService;
import org.sobiech.inspigen.service.UserService;

import java.security.Principal;
import java.util.Map;


@Controller
public class LinkController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService mailService;
	
	
    @RequestMapping(value = "/")
    public String mainPage() {
        return "home";
    }
    
    @RequestMapping(value = "/signup")
    public String signUp() {
        return "signup";
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
    
    @RequestMapping(value="/forgotPassword")
	public String forgotPassword()
	{
		return "forgotPassword"; 
	}
	
	@RequestMapping(value="/resetPassword" , method=RequestMethod.POST)
	public String resetRequest(@RequestParam(value="email") String email)
	{
		//check if the email id is valid and registered with us.
		mailService.sendMail(email);
		return "emailSent";
	}
	
	@RequestMapping(value="/newPassword/{email}" )
	public String resetPassword(@PathVariable String email, Map<String,String> model)
	{
		//check if the email id is valid and registered with us.
		model.put("emailid", email);
		
		return "newPassword";
	}
}
