package com.azhya.model_layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.azhya.model_layer.models.Student;

//our StudentService class will process our business logic 
// by the appropriate student needed by the controller layer

public class StudentService {
	// for this example, i will hard-code in some students to test our servlets
	// NOTE: this will be where the DAO layer is needed to provide the students
	// from our database

	// this method is the findStudentById method
	public List<Student> studentDatabase(){
		System.out.println("getting list of students");
		List<Student> list = new ArrayList<Student>();
		list.add(new Student(1, "John", "Doe", 80.25));
		list.add(new Student(2, "Jane", "Goodall", 96.23));
		list.add(new Student(3, "Max", "Born", 75.20));
		list.add(new Student(4, "Azhya", "Knox", 100));
		return list;
	}
	public Student getStudentById(int id) {
		System.out.println("in the student service class - id: " + id);
		//get students from database
		List<Student> list = studentDatabase();
		//compare id to student list
		for (Student student : list) {
			if(student.getId() == id) {
				//return student object if found
				return student;
			}
		}
		
		//if not student found, return null
		return null;
	}

	// this method is the findStudentByFirstName method
	public Student getStudentByFirstName(String fname) {
		System.out.println("in the student service class - first name: " + fname);
		//get students from database
		List<Student> list = studentDatabase();
		//compare fname to student list
		for (Student student : list) {
			if(student.getFirstName().equals(fname)) {
				//return student object if found
				return student;
			}
		}
		
		//if not student found, return null
		return null;
	}

}
