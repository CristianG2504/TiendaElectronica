/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import Model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author joans
 */
public interface UsuarioDao {

    boolean crear(Usuario u) throws Exception;

    boolean actualizar(Usuario u) throws Exception;

    boolean eliminar(String id) throws Exception;

    Optional<Usuario> obtenerPorId(int id) throws Exception;

    Optional<Usuario> obtenerPorUsername(String username) throws Exception;

    List<Usuario> listarTodos() throws Exception;

    List<Usuario> buscar(String filtro, Object valor) throws Exception;

    boolean validarLogin(String username, String password) throws Exception;
}
