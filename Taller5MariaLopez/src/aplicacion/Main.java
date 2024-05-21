package aplicacion;

import presentacion.Interfaz;

/**
 * Clase principal que inicia la aplicación.
 */
public class Main {
    /**
     * Método principal que inicia la aplicación.
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        // Se crea una instancia de la interfaz de usuario, pasando la lógica como parámetro
        Interfaz interfaz = new Interfaz(new Logica());

        // Se imprime el mensaje de bienvenida
        Interfaz.imprimirBienvenida();

        // Se muestra el menú principal de la interfaz
        Interfaz.mostrarMenu(interfaz);
    }
}
