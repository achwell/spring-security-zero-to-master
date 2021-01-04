package com.eazybytes.controller;


import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class LoginController {

	public static final String URL = "/user";

	private final CustomerRepository customerRepository;

	public LoginController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@RequestMapping(URL)
	public Customer getUserDetailsAfterLogin(Principal user) {
		List<Customer> customers = customerRepository.findByEmail(user.getName());
		return customers.size() > 0 ? customers.get(0) : null;

	}

}
