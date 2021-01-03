package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

	public static final String URL = "/myCards";

	@GetMapping(URL)
	public String getCardDetails(String input) {
		return "Here are the card details from the DB";
	}

}
