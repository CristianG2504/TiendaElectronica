/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;


import Model.Categoria;
import java.util.List;


/**
 *
 * @author Cris
 */

public interface CategoriaDao {

    List<Categoria> listarTodos() throws Exception;
    List<Categoria> buscarCategorias(String valor) throws Exception;

    Categoria obtenerPorId(int id) throws Exception;

    boolean agregar(Categoria categoria) throws Exception;

    boolean actualizar(Categoria categoria) throws Exception;

    boolean eliminar(int id) throws Exception;
}
