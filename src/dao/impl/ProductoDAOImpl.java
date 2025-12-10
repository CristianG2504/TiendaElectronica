/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;


import Config.DBConnection;
import Model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import dao.ProductoDao;


/**
 *
 * @author Cami
 */
public class ProductoDAOImpl implements ProductoDao{

    private final Connection conn;

    public ProductoDAOImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean crear(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (codigo,nombre,id_categoria,precio,cantidad,proveedor_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getIdCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCantidad());
            ps.setInt(6, p.getProveedorId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Producto> obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM producto WHERE codigo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("id_categoria"),
                            rs.getDouble("precio"),
                            rs.getInt("cantidad"),
                            rs.getInt("proveedor_id")
                    );
                    return Optional.of(p);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Producto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM producto";
        List<Producto> list = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("id_categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        rs.getInt("proveedor_id")
                ));
            }
        }
        return list;
    }

    @Override
    public boolean actualizar(Producto p) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(String codigo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Producto> buscar(String filtro, Object valor) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int contarPorCategoria(int idCategoria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
