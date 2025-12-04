package com.admin_servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entities.Admin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@SuppressWarnings("serial")
@WebServlet("/admin")
public class AdminLoginServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dev");
		EntityManager em = emf.createEntityManager();
		
		Admin a = em.find(Admin.class, email);
		em.close();
		
		System.out.println(a);
		
		if(a!=null) {
			if(a.getPassword().equals(password)) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("adminportal.html");
				dispatcher.forward(req, resp);
			} else {
				resp.sendRedirect("admin.html?passworderror=Password Incorrect");
			}
		} else {
			resp.sendRedirect("admin.html?emailerror=Email does not found");
		}
	
	}

}
