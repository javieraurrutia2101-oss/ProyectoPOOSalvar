package modelo;
import utilidades.EstadoFenologico;
public class Cuartel {
    private int id;
    private float superficie;
    private EstadoFenologico estado;
    private Cultivo cultivo;
    private Huerto huerto;

    public Cuartel(int id, float superficie, Cultivo cultivo, Huerto huerto) {
        this.id = id;
        this.superficie = superficie;
        this.cultivo = cultivo;
        this.huerto = huerto;
        this.estado = EstadoFenologico.REPOSO_INVERNAL;
    }
    public boolean setEstado(EstadoFenologico nuevoEstado) {
        if (esTransicionPermitida(this.estado, nuevoEstado)) {
            this.estado = nuevoEstado;
            return true;
        }
        return false;
    }
    private boolean esTransicionPermitida(EstadoFenologico actual, EstadoFenologico nuevo) {
        if (actual == EstadoFenologico.POSTCOSECHA && nuevo == EstadoFenologico.REPOSO_INVERNAL) {
            return true;
        }
        return nuevo.ordinal() >= actual.ordinal() ||
                (actual == EstadoFenologico.POSTCOSECHA && nuevo == EstadoFenologico.REPOSO_INVERNAL);
    }
    //get
    public int getId() { return id; }
    public float getSuperficie() { return superficie; }
    public EstadoFenologico getEstado() { return estado; }
    public Cultivo getCultivo() { return cultivo; }
    public Huerto getHuerto() { return huerto; }
}