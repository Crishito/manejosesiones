package com.crishito.aplicacionweb.manejosesiones.repositorio;

import com.crishito.aplicacionweb.manejosesiones.models.Categoria;
import com.crishito.aplicacionweb.manejosesiones.models.Producto;
import com.crishito.aplicacionweb.manejosesiones.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImplement implements Repository<Producto>{

   //Obtener la conexión a la BBDD
    private Connection conn;
    //Obtener mi conexión mediante el constructor
    public ProductoRepositoryJdbcImplement (Connection conn) {
        this.conn=conn;
        }


    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select p.* , c.nombreCategoria as categoria FROM producto as p " +
                     "inner join categoria as c ON (p.idCategoria=c.id) order by p.id ASC")) {

            while (rs.next()) {
                Producto p = getProducto(rs);
                productos.add(p);

            }
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        return null;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    private static Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getLong("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setStock(rs.getInt("stock"));
        p.setPrecio(rs.getDouble("precio"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setFechaElaboracion(rs.getDate("fecha_elaboracion").toLocalDate());
        p.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
        p.setCondicion(rs.getInt("condicion"));
        //Creamos un nuevo objeto de tipo categoria
        Categoria c = new Categoria();
        c.setId(rs.getLong("idCategoria"));
        c.setNombre(rs.getString("nombreCategoria"));
        p.setCategoria(c);
        return p;
    }
}
