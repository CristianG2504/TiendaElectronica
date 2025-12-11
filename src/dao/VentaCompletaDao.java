/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;


import Model.VentaCompletaDTO;
import java.util.List;

/**
 *
 * @author Cris
 */
public interface VentaCompletaDao {
     List<VentaCompletaDTO> listarVentas() throws Exception;
}
