package com.howtodoinjava.app.filter;
import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

public class ErrorWebSealFilter  implements Filter {
	
	private List<String> errorWebSealURI;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		errorWebSealURI = (List<String>)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("errorWebSealURI");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		System.out.println("before mapping getRequestURI in ERROR WEB SEAL FILTER: " + httpRequest.getRequestURI());
		
		 if (errorWebSealURI.contains(httpRequest.getRequestURI())) {
				System.out.println("value of the URI" + httpRequest.getRequestURI());
				System.out.println("value of the requestParams" + httpRequest.getParameter("loginErrorPage"));
				System.out.println("value of the URI" + httpRequest.getParameterValues("loginErrorPage"));
				String arrayLinks[] = httpRequest.getParameterValues("loginErrorPage");
				for(int i=0;i<arrayLinks.length;i++) {
					System.out.println("value of links :" + arrayLinks[i]);
					httpRequest.setAttribute("errorCodes", "fdsfsfsfdsfsdfsdfsfsd");
					RequestDispatcher rd = request.getRequestDispatcher("/errorPage");
					rd.forward(request, response);
					//httpResponse.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
					//httpResponse.setHeader("Location", "/errorPage");
					//httpResponse.sendRedirect("/errorPage");
					//chain.doFilter(httpRequest, httpResponse);
				}
				
			}
			else{
				System.out.println("cannot find mapping for url " + httpRequest.getRequestURI());
			}
		 chain.doFilter(httpRequest, httpResponse);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
