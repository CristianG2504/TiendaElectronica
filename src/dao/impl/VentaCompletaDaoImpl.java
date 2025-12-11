/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import Config.DBConnection;
import Model.VentaCompletaDTO;
import Model.VentaDetalle;
import dao.VentaCompletaDao;
import dao.VentaDetalleDao;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class VentaCompletaDaoImpl implements VentaCompletaDao {
    
      private final Connection conn;

    public VentaCompletaDaoImpl() throws SQLException {
        this.conn = DBConnection.getInstance().getConnection();
    }

    @Override
    public List<VentaCompletaDTO> listarVentas() throws Exception {
        List<VentaCompletaDTO> lista = new java.util.ArrayList<>();

        String sql = "SELECT v.id AS id_venta, v.fecha, c.cedula AS cedula_cliente, c.nombre AS cliente, "
                + "p.codigo AS codigo_producto, p.nombre AS producto, vd.cantidad, vd.precio_unitario, "
                + "(vd.cantidad * vd.precio_unitario) AS subtotal "
                + "FROM venta v "
                + "INNER JOIN cliente c ON v.cedula_cliente = c.cedula "
                + "INNER JOIN venta_detalle vd ON v.id = vd.venta_id "
                + "INNER JOIN producto p ON vd.producto_codigo = p.codigo "
                + "ORDER BY v.fecha DESC, v.id";

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                VentaCompletaDTO dto = new VentaCompletaDTO(
                        rs.getInt("id_venta"),
                        rs.getString("fecha"),
                        rs.getString("cedula_cliente"),
                        rs.getString("cliente"),
                        rs.getString("codigo_producto"),
                        rs.getString("producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("subtotal")
                );
                lista.add(dto);
            }
        }

        return lista;
    }
}
