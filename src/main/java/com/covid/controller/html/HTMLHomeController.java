package com.covid.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTMLHomeController {

	@GetMapping("/")
	public String home() {
		return "home";
	}
}
