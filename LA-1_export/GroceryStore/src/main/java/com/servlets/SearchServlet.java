package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.dao.DatabaseManager;
import com.dao.Item;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchQuery = request.getParameter("query");
        int itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));

        // Perform the search using the searchQuery
        List<Item> searchResults = performSearch(searchQuery, itemsPerPage);

        // Create the HTML response
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        sb.append("<tr><th>ID</th><th>Item Name</th><th>Photo</th><th>Description</th><th>Stock Available</th><th>Price</th><th>Action</th></tr>");
        for (Item item : searchResults) {
            sb.append("<tr>");
            sb.append("<td>").append(item.getId()).append("</td>");
            sb.append("<td>").append(item.getName()).append("</td>");
            sb.append("<td><img src='uploaded_images/").append(item.getImageUrl()).append("' width='100' height='100'></td>");
            sb.append("<td>").append(item.getDescription()).append("</td>");
            sb.append("<td>").append(item.getQuantity()).append("</td>");
            sb.append("<td>").append(item.getPrice()).append("</td>");
            sb.append("<td><form action='AddToCartServlet' method='post'>");
            sb.append("<input type='hidden' name='itemId' value='").append(item.getId()).append("'>");
            sb.append("<input type='hidden' name='itemName' value='").append(item.getName()).append("'>");
            sb.append("<input type='hidden' name='price' value='").append(item.getPrice()).append("'>");
            sb.append("<input type='hidden' name='imageURL' value='uploaded_images/").append(item.getImageUrl()).append("'>");
            sb.append("<button type='submit'>Add to Cart</button>");
            sb.append("</form></td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        // Write the HTML response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(sb.toString());
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("searchQuery");
        int itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));

        List<Item> searchResults = performSearch(query, itemsPerPage);

        request.setAttribute("searchResults", searchResults);
        request.getRequestDispatcher("search_results.jsp").forward(request, response);
    }

    private List<Item> performSearch(String query, int itemsPerPage) {
        List<Item> searchResults = new ArrayList<>();
        try {
            // Use the DatabaseManager to fetch items based on the search query and items per page
            DatabaseManager dbManager = new DatabaseManager();
            searchResults = dbManager.searchItems(query, itemsPerPage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
