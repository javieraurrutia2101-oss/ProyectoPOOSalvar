package vista;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import controlador.ControladorProduccion;
import modelo.Cuartel;
import modelo.Propietario;
import utilidades.EstadoFenologico;
import utilidades.GestionHuertosException;

public class GestionHuertosUI {
    private ControladorProduccion control;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public static void main(String[] args) {
        GestionHuertosUI app = new GestionHuertosUI();
        app.menu();
    }
    // Singleton
    private static GestionHuertosUI instance = new GestionHuertosUI();
    private GestionHuertosUI(){
       /*
        this.control = new ControlProduccion();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        */
    }
    public static GestionHuertosUI getInstance() {return instance;}
    // Fin Singleton
    public void menu() {
        int opcion;
        do {
            System.out.println("""
                    ::: MENU PRINCIPAL :::
                    1. Crear Personas
                    2. Menu Huertos
                    3. Menú Planes de Cosecha
                    4. Menu Listados
                    5. Salir
                       Opcion:
                    """);
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> creaPersona();
                    case 2 -> menuHuertos();
                    case 3 -> menuPlanesCosecha();
                    case 4 -> menuListados();
                    case 5 -> System.out.println("Cerrando Programa...");
                    default -> System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                opcion = -1;
            }
        } while (opcion != 5);

