package org.sobiech.inspigen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LinkController {
	
	@RequestMapping
    public String getLoginPartialPage() {
        return "partials/login";
    }
    
    @RequestMapping(value = "/signup")
    public String getSignupPartialPage() {
        return "signup";
    }
}
