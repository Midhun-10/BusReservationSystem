package com.user_servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        String expiry = request.getParameter("expiry");
        String cvv = request.getParameter("cvv");
        String amount = request.getParameter("amount");

        if(cardName == null || cardName.isEmpty() || 
           cardNumber == null || cardNumber.isEmpty() || 
           expiry == null || expiry.isEmpty() || 
           cvv == null || cvv.isEmpty()) {

            request.setAttribute("errorMessage", "Please fill all the fields.");
            request.getRequestDispatcher("payment.jsp").forward(request, response);
            return;
        }

        boolean paymentSuccess = true; // always success in this demo

        if(paymentSuccess) {
            request.setAttribute("amount", amount);
            request.setAttribute("cardName", cardName);
            request.getRequestDispatcher("payment_success.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Payment failed! Try again.");
            request.getRequestDispatcher("payment.jsp").forward(request, response);
        }
    }
}
