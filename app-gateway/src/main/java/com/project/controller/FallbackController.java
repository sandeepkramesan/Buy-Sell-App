package com.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class FallbackController {

	/**
	 * shows this message when a service is down using Hystrix Circuit Breaker.
	 */
	@GetMapping("/message")
	public String getFallbackMessage() {
		return "<h1>SERVICE DOWN...<br>TRY AFTER SOMETIME</h1>";
	}
}
