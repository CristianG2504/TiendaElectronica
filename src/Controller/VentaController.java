/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Cliente;
import Model.Producto;
import Model.Venta;
import Model.VentaDetalle;
import View.VentaView;
import dao.ClienteDao;
import dao.ProductoDao;
import dao.VentaDao;
import dao.VentaDetalleDao;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cami
 */
public class VentaController {

    private final VentaView vista;
    private final VentaDao ventaDao;
    private final VentaDetalleDao detalleDao;
    private final ClienteDao clienteDao;
    private final ProductoDao productoDao;

    public VentaController(
            VentaView vista,
            VentaDao ventaDao,
            VentaDetalleDao detalleDao,
            ClienteDao clienteDao,
            ProductoDao productoDao) {

        this.vista = vista;
        this.ventaDao = ventaDao;
        this.detalleDao = detalleDao;
        this.clienteDao = clienteDao;
        this.productoDao = productoDao;

        iniciarControl();
    }

    private void iniciarControl() {
        cargarClientes();
        cargarProductos();

        vista.cmbProducto.addActionListener(e -> actualizarPrecioExistencias());

        vista.btnAgregar.addActionListener(e -> agregarProducto());
        vista.btnGuardar.addActionListener(e -> guardarVenta());
        vista.btnXml.addActionListener(e -> generarXML());

    }

