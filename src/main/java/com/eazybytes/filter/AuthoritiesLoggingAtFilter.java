package com.eazybytes.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class AuthoritiesLoggingAtFilter implements Filter {


	private static final Logger LOGGER = LoggerFactory.getLogger(AuthoritiesLoggingAtFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOGGER.info("Authentication Validation is in progress");
		chain.doFilter(request, response);
	}

}
