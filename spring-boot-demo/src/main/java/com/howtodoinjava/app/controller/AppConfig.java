package com.howtodoinjava.app.controller;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.howtodoinjava.app.filter.ErrorWebSealFilter;
//import com.fepoc.fepesvcrouting.exception.FepesvcRoutingException;
import com.howtodoinjava.app.filter.PerformanceFilter;
import com.howtodoinjava.app.filter.RedirectFilter;

//***************************************************************************************************
//
//		Use this Configuration Class to add custom configurations and/or to register filters
//
//***************************************************************************************************
@Configuration
public class AppConfig {

	@Autowired
	private Environment env;

	@Bean
	@ConditionalOnProperty(name = "performanceLog.enabled", havingValue = "true", matchIfMissing = false)
	public FilterRegistrationBean performanceFilterRegistrationBean()
			throws Exception {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		
		//Performance Filter - it should be on higher priority
		registrationBean.setName("Performance Filter");
		PerformanceFilter performanceFilter = new PerformanceFilter();
		String perfTimers = env.getProperty("performanceLog.timer");
		if (!StringUtils.isEmpty(perfTimers)) {
			String[] logTimers = perfTimers.split(",");
			int[] timers = new int[logTimers.length];
			for (int i = 0; i < logTimers.length; i++) {
				timers[i] = Integer.parseInt(logTimers[i]);
			}
			performanceFilter.setLogTimer(timers);
		} else {
			throw new Exception(
					"Performance log timer property 'performanceLog.timer' not defined in property file.");
		}

		registrationBean.setFilter(performanceFilter);
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);
		
