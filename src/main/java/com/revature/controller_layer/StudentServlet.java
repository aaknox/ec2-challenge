package com.revature.controller_layer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.azhya.model_layer.models.Student;
import com.azhya.model_layer.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentService studentService = new StudentService();

	private void processLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// NOTE: do some route guarding here (aka user permission, request verb checks)

		// get information from form
		String studentFirstName = request.getParameter("username");
		String studentID = request.getParameter("password");

		// check if info retrieval was successful
		System.out.println("User submitted:\n User First Name: " + studentFirstName + "\nUser Id: " + studentID);

		// convert information accordingly (usually with a service layer conversion
		// method)
		if (studentID != null) {
			try {
				int id = Integer.parseInt(studentID);
				Student s = studentService.getStudentById(id);

				// check if first name & id match the students list
				// PRESENT -> save session info and go to home page
				// NOT PRESENT -> send back a 403 status code
				System.out.println("beginning to valid student info");
				if (s.getFirstName().equals(studentFirstName) && s.getId() == id) {
					System.out.println("student exists");
					// save parameter as session params
					request.getSession().setAttribute("studentName", studentFirstName);
					request.getSession().setAttribute("studentId", studentID);
					// send user to home page
					RequestDispatcher dispatcher = request.getRequestDispatcher("home.html");
					dispatcher.forward(request, response);
				} else {
					System.out.println("user doesn't exist");
					// send user to error page
					response.setContentType("text/html");
					PrintWriter pw = response.getWriter();
					pw.println("<h3 style='color:red'>403 - Denied</h3>");
					pw.println("<p>Username or password is incorrect</p>");
					pw.println("<br><a href='index.html'>Go back to LogIn</a>");
					response.setStatus(403);
					//send redirect
					response.sendRedirect("login");
				}
			} catch (NumberFormatException e) {
				System.out.println("That's not a number!!!");
				// send user to error page
				response.setContentType("text/html");
				PrintWriter pw = response.getWriter();
				pw.write("<h3 style='color:red'>422 - Unprocessable Entity</h3>");
				pw.write("<p>" + e + "</p>");
				pw.write("<br><a href='index.html'>Go back to LogIn</a>");
				response.sendError(422);
				pw.close();
			}
		}

	}

	private void processLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("User is logging out");
		// use printwriter to show a log out message
		HttpSession session = request.getSession();
		String studentFirstName = session.getAttribute("username").toString();
		if (!studentFirstName.isEmpty()) {
			// clear session variables
			session.invalidate();
			// show log out message
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("<h3 style='color:green'>Goodbye!</h3>");
			pw.println("<p>You have successfully logged out.</p>");
			pw.println("<br><a href='index.html'>Go back to LogIn</a>");
		} else {
			System.out.println("Client session has expired. Please log in again.");
			// send user to error page
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("<h3 style='color:red'>440 - Expired Session</h3>");
			pw.println("<p>Session timed out. Please log in again.</p>");
			pw.println("<br><a href='index.html'>Go back to LogIn</a>");
			response.setStatus(440);
		}
	}

	private void processViewProfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("User is viewing their profile");
		// use object mapper to show user profile
		HttpSession session = request.getSession();
		String studentFirstName = session.getAttribute("currentUser").toString();
		if (!studentFirstName.isEmpty()) {
			// get student from database list
			Student s = studentService.getStudentByFirstName(studentFirstName);
			// convert student info into JSON using ObjectMapper
			PrintWriter pw = response.getWriter();
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(s);
			pw.println(json);
		} else {
			System.out.println("user doesn't exist");
			// send user to error page
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("<h3 style='color:red'>404 - Student Not Found</h3>");
			pw.println("<p>Current student does not exist. Please sign in again.</p>");
			pw.println("<br><a href='index.html'>Go back to LogIn</a>");
			response.setStatus(404);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("inside doGet method of servlet");
		final String endpoint = request.getRequestURI().replace("/SimpleMVCDemo/", "");
		System.out.println("Endpoint: " + endpoint);
		switch (endpoint) {
		case "profile":
			processViewProfile(request, response);
			break;
		default:
			System.out.println("Page does not exist");
			response.setStatus(404);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("inside doPost method of servlet");
		final String endpoint = request.getRequestURI().replace("/SimpleMVCDemo/", "");
		System.out.println("Endpoint: " + endpoint);
		switch (endpoint) {
		case "login":
			processLogin(request, response);
			break;
		case "logout":
			processLogout(request, response);
			break;
		default:
			System.out.println("Page does not exist");
			response.setStatus(404);
			break;
		}

	}
}
