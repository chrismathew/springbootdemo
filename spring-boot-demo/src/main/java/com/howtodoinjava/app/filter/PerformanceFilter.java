package com.howtodoinjava.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceFilter.class);
	
	private int[] logTimer;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		long startTime = new java.util.Date().getTime();		
		filterChain.doFilter(request, response);		
		long endTime = new java.util.Date().getTime();
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		long duration = endTime - startTime;
		String uri = httpServletRequest.getRequestURI();
		String method = httpServletRequest.getMethod();
		
		String perfMessage = message(duration, uri, method);
		logPerformance(duration, perfMessage);
	}
	
	private void logPerformance(long time, String message) {
		if (time < logTimer[0]) {
			LOGGER.debug(message);
		} else if (time < logTimer[1]) {
			LOGGER.info(message);
		} else if (time < logTimer[2]) {
			LOGGER.warn(message);
		} else {
			LOGGER.error(message);
		}
	}

	private String message(long time, String uri, String method) {
		StringBuilder message = new StringBuilder();
		message.append(method);
		message.append(",").append(uri);
		message.append(",").append(time);
		return message.toString();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public int[] getLogTimer() {
		return logTimer;
	}

	public void setLogTimer(int[] logTimer) {
		this.logTimer = logTimer;
	}
}
