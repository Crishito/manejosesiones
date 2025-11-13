package com.crishito.aplicacionweb.manejosesiones.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

public class LoginServiceSessionImpl implements LoginService {

    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        // Intenta obtener la sesión existente. NO la crea si no hay.
        HttpSession session = request.getSession(false);

        // Solo procede si la sesión existe
        if (session != null) {
            String username = (String) session.getAttribute("username");

            // Solo retorna el Optional si el atributo "username" existe
            if (username != null) {
                return Optional.of(username);
            }
        }

        // Retorna Optional vacío si no hay sesión o no hay atributo
        return Optional.empty();
    }
}