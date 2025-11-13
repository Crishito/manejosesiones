package com.crishito.aplicacionweb.manejosesiones.controllers;


import com.crishito.aplicacionweb.manejosesiones.services.LoginService;
import com.crishito.aplicacionweb.manejosesiones.services.LoginServiceSessionImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login","/login.html"})
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "crishito";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        if (usernameOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Bienvenido</title>");
                out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
                out.println("</head>");
                out.println("<body class='bg-light d-flex align-items-center justify-content-center vh-100'>");

                out.println("<div class='card shadow-lg p-4 text-center' style='width: 25rem;'>");
                out.println("<h1 class='text-primary mb-3'>Hola " + usernameOptional.get() + "!</h1>");
                out.println("<p class='lead text-muted mb-4'>Has iniciado sesión correctamente.</p>");
                out.println("<div class='d-grid gap-2'>");
                out.println("<a href='" + req.getContextPath() + "/index.html' class='btn btn-outline-primary'>Volver al inicio</a>");
                out.println("<a href='" + req.getContextPath() + "/logout' class='btn btn-outline-danger'>Cerrar sesión</a>");
                out.println("</div>");
                out.println("</div>");

                out.println("</body>");
                out.println("</html>");
            }

        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String username = req.getParameter("user");
        String password = req.getParameter("password");

        if(USERNAME.equals(username) && PASSWORD.equals(password)){
            HttpSession session = req.getSession();
            session.setAttribute(USERNAME, username);
            resp.sendRedirect(req.getContextPath()+"/login.html");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No autorizado para el ingreso al sistema");
        }
    }
}
