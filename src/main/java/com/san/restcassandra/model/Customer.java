package com.san.restcassandra.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("customer")
public class Customer {
	
	@PrimaryKey
	private Long id;
	private String firstname;
	private String lastname;
	private int age;
	
	public Customer(){}
	
	public Customer(Long id, String firstname, String lastname, int age){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public String getFirstname(){
		return this.firstname;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	public String getLastname(){
		return this.lastname;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public int getAge(){
		return this.age;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%d, firstName='%s', lastName='%s', age=%d]", this.id,
				this.firstname, this.lastname, this.age);
	}
}