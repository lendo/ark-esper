package com.uumind.ark.esper.ch02eventrepresentation.pojo;

import java.util.List;
import java.util.Map;

public class Person {
	private String name;
	private int age;
	List<String> children;
	Map<String, Integer> phones;
	private Address address;

	public List<String> getChildren() {
		return children;
	}

	public Map<String, Integer> getPhones() {
		return phones;
	}

	public Address getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public void setPhones(Map<String, Integer> phones) {
		this.phones = phones;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

}
