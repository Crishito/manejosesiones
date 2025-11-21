package com.crishito.aplicacionweb.manejosesiones.repositorio;

import com.crishito.aplicacionweb.manejosesiones.models.Categoria;
import com.crishito.aplicacionweb.manejosesiones.models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImplement implements Repository<Producto> {

    //Declaramos la variable BBDD
    private Connection conn;

    //Obtengo el conexión mediante el constructor
    public ProductoRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        // Query adaptado: p.id -> p.idProducto
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombreCategoria as categoria FROM producto as p INNER JOIN categoria as c ON (p.idCategoria = c.id) order by p.id ASC;")) {
            while (rs.next()) {
                Producto p = getProducto(rs);
                productos.add(p);
            }
        }
        return productos;
    }

    //Implementamos un método para buscar un registro por ID
    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        // Query adaptado: p.id -> p.idProducto, c.nombreCategoria -> c.nombre
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.*, c.nombre as nombreCategoria FROM productos as p INNER JOIN categorias as c ON (p.idCategoria = c.id) WHERE p.idProducto=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = getProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;
        // Se usa getIdProducto()
        if (producto.getIdProducto() != null && producto.getIdProducto() > 0) {
            // Se asume que la tabla tiene las columnas de 'Producto' de Cris
            sql = "UPDATE productos SET nombre=?, idCategoria=?, stock=?, precio=?, descripcion=?, codigo=?, fecha_elaboracion=?, fecha_caducidad=?, condicion=? where idProducto=?";
        } else {
            // Columnas ajustadas para la tabla de Cris/Elvis
            sql = "INSERT INTO productos(nombre, idCategoria, stock, precio, descripcion, condicion, fecha_elaboracion, fecha_caducidad, codigo) values(?,?,?,?,?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            // Se asume que Categoria tiene un método getId() válido.
            stmt.setLong(2, producto.getCategoria().getId());
            stmt.setInt(3, producto.getStock());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setString(5, producto.getDescripcion());
            stmt.setInt(6, producto.getCondicion());
            stmt.setDate(7, Date.valueOf(producto.getFechaElaboracion()));
            stmt.setDate(8, Date.valueOf(producto.getFechaCaducidad()));

            // Si es INSERT, usamos el campo 9 para 'codigo'. Si es UPDATE, el 9 es 'condicion' y el 10 es 'idProducto'.
            if (producto.getIdProducto() != null && producto.getIdProducto() > 0) {
                // UPDATE
                stmt.setString(6, producto.getCodigo());
                stmt.setLong(10, producto.getIdProducto());
            } else {
                // INSERT
                stmt.setString(9, producto.getCodigo());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE idProducto=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getLong("idProducto")); // Columna ID cambiada a idProducto
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStock(rs.getInt("stock"));
        p.setCondicion(rs.getInt("condicion"));
        p.setFechaElaboracion(rs.getDate("fecha_elaboracion").toLocalDate());
        p.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
        p.setCodigo(rs.getString("codigo"));

        //Creamos un nuevo objeto de tipo categoria
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("idCategoria"));
        // El alias de la consulta es 'nombreCategoria', pero la clase Categoria tiene 'nombre'
        categoria.setNombre(rs.getString("nombreCategoria"));
        p.setCategoria(categoria);
        return p;
    }
}