package com.user_servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connections.EntityConnection;
import com.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@SuppressWarnings("serial")
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = EntityConnection.getInstance().getEntityConnection();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User u = new User();
        u.setEmail(email);
        u.setPassword(password);

        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            em.persist(u);
            et.commit();

            RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            if (et != null && et.isActive()) et.rollback();
            resp.sendRedirect("register.html?error=Email already exist");

        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        EntityConnection.getInstance().closeConnections();
    }
}
