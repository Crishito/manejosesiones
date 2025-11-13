package com.crishito.aplicacionweb.manejosesiones.controllers;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Listado de Productos</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body class='bg-light d-flex align-items-center justify-content-center vh-100'>");

        out.println("<div class='container'>");
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-6'>");

        out.println("<div class='card shadow-lg'>");
        out.println("<div class='card-header bg-primary text-white text-center'>");
        out.println("<h3>Listado de Productos</h3>");
        out.println("</div>");
        out.println("<ul class='list-group list-group-flush'>");
        out.println("<li class='list-group-item'>Mouse Gamer RGB</li>");
        out.println("<li class='list-group-item'>Teclado Mecánico Redragon</li>");
        out.println("<li class='list-group-item'>Auriculares HyperX</li>");
        out.println("<li class='list-group-item'>Monitor 27'' Curvo</li>");
        out.println("<li class='list-group-item'>PC Gamer Ryzen 7</li>");
        out.println("</ul>");
        out.println("<div class='card-body text-center'>");
        out.println("<a href='/manejosesiones/' class='btn btn-outline-primary'>Volver al inicio</a>");
        out.println("</div>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body>");
        out.println("</html>");
    }
}
