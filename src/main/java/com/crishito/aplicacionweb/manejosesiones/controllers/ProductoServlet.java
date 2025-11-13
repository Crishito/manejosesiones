package com.crishito.aplicacionweb.manejosesiones.controllers;

import com.crishito.aplicacionweb.manejosesiones.models.Producto;
import com.crishito.aplicacionweb.manejosesiones.services.LoginService;
import com.crishito.aplicacionweb.manejosesiones.services.LoginServiceSessionImpl;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoService;
import com.crishito.aplicacionweb.manejosesiones.services.ProductoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Inicializar servicios
        ProductoService productoService = new ProductoServiceImpl();
        List<Producto> productos = productoService.listar();

        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        // Configurar la respuesta
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("  <meta charset=\"UTF-8\">");
            out.println("  <title>Listado de Productos</title>");
            // Incluir estilos Bootstrap y íconos
            out.println("  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
            out.println("  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css\" rel=\"stylesheet\">");
            out.println("</head>");

            // Aplicar estilo de fondo del index.html para coherencia
            out.println("<body style=\"background: linear-gradient(135deg, #0d6efd, #6f42c1); min-height: 100vh; padding: 20px;\">");
            out.println("<div class=\"container my-5\">"); // Contenedor central
            out.println("<div class=\"card shadow-lg p-4\">");

            out.println("  <h1 class=\"text-center mb-4 text-primary\"><i class=\"bi bi-boxes\"></i> Listado de Productos</h1>");

            // Mensaje de bienvenida condicional con alerta Bootstrap
            if (usernameOptional.isPresent()) {
                out.println("  <div class=\"alert alert-success text-center\" role=\"alert\">Hola <strong>" + usernameOptional.get() + "</strong>, ¡Bienvenido!</div>");
            } else {
                out.println("  <div class=\"alert alert-warning text-center\" role=\"alert\">Inicia sesión para ver los precios de los productos.</div>");
            }

            // Inicio de la tabla con clases de Bootstrap
            out.println("  <table class=\"table table-striped table-hover table-bordered text-center align-middle mt-4\">");
            out.println("    <thead class=\"table-dark\">"); // Estilo oscuro para el encabezado
            out.println("    <tr>");
            out.println("      <th><i class=\"bi bi-hash\"></i> Id</th>");
            out.println("      <th><i class=\"bi bi-tag\"></i> Nombre</th>");
            out.println("      <th><i class=\"bi bi-collection\"></i> Categoría</th>");

            // Columna de Precio (solo si el usuario está logueado)
            if (usernameOptional.isPresent()) {
                out.println("      <th><i class=\"bi bi-currency-dollar\"></i> Precio</th>");
            }
            out.println("    </tr>");
            out.println("    </thead>");
            out.println("    <tbody>");

            // Iterar sobre los productos
            for (Producto p : productos) {
                out.println("    <tr>");
                out.println("      <td>" + p.getIdProducto() + "</td>");
                out.println("      <td>" + p.getNombre() + "</td>");
                out.println("      <td>" + p.getCategoria() + "</td>");

                // Celda de Precio (solo si el usuario está logueado)
                if (usernameOptional.isPresent()) {
                    out.println("      <td>$" + p.getPrecio() + "</td>");
                }
                out.println("    </tr>");
            }

            out.println("    </tbody>");
            out.println("  </table>");

            // Botones de navegación
            out.println("<div class=\"text-center mt-4 d-grid gap-2 d-md-block\">");
            out.println("<a href=\"" + req.getContextPath() + "/index.html\" class=\"btn btn-outline-primary btn-lg\"><i class=\"bi bi-house\"></i> Volver al inicio</a>");
            if (usernameOptional.isPresent()) {
                out.println("<a href=\"" + req.getContextPath() + "/logout\" class=\"btn btn-outline-danger btn-lg\"><i class=\"bi bi-box-arrow-right\"></i> Cerrar sesión</a>");
            }
            out.println("</div>");

            // Cierre del div card, div container y del documento
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}