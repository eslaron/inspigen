package org.sobiech.inspigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.EmailService;
import org.sobiech.inspigen.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CheckService checkService;
	
	@Autowired
	EmailService mailService;
	
}