/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Model.Categoria;
import dao.impl.CategoriaDaoImpl;
import View.CategoriaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author joans
 */
public class CategoriaController {
      private final CategoriaView view;
    private final CategoriaDaoImpl dao;

    public CategoriaController(CategoriaView view, CategoriaDaoImpl dao) {
        this.view = view;
        this.dao = dao;

        // Inicializar tabla al cargar
        cargarTabla();

        // Botón agregar
        view.btnAgregar.addActionListener(e -> agregarCategoria());

        // Botón actualizar
        view.btnActualizar.addActionListener(e -> actualizarCategoria());

        // Botón eliminar
        view.btnEliminar.addActionListener(e -> eliminarCategoria());

        // Botón limpiar
        view.btnLimpiar.addActionListener(e -> limpiarCampos());

        // Click en la tabla
        view.TbTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filaSeleccionada();
            }
        });
    }

    // Cargar tabla de categorías
    public void cargarTabla() {
        try {
            List<Categoria> lista = dao.listarTodos();

            // Crear modelo nuevo
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Descripción");

            // Agregar filas
            for (Categoria cat : lista) {
                model.addRow(new Object[]{cat.getId(), cat.getNombre(), cat.getDescripcion()});
            }

            view.TbTabla.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar categorías: " + ex.getMessage());
        }
    }

    // Agregar categoría
    private void agregarCategoria() {
        try {
            String nombre = view.txtNombre.getText();
            String descripcion = view.txtDescripcion.getText();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(view, "El nombre no puede estar vacío");
                return;
            }

            Categoria cat = new Categoria();
            cat.setNombre(nombre);
            cat.setDescripcion(descripcion);

            if (dao.agregar(cat)) {
                JOptionPane.showMessageDialog(view, "Categoría agregada correctamente");
                limpiarCampos();
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al agregar categoría: " + ex.getMessage());
        }
    }

    // Actualizar categoría
    private void actualizarCategoria() {
        try {
            int id = Integer.parseInt(view.txtID.getText());
            String nombre = view.txtNombre.getText();
            String descripcion = view.txtDescripcion.getText();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(view, "El nombre no puede estar vacío");
                return;
            }

            Categoria cat = new Categoria();
            cat.setId(id);
            cat.setNombre(nombre);
            cat.setDescripcion(descripcion);

            if (dao.actualizar(cat)) {
                JOptionPane.showMessageDialog(view, "Categoría actualizada correctamente");
                limpiarCampos();
                cargarTabla();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Seleccione una categoría de la tabla");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al actualizar categoría: " + ex.getMessage());
        }
    }

    // Eliminar categoría
    private void eliminarCategoria() {
        try {
            int id = Integer.parseInt(view.txtID.getText());

            int confirm = JOptionPane.showConfirmDialog(view, "¿Está seguro de eliminar esta categoría?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.eliminar(id)) {
                    JOptionPane.showMessageDialog(view, "Categoría eliminada correctamente");
                    limpiarCampos();
                    cargarTabla();
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Seleccione una categoría de la tabla");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al eliminar categoría: " + ex.getMessage());
        }
    }

    // Limpiar campos
    private void limpiarCampos() {
        view.txtID.setText("");
        view.txtNombre.setText("");
        view.txtDescripcion.setText("");
    }

    // Cuando se selecciona una fila de la tabla
    private void filaSeleccionada() {
        int fila = view.TbTabla.getSelectedRow();
        if (fila >= 0) {
            view.txtID.setText(view.TbTabla.getValueAt(fila, 0).toString());
            view.txtNombre.setText(view.TbTabla.getValueAt(fila, 1).toString());
            view.txtDescripcion.setText(view.TbTabla.getValueAt(fila, 2).toString());
        }
    }
}
