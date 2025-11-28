package com.crishito.aplicacionweb.manejosesiones.services;

import com.crishito.aplicacionweb.manejosesiones.models.Categoria;
import com.crishito.aplicacionweb.manejosesiones.models.Producto;
import com.crishito.aplicacionweb.manejosesiones.repositorio.CategoriaRepositoryJdbcImplement;
import com.crishito.aplicacionweb.manejosesiones.repositorio.ProductoRepositoryJdbcImplement;
import com.crishito.aplicacionweb.manejosesiones.repositorio.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoServiceJdbcImpl implements ProductoService {

    // Declaramos una variable de tipo ProductoRepositoryJdbcImplement
    private Repository<Producto> repositoryJdbc;

    // Creamos un nuevo objeto de tipo CategoriaRepository para las categorías
    private Repository<Categoria> repositoryCategoriaJdbc;

    // Implementamos un constuctor paa traer la conexion
    public ProductoServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new ProductoRepositoryJdbcImplement(connection);
        this.repositoryCategoriaJdbc = new CategoriaRepositoryJdbcImplement(connection);
    }

    @Override
    public List<Producto> listar() {
        try {
            return repositoryJdbc.listar();
        } catch(SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch(SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            repositoryJdbc.guardar(producto);
        } catch(SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        } catch(SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategoria() {
        try {
            return repositoryCategoriaJdbc.listar();
        } catch(SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(repositoryCategoriaJdbc.porId(id));
        } catch(SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}