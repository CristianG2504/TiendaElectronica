/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import Model.Producto;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Cami
 */
public interface ProductoDao {

    boolean crear(Producto p) throws Exception;

    boolean actualizar(Producto p) throws Exception;

    boolean eliminar(String codigo) throws Exception;

    Optional<Producto> obtenerPorCodigo(String codigo) throws Exception;

    List<Producto> listarTodos() throws Exception;

    List<Producto> buscar(String filtro, Object valor) throws Exception;

    int contarPorCategoria(int idCategoria) throws Exception;
}
