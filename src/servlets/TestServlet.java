package servlets;

import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter w = response.getWriter();

        w.println("<html>");
        w.println("<h1>HELLO</h1>");
        w.println("<html>");
    }
}
