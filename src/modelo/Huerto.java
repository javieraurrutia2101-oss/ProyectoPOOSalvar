package modelo;
import utilidades.EstadoFenologico;
import utilidades.GestionHuertosException;
import java.util.ArrayList;
import java.util.List;

public class Huerto {
    private String nombre;
    private float superficie;
    private String ubicacion;
    private Propietario propietario;
    private List<Cuartel> cuarteles;

    public Huerto(String nombre, float superficie, String ubicacion, Propietario propietario) {
        this.nombre = nombre;
        this.superficie = superficie;
        this.ubicacion = ubicacion;
        this.propietario = propietario;
        this.cuarteles = new ArrayList<>();
        this.propietario.addHuerto(this);
    }
    public void addCuartel(int id, float superficie, Cultivo cultivo) throws GestionHuertosException {
        for (Cuartel cuartel : cuarteles) {
            if (cuartel.getId() == id) {
                throw new GestionHuertosException("Ya existe en el huerto un cuartel con el id indicado");
            }
        }
        float superficieTotal = superficie;
        for (Cuartel cuartel : cuarteles) {
            superficieTotal += cuartel.getSuperficie();
        }
        if (superficieTotal > this.superficie) {
            throw new GestionHuertosException("La superficie del cuartel excederá la superficie del huerto");
        }
        Cuartel nuevoCuartel = new Cuartel(id, superficie, cultivo, this);
        cuarteles.add(nuevoCuartel);
    }
    public Cuartel getCuartelById(int id) throws GestionHuertosException {
        for (Cuartel cuartel : cuarteles) {
            if (cuartel.getId() == id) {
                return cuartel;
            }
        }
        throw new GestionHuertosException("No existe en el huerto un cuartel con el id indicado");
    }
    public void setEstadoCuartel(int id, EstadoFenologico estado) throws GestionHuertosException {
        Cuartel cuartel = getCuartelById(id);
        if (!cuartel.setEstado(estado)) {
            throw new GestionHuertosException("No está permitido el cambio de estado solicitado");
        }
    }
    //get set
    public String getNombre() { return nombre; }
    public float getSuperficie() { return superficie; }
    public String getUbicacion() { return ubicacion; }
    public Propietario getPropietario() { return propietario; }
    public ArrayList<Cuartel> getCuarteles() { return cuarteles; }
    public int getNumeroCuarteles() { return cuarteles.size(); }
}