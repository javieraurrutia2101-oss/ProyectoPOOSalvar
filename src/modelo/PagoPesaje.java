package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoPesaje {
    private int idPago;
    private LocalDate fechaPago;
    private Cosechador cosechador;
    private List<Pesaje> pesajesIncluidos;

    public PagoPesaje(int idPago, LocalDate fechaPago, Cosechador cosechador) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.cosechador = cosechador;
        this.pesajesIncluidos = new ArrayList<>();
    }

    public void agregarPesaje(Pesaje pesaje) {
        this.pesajesIncluidos.add(pesaje);
    }

    public int getIdPago() { return idPago; }
    public LocalDate getFechaPago() { return fechaPago; }
    public Cosechador getCosechador() { return cosechador; }
    public List<Pesaje> getPesajesIncluidos() { return pesajesIncluidos; }

    public double getMonto() {
        double montoTotal = 0;
        for (Pesaje pesaje : pesajesIncluidos) {
            montoTotal += pesaje.getMontoParcial();
        }
        return montoTotal;
    }

    @Override
    public String toString() {
        return "Pago ID: " + idPago + " a " + cosechador.getNombre() + " - Monto Total: $" + String.format("%.2f", getMonto());
    }
}