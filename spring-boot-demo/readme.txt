
http://localhost:8080/fepesvc/loginHandler.do?loginErrorPage=ACCOUNT_LOCKED
http://localhost:8888/fepesvc/loginHandler.do?loginErrorPage=ACCOUNT_LOCKED


http://localhost:8080/fepesvc/benefits.do
http://localhost:8888/fepesvc/benefits.do





Type in the below url, you should be able to see the corresponding error page for login error.

/fepesvc/loginHandler.do?loginErrorPage=InvalidAccess
/fepesvc/loginHandler.do?loginErrorPage=planUser
/fepesvc/loginHandler.do?loginErrorPage=pendingaccount
/fepesvc/loginHandler.do?loginErrorPage=CONTRACT_LOCKED
/fepesvc/loginHandler.do?loginErrorPage=MEMBER_NOT_FOUND
/fepesvc/loginHandler.do?loginErrorPage=MEMBER_NULLDOB
/fepesvc/loginHandler.do?loginErrorPage=MEMBER_INACTIVE
/fepesvc/loginHandler.do?loginErrorPage=unexpected.error 
/fepesvc/loginHandler.do?loginErrorPage=SSO.ERROR.INVALID.MSG  (this is the fall back out for the login error)

	@Bean
	public FilterRegistrationBean errorWebSealFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		
		registrationBean.setName("Error Redirect Filter");
		ErrorWebSealFilter redirectFilter = new ErrorWebSealFilter();
		registrationBean.setFilter(redirectFilter);
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(3);
		
		return registrationBean;
	}
	
	
	
		System.out.println("before mapping getRequestURI: " + httpRequest.getRequestURI());
		if(httpRequest.getRequestURI().contains("benefits.do")) {
			httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			httpResponse.setHeader("Location", "/eservice/index.html#/benefitsStatments");
			
		}else if (httpRequest.getRequestURI().contains("loginHandler.do")) {
			RequestDispatcher rd = request.getRequestDispatcher("/errorPage");
			rd.forward(request, response);
			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
		}
		
		