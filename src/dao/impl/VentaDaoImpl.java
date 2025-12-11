/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;


import Config.DBConnection;
import Model.Venta;
import dao.VentaDao;
import java.sql.*;

/**
 *
 * @author joans
 */
public class VentaDaoImpl implements VentaDao {

    private final Connection conn;

    public VentaDaoImpl(Connection conn) {
        this.conn = conn;
    }
    
       public VentaDaoImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }

    @Override
    public int crearVenta(Venta venta) throws Exception {
        String sql = "INSERT INTO venta (cedula_cliente) VALUES (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, venta.getCliente().getCedula());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        }
        throw new Exception("No se pudo crear la venta.");
    }
}
