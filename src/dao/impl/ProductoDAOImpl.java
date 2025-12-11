/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import Config.DBConnection;
import Model.Categoria;
import Model.Producto;
import Model.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import dao.ProductoDao;

/**
 *
 * @author Cami
 */
public class ProductoDAOImpl implements ProductoDao {

    private final Connection conn;

    public ProductoDAOImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean crear(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (codigo, nombre, id_categoria, precio, cantidad, proveedor_id) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getCategoria().getId());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCantidad());
            ps.setInt(6, p.getProveedor().getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Producto> obtenerPorCodigo(String codigo) throws SQLException {

        String sql = """
                SELECT 
                    p.codigo, p.nombre, p.precio, p.cantidad,
                    c.id AS cat_id, c.nombre AS cat_nombre, c.descripcion AS cat_desc,
                    pr.id AS pr_id, pr.nombre AS pr_nombre, pr.contacto AS pr_contacto,
                    pr.direccion AS pr_dir, pr.telefono AS pr_tel, pr.correo AS pr_correo
                FROM producto p
                INNER JOIN categoria c ON p.id_categoria = c.id
                INNER JOIN proveedor pr ON p.proveedor_id = pr.id
                WHERE p.codigo = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                Categoria cat = new Categoria(
                        rs.getInt("cat_id"),
                        rs.getString("cat_nombre"),
                        rs.getString("cat_desc")
                );

                Proveedor prov = new Proveedor(
                        rs.getInt("pr_id"),
                        rs.getString("pr_nombre"),
                        rs.getString("pr_dir"),
                        rs.getString("pr_tel"),
                        rs.getString("pr_correo")
                );

                Producto p = new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        cat,
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        prov
                );

                return Optional.of(p);
            }
        }
    }

    @Override
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> lista = new ArrayList<>();

        String sql = """
                SELECT 
                    p.codigo, p.nombre, p.precio, p.cantidad,
                    c.id AS cat_id, c.nombre AS cat_nombre, c.descripcion AS cat_desc,
                    pr.id AS pr_id, pr.nombre AS pr_nombre, pr.contacto AS pr_contacto,
                    pr.direccion AS pr_dir, pr.telefono AS pr_tel, pr.correo AS pr_correo
                FROM producto p
                INNER JOIN categoria c ON p.id_categoria = c.id
                INNER JOIN proveedor pr ON p.proveedor_id = pr.id
                """;

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Categoria cat = new Categoria(
                        rs.getInt("cat_id"),
                        rs.getString("cat_nombre"),
                        rs.getString("cat_desc")
                );

                Proveedor prov = new Proveedor(
                        rs.getInt("pr_id"),
                        rs.getString("pr_nombre"),
                        rs.getString("pr_dir"),
                        rs.getString("pr_tel"),
                        rs.getString("pr_correo")
                );

                Producto p = new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        cat,
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        prov
                );

                lista.add(p);
            }
        }

        return lista;
    }

    @Override
    public boolean actualizar(Producto p) throws SQLException {

        String sql = """
                UPDATE producto 
                SET nombre=?, id_categoria=?, precio=?, cantidad=?, proveedor_id=?
                WHERE codigo=?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCategoria().getId());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCantidad());
            ps.setInt(5, p.getProveedor().getId());
            ps.setString(6, p.getCodigo());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM producto WHERE codigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Producto> buscar(String valor) throws SQLException {
        List<Producto> lista = new ArrayList<>();

        String sql = """
        SELECT 
            p.codigo, p.nombre, p.precio, p.cantidad,
            c.id AS cat_id, c.nombre AS cat_nombre, c.descripcion AS cat_desc,
            pr.id AS pr_id, pr.nombre AS pr_nombre, pr.contacto AS pr_contacto,
            pr.direccion AS pr_dir, pr.telefono AS pr_tel, pr.correo AS pr_correo
        FROM producto p
        INNER JOIN categoria c ON p.id_categoria = c.id
        INNER JOIN proveedor pr ON p.proveedor_id = pr.id
        WHERE 
            p.codigo   LIKE ? OR
            c.nombre   LIKE ? OR
            p.cantidad   LIKE ? OR
            p.nombre   LIKE ? OR
            c.descripcion LIKE ? OR
            pr.nombre  LIKE ? OR
            pr.direccion LIKE ? OR
            pr.telefono LIKE ? OR
            pr.correo   LIKE ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            String v = "%" + valor + "%";

            // Asignar el mismo valor a todos los LIKE
            for (int i = 1; i <= 9; i++) {
                ps.setString(i, v);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Categoria cat = new Categoria(
                            rs.getInt("cat_id"),
                            rs.getString("cat_nombre"),
                            rs.getString("cat_desc")
                    );

                    Proveedor prov = new Proveedor(
                            rs.getInt("pr_id"),
                            rs.getString("pr_nombre"),
                            rs.getString("pr_dir"),
                            rs.getString("pr_tel"),
                            rs.getString("pr_correo")
                    );

                    Producto p = new Producto(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            cat,
                            rs.getDouble("precio"),
                            rs.getInt("cantidad"),
                            prov
                    );

                    lista.add(p);
                    
                    System.out.println(p.getNombre());
                }
            }
        }

        return lista;
    }

    @Override
    public int contarPorCategoria(int idCategoria) throws SQLException {
        String sql = "SELECT COUNT(*) FROM producto WHERE id_categoria = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
