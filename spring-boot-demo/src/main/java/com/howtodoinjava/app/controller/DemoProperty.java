package com.howtodoinjava.app.controller;

import java.util.List;
import java.util.Map;
 
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
 

@Component
@PropertySource("classpath:message.properties")
@ConfigurationProperties("routing")
public class DemoProperty {
 Map<String, String> mapProperty;
 private int threadPool;
 private String email;	
	
	@Override
public String toString() {
	return "DemoProperty [threadPool=" + threadPool + ", email=" + email + "]";
}
	public int getThreadPool() {
	return threadPool;
}
public void setThreadPool(int threadPool) {
	this.threadPool = threadPool;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
	public Map<String, String> getMapProperty() {
		return mapProperty;
	}
	public void setMapProperty(Map<String, String> mapProperty) {
		this.mapProperty = mapProperty;
	}
}