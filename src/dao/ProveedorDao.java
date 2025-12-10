/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;



import Model.Proveedor;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author joans
 */
public interface ProveedorDao {

    boolean crear(Proveedor p) throws Exception;

    boolean actualizar(Proveedor p) throws Exception;

    boolean eliminar(int id) throws Exception;

    Optional<Proveedor> obtenerPorId(int id) throws Exception;

    List<Proveedor> listarTodos() throws Exception;

    List<Proveedor> buscar(String filtro, Object valor) throws Exception;

    int contarProveedores() throws Exception;
}
