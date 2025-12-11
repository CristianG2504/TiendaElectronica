/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Cliente;
import View.ClienteView;
import dao.ClienteDao;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Paulo
 */
public class ClienteController {

    private final ClienteView vista;
    private final ClienteDao clienteDAO;

    public ClienteController(ClienteView vista, ClienteDao clienteDAO) {
        this.vista = vista;
        this.clienteDAO = clienteDAO;
        iniciarControl();
    }

    private void iniciarControl() {
        vista.btnAgregar.addActionListener(e -> guardarCliente());
        vista.btnActualizar.addActionListener(e -> actualizarCliente());
        vista.btnEliminar.addActionListener(e -> eliminarCliente());
        vista.btnLimpiar.addActionListener(e -> limpiarFormulario());
        vista.BtnBuscar.addActionListener(e -> buscarCliente());

        cargarTabla();
    }

    // -------------------------------------------------------
    // VALIDACIONES
    // -------------------------------------------------------
    private boolean validarFormulario() {
        if (vista.txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "La cédula es obligatoria");
            return false;
        }
        if (vista.txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre es obligatorio");
            return false;
        }
        return true;
    }

    // -------------------------------------------------------
    // CRUD
    // -------------------------------------------------------

    private void guardarCliente() {
        if (!validarFormulario()) return;

        Cliente cli = new Cliente(
                vista.txtCedula.getText(),
                vista.txtNombre.getText(),
                vista.txtDireccion.getText(),
                vista.txtTelefono.getText(),
                vista.txtCorreo.getText()
        );

        try {
            if (clienteDAO.crear(cli)) {
                JOptionPane.showMessageDialog(vista, "Cliente registrado correctamente");
                cargarTabla();
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al registrar cliente: " + ex.getMessage());
        }
    }

    private void actualizarCliente() {
        if (!validarFormulario()) return;

        Cliente cli = new Cliente(
                vista.txtCedula.getText(),
                vista.txtNombre.getText(),
                vista.txtDireccion.getText(),
                vista.txtTelefono.getText(),
                vista.txtCorreo.getText()
        );

        try {
            if (clienteDAO.actualizar(cli)) {
                JOptionPane.showMessageDialog(vista, "Cliente actualizado correctamente");
                cargarTabla();
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarCliente() {
        if (vista.txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un cliente");
            return;
        }

        String cedula = vista.txtCedula.getText();

        try {
            if (clienteDAO.eliminar(cedula)) {
                JOptionPane.showMessageDialog(vista, "Cliente eliminado");
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
    private void buscarCliente() {
        String valor = vista.txtBuscar.getText();

        try {
            List<Cliente> lista = clienteDAO.buscar(valor);
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
            List<Cliente> lista = clienteDAO.listarTodos();
            vista.cargarTabla(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tabla: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        vista.txtCedula.setText("");
        vista.txtNombre.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
    }
}