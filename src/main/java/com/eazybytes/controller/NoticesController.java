package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

	public static final String URL = "/notices";

	@GetMapping(URL)
	public String getNotices(String input) {
		return "Here are the notices details from the DB";
	}

}
