package controlador;

import utilidades.GestionHuertosException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LecturaArchivo {


    public void cargarDatos(String nombreArchivo, ControladorProduccion controlador) {
        File file = new File(nombreArchivo);
        try (BufferedReader br = new BufferedReader(new FileReader("Datos.txt"))) {
            String linea;
            String operacionActual = null;
            int lineasALeer = 0;

            // Leer el archivo línea por línea
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Omitir comentarios y líneas en blanco
                if (linea.startsWith("#") || linea.isEmpty()) {
                    continue;
                }


                if (lineasALeer > 0) {
                    procesarLinea(linea, operacionActual, controlador);
                    lineasALeer--;
                }

                else {
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
        } catch (IOException e) {
            System.err.println("Error crítico al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado durante la carga: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void procesarLinea(String linea, String operacion, ControladorProduccion controlador) {
        String[] datos = linea.split(";");
        for (int i = 0; i < datos.length; i++) {
            datos[i] = datos[i].trim();
        }
        try {
            switch (operacion) {
                case "createPropietario":
                    controlador.createPropietario(datos[0], datos[1], datos[2], datos[3], datos[4]);
                    break;
                case "createSupervisor":
                    controlador.createSupervisor(datos[0], datos[1], datos[2], datos[3], datos[4]);
                    break;
                case "createCosechador":
                    controlador.createCosechador(datos[0], datos[1], datos[2], datos[3], datos[4]);
                    break;
                case "createCultivo":
                    controlador.createCultivo(Integer.parseInt(datos[0]), datos[1], datos[2], Float.parseFloat(datos[3]));
                    break;
                case "createHuerto":
                    controlador.createHuerto(datos[0], Float.parseFloat(datos[1]), datos[2], datos[3]);
                    break;
                case "addCuartelToHuerto":
                    controlador.addCuartelToHuerto(datos[0], Integer.parseInt(datos[1]), Float.parseFloat(datos[2]), Integer.parseInt(datos[3]));
                    break;
                case "createPlanCosecha":
                    controlador.createPlanCosecha(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], Double.parseDouble(datos[4]), Double.parseDouble(datos[5]), datos[6], Integer.parseInt(datos[7]));
                    break;
                case "addCuadrillaToPlan":
                    controlador.addCuadrillaToPlan(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), datos[2], datos[3]);
                    break;
                case "addCosechadorToCuadrilla":
                    controlador.addCosechadorToCuadrilla(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), datos[2], datos[3], Double.parseDouble(datos[4]), datos[5]);
                    break;
                case "changeEstadoPlan":
                    controlador.changeEstadoPlan(Integer.parseInt(datos[0]), datos[1]);
                    break;
                case "changeEstadoCuartel":
                    controlador.changeEstadoCuartel(Integer.parseInt(datos[0]), datos[1], datos[2]);
                    break;
                case "addPesaje":
                    String cantidadStr = datos[4].replace(',', '.'); // Asegura usar punto
                    float cantidad = Float.parseFloat(cantidadStr);

                    controlador.addPesaje(Integer.parseInt(datos[0]), datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), cantidad, datos[5]);
                    break;
                default:
                    System.err.println("Operación desconocida: " + operacion);
            }
        } catch (GestionHuertosException e) {
            System.err.println("Error de lógica (" + operacion + "): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando línea (" + operacion + "): " + linea + " | Error: " + e.getMessage());
        }
    }
}