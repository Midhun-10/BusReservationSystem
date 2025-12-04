package com.user_servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connections.EntityConnection;
import com.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = EntityConnection.getInstance().getEntityConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        EntityManager em = emf.createEntityManager();
        User u = null;

        try {
            u = em.find(User.class, email);

        } finally {
            em.close();
        }

        if (u == null) {
            resp.sendRedirect("login.html?emailerror=Email does not exist");
            return;
        }

        if (!u.getPassword().equals(password)) {
            resp.sendRedirect("login.html?passworderror=Incorrect Password");
            return;
        }

        req.getRequestDispatcher("/userportal").forward(req, resp);
    }

    @Override
    public void destroy() {
        EntityConnection.getInstance().closeConnections();
    }
}
