package com.servlets;

import java.io.IOException;
import java.util.List;


import com.dao.DatabaseManager;
import com.dao.ReportData;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GenerateReportServlet
 */
public class GenerateReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the start and end dates from the form
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // Call a method to generate the report based on the specified dates
        List<ReportData> reportData = DatabaseManager.generateReport(startDate, endDate);

        // Set the reportData attribute and forward the request to the report page
        request.setAttribute("reportData", reportData);
        RequestDispatcher rd = request.getRequestDispatcher("report.jsp");
        rd.forward(request, response);
    }
}
