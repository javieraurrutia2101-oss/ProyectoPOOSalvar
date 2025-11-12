package modelo;

import utilidades.EstadoPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanCosecha {
    private int id;
    private String nombrePlan;
    private LocalDate fechaIni;
    private LocalDate fechaFin;
    private double metaKilos;
    private double precioBaseKilos;
    private Huerto huerto;
    private Cuartel cuartel;
    private EstadoPlan estado;
    private List<Cuadrilla> cuadrillas;

    public PlanCosecha(int id, String nombrePlan, LocalDate fechaIni, LocalDate fechaFin, double metaKilos, double precioBaseKilos, Huerto huerto, Cuartel cuartel) {
        this.id = id;
        this.nombrePlan = nombrePlan;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.metaKilos = metaKilos;
        this.precioBaseKilos = precioBaseKilos;
        this.huerto = huerto;
        this.cuartel = cuartel;
        this.estado = EstadoPlan.PLANIFICADO;
        this.cuadrillas = new ArrayList<>();
    }

    public void addCuadrilla(Cuadrilla cuadrilla) {
        this.cuadrillas.add(cuadrilla);
    }

    public int getId() { return id; }
    public String getNombrePlan() { return nombrePlan; }
    public double getPrecioBaseKilos() { return precioBaseKilos; }
    public EstadoPlan getEstado() { return estado; }
    public void setEstado(EstadoPlan estado) { this.estado = estado; }
    public List<Cuadrilla> getCuadrillas() { return cuadrillas; }
    public Huerto getHuerto() { return huerto; }
    public Cuartel getCuartel() { return cuartel; }

    public Cuadrilla buscarCuadrilla(int idCuadrilla) {
        for (Cuadrilla c : cuadrillas) {
            if (c.getIdCuadrilla() == idCuadrilla) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Plan '" + nombrePlan + "' (ID: " + id + ")";
    }
}