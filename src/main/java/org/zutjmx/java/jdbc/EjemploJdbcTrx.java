package org.zutjmx.java.jdbc;

import org.zutjmx.java.jdbc.modelo.Categoria;
import org.zutjmx.java.jdbc.modelo.Producto;
import org.zutjmx.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.zutjmx.java.jdbc.repositorio.Repositorio;
import org.zutjmx.java.jdbc.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJdbcTrx {
    public static void main(String[] args) throws SQLException {

        try(Connection connection = ConexionBD.getConnection()) {

            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }

            try {
                Repositorio<Producto> repositorio = new ProductoRepositorioImpl();

                System.out.println(":::: Se prueba el método lista() ::::");
                repositorio.listar().forEach(System.out::println);

                System.out.println(":::: Se prueba el método porId() ::::");
                System.out.println(repositorio.porId(2L));

                System.out.println(":::: Se prueba el método guardar() nuevo producto ::::");
                Categoria categoria = new Categoria();
                categoria.setId(4L);
                categoria.setNombre("Línea Blanca");
                Producto nuevoProducto = new Producto();
                nuevoProducto.setNombre("Cafetera con IOT");
                nuevoProducto.setPrecio(2500);
                nuevoProducto.setFechaCreacion(new Date());
                nuevoProducto.setCategoria(categoria);
                nuevoProducto.setSku("86256108");
                repositorio.guardar(nuevoProducto);
                System.out.println(":::: Producto guardado ::::");

                System.out.println(":::: Se prueba el método guardar() con update ::::");
                Producto productoUpdate = new Producto();
                productoUpdate.setId(12L);
                productoUpdate.setNombre("Estufa Eléctrica");
                productoUpdate.setPrecio(13500);
                Categoria categoriaUpdate = new Categoria();
                categoriaUpdate.setId(4L);
                productoUpdate.setCategoria(categoriaUpdate);
                productoUpdate.setSku("abcdefghij");
                repositorio.guardar(productoUpdate);
                System.out.println(":::: Producto actualizado ::::");

                System.out.println(":::: Lista de productos ::::");
                repositorio.listar().forEach(System.out::println);

                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        }

    }
}
