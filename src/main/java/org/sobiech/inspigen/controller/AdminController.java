package org.sobiech.inspigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.EmailService;
import org.sobiech.inspigen.service.UserService;

import java.security.Principal;
import java.util.Date;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CheckService checkService;
	
	@Autowired
	EmailService mailService;
	
	private String message;

	// Linki
	
	
	
}