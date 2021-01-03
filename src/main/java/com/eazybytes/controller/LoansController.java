package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

	public static final String URL = "/myLoans";

	@GetMapping(URL)
	public String getLoanDetails(String input) {
		return "Here are the loan details from the DB";
	}

}
