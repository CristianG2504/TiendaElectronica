/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cami
 */
public class Venta {
    
    private int id;
    private Timestamp fecha;
    private Cliente cliente;
    private Usuario usuario;
    private List<VentaDetalle> detalles;

    public Venta() {
        detalles = new ArrayList<>();
    }

    public Venta(Cliente cliente, Usuario usuario) {
        this.cliente = cliente;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<VentaDetalle> getDetalles() { return detalles; }
    public void agregarDetalle(VentaDetalle d) { detalles.add(d); }
}
