package com.example.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class ForRequestFilter implements Filter {

	@Resource
	private RequestDatas d;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println(d);
		System.out.println(d.getAa());
		if (d.getAa() == null) {
			d.setAa(1);
		} else {
			d.setAa(d.getAa() + 1);
		}
		chain.doFilter(request, response);			

		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {		
	}

}
