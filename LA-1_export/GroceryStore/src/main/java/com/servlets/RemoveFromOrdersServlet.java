package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.dao.Item;

/**
 * Servlet implementation class RemoveFromOrdersServlet
 */
import com.dao.DatabaseManager;



public class RemoveFromOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        int itemID = Integer.parseInt(request.getParameter("itemID"));

        DatabaseManager dbManager = new DatabaseManager();
        boolean removed = dbManager.removeItemFromOrders(orderID,itemID);

        if (removed) {
            response.sendRedirect("orders.jsp");
        } else {
            // handle the case when the item could not be removed
            response.getWriter().println("Item could not be removed from orders.");
            
            
        }
    }
}