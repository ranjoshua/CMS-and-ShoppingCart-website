package com.springbootapp.cmsshoppingcart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/ss") /* the mapping "/" means the root domain */
	public String home() {
		return "home"; // spring will look for a html file called "home" inside 'templates' folder.
	}
}