        scanner.close();
    }
    private void menuHuertos(){
        int opcion = -1;
        String[] opcionesHuertos = {">>> SUBMENU HUERTOS <<<", "1. Crear Cultivo", "2. Crear Huerto",
                "3. Agregar Cuarteles a Huerto" , "4. Cambiar Estado Cuartel", "5. Volver", "Opcion:"};
        do{
            for(int i = 0; i < opcionesHuertos.length; i++){
                System.out.println(opcionesHuertos[i]);
            }
            opcion = Integer.parseInt(scanner.next());
            switch (opcion) {
                case 1 -> creaCultivo();
                case 2 -> creaHuerto();
                case 3 -> agregaCuartelesAHuerto();
                case 4 -> throw new GestionHuertosException("Falta changeEstadoCuartel en controlador");
                case 5 -> GestionHuertosUI.getInstance().menu();
            }
        }while(opcion != 5);
    }
    private void menuPlanesCosecha(){
        int opcion = -1;
        String[] opcionesPlanes = {">>> SUBMENU PLANES DE COSECHA <<<", "1. Crear Plan de Cosecha",
                "2. Cambiar Estado de Plan", "3. Agregar Cuadrillas a Plan", "4. Agregar Cosechadores a Cuadrilla",
                "5. Agregar Pesaje a Cosechador", "6. Pagar Pesajes Impagos de Cosechador", "7. Volver", "Opcion:"};
        do{
            for(int i = 0; i < opcionesPlanes.length; i++){
                System.out.println(opcionesPlanes[i]);
            }
            opcion = Integer.parseInt(scanner.next());
            switch (opcion) {
                case 1 -> creaPlanDeCosecha();
            }
        }while(opcion != 7);
    }
    private void menuListados(){
        int opcion = -1;
        String[] opcionesListados = {">>> SUBMENU LISTADOS <<<", "1. Listado de Propietarios", "2. Listado de Supervisores", "3. Listado de Cosechadores", "4. Listado de Cultivos", "5. Listado de Huertos", "6. Listado de Planes de Cosecha", "7. Listado Pesajes",
                "8. Listado Pesajes de un Cosechador", "9. Listado de Pagos", "10. Volver", "Opcion:"};
        do{
            for(int i = 0; i < opcionesListados.length; i++){
                System.out.println(opcionesListados[i]);
            }
            opcion = Integer.parseInt(scanner.next());
            switch (opcion) {
                case 1 -> listaPropietarios();
                case 2 -> listaSupervisores();
                case 3 -> listaCosechadores();
                case 4 -> listaCultivos();
                case 5 -> listaHuertos();
                case 6 -> listaPlanesDeCosecha();
                case 7 -> throw new GestionHuertosException("Falta listPesajes en controlador");
                case 8 -> throw new GestionHuertosException("Falta listPesajesCosechador en controlador");
                case 9 -> throw new GestionHuertosException("Falta listPagosPesajes en controlador");
                case 10 -> GestionHuertosUI.getInstance().menu();
                default -> System.out.println("Opcion Invalida");
            }
        }while(opcion != 10);
    }
    private void creaPersona() {
        System.out.println("\n--- CREAR PERSONA ---");
        System.out.println("Seleccione el rol:");
        System.out.println("1. Propietario");
        System.out.println("2. Supervisor");
        System.out.println("3. Cosechador");
        System.out.print("Opción: ");

        int rol = Integer.parseInt(scanner.nextLine());

        System.out.print("RUT (formato: 11.111.111-1): ");
        // todo cambiar el formato de Rut a String
        String rut = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        boolean resultado = false;

        switch (rol) {
            case 1 -> {
                System.out.print("Dirección comercial: ");
                String dirComercial = scanner.nextLine();
                resultado = control.createPropietario(rut, nombre, email, direccion, dirComercial);
            }
            case 2 -> {
                System.out.print("Profesión: ");
                String profesion = scanner.nextLine();
                resultado = control.createSupervisor(rut, nombre, email, direccion, profesion);
            }
            case 3 -> {
                System.out.print("Fecha de nacimiento (dd/mm/aaaa): ");
                LocalDate fechaNac = LocalDate.parse(scanner.nextLine(), dateFormatter);
                resultado = control.createCosechador(rut, nombre, email, direccion, fechaNac);
            }
            default -> {
                System.out.println("Rol no válido.");
                return;
            }
        }

        if (resultado) {
            System.out.println("Persona creada exitosamente.");
        } else {
            System.out.println("Error: No se pudo crear la persona. Verifique que los datos sean correctos.");
        }
    }

    private void creaCultivo() {
        System.out.println("\n--- CREAR CULTIVO ---");

        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Especie: ");
        String especie = scanner.nextLine();

        System.out.print("Variedad: ");
        String variedad = scanner.nextLine();

        System.out.print("Rendimiento: ");
        float rendimiento = Float.parseFloat(scanner.nextLine());

        if (control.createCultivo(id, especie, variedad, rendimiento)) {
            System.out.println("Cultivo creado exitosamente.");
        } else {
            System.out.println("Error: No se pudo crear el cultivo. Verifique que el ID no esté en uso.");
        }
    }

    private void creaHuerto() {
        System.out.println("\n--- CREAR HUERTO ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Superficie (m²): ");
        float superficie = Float.parseFloat(scanner.nextLine());

        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();

        System.out.print("RUT del propietario: ");
        String rutProp = scanner.nextLine();

        if (!control.createHuerto(nombre, superficie, ubicacion, rutProp)) {
            System.out.println("Error: No se pudo crear el huerto.");
            return;
        }

        System.out.println("Huerto creado exitosamente.");

        System.out.print("Número de cuarteles: ");
        int numCuarteles = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numCuarteles; i++) {
            System.out.println("\nCuartel #" + (i + 1));
            System.out.print("ID del cuartel: ");
            int idCuartel = Integer.parseInt(scanner.nextLine());

            System.out.print("Superficie (m²): ");
            float supCuartel = Float.parseFloat(scanner.nextLine());

            System.out.print("ID del cultivo: ");
            int idCultivo = Integer.parseInt(scanner.nextLine());

            if (control.addCuartelToHuerto(nombre, idCuartel, supCuartel, idCultivo)) {
                System.out.println("Cuartel agregado exitosamente.");
            } else {
                System.out.println("Error: No se pudo agregar el cuartel.");
            }
        }
    }
    private void agregaCuartelesAHuerto() {
        System.out.println("Agregando el cuarteles a un huerto...");
        try {
            System.out.println("Ingrese el nombre del huerto: ");
            String nombreHuerto = scanner.nextLine();
            System.out.println("Nros. de cuarteles: ");
            int nroCuarteles = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < nroCuarteles; i++) {
                System.out.println("\nId Cuartel: ");
                int idCuartel = Integer.parseInt(scanner.nextLine());
                System.out.println("Superficie del Cuartel: ");
                float superficie = Float.parseFloat(scanner.nextLine());
                System.out.println("Id Cultivo del Cuartel: " + scanner.next());
                int idCultivo = Integer.parseInt(scanner.nextLine());
                System.out.println();
                if (control.addCuartelToHuerto(nombreHuerto, idCuartel, superficie, idCultivo)) {
                    System.out.println("Cuartel agregado exitosamente.");
                } else {
                    break;
                }
            }
        } catch (GestionHuertosException e) {
            System.out.println("Error: No se pudo agregar el cuarteles.");
        } finally {
            System.out.println();
        }
    }
    private void cambiaEstadoCuartel(){ // FALTA CHANGEESTADOCUARTEL EN CONTROL
        System.out.println("Cambiando estado de un cuartel...");
        System.out.println("Id cuartel: ");
        int idCuartel = Integer.parseInt(scanner.nextLine());
        System.out.println("Nombre del huerto: ");
        String nombreCuartel = scanner.nextLine();
        System.out.println("""
                || ----- N U E V O  E S T A D O ----- ||
                [1] = Reposo Invernal  [5] Maduracion
                [2] = Floracion        [6] Cosecha
                [3] = Cuaja            [7] PostCosecha
                [4] = Fructificacion
                Ingrese Su opcion:
                """);
        int opcion = Integer.parseInt(scanner.nextLine());
        boolean esValida = false;
        if(opcion >= 1 &&  opcion <= 4){
            EstadoFenologico nuevoFenologico = EstadoFenologico.values()[opcion-1];
            // Aca deberia ir metodo de change cuartel
        }else{
            System.out.println("Opcion Invalida");
        }
    }
    private void creaPlanDeCosecha() {
        System.out.println("\n--- CREAR PLAN DE COSECHA ---");

        System.out.print("ID del plan: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Fecha inicio (dd/mm/aaaa): ");
        LocalDate fechaIni = LocalDate.parse(scanner.nextLine(), dateFormatter);

        System.out.print("Fecha término (dd/mm/aaaa): ");
        LocalDate fechaFin = LocalDate.parse(scanner.nextLine(), dateFormatter);

        System.out.print("Meta (kilos): ");
        double meta = Double.parseDouble(scanner.nextLine());

        System.out.print("Precio base por kilo: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.print("Nombre del huerto: ");
        String nomHuerto = scanner.nextLine();

        System.out.print("ID del cuartel: ");
        int idCuartel = Integer.parseInt(scanner.nextLine());

        if (!control.createPlanCosecha(id, nombre, fechaIni, fechaFin, meta, precio, nomHuerto, idCuartel)) {
            System.out.println("Error: No se pudo crear el plan de cosecha.");
            return;
        }

        System.out.println("Plan de cosecha creado exitosamente.");

        System.out.print("Número de cuadrillas: ");
        int numCuadrillas = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numCuadrillas; i++) {
            System.out.println("\nCuadrilla #" + (i + 1));
            System.out.print("ID de la cuadrilla: ");
            int idCuadrilla = Integer.parseInt(scanner.nextLine());

            System.out.print("Nombre: ");
            String nomCuadrilla = scanner.nextLine();

            System.out.print("RUT del supervisor: ");
            String rutSupervisor = scanner.nextLine();

            if (control.addCuadrillaToPlan(id, idCuadrilla, nomCuadrilla, rutSupervisor)) {
                System.out.println("Cuadrilla agregada exitosamente.");
            } else {
                System.out.println("Error: No se pudo agregar la cuadrilla.");
            }
        }
    }

    private void asignaCosechadoresAPlan() {
        System.out.println("\n--- AGREGAR COSECHADORES A CUADRILLA ---");

        System.out.print("ID del plan de cosecha: ");
        int idPlan = Integer.parseInt(scanner.nextLine());

        System.out.print("ID de la cuadrilla: ");
        int idCuadrilla = Integer.parseInt(scanner.nextLine());

        System.out.print("Número de cosechadores a asignar: ");
        int numCosechadores = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numCosechadores; i++) {
            System.out.println("\nCosechador #" + (i + 1));

            System.out.print("Fecha de inicio (dd/mm/aaaa): ");
            LocalDate fechaIni = LocalDate.parse(scanner.nextLine(), dateFormatter);

            System.out.print("Fecha de término (dd/mm/aaaa): ");
            LocalDate fechaFin = LocalDate.parse(scanner.nextLine(), dateFormatter);

            System.out.print("Meta (kilos): ");
            double meta = Double.parseDouble(scanner.nextLine());

            System.out.print("RUT del cosechador: ");
            String rutCosechador = scanner.nextLine();

            if (control.addCosechadorToCuadrilla(idPlan, idCuadrilla, fechaIni, fechaFin, meta, rutCosechador)) {
                System.out.println("Cosechador asignado exitosamente.");
            } else {
                System.out.println("Error: No se pudo asignar el cosechador.");
            }
        }
    }
    private void listaPropietarios() {
        System.out.println("\n--- LISTADO DE PROPIETARIOS ---");
        String[] propietarios = control.listPropietarios();
        if (propietarios.length == 0) {
            System.out.println("No hay propietarios registrados");
            return;
        }
        System.out.println("Rut, Nombre, Dirección, Email, Dirección Comercial, Nro huertos");
        System.out.println("---------------------------------------------------------------");
        for (String propietario : propietarios) {
            System.out.println(propietario);
        }
    }
    private void listaSupervisores() {
        System.out.println("\n--- LISTADO DE SUPERVISORES ---");
        String[] supervisores = control.listSupervisores();
        if (supervisores.length == 0) {
            System.out.println("No hay supervisores registrados");
            return;
        }
        System.out.println("Rut, Nombre, Dirección, Email, Profesion, Nom Cuadrilla, Kg Pesados, Psjes impagos");
        System.out.println("----------------------------------------------------------------------------------");
        for (String supervisor : supervisores) {
            System.out.println(supervisor);
        }
    }
    private void listaCosechadores() {
        System.out.println("\n--- LISTADO DE COSECHADORES ---");
        String[] cosechadores = control.listCosechadores();
        if (cosechadores.length == 0) {
            System.out.println("No hay propietarios registrados");
            return;
        }
        System.out.println("Rut, Nombre, Dirección, Email, Fecha Nac, Nro Cuadrillas, Monto Impago, Monto Pagado");
        System.out.println("------------------------------------------------------------------------------------");
        for (String cosechador : cosechadores) {
            System.out.println(cosechador);
        }
    }
    private void listaCultivos() {
        System.out.println("\n--- LISTADO DE CULTIVOS ---");
        String[] cultivos = control.listCultivos();

        if (cultivos.length == 0) {
            System.out.println("No hay cultivos registrados en el sistema.");
            return;
        }

        System.out.println("ID, Especie, Variedad, Rendimiento");
        System.out.println("-----------------------------------");
        for (String cultivo : cultivos) {
            System.out.println(cultivo);
        }
    }
    private void listaHuertos() {
        System.out.println("\n--- LISTADO DE HUERTOS ---");
        String[] huertos = control.listHuertos();

        if (huertos.length == 0) {
            System.out.println("No hay huertos registrados en el sistema.");
            return;
        }

        System.out.println("Nombre, Superficie, Ubicación, Propietario, Cant. Cuarteles");
        System.out.println("------------------------------------------------------------");
        for (String huerto : huertos) {
            System.out.println(huerto);
        }
    }

    private void listaPersonas() {
        System.out.println("\n--- LISTADO DE PERSONAS ---");

        // Listar propietarios
        System.out.println("\nPROPIETARIOS:");
        System.out.println("RUT, Nombre, Email, Huerto");
        System.out.println("---------------------------");
        String[] propietarios = control.listPropietarios();
        if (propietarios.length == 0) {
            System.out.println("No hay propietarios registrados.");
        } else {
            for (String propietario : propietarios) {
                System.out.println(propietario);
            }
        }

        // Listar supervisores
        System.out.println("\nSUPERVISORES:");
        System.out.println("RUT, Nombre, Email, Cuadrilla");
        System.out.println("-----------------------------");
        String[] supervisores = control.listSupervisores();
        if (supervisores.length == 0) {
            System.out.println("No hay supervisores registrados.");
        } else {
            for (String supervisor : supervisores) {
                System.out.println(supervisor);
            }
        }

        // Listar cosechadores
        System.out.println("\nCOSECHADORES:");
        System.out.println("RUT, Nombre, Email, Cant. Cuadrillas");
        System.out.println("------------------------------------");
        String[] cosechadores = control.listCosechadores();
        if (cosechadores.length == 0) {
            System.out.println("No hay cosechadores registrados.");
        } else {
            for (String cosechador : cosechadores) {
                System.out.println(cosechador);
            }
        }
    }

    private void listaPlanesDeCosecha() {
        System.out.println("\n--- LISTADO DE PLANES DE COSECHA ---");
        String[] planes = control.listPlanesCosecha();

        if (planes.length == 0) {
            System.out.println("No hay planes de cosecha registrados en el sistema.");
            return;
        }

        System.out.println("ID, Nombre, Fecha Inicio, Fecha Fin, Meta, Precio Base, Huerto, Cant. Cuadrillas");
        System.out.println("---------------------------------------------------------------------------------");
        for (String plan : planes) {
            System.out.println(plan);
        }
    }
}
