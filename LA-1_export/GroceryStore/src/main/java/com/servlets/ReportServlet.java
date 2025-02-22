package com.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.dao.ReportData;


/**
 * Servlet implementation class ReportServlet
 */
import java.io.*;
import java.sql.*;
import java.util.*;

import com.dao.Item;
import com.dao.Order;
import com.dao.ReportData;
import com.dao.DatabaseManager;

public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            String startDate = request.getParameter("startDate"); // Adjust this to get the actual start date from the request
            String endDate = request.getParameter("endDate"); // Adjust this to get the actual end date from the request
            String seller = (String) request.getSession().getAttribute("username"); // Assuming username is used to identify the seller

            DatabaseManager dbManager = new DatabaseManager();
            List<ReportData> reportData = dbManager.generateReport(startDate, endDate,seller);

            request.setAttribute("items", reportData); // Assuming you want to pass this data to the JSP for display
            RequestDispatcher rd = request.getRequestDispatcher("report.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            out.println(e);
        }
    }

}

