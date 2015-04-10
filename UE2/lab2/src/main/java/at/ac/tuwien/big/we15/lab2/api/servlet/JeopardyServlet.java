package at.ac.tuwien.big.we15.lab2.api.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "", urlPatterns = {""})
@WebServlet("/home")
public class JeopardyServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	response.getWriter().write("Herro");
    }
}