    // =====================================================
    // CARGA DE COMBOS
    // =====================================================
    private void cargarClientes() {
        try {
            List<Cliente> clientes = clienteDao.listarTodos();
            vista.cmbCliente.removeAllItems();
            for (Cliente c : clientes) {
                vista.cmbCliente.addItem(c);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error cargando clientes: " + ex.getMessage());
        }
    }

    private void actualizarPrecioExistencias() {
        Producto p = (Producto) vista.cmbProducto.getSelectedItem();
        if (p != null) {
            vista.txtPrecio.setText(String.format("%.2f", p.getPrecio()));
            vista.txtCantidad1.setText(String.valueOf(p.getCantidad()));
        } else {
            vista.txtPrecio.setText("");
            vista.txtCantidad1.setText("");
        }
    }

    private void cargarProductos() {
        try {
            List<Producto> productos = productoDao.listarTodos();
            vista.cmbProducto.removeAllItems();
            for (Producto p : productos) {
                vista.cmbProducto.addItem(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error cargando productos: " + ex.getMessage());
        }
    }

    // =====================================================
    // AGREGAR PRODUCTO A LA TABLA
    // =====================================================
    private void agregarProducto() {
        Producto p = (Producto) vista.cmbProducto.getSelectedItem();
        if (p == null) {
            return;
        }

        int cant;
        try {
            cant = Integer.parseInt(vista.txtCantidad.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Cantidad inválida");
            return;
        }

        if (cant > p.getCantidad()) {
            JOptionPane.showMessageDialog(vista, "Stock insuficiente");
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.TbTabla.getModel();
        boolean productoExistente = false;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object value = modelo.getValueAt(i, 0);
            if (value != null && value.toString().equals(p.getCodigo())) {

                int cantidadActual = 0;
                Object cantValue = modelo.getValueAt(i, 2);
                if (cantValue != null) {
                    try {
                        cantidadActual = Integer.parseInt(cantValue.toString());
                    } catch (NumberFormatException ex) {
                        cantidadActual = 0;
                    }
                }
                int nuevaCantidad = cantidadActual + cant;

                if (nuevaCantidad > p.getCantidad()) {
                    JOptionPane.showMessageDialog(vista, "Stock insuficiente para esa cantidad total");
                    return;
                }

                modelo.setValueAt(nuevaCantidad, i, 2);
                modelo.setValueAt(nuevaCantidad * p.getPrecio(), i, 4);
                productoExistente = true;
                break;
            }
        }

        if (!productoExistente) {
            double subtotal = cant * p.getPrecio();
            modelo.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                cant,
                p.getPrecio(),
                subtotal
            });
        }

        calcularTotales();
    }

    // =====================================================
    // CALCULAR SUBTOTAL, IMPUESTO, TOTAL
    // =====================================================
    private void calcularTotales() {
        DefaultTableModel modelo = (DefaultTableModel) vista.TbTabla.getModel();

        double subtotal = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object value = modelo.getValueAt(i, 4);
            if (value != null) {
                try {
                    subtotal += Double.parseDouble(value.toString());
                } catch (NumberFormatException ex) {

                }
            }
        }

        double impuesto = subtotal * 0.13;
        double total = subtotal + impuesto;

        vista.txtSubtotal.setText(String.format("%.2f", subtotal));
        vista.txtImpuesto.setText(String.format("%.2f", impuesto));
        vista.txtTotal.setText(String.format("%.2f", total));
    }

    // =====================================================
    // GUARDAR VENTA COMPLETA
    // =====================================================
    private void guardarVenta() {
        try {
            Cliente cliente = (Cliente) vista.cmbCliente.getSelectedItem();

            if (cliente == null) {
                JOptionPane.showMessageDialog(vista, "Seleccione un cliente");
                return;
            }

            Venta venta = new Venta();
            venta.setCliente(cliente);

            int idVenta = ventaDao.crearVenta(venta);

            DefaultTableModel modelo = (DefaultTableModel) vista.TbTabla.getModel();

            for (int i = 0; i < modelo.getRowCount(); i++) {

                VentaDetalle d = new VentaDetalle(
                        new Producto(modelo.getValueAt(i, 0).toString()),
                        (int) modelo.getValueAt(i, 2),
                        (double) modelo.getValueAt(i, 3)
                );

                venta.getDetalles().add(d);
            }

            detalleDao.insertarDetalles(idVenta, venta.getDetalles());

            JOptionPane.showMessageDialog(vista,
                    "Venta registrada correctamente. ID: " + idVenta);
            
              int opcion = JOptionPane.showConfirmDialog(vista,
                "¿Desea generar el XML de la venta?",
                "Generar XML",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            generarXML(); 
        }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar venta: " + ex.getMessage());
        }
    }
    
    
    private void generarXML() {
    try {
        Cliente cliente = (Cliente) vista.cmbCliente.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un cliente antes de generar XML");
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.TbTabla.getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "No hay productos en la venta para generar XML");
            return;
        }

       
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

       
        Element ventaRoot = doc.createElement("venta");
        doc.appendChild(ventaRoot);

        
        Element clienteNode = doc.createElement("cliente");
        clienteNode.setAttribute("id", String.valueOf(cliente.getCedula()));
        clienteNode.setAttribute("nombre", cliente.getNombre());
        ventaRoot.appendChild(clienteNode);

        // Productos
        Element productosNode = doc.createElement("productos");
        ventaRoot.appendChild(productosNode);

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object codigo = modelo.getValueAt(i, 0);
            Object nombre = modelo.getValueAt(i, 1);
            Object cantidad = modelo.getValueAt(i, 2);
            Object precio = modelo.getValueAt(i, 3);
            Object subtotal = modelo.getValueAt(i, 4);

            Element productoNode = doc.createElement("producto");
            productoNode.setAttribute("codigo", codigo != null ? codigo.toString() : "");
            productoNode.setAttribute("nombre", nombre != null ? nombre.toString() : "");
            productoNode.setAttribute("cantidad", cantidad != null ? cantidad.toString() : "0");
            productoNode.setAttribute("precio", precio != null ? precio.toString() : "0.0");
            productoNode.setAttribute("subtotal", subtotal != null ? subtotal.toString() : "0.0");

            productosNode.appendChild(productoNode);
        }

        // Totales
        Element totalesNode = doc.createElement("totales");
        totalesNode.setAttribute("subtotal", vista.txtSubtotal.getText());
        totalesNode.setAttribute("impuesto", vista.txtImpuesto.getText());
        totalesNode.setAttribute("total", vista.txtTotal.getText());
        ventaRoot.appendChild(totalesNode);

        // Elegir ubicación del archivo con JFileChooser
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar XML de la venta");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos XML", "xml"));
        int userSelection = chooser.showSaveDialog(vista);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".xml")) {
                ruta += ".xml"; // asegurarse de que tenga extensión .xml
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(ruta);

            transformer.transform(source, result);

            JOptionPane.showMessageDialog(vista, "XML generado correctamente en: " + ruta);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al generar XML: " + ex.getMessage());
        ex.printStackTrace();
    }
}

}
