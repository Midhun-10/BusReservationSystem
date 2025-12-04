package com.admin_servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connections.EntityConnection;
import com.entities.Bus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@SuppressWarnings("serial")
@WebServlet("/busportal")
public class BusServlet extends HttpServlet {
	private EntityManagerFactory emf = null;
	
	@Override
	public void init() throws ServletException {
		emf = EntityConnection.getInstance().getEntityConnection();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String vehNumber = req.getParameter("veh_number");
        String name = req.getParameter("name");
        String contact = req.getParameter("contact");
        String destination = req.getParameter("destination");
        int capacity = Integer.parseInt(req.getParameter("capacity"));
        double price = Double.parseDouble(req.getParameter("price"));

        Bus bus = new Bus();
        bus.setVehNumber(vehNumber);
        bus.setName(name);
        bus.setContact(contact);
        bus.setDestination(destination);
        bus.setTotalSeats(capacity);
        bus.setPrice(price);

        bus.generateSeats();

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
        em.persist(bus);
        et.commit();
        
        resp.sendRedirect("busportal.html?success=true");
	}
	
	@Override
	public void destroy() {
		EntityConnection.getInstance().closeConnections();
	}
}
