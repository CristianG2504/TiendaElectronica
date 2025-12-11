/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;


import Config.DBConnection;
import Model.Cliente;
import dao.ClienteDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Cris
 */
public class ClienteDaoImpl implements ClienteDao {

    private final Connection conn;

    public ClienteDaoImpl(Connection conn) {
        this.conn = conn;
    }
    
      public ClienteDaoImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }

    // =============================================
    // CREAR
    // =============================================
    @Override
    public boolean crear(Cliente c) throws Exception {
        String sql = "INSERT INTO cliente (cedula, nombre, direccion, telefono, correo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());
            return ps.executeUpdate() > 0;
        }
    }

    // =============================================
    // ACTUALIZAR
    // =============================================
    @Override
    public boolean actualizar(Cliente c) throws Exception {
        String sql = "UPDATE cliente SET nombre=?, direccion=?, telefono=?, correo=? WHERE cedula=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDireccion());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.setString(5, c.getCedula());
            return ps.executeUpdate() > 0;
        }
    }

    // =============================================
    // ELIMINAR
    // =============================================
    @Override
    public boolean eliminar(String cedula) throws Exception {
        String sql = "DELETE FROM cliente WHERE cedula=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            return ps.executeUpdate() > 0;
        }
    }

    // =============================================
    // OBTENER POR CÉDULA
    // =============================================
    @Override
    public Cliente obtener(String cedula) throws Exception {
        String sql = "SELECT * FROM cliente WHERE cedula=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );
            }
        }
        return null;
    }

    // =============================================
    // LISTAR TODOS
    // =============================================
    @Override
    public List<Cliente> listarTodos() throws Exception {
        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM cliente ORDER BY nombre";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                ));
            }
        }
        return lista;
    }

    // =============================================
    // BUSCAR POR NOMBRE, CÉDULA O TELÉFONO
    // =============================================
    @Override
    public List<Cliente> buscar(String valor) throws Exception {
        List<Cliente> lista = new ArrayList<>();

        String sql = """
                     SELECT * FROM cliente 
                     WHERE nombre LIKE ? 
                        OR cedula LIKE ? 
                        OR telefono LIKE ?
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + valor + "%");
            ps.setString(2, "%" + valor + "%");
            ps.setString(3, "%" + valor + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                ));
            }
        }
        return lista;
    }
}