package controlador;


// Importando las clases del modelo
import modelo.Propietario;
import modelo.Supervisor;
import modelo.Cosechador;
import modelo.Cultivo;
import modelo.Huerto;
import modelo.Cuartel;
import modelo.PlanCosecha;
import modelo.Cuadrilla;
import modelo.CosechadorAsignado;
import modelo.Pesaje;
import modelo.PagoPesaje;

// importando las clases de utilidades
import utilidades.Rut;
import utilidades.Calidad;
import utilidades.EstadoPlan;
import utilidades.EstadoFenologico;
import utilidades.GestionHuertosException; // Usando la excepción personalizada

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ControladorProduccion {


    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US);

    private List<Propietario> propietarios = new ArrayList<>();
    private List<Supervisor> supervisores = new ArrayList<>();
    private List<Cosechador> cosechadores = new ArrayList<>();
    private List<Cultivo> cultivos = new ArrayList<>();
    private List<Huerto> huertos = new ArrayList<>();
    private List<PlanCosecha> planesCosecha = new ArrayList<>();
    private List<Pesaje> pesajes = new ArrayList<>();
    private List<PagoPesaje> pagos = new ArrayList<>();


    public Propietario buscarPropietario(Rut rut) throws GestionHuertosException {
        for (Propietario p : propietarios) {
            if (p.getRut().equals(rut)) return p;
        }
        throw new GestionHuertosException("Propietario con rut " + rut + " no encontrado.");
    }

    public Supervisor buscarSupervisor(Rut rut) throws GestionHuertosException {
        for (Supervisor s : supervisores) {
            if (s.getRut().equals(rut)) return s;
        }
        throw new GestionHuertosException("Supervisor con rut " + rut + " no encontrado.");
    }

    public Cosechador buscarCosechador(Rut rut) throws GestionHuertosException {
        for (Cosechador c : cosechadores) {
            if (c.getRut().equals(rut)) return c;
        }
        throw new GestionHuertosException("Cosechador con rut " + rut + " no encontrado.");
    }

    public Cultivo buscarCultivo(int id) throws GestionHuertosException {
        for (Cultivo c : cultivos) {
            if (c.getId() == id) return c;
        }
        throw new GestionHuertosException("Cultivo con ID " + id + " no encontrado.");
    }

    public Huerto buscarHuerto(String nombre) throws GestionHuertosException {
        for (Huerto h : huertos) {
            if (h.getNombre().equalsIgnoreCase(nombre)) return h;
        }
        throw new GestionHuertosException("Huerto con nombre '" + nombre + "' no encontrado.");
    }

    public PlanCosecha buscarPlan(int id) throws GestionHuertosException {
        for (PlanCosecha p : planesCosecha) {
            if (p.getId() == id) return p;
        }
        throw new GestionHuertosException("Plan de Cosecha con ID " + id + " no encontrado.");
    }

    public void readDataFromFile() {
            File file = new File("Datos.txt");
                try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linea;
                String operacionActual = null;
                int lineasALeer = 0;
                while ((linea = br.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.startsWith("#") || linea.isEmpty()) {
                        continue;
                    }
                    if (lineasALeer > 0) {
                        String[] datos = linea.split(";");
                        for (int i = 0; i < datos.length; i++) {
                            datos[i] = datos[i].trim();
                        }
                        try {
                            switch (operacionActual) {
                                case "createPropietario":
                                    createPropietario(datos[0], datos[1], datos[2], datos[3], datos[4]);
                                    break;
                                case "createSupervisor":
                                    createSupervisor(datos[0], datos[1], datos[2], datos[3], datos[4]);
                                    break;
                                case "createCosechador":
                                    createCosechador(datos[0], datos[1], datos[2], datos[3], datos[4]);
                                    break;
                                case "createCultivo":
                                    createCultivo(Integer.parseInt(datos[0]), datos[1], datos[2], Float.parseFloat(datos[3]));
                                    break;
                                case "createHuerto":
                                    createHuerto(datos[0], Float.parseFloat(datos[1]), datos[2], datos[3]);
                                    break;
                                case "addCuartelToHuerto":
                                    addCuartelToHuerto(datos[0], Integer.parseInt(datos[1]), Float.parseFloat(datos[2]), Integer.parseInt(datos[3]));
                                    break;
                                case "createPlanCosecha":
                                    createPlanCosecha(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], Double.parseDouble(datos[4]), Double.parseDouble(datos[5]), datos[6], Integer.parseInt(datos[7]));
                                    break;
                                case "addCuadrillaToPlan":
                                    addCuadrillaToPlan(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), datos[2], datos[3]);
                                    break;
                                case "addCosechadorToCuadrilla":
                                    addCosechadorToCuadrilla(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), datos[2], datos[3], Double.parseDouble(datos[4]), datos[5]);
                                    break;
                                case "changeEstadoPlan":
                                    changeEstadoPlan(Integer.parseInt(datos[0]), datos[1]);
                                    break;
                                case "changeEstadoCuartel":
                                    changeEstadoCuartel(Integer.parseInt(datos[0]), datos[1], datos[2]);
                                    break;
                                case "addPesaje":
                                    String cantidadStr = datos[4].replace(',', '.');
                                    float cantidad = Float.parseFloat(cantidadStr);
                                    addPesaje(Integer.parseInt(datos[0]), datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), cantidad, datos[5]);
                                    break;
                                default:
                                    System.err.println("Operación desconocida: " + operacionActual);
                            }
                        } catch (GestionHuertosException e) {
                            System.err.println("Error de lógica (" + operacionActual + "): " + e.getMessage());
                        } catch (Exception e) {
                            System.err.println("Error procesando línea (" + operacionActual + "): " + linea + " | Error: " + e.getMessage());
                        }
                        lineasALeer--;
                    } else {
                        String[] partes = linea.split(";");
                        if (partes.length == 2) {
                            try {
                                operacionActual = partes[0].trim();
                                lineasALeer = Integer.parseInt(partes[1].trim());
                            } catch (NumberFormatException e) {
                                System.err.println("Error al parsear línea de operación: " + linea);
                                operacionActual = null;
                                lineasALeer = 0;
                            }
                        }
                    }
                }
            } catch(IOException e){
                System.err.println("Error crítico al leer el archivo: " + e.getMessage());
            } catch(Exception e){
                System.err.println("Error inesperado durante la carga: " + e.getMessage());
                e.printStackTrace();
            }
    }
    public void createPropietario(String rut, String nombre, String email, String direccion, String dirComercial) {
        propietarios.add(new Propietario(new Rut(rut), nombre, email, direccion, dirComercial));
    }

    public void createSupervisor(String rut, String nombre, String email, String direccion, String profesion) {
        supervisores.add(new Supervisor(new Rut(rut), nombre, email, direccion, profesion));
    }

    public void createCosechador(String rut, String nombre, String email, String direccion, String fechaNac) {
        LocalDate fecha = LocalDate.parse(fechaNac, DATE_FORMATTER);
        cosechadores.add(new Cosechador(new Rut(rut), nombre, email, direccion, fecha));
    }

    public void createCultivo(int id, String especie, String variedad, float rendimiento) {
        cultivos.add(new Cultivo(id, especie, variedad, rendimiento));
    }

    public void createHuerto(String nombre, float superficie, String ubicacion, String rutPropietario) throws GestionHuertosException {
        Propietario p = buscarPropietario(new Rut(rutPropietario)); // Lanza excepción si no existe
        huertos.add(new Huerto(nombre, superficie, ubicacion, p));
    }

    public void addCuartelToHuerto(String nombreHuerto, int idCuartel, float superficie, int idCultivo) throws GestionHuertosException {
        Huerto h = buscarHuerto(nombreHuerto);
        Cultivo c = buscarCultivo(idCultivo);
        h.addCuartel(new Cuartel(idCuartel, superficie, c));
    }

    public void createPlanCosecha(int id, String nombre, String fechaIni, String fechaFin, double meta, double precio, String nomHuerto, int idCuartel) throws GestionHuertosException {
        Huerto h = buscarHuerto(nomHuerto);
        Cuartel c = h.buscarCuartel(idCuartel);
        if (c == null) {
            throw new GestionHuertosException("Cuartel ID " + idCuartel + " no encontrado en huerto '" + nomHuerto + "'.");
        }
        LocalDate fIni = LocalDate.parse(fechaIni, DATE_FORMATTER);
        LocalDate fFin = LocalDate.parse(fechaFin, DATE_FORMATTER);
        planesCosecha.add(new PlanCosecha(id, nombre, fIni, fFin, meta, precio, h, c));
    }

    public void addCuadrillaToPlan(int idPlan, int idCuadrilla, String nombre, String rutSupervisor) throws GestionHuertosException {
        PlanCosecha p = buscarPlan(idPlan);
        Supervisor s = buscarSupervisor(new Rut(rutSupervisor));
        p.addCuadrilla(new Cuadrilla(idCuadrilla, nombre, s));
    }

    public void addCosechadorToCuadrilla(int idPlan, int idCuadrilla, String fechaIni, String fechaFin, double meta, String rutCosechador) throws GestionHuertosException {
        PlanCosecha p = buscarPlan(idPlan);
        Cuadrilla c = p.buscarCuadrilla(idCuadrilla);
        if (c == null) {
            throw new GestionHuertosException("Cuadrilla ID " + idCuadrilla + " no encontrada en Plan ID " + idPlan + ".");
        }
        Cosechador cos = buscarCosechador(new Rut(rutCosechador));

        LocalDate fIni = LocalDate.parse(fechaIni, DATE_FORMATTER);
        LocalDate fFin = LocalDate.parse(fechaFin, DATE_FORMATTER);
        c.addAsignacion(new CosechadorAsignado(fIni, fFin, meta, cos));
    }

    public void changeEstadoPlan(int idPlan, String nuevoEstado) throws GestionHuertosException {
        PlanCosecha p = buscarPlan(idPlan);
        p.setEstado(EstadoPlan.valueOf(nuevoEstado.toUpperCase()));
    }

    public void changeEstadoCuartel(int idCuartel, String nombreHuerto, String nuevoEstado) throws GestionHuertosException {
        Huerto h = buscarHuerto(nombreHuerto);
        Cuartel c = h.buscarCuartel(idCuartel);
        if (c == null) {
            throw new GestionHuertosException("Cuartel ID " + idCuartel + " no encontrado en huerto '" + nombreHuerto + "'.");
        }
        c.setEstado(EstadoFenologico.valueOf(nuevoEstado.toUpperCase()));
    }

    public void addPesaje(int id, String rutCosechador, int idPlan, int idCuadrilla, float cantidad, String calidad) throws GestionHuertosException {
        Cosechador cos = buscarCosechador(new Rut(rutCosechador));
        PlanCosecha plan = buscarPlan(idPlan);

        Cuadrilla cuad = plan.buscarCuadrilla(idCuadrilla);
        if (cuad == null) {
            throw new GestionHuertosException("Cuadrilla ID " + idCuadrilla + " no encontrada en Plan ID " + idPlan + ".");
        }

        if (cuad.buscarCosechador(cos.getRut()) == null) {
            throw new GestionHuertosException("Cosechador " + cos.getNombre() + " no pertenece a la cuadrilla " + cuad.getNombreCuadrilla());
        }

        Calidad c = Calidad.valueOf(calidad.toUpperCase());
        Pesaje pesaje = new Pesaje(id, cantidad, c, cos, plan, cuad);
        this.pesajes.add(pesaje);
        cos.addPesaje(pesaje);
    }


    public void mostrarResultados() {
        System.out.println("--- Resumen del Sistema ---");
        System.out.println("Propietarios: " + propietarios.size());
        System.out.println("Supervisores: " + supervisores.size());
        System.out.println("Cosechadores: " + cosechadores.size());
        System.out.println("Huertos: " + huertos.size());
        System.out.println("Planes de Cosecha: " + planesCosecha.size());
        System.out.println("Total Pesajes Registrados: " + pesajes.size());
        System.out.println("\n--- Detalle de Pesajes (Avance 2) ---");

        double montoTotalGeneral = 0;
        for (Pesaje p : pesajes) {
            System.out.println(p + " - Cosechador: " + p.getCosechador().getNombre());
            montoTotalGeneral += p.getMontoParcial();
        }
        System.out.println("-------------------------------------");
        System.out.printf("MONTO TOTAL (Todos los pesajes): $%.2f\n", montoTotalGeneral);

        System.out.println("\n--- Ejemplo de Pago (Cosechador 1) ---");
        try {
            Cosechador c1 = buscarCosechador(new Rut("33.333.333-3"));


            PagoPesaje pago = new PagoPesaje(1001, LocalDate.now(), c1);
            for (Pesaje p : c1.getPesajes()) {
                pago.agregarPesaje(p);
            }

            pagos.add(pago);

            System.out.println(pago);

        } catch (GestionHuertosException e) {
            System.err.println(e.getMessage());
        }
    }
}