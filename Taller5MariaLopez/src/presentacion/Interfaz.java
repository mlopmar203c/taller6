package presentacion;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import aplicacion.Logica;
import dominio.Incidencia;

/**
 * Clase principal que contiene el método main para ejecutar el programa de gestión de incidencias.
 * Esta clase proporciona una interfaz de usuario para interactuar con el sistema de gestión de incidencias.
 * @author Maria
 */
public class Interfaz {
    private Scanner scanner;
    private Logica logica;

    /**
     * Constructor de la clase Interfaz.
     * @param logica El objeto de la clase Logica que proporciona la lógica de negocio.
     */
    public Interfaz(Logica logica) {
        this.scanner = new Scanner(System.in);
        this.logica = logica;
    }

    /**
     * Método estático que imprime un mensaje de bienvenida al usuario.
     */
    public static void imprimirBienvenida() {
        System.out.println("*******************************");
        System.out.println("REGISTRO DE INCIDENCIAS");
        System.out.println("*******************************");
        System.out.println(
                "\nBienvenido/a a la aplicación de registro de incidencias.\nUse las opciones del menú para trabajar.");
    }

    /**
     * Método estático que espera hasta que se pulse la tecla Enter.
     */
	public static void esperaIntro() {
        System.out.println("\nPulsa Enter para continuar ...");
        new Scanner(System.in).nextLine();
    }

    /**
     * Método estático que salta un número de líneas especificado.
     * @param numLineas El número de líneas que se deben saltar.
     */
    public static void saltarLineas(int numLineas) {
        for (int i = 1; i <= numLineas; i++) {
            System.out.println();
        }
    }

