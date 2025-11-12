package modelo;

import utilidades.Rut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cosechador extends Persona {
    private LocalDate fechaNacimiento;
    private List<Pesaje> pesajes;


    public Cosechador(Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        super(rut, nombre, email, direccion);
        this.fechaNacimiento = fechaNacimiento;
        this.pesajes = new ArrayList<>();
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void addPesaje(Pesaje p) {
        this.pesajes.add(p);
    }

    public List<Pesaje> getPesajes() {
        return pesajes;
    }
}