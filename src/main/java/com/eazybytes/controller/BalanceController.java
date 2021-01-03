package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

	public static final String URL = "/myBalance";

	@GetMapping(URL)
	public String getBalanceDetails(String input) {
		return "Here are the balance details from the DB";
	}

}