    /**
     * Método estático que muestra el menú principal de la aplicación y gestiona la interacción con el usuario.
     * @param interfaz El objeto de la clase Interfaz que se utilizará para llamar a los métodos correspondientes.
     */
    public static void mostrarMenu(Interfaz interfaz) {
        Scanner scanner = interfaz.scanner;
        int opcion = 0;

        do {
            esperaIntro();
            saltarLineas(6);
            System.out.println("*******************************");
            System.out.println("SELECCIONA UNA OPCIÓN");
            System.out.println("*******************************");
            System.out.println("1. Registrar incidencia");
            System.out.println("2. Buscar incidencia");
            System.out.println("3. Modificar incidencia");
            System.out.println("4. Eliminar incidencia");
            System.out.println("5. Resolver incidencia");
            System.out.println("6. Modificar incidencia resuelta");
            System.out.println("7. Devolver incidencia resuelta");
            System.out.println("8. Mostrar incidencias pendientes");
            System.out.println("9. Mostrar incidencias resueltas");
            System.out.println("10. Mostrar incidencias eliminadas");
            System.out.println("11. Salir");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                if (opcion < 1 || opcion > 12) {
                    System.out.println("Por favor, introduzca un número del 1 al 11.");
                    continue;
                }

                switch (opcion) {
                    case 1:
                        interfaz.registrarIncidencia();
                        break;
                    case 2:
                        interfaz.buscarIncidencia();
                        break;
                    case 3:
                        interfaz.modificarIncidencia();
                        break;
                    case 4:
                        interfaz.eliminarIncidencia();
                        break;
                    case 5:
                        interfaz.resolverIncidencia();
                        break;
                    case 6:
                        interfaz.modificarIncidenciaResuelta();
                        break;
                    case 7:
                        interfaz.devolverIncidenciaResuelta();
                        break;
                    case 8:
                        interfaz.mostrarIncidenciasPendientes();
                        break;
                    case 9:
                        interfaz.mostrarIncidenciasResueltas();
                        break;
                    case 10:
                        interfaz.mostrarIncidenciasEliminadas();
                        break;
                    case 11:
                        System.out.println("Acaba de salir del programa.");
                        break;
                }
            } else {
                System.out.println("Por favor, introduzca un número del 1 al 11.");
                scanner.next(); // Limpiar el buffer
            }
        } while (opcion != 12);
    }


    /**
     * Método que registra una nueva incidencia.
     */
    public void registrarIncidencia() {
        int puesto;
        while (true) {
            try {
                System.out.println("Ingrese el número de puesto:");
                puesto = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error de formato, introduzca solo números.");
            }
        }

        System.out.println("Describa el problema:");
        String problema = scanner.nextLine();

        // Llamar al método de servicio para registrar la incidencia
        logica.registrarIncidencia(puesto, problema);
    }

    /**
     * Método que busca una incidencia por su código.
     */
    public void buscarIncidencia() {
        String codigoBuscado = obtenerCodigoBuscado();

        // Llamar al método de servicio para buscar la incidencia
        Incidencia incidencia = logica.buscarIncidencia(codigoBuscado);

        // Verifica si se encontró la incidencia
        if (incidencia != null) {
            // Muestra la información de la incidencia
            DateTimeFormatter formatterFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
            System.out.println("Código: " + incidencia.getCodigo());
            System.out.println("Estado: " + incidencia.getEstado());
            System.out.println("Puesto: " + incidencia.getPuesto());
            System.out.println("Problema: " + incidencia.getProblema());
            System.out.println("Fecha Registro: " + incidencia.getFechaRegistro().format(formatterFechaHora));
            System.out.println();
        } else {
            System.out.println("Incidencia no encontrada o formato incorrecto.");
        }
    }

    /**
     * Método para obtener el código de la incidencia a buscar.
     * @return El código de la incidencia a buscar.
     */
    	public String obtenerCodigoBuscado() {
        System.out.println("Ingrese el código de la incidencia a buscar:");
        return scanner.nextLine();
    }
    	
    	/**
    	 * Método que modifica una incidencia
    	 */

    	public void modificarIncidencia() {
        System.out.println("Ingrese el código de la incidencia a modificar (o presione 's' para salir):");
        String codigoModificar = scanner.nextLine();

        // Verifica si se presionó 's' para salir
        if (codigoModificar.equalsIgnoreCase("s")) {
            return;
        }

        System.out.println("Ingrese el nuevo número de puesto:");
        int nuevoPuesto = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.println("Ingrese la nueva descripción del problema:");
        String nuevoProblema = scanner.nextLine();

        // Llamar al método de servicio para modificar la incidencia
        logica.modificarIncidencia(codigoModificar, nuevoPuesto, nuevoProblema);
    }

    	/**
    	 * Método que elimina una incidencia
    	 */
    public void eliminarIncidencia() {
        System.out.println("Ingrese el código de la incidencia a eliminar:");
        String codigoEliminar = scanner.nextLine();

        // Llama al método de la capa de aplicación para eliminar la incidencia
        logica.eliminarIncidencia(codigoEliminar, scanner);
    }
    
    /**
     * Método que resuelve una incidencia
     */

    public void resolverIncidencia() {
        System.out.println("Ingrese el código de la incidencia a resolver:");
        String codigoResolver = scanner.nextLine();

        // Llama al método de la capa de aplicación para resolver la incidencia
        logica.resolverIncidencia(codigoResolver, scanner);
    }
    
    /**
     * Método que modifica una incidencia resuelta
     */

    public void modificarIncidenciaResuelta() {
        System.out.println("Ingrese el código de la incidencia resuelta a modificar:");
        String codigoModificar = scanner.nextLine();

        // Llama al método de la capa de aplicación para modificar la incidencia resuelta
        logica.modificarIncidenciaResuelta(codigoModificar, scanner);
    }

    /**
     * Método que devuelve una incidencia resuelta
     */
    public void devolverIncidenciaResuelta() {
        System.out.println("Ingrese el código de la incidencia resuelta a devolver:");
        String codigoDevolver = scanner.nextLine();

        // Llama al método de la capa de aplicación para devolver la incidencia resuelta
        logica.devolverIncidenciaResuelta(codigoDevolver);
    }

    /**
     * Método que muestra las incidencia pendientes
     */
    public void mostrarIncidenciasPendientes() {
        List<Incidencia> incidenciasPendientes = logica.obtenerIncidenciasPendientes();
        if (!incidenciasPendientes.isEmpty()) {
            DateTimeFormatter formatterFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
            for (Incidencia incidencia : incidenciasPendientes) {
                System.out.println("Código: " + incidencia.getCodigo());
                System.out.println("Estado: " + incidencia.getEstado());
                System.out.println("Puesto: " + incidencia.getPuesto());
                System.out.println("Problema: " + incidencia.getProblema());
                System.out.println("Fecha Registro: " + incidencia.getFechaRegistro().format(formatterFechaHora));
                System.out.println();
            }
        } else {
            System.out.println("No hay incidencias pendientes.");
        }
    }
    
    /**
     * Método que muestra las incidencias resueltas
     */

    public void mostrarIncidenciasResueltas() {
        List<Incidencia> incidenciasResueltas = logica.obtenerIncidenciasResueltas();
        if (!incidenciasResueltas.isEmpty()) {
            DateTimeFormatter formatterFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
            for (Incidencia incidencia : incidenciasResueltas) {
                System.out.println("Código: " + incidencia.getCodigo());
                System.out.println("Estado: " + incidencia.getEstado());
                System.out.println("Puesto: " + incidencia.getPuesto());
                System.out.println("Problema: " + incidencia.getProblema());
                System.out.println("Fecha Registro: " + incidencia.getFechaRegistro().format(formatterFechaHora));
                System.out.println("Fecha Resolución: " + incidencia.getFechaResolucion().format(formatterFechaHora));
                System.out.println("Resolución: " + incidencia.getResolucion());
                System.out.println();
            }
        } else {
            System.out.println("No hay incidencias resueltas.");
        }
    }

    
    /**
     * Método que muestra las incidencias eliminadas
     */

    public void mostrarIncidenciasEliminadas() {
        List<Incidencia> incidenciasEliminadas = logica.obtenerIncidenciasEliminadas();
        if (!incidenciasEliminadas.isEmpty()) {
            DateTimeFormatter formatterFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
            for (Incidencia incidencia : incidenciasEliminadas) {
                System.out.println("Código: " + incidencia.getCodigo());
                System.out.println("Estado: " + incidencia.getEstado());
                System.out.println("Puesto: " + incidencia.getPuesto());
                System.out.println("Problema: " + incidencia.getProblema());
                System.out.println("Fecha Registro: " + incidencia.getFechaRegistro().format(formatterFechaHora));
                System.out.println("Fecha Eliminación: " + incidencia.getFechaEliminacion().format(formatterFechaHora));
                System.out.println("Causa Eliminación: " + incidencia.getCausaEliminacion());
                System.out.println();
            }
        } else {
            System.out.println("No hay incidencias eliminadas.");
        }
    }
}
