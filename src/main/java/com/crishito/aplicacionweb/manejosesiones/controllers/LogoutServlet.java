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
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Inicializa el servicio para verificar la sesión
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        // Si el nombre de usuario está presente (hay una sesión activa)
        if (usernameOptional.isPresent()) {
            // Obtiene la sesión (existente)
            HttpSession session = req.getSession();
            // Invalida la sesión, eliminando todos sus atributos (incluyendo "username")
            session.invalidate();
        }

        // Redirige al usuario a la página de inicio o de login después de cerrar sesión
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}