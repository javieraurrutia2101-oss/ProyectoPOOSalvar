package modelo;

import utilidades.Rut;

public class Propietario extends Persona {
    private String direccionComercial;

    public Propietario(Rut rut, String nombre, String email, String direccion, String direccionComercial) {
        super(rut, nombre, email, direccion);
        this.direccionComercial = direccionComercial;
    }

    public String getDireccionComercial() { return direccionComercial; }
}
