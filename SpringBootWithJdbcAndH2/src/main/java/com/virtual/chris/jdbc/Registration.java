package com.virtual.chris.jdbc;

public class Registration {
	
	private Long id;
	private String name;
	private String address;
	
	
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Registration(Long id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Registration [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
	
	

}
