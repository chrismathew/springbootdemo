package com.howtodoinjava.app.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;



public class RedirectFilter implements Filter {

		
	private Map<String, String> mapClassicURIToMSiteURI;
	private List<String> excludeClassicURI;
	private Map<String, String> classicToMSiteUrlParametersMap;
	private Map<String, Map<String,String>> paramBasedDeepLinksMap;
	private List<String> errorWebSealURI;
	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		mapClassicURIToMSiteURI = (Map<String,String>)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("mapClassicURIToMSiteURI");
		excludeClassicURI = (List<String>)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("excludeClassicURI");
		classicToMSiteUrlParametersMap = (Map<String,String>)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("classicToMSiteUrlParametersMap");
		paramBasedDeepLinksMap = (Map<String,Map<String,String>>)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("paramBasedDeepLinksMap");
		errorWebSealURI = (List<String>)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("errorWebSealURI");
		System.out.println("mapClassicURIToMSiteURI: " + mapClassicURIToMSiteURI.toString());
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		System.out.println("before mapping getRequestURI: " + httpRequest.getRequestURI());
	
		if(errorWebSealURI.contains(httpRequest.getRequestURI()) ){
				System.out.println("value of the URI" + httpRequest.getRequestURI());
				System.out.println("value of the requestParams" + httpRequest.getParameter("loginErrorPage"));
				String arrayLinks[] = httpRequest.getParameterValues("loginErrorPage");
				
				System.out.println("Servlet Context" + httpRequest.getServletContext());
				System.out.println("Servlet Path" + httpRequest.getServletPath());
				System.out.println("Servlet name" + httpRequest.getServerName());
				System.out.println("Servlet port" + httpRequest.getServerPort());
				
				for(int i=0;i<arrayLinks.length;i++) {
					System.out.println("The error code :" + arrayLinks[i]);
					if(arrayLinks[i].equals("CONTRACT_LOCKED")) {
						httpRequest.setAttribute("errorCode", "CONTRACT_LOCKED");
					} else if(arrayLinks[i].equals("MEMBER_NOT_FOUND")) {
						httpRequest.setAttribute("errorCode", "MEMBER_NOT_FOUND");
					} else if(arrayLinks[i].equals("MEMBER_NULLDOB")) {
						httpRequest.setAttribute("errorCode", "MEMBER_NULLDOB");
					} else if(arrayLinks[i].equals("MEMBER_INACTIVE")) {
						httpRequest.setAttribute("errorCode", "MEMBER_INACTIVE");
					} else if(arrayLinks[i].equals("unexpected.error")) {
						httpRequest.setAttribute("errorCode", "unexpected.error");
					} else if(arrayLinks[i].equals("planUser")) {
						httpRequest.setAttribute("errorCode", "planUser");
					} else if(arrayLinks[i].equals("SSO.ERROR.INVALID.MSG")) {
						httpRequest.setAttribute("errorCode", "SSO.ERROR.INVALID.MSG");
					} 
					
					System.out.println("value of request :" + request);
					System.out.println("value of response :" + response);
					RequestDispatcher rd = httpRequest.getRequestDispatcher("/errorPage");
					rd.forward(request, response);
					//chain.doFilter(request, response);
					
				}
		} else if(excludeClassicURI.contains(httpRequest.getRequestURI()) || mapClassicURIToMSiteURI.containsKey(httpRequest.getRequestURI())){
			
			if(excludeClassicURI.contains(httpRequest.getRequestURI())) {
				StringBuilder newParams = new StringBuilder("");
				StringBuilder deeplinkUri = new StringBuilder("");
	
				Map<String, String[]> urlParamsMap = httpRequest.getParameterMap();
				if (urlParamsMap != null) {
					for (Map.Entry<String, String[]> entry : urlParamsMap.entrySet()) {
						if (classicToMSiteUrlParametersMap.containsKey(entry.getKey())) {
							newParams.append(classicToMSiteUrlParametersMap.get(entry.getKey()).contains("?") ? "" : newParams.toString().contains("?") ? "&" : "?");
							newParams.append(classicToMSiteUrlParametersMap.get(entry.getKey()));
							newParams.append("=").append(StringUtils.join(entry.getValue()));
						} 
						else if (paramBasedDeepLinksMap.containsKey(entry.getKey())) {
							Map<String, String> uriMap = paramBasedDeepLinksMap	.get(entry.getKey());
							deeplinkUri.append(uriMap.get(StringUtils.join(entry.getValue())));
						}
					}
				}
				// added for referer
				httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				httpResponse.setHeader("Location", mapClassicURIToMSiteURI.get(httpRequest.getRequestURI())	+ deeplinkUri.toString() + newParams.toString());
				System.out.println("exclude url after mapping location = "	+ mapClassicURIToMSiteURI.get(httpRequest.getRequestURI()) + deeplinkUri.toString() + newParams.toString());
			}
			else if(mapClassicURIToMSiteURI.containsKey(httpRequest.getRequestURI())) {
				if(httpRequest.getRequestURI().equalsIgnoreCase("/fepesvc/addressChange.do")){
					Map<String, String[]> urlParamsMap = httpRequest.getParameterMap();
					if(urlParamsMap!=null) {
						if(urlParamsMap.size() == 0){
							//added for referer
							String badContractAddressStr = "conAddrReq";
							String uriStr = mapClassicURIToMSiteURI.get(httpRequest.getRequestURI())  + badContractAddressStr;
							httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
							httpResponse.setHeader("Location", uriStr);
							System.out.println("bad address conAddrReq after mapping location = " + uriStr);
						}
						else{
							for(Map.Entry<String, String[]> entry : urlParamsMap.entrySet()) {
								if(entry.getKey().equalsIgnoreCase("addressType")) {
									String paramValue = entry.getValue()[0];
									if(paramValue.equals("Alternate") ){
										String badAlternateAddressStr = "badAltAddrReq";
										String uriStr = mapClassicURIToMSiteURI.get(httpRequest.getRequestURI())  + badAlternateAddressStr;
										httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
										httpResponse.setHeader("Location", uriStr);
										System.out.println("bad address badAltAddrReq after mapping location = "	+ uriStr);
									}
									else {
										System.out.println("cannot find mapping for url " + httpRequest.getRequestURI());
									}
								}
							}
						}
					}
				}
				else{
					//added for referer
					httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
					httpResponse.setHeader("Location", mapClassicURIToMSiteURI.get(httpRequest.getRequestURI()));
					System.out.println("map classic uri to mSite uri after mapping location " + mapClassicURIToMSiteURI.get(httpRequest.getRequestURI()));
				}
			}
				
			else{
				System.out.println("cannot find mapping for url " + httpRequest.getRequestURI());
			}
		 }  else {
			 chain.doFilter(request, response);
			 
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	//added for junit
	public void setMapClassicURIToMSiteURI(
			Map<String, String> mapClassicURIToMSiteURI) {
		this.mapClassicURIToMSiteURI = mapClassicURIToMSiteURI;
	}
	
	public void setExcludeClassicURI(List<String> excludeClassicURI) {
		this.excludeClassicURI = excludeClassicURI;
	}

	public void setClassicToMSisteUrlParametersMap(
			Map<String, String> classicToMSiteUrlParametersMap) {
		this.classicToMSiteUrlParametersMap = classicToMSiteUrlParametersMap;
	}

	public void setParamBasedDeepLinksMap(
			Map<String, Map<String, String>> paramBasedDeepLinksMap) {
		this.paramBasedDeepLinksMap = paramBasedDeepLinksMap;
	}
}
