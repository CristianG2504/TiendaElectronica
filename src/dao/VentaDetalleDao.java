/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import Model.VentaDetalle;
import java.util.List;

/**
 *
 * @author joans
 */
public interface VentaDetalleDao {
        void insertarDetalles(int idVenta, List<VentaDetalle> detalles) throws Exception;
        

}
