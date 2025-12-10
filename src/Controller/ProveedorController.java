/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Proveedor;
import View.ProveedorView;
import dao.ProveedorDao;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author joans
 */
public class ProveedorController {

    private final ProveedorView vista;
    private final ProveedorDao proveedorDAO;

    public ProveedorController(ProveedorView vista, ProveedorDao proveedorDAO) {
        this.vista = vista;
        this.proveedorDAO = proveedorDAO;
        iniciarControl();
    }

    private void iniciarControl() {
        vista.btnActualizar.addActionListener(e -> actualizarProveedor());
        vista.btnEliminar.addActionListener(e -> eliminarProveedor());
        vista.BtnBuscar.addActionListener(e -> buscarProveedor());
        vista.btnLimpiar.addActionListener(e -> limpiarFormulario());
        vista.btnAgregar.addActionListener(e -> guardarProveedor());

        cargarTabla();
    }

    // -------------------------------------------------------
    // VALIDACIONES
    // -------------------------------------------------------
    private boolean validarFormulario() {
        if (vista.txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre es obligatorio");
            return false;
        }
        return true;
    }

    // -------------------------------------------------------
    // CRUD
    // -------------------------------------------------------
    private void guardarProveedor() {
        if (!validarFormulario()) {
            return;
        }

        Proveedor p = new Proveedor(
                0, 
                vista.txtNombre.getText(),
                vista.txtDireccion.getText(),
                vista.txtTelefono.getText(),
                vista.txtCorreo.getText()
        );

        try {
            if (proveedorDAO.crear(p)) {
                JOptionPane.showMessageDialog(vista, "Proveedor creado correctamente");
                cargarTabla();
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al crear proveedor: " + ex.getMessage());
        }
    }

    private void actualizarProveedor() {
        if (!validarFormulario()&& vista.txtID.getText().isEmpty()) {
            return;
        }

        int id = Integer.parseInt(vista.txtID.getText());

        Proveedor p = new Proveedor(
                id,
                vista.txtNombre.getText(),
                vista.txtDireccion.getText(),
                vista.txtTelefono.getText(),
                vista.txtCorreo.getText()
        );

        try {
            if (proveedorDAO.actualizar(p)) {
                JOptionPane.showMessageDialog(vista, "Proveedor actualizado");
                cargarTabla();
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarProveedor() {
        if (vista.txtID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un proveedor");
            return;
        }

        int id = Integer.parseInt(vista.txtID.getText());

        try {
            if (proveedorDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(vista, "Proveedor eliminado");
                cargarTabla();
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar: " + ex.getMessage());
        }
    }

    // -------------------------------------------------------
    // BÚSQUEDAS
    // -------------------------------------------------------
    private void buscarProveedor() {

        String valor = vista.txtBuscar.getText();

        try {
            List<Proveedor> lista = proveedorDAO.buscar(valor);
            vista.cargarTabla(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar: " + ex.getMessage());
        }
    }

    // -------------------------------------------------------
    // TABLA Y FORMULARIO
    // -------------------------------------------------------
    private void cargarTabla() {
        try {
            List<Proveedor> lista = proveedorDAO.listarTodos();
            vista.cargarTabla(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tabla: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        vista.txtID.setText("");
        vista.txtNombre.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
    }
}
