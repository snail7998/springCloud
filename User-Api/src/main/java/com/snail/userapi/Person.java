package com.snail.userapi;

public class Person {

	int id;
	String name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Person{" +
						"id=" + id +
						", name='" + name + '\'' +
						'}';
	}
}
