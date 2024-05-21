package aplicacion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dominio.Incidencia;


/**
 * Clase que representa la lógica del sistema para gestionar incidencias.
 */
public class Logica {
    private List<Incidencia> incidencias; // Lista de incidencia
    private LocalDateTime fechaRegistroActual;
    private int contadorDiario;
    /**
     * Constructor de la clase Logica.
     */
    public Logica() {
        this.incidencias = new ArrayList<>(); // Inicializa la lista de incidencias
        this.fechaRegistroActual = null;
        this.contadorDiario = 1;
    }

    Scanner scanner = new Scanner(System.in);

    /**
     * Método para registrar una nueva incidencia.
     * 
     * @param puesto   El número del puesto asociado a la incidencia.
     * @param problema La descripción del problema de la incidencia.
     */
    public void registrarIncidencia(int puesto, String problema) {
        // Obtiene la fecha actual
        LocalDateTime fechaActual = LocalDateTime.now();

        // Verifica si es un nuevo día para reiniciar el contador diario
        if (fechaRegistroActual == null || !fechaActual.toLocalDate().isEqual(fechaRegistroActual.toLocalDate())) {
            fechaRegistroActual = fechaActual;
            contadorDiario = 1; // Reiniciar el contador diario si es un nuevo día
        } else {
            contadorDiario++; // Incrementar el contador si es el mismo día
        }

        // Formatea la fecha y hora en el formato especificado
        DateTimeFormatter formatterFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
        String codigo = String.format("%s-%d", fechaActual.format(formatterFechaHora), contadorDiario);

        // Crea una nueva instancia de Incidencia con los datos proporcionados y la
        // agrega a la lista de incidencias
        Incidencia incidencia = new Incidencia(codigo, "pendiente", puesto, problema, fechaActual);
        incidencias.add(incidencia);

        // Muestra un mensaje de confirmación
        System.out.println("Incidencia registrada con éxito.");
    }

    /**
     * Método para buscar una incidencia por su código.
     * 
     * @param codigoBuscado El código de la incidencia a buscar.
     * @return La incidencia encontrada, o null si no se encuentra ninguna.
     */
    public Incidencia buscarIncidencia(String codigoBuscado) {
        // Verifica si el código tiene el formato correcto antes de buscar la incidencia
        if (!codigoBuscado.matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}-\\d+$")) {
            // No lanza una excepción, sino que devuelve null para indicar un formato incorrecto
            return null;
        }

