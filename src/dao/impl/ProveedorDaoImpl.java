/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import Config.DBConnection;
import Model.Proveedor;
import dao.ProveedorDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author joans
 */
public class ProveedorDaoImpl implements ProveedorDao {

    private final Connection conn;

    public ProveedorDaoImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean crear(Proveedor p) throws SQLException {
        String sql = "INSERT INTO proveedor (nombre, contacto, direccion, telefono, correo) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Proveedor> obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM proveedor WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Proveedor p = new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Proveedor> listarTodos() throws SQLException {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor ORDER BY nombre ASC";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                ));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Proveedor p) throws SQLException {
        String sql = "UPDATE proveedor SET nombre=?, contacto=?, direccion=?, telefono=?, correo=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proveedor WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Proveedor> buscar(String filtro, Object valor) throws SQLException {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor WHERE " + filtro + " LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + valor + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                ));
            }
        }
        return lista;
    }

    @Override
    public int contarProveedores() throws SQLException {
        String sql = "SELECT COUNT(*) FROM proveedor";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}