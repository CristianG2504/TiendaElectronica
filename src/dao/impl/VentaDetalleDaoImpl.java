/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;


import Config.DBConnection;
import Model.VentaDetalle;
import dao.VentaDetalleDao;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class VentaDetalleDaoImpl implements VentaDetalleDao {

    private final Connection conn;

    public VentaDetalleDaoImpl(Connection conn) {
        this.conn = conn;
    }
    
    public VentaDetalleDaoImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }


    @Override
    public void insertarDetalles(int idVenta, List<VentaDetalle> detalles) throws Exception {

        String sql = "INSERT INTO venta_detalle (venta_id, producto_codigo, cantidad, precio_unitario) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (VentaDetalle d : detalles) {
                ps.setInt(1, idVenta);
                ps.setString(2, d.getProducto().getCodigo());
                ps.setInt(3, d.getCantidad());
                ps.setDouble(4, d.getPrecioUnitario());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
}