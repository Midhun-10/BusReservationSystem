package com.admin_servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connections.EntityConnection;
import com.entities.Bus;
import com.entities.Seat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@SuppressWarnings("serial")
@WebServlet("/busdetail")
public class InspectBusServlet extends HttpServlet {
    private EntityManagerFactory emf = null;

    @Override
    public void init() throws ServletException {
        emf = EntityConnection.getInstance().getEntityConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        List<Bus> list = null;

        try {
            list = em.createQuery("SELECT b FROM Bus b", Bus.class).getResultList();
        } finally {
            em.close();
        }

        req.setAttribute("buslist", list);
        RequestDispatcher dispatcher = req.getRequestDispatcher("inspectbus.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busName = req.getParameter("bus_name");

        EntityManager em = emf.createEntityManager();
        Bus bus = em.createQuery("SELECT b FROM Bus b WHERE b.name = :name", Bus.class).setParameter("name", busName).getSingleResult();

        List<Seat> seats = em.createQuery("SELECT s FROM Seat s WHERE s.bus.id = :bid", Seat.class).setParameter("bid", bus.getId()).getResultList();

        req.setAttribute("bus", bus);
        req.setAttribute("seats", seats);
        req.setAttribute("buslist", em.createQuery("SELECT b FROM Bus b", Bus.class).getResultList());

        em.close();

        req.getRequestDispatcher("inspectbus.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        EntityConnection.getInstance().closeConnections();
    }
}
