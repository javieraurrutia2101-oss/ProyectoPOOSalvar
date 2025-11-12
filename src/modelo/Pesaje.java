package modelo;

import utilidades.Calidad;

import static utilidades.Calidad.*;

public class Pesaje {
    private int id;
    private float cantidadkg;
    private Calidad calidad;
    private Cosechador cosechador;
    private PlanCosecha plan;
    private Cuadrilla cuadrilla;

    public Pesaje(int id, float cantidadkg, Calidad calidad, Cosechador cosechador, PlanCosecha plan, Cuadrilla cuadrilla) {
        this.id = id;
        this.cantidadkg = cantidadkg;
        this.calidad = calidad;
        this.cosechador = cosechador;
        this.plan = plan;
        this.cuadrilla = cuadrilla;
    }

    public int getId() { return id; }
    public float getCantidadkg() { return cantidadkg; }
    public Calidad getCalidad() { return calidad; }
    public Cosechador getCosechador() { return cosechador; }
    public PlanCosecha getPlan() { return plan; }
    public Cuadrilla getCuadrilla() { return cuadrilla; }


    public double getMontoParcial() {
        if (plan == null) {
            return 0;
        }

        double precioBase = plan.getPrecioBaseKilos();

        switch (calidad) {
            case EXCELENTE:
                return cantidadkg * precioBase;
            case SUFICIENTE:
                return cantidadkg * precioBase * 0.80;
            case DEFICIENTE:
                return cantidadkg * precioBase * 0.60;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return "Pesaje ID: " + id + " (" + cantidadkg + "kg, " + calidad + ") - Monto: $" + String.format("%.2f", getMontoParcial());
    }
}
