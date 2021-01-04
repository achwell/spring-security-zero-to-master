package com.eazybytes.controller;

import com.eazybytes.model.Customer;
import com.eazybytes.model.Loans;
import com.eazybytes.repository.LoanRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

	public static final String URL = "/myLoans";

	private final LoanRepository loanRepository;

	public LoansController(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@PostMapping(URL)
	@PreAuthorize("#customer.email == authentication.principal")
	public List<Loans> getLoanDetails(@RequestBody Customer customer) {
		return loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
	}

}
