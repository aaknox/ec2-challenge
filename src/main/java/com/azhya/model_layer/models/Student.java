package com.azhya.model_layer.models;

//This is a entity model class that consist of:
//1) private fields
//2) a no-args constructor and all-args constructor
//3) getters and setters
//4) any needed object-class overridden methods (like toString())
//
//***For persisting data, you will need to implement Serializable 
//and a mirror DTO (data transfer object w/ same fields as Strings) class***

public class Student {
	private int id;
	private String firstName;
	private String lastName;
	private double grade;

	public Student() {
		// no args constructor
	}

	public Student(int id, String firstName, String lastName, double grade) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grade = grade;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", grade=" + grade + "]";
	}
	
}
