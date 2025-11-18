package com.crishito.aplicacionweb.manejosesiones.filter;


import com.crishito.aplicacionweb.manejosesiones.util.Conexion;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter("/*")
public class ConexionFilter implements Filter {
    /*
    * Una clase filtrer en java es un objeto que realiza tareas de filtrar
    * en las solicitudes en peticiíon y respuesya a un recurso. Los filtros
    * se pueden ejecutar de manera dinamica para trasformar la
    * informacion que contiene,. El filtrado se realiza mediante el método doFiltrer () */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*
        *
        * request: peticion del cliente
        * response: respuesta del servidor
        * chain: Es una clase de filtro qie representa el flujo de procesamiento,
        * llama al método chain .doFilter (request, response), dentro de un filtro
        * pasa la solicitud al soguiente fitro o al recurso destino (servlet, jsp,
        * pdf u otro)
        * */

        //Llamamos a la conexión
        try(Connection connection = Conexion.getConnection()){
            //Verificamos que la conexion no se realice automáticamente
            if (connection.getAutoCommit()) {
                //cambiamos a una conexion manual
                connection.setAutoCommit(false);

            }
            try{
                request.setAttribute("conn", connection);
                chain.doFilter(request, response);
                connection.commit();
            }catch (SQLException e){
                connection.rollback();

            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}
