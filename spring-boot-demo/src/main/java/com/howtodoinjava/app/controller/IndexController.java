package com.howtodoinjava.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;





/*
 *     private AppProperties app;
    private GlobalProperties global;

    @Autowired
    public void setApp(AppProperties app) {
        this.app = app;
    }

    @Autowired
    public void setGlobal(GlobalProperties global) {
        this.global = global;
    }
 */

@Controller
public class IndexController {
	

	// a SpringBoot way to retrieve property
	
	private DemoProperty demoProperty;
	 private GlobalProperties global;
	@Autowired
	public void setDemoProperty(DemoProperty demoProperty) {
		this.demoProperty = demoProperty;
	}
	
	  @Autowired
	    public void setGlobal(GlobalProperties global) {
	        this.global = global;
	    }

	@RequestMapping("/")
	public String home(Map<String, Object> model) {
		 String globalProperties = global.toString();
		 System.out.println("GLOBLA VALUES:" + globalProperties);
		return "index";
	}
	
	@RequestMapping("/next")
	public String next(Map<String, Object> model) {
		model.put("message", "You are in new page !!");
		return "next";
	}
	
	@RequestMapping("/errorPage")
	public String errorPage() {
	/*	demoProperty.getMapProperty().get("CONTRACT_LOCKED"); 
		demoProperty.getMapProperty().get("MEMBER_NOT_FOUND");
		System.out.println("DEMO PROPERTY 1: " + demoProperty.getMapProperty().get("CONTRACT_LOCKED"));
		
		
		System.out.println("VALUE OF EMAIL :" + demoProperty.getEmail());
*/		return "errorPage";
	}

}