/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Model.Producto;
import View.ProductoView;
import dao.ProductoDao;
import view.ProductoView;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author joans
 */
public class ProductoController {
    
   private ProductoView vista;
    private ProductoDao productoDAO;
    private CategoriaDao categoriaDAO;
    private ProveedorDao proveedorDAO;

    public ProductoController(ProductoView vista, ProductoDao productoDAO,
                              CategoriaDao categoriaDAO, ProveedorDao proveedorDAO) {

        this.vista = vista;
        this.productoDAO = productoDAO;
        this.categoriaDAO = categoriaDAO;
        this.proveedorDAO = proveedorDAO;

        iniciarControl();
        cargarCombos();
        cargarTablaProductos();
    }

    private void iniciarControl() {
        vista.btnGuardar.addActionListener(e -> guardarProducto());
        vista.btnActualizar.addActionListener(e -> actualizarProducto());
        vista.btnEliminar.addActionListener(e -> eliminarProducto());
        vista.btnBuscar.addActionListener(e -> buscarProducto());
        vista.btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    // ===============================================================
    //              CARGAR COMBOS DESDE LA BASE DE DATOS
    // ===============================================================
    private void cargarCombos() {
        try {
            // ---- Categorías ----
            vista.cmbCategoria.removeAllItems();
            List<Categoria> categorias = categoriaDAO.listarCategorias();
            for (Categoria c : categorias) {
                vista.cmbCategoria.addItem(c);
            }

            // ---- Proveedores ----
            vista.cmbProveedor.removeAllItems();
            List<Proveedor> proveedores = proveedorDAO.listarProveedores();
            for (Proveedor p : proveedores) {
                vista.cmbProveedor.addItem(p);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error cargando combos: " + e.getMessage());
        }
    }

    // ===============================================================
    //                    VALIDACIONES
    // ===============================================================
    private boolean validarFormulario() {

        if (vista.txtCodigo.getText().trim().isEmpty()
                || vista.txtNombre.getText().trim().isEmpty()
                || vista.txtPrecio.getText().trim().isEmpty()
                || vista.txtCantidad.getText().trim().isEmpty()
                || vista.cmbCategoria.getSelectedItem() == null
                || vista.cmbProveedor.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(vista,
                    "Todos los datos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }

        try {
            Double.parseDouble(vista.txtPrecio.getText());
            Integer.parseInt(vista.txtCantidad.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Precio y cantidad deben ser numéricos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // ===============================================================
    //                          CRUD
    // ===============================================================

    private void guardarProducto() {
        if (!validarFormulario()) return;

        Categoria categoria = (Categoria) vista.cmbCategoria.getSelectedItem();
        Proveedor proveedor = (Proveedor) vista.cmbProveedor.getSelectedItem();

        Producto p = new Producto(
                vista.txtCodigo.getText(),
                vista.txtNombre.getText(),
                categoria.getId(),         // Guarda ID de categoría
                Double.parseDouble(vista.txtPrecio.getText()),
                Integer.parseInt(vista.txtCantidad.getText()),
                proveedor.getId()          // Guarda ID del proveedor
        );

        try {
            if (productoDAO.insertar(p)) {
                JOptionPane.showMessageDialog(vista, "Producto registrado correctamente");
                cargarTablaProductos();
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al registrar: " + ex.getMessage());
        }
    }

    private void actualizarProducto() {
        if (!validarFormulario()) return;

        Categoria categoria = (Categoria) vista.cmbCategoria.getSelectedItem();
        Proveedor proveedor = (Proveedor) vista.cmbProveedor.getSelectedItem();

        Producto p = new Producto(
                vista.txtCodigo.getText(),
                vista.txtNombre.getText(),
                categoria.getId(),
                Double.parseDouble(vista.txtPrecio.getText()),
                Integer.parseInt(vista.txtCantidad.getText()),
                proveedor.getId()
        );

        try {
            if (productoDAO.actualizar(p)) {
                JOptionPane.showMessageDialog(vista, "Producto actualizado correctamente");
                cargarTablaProductos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarProducto() {
        String codigo = vista.txtCodigo.getText();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe indicar un código");
            return;
        }

        try {
            if (productoDAO.eliminar(codigo)) {
                JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente");
                cargarTablaProductos();
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar: " + ex.getMessage());
        }
    }

    // ===============================================================
    //                     BUSCAR PRODUCTOS
    // ===============================================================

    private void buscarProducto() {
        String filtro = vista.txtBuscar.getText();

        try {
            List<Producto> lista = productoDAO.buscar(filtro);
            vista.cargarTabla(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al buscar productos: " + ex.getMessage());
        }
    }

    // ===============================================================
    //                        TABLA
    // ===============================================================

    private void cargarTablaProductos() {
        try {
            List<Producto> lista = productoDAO.listar();
            vista.cargarTabla(lista);

            // alertas de stock bajo
            for (Producto p : lista) {
                if (p.getCantidadDisponible() <= 5) {
                    JOptionPane.showMessageDialog(vista,
                            "El producto " + p.getNombre() + " está casi agotado",
                            "Alerta",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error cargando productos: " + ex.getMessage());
        }
    }

    // ===============================================================
    //                     LIMPIAR FORMULARIO
    // ===============================================================

    private void limpiarFormulario() {
        vista.txtCodigo.setText("");
        vista.txtNombre.setText("");
        vista.txtPrecio.setText("");
        vista.txtCantidad.setText("");

        vista.cmbCategoria.setSelectedIndex(0);
        vista.cmbProveedor.setSelectedIndex(0);
    }
}
