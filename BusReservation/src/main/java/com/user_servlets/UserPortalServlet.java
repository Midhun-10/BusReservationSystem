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
import com.entities.Passenger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@SuppressWarnings("serial")
@WebServlet("/userportal")
public class UserPortalServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = EntityConnection.getInstance().getEntityConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        List<String> list = null;

        try {
            list = em.createQuery("select b.name from Bus b", String.class).getResultList();
        } finally {
            em.close();
        }

        req.setAttribute("buslist", list);
        RequestDispatcher dispatcher = req.getRequestDispatcher("userportal.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String bus = req.getParameter("bus");   // bus name
        String contact = req.getParameter("contact");
        String price = req.getParameter("price");
        String name = req.getParameter("name");
        int seatno = Integer.parseInt(req.getParameter("seatno"));

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Passenger p = new Passenger();
            p.setName(name);
            p.setContact(contact);
            p.setBus_name(bus);
            p.setSeatno(seatno);
            em.persist(p);

            int busid = em.createQuery(
                    "select b.id from Bus b where b.name = :busname",
                    Integer.class
            ).setParameter("busname", bus)
             .getSingleResult();

            em.createQuery(
                    "UPDATE Seat s SET s.available = false " +
                    "WHERE s.bus.id = :busid AND s.seatNumber = :seatno"
            )
            .setParameter("busid", busid)
            .setParameter("seatno", seatno)
            .executeUpdate();

            et.commit();

        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        req.setAttribute("amount", price);
        RequestDispatcher dispatcher = req.getRequestDispatcher("payment.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void destroy() {
        EntityConnection.getInstance().closeConnections();
    }
}
