/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import Model.Cliente;
import java.util.List;

/**
 *
 * @author joans
 */
public interface ClienteDao {

    boolean crear(Cliente c) throws Exception;

    boolean actualizar(Cliente c) throws Exception;

    boolean eliminar(String cedula) throws Exception;

    Cliente obtener(String cedula) throws Exception;

    List<Cliente> listarTodos() throws Exception;

    List<Cliente> buscar(String valor) throws Exception;
}
