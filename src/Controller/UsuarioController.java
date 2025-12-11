/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Usuario;
import View.UsuarioView;
import dao.UsuarioDao;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class UsuarioController {

    private final UsuarioView vista;
    private final UsuarioDao usuarioDao;

    public UsuarioController(UsuarioView vista, UsuarioDao usuarioDao) {
        this.vista = vista;
        this.usuarioDao = usuarioDao;
        iniciarControl();
    }

    private void iniciarControl() {
        vista.btnGuardar.addActionListener(e -> guardar());
        vista.btnActualizar.addActionListener(e -> actualizar());
        vista.btnEliminar.addActionListener(e -> eliminar());

        cargarTabla();
    }

    private void guardar() {
        try {

            String contrasena = new String(vista.txtContrasena.getPassword());

            Usuario u = new Usuario(
                    vista.txtUsuario.getText(),
                    contrasena,
                    vista.cmbRol.getSelectedItem().toString()
            );

            if (usuarioDao.crear(u)) {
                JOptionPane.showMessageDialog(vista, "Usuario creado correctamente");
                cargarTabla();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }

    private void actualizar() {
        try {
            Usuario u = new Usuario(
                    vista.txtUsuario.getText(),
                    null,
                    vista.cmbRol.getSelectedItem().toString()
            );

            if (usuarioDao.actualizar(u)) {
                JOptionPane.showMessageDialog(vista, "Usuario actualizado");
                cargarTabla();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }

    private void eliminar() {
        try {
            String user = (vista.txtUsuario.getText());

            if (usuarioDao.eliminar(user)) {
                JOptionPane.showMessageDialog(vista, "Usuario eliminado");
                cargarTabla();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            vista.cargarTabla(usuarioDao.listarTodos());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }
}
