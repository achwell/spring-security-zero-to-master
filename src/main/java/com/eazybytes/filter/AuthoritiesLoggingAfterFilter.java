package com.eazybytes.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;

public class AuthoritiesLoggingAfterFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			LOGGER.info("User " + authentication.getName() + " is successfully authenticated and " + "has the authorities " + authentication.getAuthorities().toString());
		}

		chain.doFilter(request, response);
	}

}
