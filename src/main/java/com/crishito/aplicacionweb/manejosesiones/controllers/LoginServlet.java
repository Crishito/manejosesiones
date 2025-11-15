package com.crishito.aplicacionweb.manejosesiones.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.crishito.aplicacionweb.manejosesiones.services.LoginService;
import com.crishito.aplicacionweb.manejosesiones.services.LoginServiceSessionImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {
    // Credenciales estáticas de ejemplo (¡no usar en producción!)
    public final static String USERNAME = "admin";
    public final static String PASSWORD = "12345";
    // Atributo de sesión para el contador
    public final static String CONTADOR_SESSION = "contador";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        // Si el usuario ya está logueado (en sesión)
        if (usernameOptional.isPresent()) {
            // contador
            HttpSession session = req.getSession(); // Obtenemos la sesión actual
            Integer contador = (Integer) session.getAttribute(CONTADOR_SESSION);

            if (contador == null) {
                contador = 1;
            } else {
                contador++;
            }
            session.setAttribute(CONTADOR_SESSION, contador);


            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("  <meta charset=\"UTF-8\">");
                // Incluimos estilos Bootstrap para esta vista también
                out.println("  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
                out.println("  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css\" rel=\"stylesheet\">");
                out.println("  <title>Hola " + usernameOptional.get() + "</title>");
                out.println("</head>");

                // Aplicamos el estilo del index al cuerpo para la coherencia de color
                out.println("<body style=\"background: linear-gradient(135deg, #0d6efd, #6f42c1); min-height: 100vh; display: flex; align-items: center; justify-content: center; padding: 20px;\">");
                out.println("<div class=\"card shadow-lg p-5 text-center\" style=\"width: 28rem; border-radius: 1rem;\">");

                // --- MOSTRAR CONTADOR EN EL MENSAJE ---
                out.println("  <h1 class=\"text-primary mb-3\"><i class=\"bi bi-check-circle-fill\"></i> ¡Hola " + usernameOptional.get() + "!</h1>");
                out.println("  <p class=\"lead text-success\">Has iniciado sesión <strong class=\"text-dark\">" + contador + "</strong> veces con éxito!</p>");
                // --- FIN MOSTRAR CONTADOR ---

                out.println("  <div class=\"d-grid gap-3 mt-4\">");
                out.println("  <a href=\"" + req.getContextPath() + "/index.html\" class=\"btn btn-primary\"><i class=\"bi bi-house\"></i> Volver al inicio</a>");
                out.println("  <a href=\"" + req.getContextPath() + "/logout\" class=\"btn btn-outline-danger\"><i class=\"bi bi-box-arrow-right\"></i> Cerrar sesión</a>");
                out.println("  </div>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
            // Si el usuario no está logueado, redirige a la página de login (login.jsp)
        } else {
            // Aseguramos que solo se envíe al login.jsp
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Obtiene los parámetros del formulario
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 2. Valida las credenciales
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Éxito:
            // Crea/obtiene la sesión
            HttpSession session = req.getSession();
            // Guarda el nombre de usuario en la sesión
            session.setAttribute("username", username);

            // Redirige al usuario a la página principal /login.html
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            // Error:
            // Envía un error 401 (No autorizado) con un mensaje
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "¡Lo sentimos no esta autorizado para ingresar a esta página!");
        }
    }
}