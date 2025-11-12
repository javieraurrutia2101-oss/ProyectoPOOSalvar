package modelo;

import utilidades.Rut;

public abstract class Persona {
    protected Rut rut;
    protected String nombre;
    protected String email;
    protected String direccion;

    public Persona(Rut rut, String nombre, String email, String direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
    }


    public Rut getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }

    @Override
    public String toString() {
        return nombre + " (" + rut + ")";
    }
}