		return registrationBean;
	}
	/*
	@Bean
	public FilterRegistrationBean errorWebSealFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		
		registrationBean.setName("Error Redirect Filter");
		ErrorWebSealFilter redirectFilter = new ErrorWebSealFilter();
		registrationBean.setFilter(redirectFilter);
		registrationBean.addUrlPatterns("/*"); 
		registrationBean.setOrder(2);
		
		return registrationBean;
	}*/
	
	@Bean
	public FilterRegistrationBean redirectFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		
		registrationBean.setName("Redirect Filter");
		RedirectFilter redirectFilter = new RedirectFilter();
		registrationBean.setFilter(redirectFilter);
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(2);
		
		return registrationBean;
	}

	@Bean(name="classicToMSiteUrlParametersMap")
	public Map<String, String> loadClassicToMSiteUrlParametersMap(){
		Map<String, String> classicToMSiteUrlParametersMap = new LinkedHashMap<>();
		
		classicToMSiteUrlParametersMap.put("selectInquiryId","/view?inqId");
		classicToMSiteUrlParametersMap.put("inquiryDetail.inquiryType","?inqType");
		classicToMSiteUrlParametersMap.put("claimTranId","?claimTranId");
		classicToMSiteUrlParametersMap.put("eobMailingPrefEventId","?eobMailingPrefEventId"); 
		classicToMSiteUrlParametersMap.put("memberPreferenceSection","?memberPreferenceSection");
		classicToMSiteUrlParametersMap.put("category","?category");
		
		return classicToMSiteUrlParametersMap;
	}
	@Bean(name="errorWebSealURI")
	public List<String> loadErrorWebSealURI() {
		List<String> errorWebSealURI = new LinkedList<String> ();
		errorWebSealURI.add("/fepesvc/loginHandler.do");
		
		return errorWebSealURI;
	}
	@Bean(name="excludeClassicURI")
	public List<String> loadExcludeClassicURI(){
		List<String> excludeClassicURI = new LinkedList<String> ();
		
		excludeClassicURI.add("/fepesvc/home.do");
		excludeClassicURI.add("/fepesvc/profile.do");
		excludeClassicURI.add("/fepesvc/inbox.do");
		excludeClassicURI.add("/fepesvc/initiateInquiry.do");
		excludeClassicURI.add("/fepesvc/initiateInquiryCreate.do");
		excludeClassicURI.add("/fepesvc/initiateInquirySave.do");
		excludeClassicURI.add("/fepesvc/viewClaimAlert.do");
		excludeClassicURI.add("/fepesvc/memberEligibilityProcess.do");
		excludeClassicURI.add("/fepesvc/memberPreference");
		excludeClassicURI.add("/fepesvc/taxdocuments");
		excludeClassicURI.add("/fepesvc/eobMailingPrefConfirm.do");
		excludeClassicURI.add("/fepesvc/lifeEvents");
		excludeClassicURI.add("/fepesvc/ecf");
		
		return excludeClassicURI;
	}
	
	@Bean(name="mapClassicURIToMSiteURI")
	public Map<String, String> loadMapClassicURIToMSiteURI(){
		Map<String, String> mapClassicURIToMSiteURI = new LinkedHashMap<>();
		
		mapClassicURIToMSiteURI.put("/fepesvc/home.do", "/eservice/index.html#/home");
		mapClassicURIToMSiteURI.put("/fepesvc/profile.do", "/eservice/index.html#/enrollmentRoster");
		mapClassicURIToMSiteURI.put("/fepesvc/memberEligibilityProcess.do", "/eservice/index.html#/claims");
		mapClassicURIToMSiteURI.put("/fepesvc/viewClaimAlert.do", "/eservice/index.html");
		mapClassicURIToMSiteURI.put("/fepesvc/viewSOC.do", "/eservice/index.html#/pharmacyClaims");
		mapClassicURIToMSiteURI.put("/fepesvc/benefits.do", "/eservice/index.html#/accumulator");
		mapClassicURIToMSiteURI.put("/fepesvc/inbox.do", "/eservice/index.html#/inquiry");
		mapClassicURIToMSiteURI.put("/fepesvc/initiateInquiry.do", "/eservice/index.html#/inquiry/create");
		mapClassicURIToMSiteURI.put("/fepesvc/initiateInquiryCreate.do", "/eservice/index.html#/inquiry/create");
		mapClassicURIToMSiteURI.put("/fepesvc/initiateInquirySave.do", "/eservice/index.html#/inquiry/create");
		mapClassicURIToMSiteURI.put("/fepesvc/updateOHI", "/eservice/index.html#/enrollmentRoster/opl");
		mapClassicURIToMSiteURI.put("/fepesvc/otherCoverageMain.do", "/eservice/index.html#/enrollmentRoster/opl");
		mapClassicURIToMSiteURI.put("/fepesvc/enrolleeInformation.do", "/eservice/index.html#/enrollmentRoster/memberInformation?alert=ssnAlert");
		mapClassicURIToMSiteURI.put("/fepesvc/updateSSN", "/eservice/index.html#/enrollmentRoster/memberInformation?alert=ssnAlert");
		mapClassicURIToMSiteURI.put("/fepesvc/addressChangeRoster.do", "/eservice/index.html#/enrollmentRoster");
		mapClassicURIToMSiteURI.put("/fepesvc/otherCoverageMemberProcess.do", "/eservice/index.html#/enrollmentRoster");
		mapClassicURIToMSiteURI.put("/fepesvc/financialSummary", "/eservice/index.html#/financialSummary");
		mapClassicURIToMSiteURI.put("/fepesvc/memberPreference", "/eservice/index.html#/memberPreference");
		mapClassicURIToMSiteURI.put("/fepesvc/eobMailingPrefWelcome.do", "/eservice/index.html#/memberPreference?memberPreferenceSection=EobPreference");
		mapClassicURIToMSiteURI.put("/fepesvc/benefitStatements", "/eservice/index.html#/benefitStatements");	
		mapClassicURIToMSiteURI.put("/fepesvc/addressChange.do", "/eservice/index.html#/enrollmentRoster/contactInformation?action=");	
		mapClassicURIToMSiteURI.put("/fepesvc/permissions.do", "/eservice/index.html#/memberPreference/permission");
		mapClassicURIToMSiteURI.put("/fepesvc/taxdocuments", "/eservice/index.html#/taxDocuments");
		mapClassicURIToMSiteURI.put("/fepesvc/lifeEvents", "/eservice/index.html#/lifeEvents"); 
		mapClassicURIToMSiteURI.put("/fepesvc/ecf", "/eservice/index.html#/ecf");
		mapClassicURIToMSiteURI.put("/fepesvc/dnc", "/eservice/index.html#/dnc");
		mapClassicURIToMSiteURI.put("/fepesvc/idCardRequest.do", "/eservice/index.html#/requestIdCard");
		mapClassicURIToMSiteURI.put("/fepesvc/newIdCardRequest.do", "/eservice/index.html#/requestIdCard");
		mapClassicURIToMSiteURI.put("/fepesvc/eobMailingPrefConfirm.do", "/eservice/index.html#/paperlessEobConfirmation");
		mapClassicURIToMSiteURI.put("/fepesvc/mobileVerify", "/eservice/index.html#/enrollmentRoster?contactVerify=mobile");
		
		return mapClassicURIToMSiteURI;
	}
	
	@Bean(name="classicToMSisteUrlParametersMap")
	public Map<String, String> loadClassicToMSisteUrlParametersMap(){
		Map<String, String> classicToMSisteUrlParametersMap = new LinkedHashMap<>();
		
		classicToMSisteUrlParametersMap.put("selectInquiryId","/view?inqId");
		classicToMSisteUrlParametersMap.put("inquiryDetail.inquiryType","?inqType");
		classicToMSisteUrlParametersMap.put("claimTranId","?claimTranId");
		classicToMSisteUrlParametersMap.put("eobMailingPrefEventId","?eobMailingPrefEventId"); 
		classicToMSisteUrlParametersMap.put("memberPreferenceSection","?memberPreferenceSection");
		classicToMSisteUrlParametersMap.put("category","?category");
		
		return classicToMSisteUrlParametersMap;
	}
	
	@Bean(name="paramBasedDeepLinksMap")
	public Map<String, Map<String, String>> loadParamBasedDeepLinksMap(){
		Map<String, Map<String, String>> paramBasedDeepLinksMap = new LinkedHashMap<>();
		
		Map<String, String> claimTypeLinksMap = new LinkedHashMap<>();
		claimTypeLinksMap.put("Medical", "#/claims");
		claimTypeLinksMap.put("Pharmacy", "#/pharmacyClaims");
		
		paramBasedDeepLinksMap.put("claimType", claimTypeLinksMap);
		
		return paramBasedDeepLinksMap;
	}

}
