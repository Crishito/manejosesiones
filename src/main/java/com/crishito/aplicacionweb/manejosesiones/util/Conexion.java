package com.crishito.aplicacionweb.manejosesiones.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //Declaramos e inicializamos tres variables privadas
    //Para la Coneccion
    private static String url = "jdbc:mysql://localhost:3306/siscompraventa?useTimezone=UTC";
    private static String userName = "root";
    private static String password = "";

    /*
    * Implementacmos un méyodo de tipo Connection para obtener la
    * conexión meduiante la variable que inicializamos*/

    public static Connection getConnection()throws SQLException {
        return DriverManager.getConnection(url,userName,password);
    }



    //Metodo main para probar la conexion
    public static void main(String[] args) {
        try (Connection con = Conexion.getConnection()) {
            System.out.println("Conexión correcta a la base de datos");
        } catch (SQLException e) {
            System.out.println("Conexión incorrecta a la base de datos");
            e.printStackTrace();
        }

    }
}
