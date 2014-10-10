package org.sobiech.inspigen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String getIndexPage() {
        return "index";
    }
    
	@RequestMapping("/partials/login")
    public String getLoginPartialPage() {
        return "partials/login";
    }
}
