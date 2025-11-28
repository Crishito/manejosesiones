package com.crishito.aplicacionweb.manejosesiones.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static String url = "jdbc:mysql://localhost:3306/siscompraventa?useTimezone=UTC"; // URL de conexión
    private static String username = "root"; // Usuario de la base de datos
    private static String password = ""; // Contraseña de la base de datos

    public static Connection getConnection() throws SQLException {
        //Llamamos la conexión mediante el DriverManager.
        return DriverManager.getConnection(url, username, password);
    }
}