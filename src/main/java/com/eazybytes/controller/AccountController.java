package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	public static final String URL = "/myAccount";

	@GetMapping(URL)
	public String getAccountDetails(String input) {
		return "Here are the account details from the DB";
	}

}