        // Itera sobre la lista de incidencias para buscar la incidencia con el código especificado
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getCodigo().equals(codigoBuscado)) {
                return incidencia; // Devuelve la incidencia si se encuentra
            }
        }

        return null; // Devuelve null si no se encuentra la incidencia
    }

    /**
     * Modifica una incidencia existente.
     * 
     * @param codigoModificar El código de la incidencia a modificar.
     * @param nuevoPuesto      El nuevo número de puesto asociado a la incidencia.
     * @param nuevoProblema    La nueva descripción del problema de la incidencia.
     */
    public void modificarIncidencia(String codigoModificar, int nuevoPuesto, String nuevoProblema) {
        // Verifica si el formato del código es correcto
        if (!codigoModificar.matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}-\\d+$")) {
            System.out.println("El formato del código no es correcto.");
            return;
        }

        // Bucle para buscar la incidencia correspondiente al código ingresado
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getCodigo().equals(codigoModificar)) {
                // Verifica si la incidencia no está resuelta para permitir la modificación
                if (!incidencia.getEstado().equals("resuelta")) {
                    // Modifica la incidencia con los nuevos datos
                    incidencia.setPuesto(nuevoPuesto);
                    incidencia.setProblema(nuevoProblema);
                    System.out.println("Incidencia modificada con éxito.");
                } else {
                    // Muestra un mensaje indicando que no se puede modificar una incidencia
                    // resuelta
                    System.out.println("No se puede modificar una incidencia resuelta.");
                }
                return; // Sale del método una vez que se realiza la modificación o se muestra el
                        // mensaje de error
            }
        }
        // Muestra un mensaje indicando que la incidencia no se encontró
        System.out.println("Incidencia no encontrada.");
    }

    /**
     * Elimina una incidencia existente.
     * 
     * @param codigoEliminar El código de la incidencia a eliminar.
     * @param scanner         El objeto Scanner para recibir entrada del usuario.
     */
    public void eliminarIncidencia(String codigoEliminar, Scanner scanner) {
        // Verifica si el formato del código proporcionado es correcto
        if (!codigoEliminar.matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}-\\d+$")) {
            System.out.println("Formato incorrecto, formato correcto: XX/XX/XXXX-XX:XX-X");
            return;
        }

        for (Incidencia incidencia : incidencias) {
            if (incidencia != null && incidencia.getCodigo().equals(codigoEliminar)) {
                if (!incidencia.getEstado().equals("eliminada")) {
                    LocalDateTime fechaEliminacion = LocalDateTime.now();
                    incidencia.setEstado("eliminada");
                    incidencia.setFechaEliminacion(fechaEliminacion);

                    System.out.println("Ingrese la causa de la eliminación:");
                    String causaEliminacion = scanner.nextLine();
                    incidencia.setCausaEliminacion(causaEliminacion);
                    System.out.println("Incidencia eliminada con éxito.");
                } else {
                    System.out.println("La incidencia ya está eliminada.");
                }
                return;
            }
        }
        System.out.println("Incidencia no encontrada.");
    }
    

    /**
     * Resuelve una incidencia pendiente.
     * 
     * @param codigoResolver El código de la incidencia a resolver.
     * @param scanner         El objeto Scanner para recibir entrada del usuario.
     */
    public void resolverIncidencia(String codigoResolver, Scanner scanner) {
        // Verifica si el formato del código proporcionado es correcto
        if (!codigoResolver.matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}-\\d+$")) {
            System.out.println("Formato incorrecto, formato correcto: XX/XX/XXXX-XX:XX-X");
            return;
        }

        for (Incidencia incidencia : incidencias) {
            if (incidencia != null && incidencia.getCodigo().equals(codigoResolver)) {
                if (!incidencia.getEstado().equals("resuelta")) {
                    LocalDateTime fechaResolucion = LocalDateTime.now();
                    incidencia.setEstado("resuelta");
                    incidencia.setFechaResolucion(fechaResolucion);

                    System.out.println("Ingrese la descripción de la resolución:");
                    String resolucion = scanner.nextLine();
                    incidencia.setResolucion(resolucion);
                    System.out.println("Incidencia resuelta con éxito.");
                } else {
                    System.out.println("La incidencia ya está resuelta.");
                }
                return;
            }
        }
        System.out.println("Incidencia no encontrada.");
    }
    

    /**
     * Modifica la descripción de la resolución de una incidencia ya resuelta.
     * 
     * @param codigoModificar El código de la incidencia a modificar.
     * @param scanner          El objeto Scanner para recibir entrada del usuario.
     */
    public void modificarIncidenciaResuelta(String codigoModificar, Scanner scanner) {
        // Verifica si el código tiene el formato correcto antes de buscar la incidencia
        if (!codigoModificar.matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}-\\d+$")) {
            System.out.println("Formato incorrecto, formato correcto: XX/XX/XXXX-XX:XX-X");
            return;
        }

        for (Incidencia incidencia : incidencias) {
            if (incidencia != null && incidencia.getCodigo().equals(codigoModificar)) {
                if (incidencia.getEstado().equals("resuelta")) {
                    System.out.println("Ingrese la nueva descripción de la resolución:");
                    String nuevaResolucion = scanner.nextLine();
                    incidencia.setResolucion(nuevaResolucion);
                    System.out.println("Incidencia resuelta modificada con éxito.");
                } else {
                    System.out.println("La incidencia no está resuelta.");
                }
                return;
            }
        }
        System.out.println("Incidencia no encontrada.");
    }

    
    /**
     * Devuelve una incidencia resuelta a estado pendiente.
     * 
     * @param codigoDevolver El código de la incidencia a devolver.
     */
    public void devolverIncidenciaResuelta(String codigoDevolver) {
        // Verifica el formato del código antes de continuar
        if (!codigoDevolver.matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}-\\d+$")) {
            System.out.println("Formato incorrecto, formato correcto: XX/XX/XXXX-XX:XX-X");
            return;
        }

        for (Incidencia incidencia : incidencias) {
            if (incidencia != null && incidencia.getCodigo().equals(codigoDevolver)) {
                if (incidencia.getEstado().equals("resuelta")) {
                    incidencia.setEstado("pendiente");
                    incidencia.setFechaResolucion(null);
                    incidencia.setResolucion(null);
                    System.out.println("Incidencia devuelta a pendiente con éxito.");
                } else {
                    System.out.println("La incidencia no está resuelta.");
                }
                return;
            }
        }
        System.out.println("Incidencia no encontrada.");
    }


    /**
     * Obtiene todas las incidencias pendientes.
     * 
     * @return Lista de incidencias pendientes.
     */
    public List<Incidencia> obtenerIncidenciasPendientes() {
        List<Incidencia> incidenciasPendientes = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia != null && incidencia.getEstado().equals("pendiente")) {
                incidenciasPendientes.add(incidencia);
            }
        }

        return incidenciasPendientes;
    }


    /**
     * Obtiene todas las incidencias resueltas.
     * 
     * @return Lista de incidencias resueltas.
     */
    public List<Incidencia> obtenerIncidenciasResueltas() {
        List<Incidencia> incidenciasResueltas = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia != null && incidencia.getEstado().equals("resuelta")) {
                incidenciasResueltas.add(incidencia);
            }
        }

        return incidenciasResueltas;
    }


    /**
     * Obtiene todas las incidencias eliminadas.
     * 
     * @return Lista de incidencias eliminadas.
     */
    public List<Incidencia> obtenerIncidenciasEliminadas() {
        List<Incidencia> incidenciasEliminadas = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia != null && incidencia.getEstado().equals("eliminada")) {
                incidenciasEliminadas.add(incidencia);
            }
        }

        return incidenciasEliminadas;
    }


}
