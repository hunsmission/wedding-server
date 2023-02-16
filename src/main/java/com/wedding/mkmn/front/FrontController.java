package com.wedding.mkmn.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class FrontController {
	/**
	 * English
	 * @return
	 */
	@GetMapping("/en")
	public String en() {
		return "front/en";
	}
	
	/**
	 * Korean
	 * @return
	 */
	@GetMapping("/kr")
	public String kr() {
		return "front/en";
	}
}
