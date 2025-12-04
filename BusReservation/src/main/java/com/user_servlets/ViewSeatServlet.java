package com.user_servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connections.EntityConnection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@SuppressWarnings("serial")
@WebServlet("/viewseat")
public class ViewSeatServlet extends HttpServlet {
	private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = EntityConnection.getInstance().getEntityConnection();
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bus = req.getParameter("bus");
		
		EntityManager em = emf.createEntityManager();
		
		int id = em.createQuery("SELECT b.id FROM Bus b WHERE b.name=:name", Integer.class).setParameter("name", bus).getSingleResult();

		List<Boolean> seatList = em.createQuery("SELECT s.available FROM Seat s WHERE s.bus.id = :id ORDER BY s.seatNumber",Boolean.class).setParameter("id", id).getResultList();

		req.setAttribute("seatList", seatList);

		List<String> allBuses = em.createQuery("SELECT b.name FROM Bus b", String.class).getResultList();
		req.setAttribute("buslist", allBuses);
		
		req.setAttribute("bus", bus);
		
		double price = em.createQuery("select b.price from Bus b where b.name=:name", Double.class).setParameter("name", bus).getSingleResult();
		req.setAttribute("price", price);

		RequestDispatcher dispatcher = req.getRequestDispatcher("userportal.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	@Override
	public void destroy() {
		EntityConnection.getInstance().closeConnections();
	}

}
