/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import Config.DBConnection;
import Model.Usuario;
import dao.UsuarioDao;
import java.sql.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Cami
 */
public class UsuarioDaoImpl implements UsuarioDao {

    private final Connection conn;

    public UsuarioDaoImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }

    // =======================================================
    //   MÉTODO PARA ENCRIPTAR CONTRASEÑA (SHA-256)
    // =======================================================
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }

    // =======================================================
    //                 CREAR USUARIO
    // =======================================================
    @Override
    public boolean crear(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (username, password_hash, rol) "
                   + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, hashPassword(u.getPasswordHash())); 
            ps.setString(3, u.getRol());


            return ps.executeUpdate() > 0;
        }
    }

    // =======================================================
    //             OBTENER POR ID
    // =======================================================
    @Override
    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                    );

                    return Optional.of(u);
                }
            }
        }
        return Optional.empty();
    }

    // =======================================================
    //          OBTENER POR USERNAME
    // =======================================================
    @Override
    public Optional<Usuario> obtenerPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE username = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                    );
                    return Optional.of(u);
                }
            }
        }

        return Optional.empty();
    }

    // =======================================================
    //                LISTAR TODOS LOS USUARIOS
    // =======================================================
    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nombre ASC";

        try (Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("nombre"),
                    rs.getString("rol"),
                    rs.getBoolean("activo")
                ));
            }
        }

        return list;
    }

    // =======================================================
    //                    ACTUALIZAR
    // =======================================================
    @Override
    public boolean actualizar(Usuario u) throws SQLException {
        String sql;

        boolean cambiarPass = u.getPasswordHash() != null && !u.getPasswordHash().isBlank();

        if (cambiarPass) {
            sql = "UPDATE usuario SET username=?, password_hash=?, nombre=?, rol=?, activo=? WHERE id=?";
        } else {
            sql = "UPDATE usuario SET username=?, nombre=?, rol=?, activo=? WHERE id=?";
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());

            int index = 2;

            if (cambiarPass) {
                ps.setString(index++, hashPassword(u.getPasswordHash())); 
            }

            ps.setString(index++, u.getNombre());
            ps.setString(index++, u.getRol());
            ps.setBoolean(index++, u.isActivo());
            ps.setInt(index, u.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // =======================================================
    //                  ELIMINAR USUARIO
    // =======================================================
    @Override
    public boolean eliminar(String id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE username=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // =======================================================
    //                     BÚSQUEDA
    // =======================================================
    @Override
    public List<Usuario> buscar(String filtro, Object valor) throws SQLException {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE " + filtro + " LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + valor + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                    ));
                }
            }
        }

        return list;
    }

    // =======================================================
    //                  VALIDAR LOGIN
    // =======================================================
    @Override
    public boolean validarLogin(String username, String password) throws Exception {
        Optional<Usuario> usuarioOpt = obtenerPorUsername(username);

        if (!usuarioOpt.isPresent()) {
            return false;
        }

        Usuario u = usuarioOpt.get();

        return u.getPasswordHash().equals(hashPassword(password));
    }
}