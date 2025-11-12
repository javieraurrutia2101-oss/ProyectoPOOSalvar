package modelo;

import utilidades.Rut;

import java.util.ArrayList;
import java.util.List;

public class Cuadrilla {
    private int idCuadrilla;
    private String nombreCuadrilla;
    private Supervisor supervisor;
    private List<CosechadorAsignado> asignaciones;

    public Cuadrilla(int idCuadrilla, String nombreCuadrilla, Supervisor supervisor) {
        this.idCuadrilla = idCuadrilla;
        this.nombreCuadrilla = nombreCuadrilla;
        this.supervisor = supervisor;
        this.asignaciones = new ArrayList<CosechadorAsignado>();
    }

    public void addAsignacion(CosechadorAsignado asignacion) {
        this.asignaciones.add(asignacion);
    }

    public int getIdCuadrilla() { return idCuadrilla; }
    public String getNombreCuadrilla() { return nombreCuadrilla; }
    public Supervisor getSupervisor() { return supervisor; }
    public List<CosechadorAsignado> getAsignaciones() { return asignaciones; }

    public Cosechador buscarCosechador(Rut rut) {
        for (CosechadorAsignado a : asignaciones) {
            if (a.getCosechador().getRut().equals(rut)) {
                return a.getCosechador();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Cuadrilla '" + nombreCuadrilla + "' (ID: " + idCuadrilla + ")";
    }
}