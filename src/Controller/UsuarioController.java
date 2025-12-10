/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Model.Usuario;
import View.UsuarioView;
import dao.UsuarioDao;
import view.UsuarioView;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author joans
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
        vista.btnBuscar.addActionListener(e -> buscar());
        cargarTabla();
    }

    private void guardar() {
        try {
            Usuario u = new Usuario(
                    vista.txtUsername.getText(),
                    vista.txtPassword.getText(), // se encripta en el DAO
                    vista.txtNombre.getText(),
                    vista.cmbRol.getSelectedItem().toString(),
                    vista.chkActivo.isSelected()
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
                    Integer.parseInt(vista.txtId.getText()),
                    vista.txtUsername.getText(),
                    null, // la contraseña NO se actualiza aquí
                    vista.txtNombre.getText(),
                    vista.cmbRol.getSelectedItem().toString(),
                    vista.chkActivo.isSelected()
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
            int id = Integer.parseInt(vista.txtId.getText());

            if (usuarioDao.eliminar(id)) {
                JOptionPane.showMessageDialog(vista, "Usuario eliminado");
                cargarTabla();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            List<Usuario> lista = usuarioDao.buscar("nombre", vista.txtBuscar.getText());
            vista.cargarTabla(lista);

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
