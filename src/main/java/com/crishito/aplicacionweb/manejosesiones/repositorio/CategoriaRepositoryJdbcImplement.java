package com.crishito.aplicacionweb.manejosesiones.repositorio;

import com.crishito.aplicacionweb.manejosesiones.models.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositoryJdbcImplement implements Repository<Categoria> {

    private Connection conn;

    public CategoriaRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    // ... (Métodos listar y porId no necesitan cambios en el ResultSet de la query)

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categoria")) {

            while (rs.next()) {
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias;
    }

    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categoria WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = getCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {
        String sql;

        // 💡 CORRECCIÓN 1: Usar 'condicion' en lugar de 'estado' en la sentencia SQL
        if (categoria.getId() != null && categoria.getId() > 0) {
            sql = "UPDATE categoria SET nombreCategoria=?, descripcion=?, condicion=? WHERE id=?";
        } else {
            sql = "INSERT INTO categoria(nombreCategoria, descripcion, condicion) VALUES (?, ?, ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getEstado()); // El getEstado() del modelo Java sigue siendo válido aquí

            if (categoria.getId() != null && categoria.getId() > 0) {
                stmt.setLong(4, categoria.getId()); // WHERE id=?
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM categoria WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id"));
        categoria.setNombre(rs.getString("nombreCategoria"));
        categoria.setDescripcion(rs.getString("descripcion"));
        // 💡 CORRECCIÓN 2: Leer la columna 'condicion' de la base de datos
        categoria.setEstado(rs.getInt("condicion"));

        return categoria;
    }
}