package modelo;

import java.time.LocalDate;

public class CosechadorAsignado {
    private LocalDate fechaIniCosechador;
    private LocalDate fechaFinCosechador;
    private double metaKilosCosechador;
    private Cosechador cosechador; // Asociación

    public CosechadorAsignado(LocalDate fechaIni, LocalDate fechaFin, double meta, Cosechador cosechador) {
        this.fechaIniCosechador = fechaIni;
        this.fechaFinCosechador = fechaFin;
        this.metaKilosCosechador = meta;
        this.cosechador = cosechador;
    }

    public LocalDate getFechaIniCosechador() { return fechaIniCosechador; }
    public LocalDate getFechaFinCosechador() { return fechaFinCosechador; }
    public double getMetaKilosCosechador() { return metaKilosCosechador; }
    public Cosechador getCosechador() { return cosechador; }
}