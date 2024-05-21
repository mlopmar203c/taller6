package dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa una incidencia en el sistema de control de incidencias.
 * @autor Maria
 */
public class Incidencia implements Comparable<Incidencia> {
    private String codigo;
    private String estado;
    private int puesto;
    private String problema;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaResolucion;
    private String resolucion;
    private LocalDateTime fechaEliminacion;
    private String causaEliminacion;

    /**
     * Constructor para devolver listado de incidencias
     * @param causaEliminacion2 
     * @param fechaEliminacion2 
     * @param resolucion2 
     * @param fechaResolucion2 
     * @param fechaRegistro2 
     * @param estado2 
     * @param puesto2 
     * @param problema2 
     * @param codigo2 
     */
    public Incidencia(String codigo2, String problema2, int puesto2, String estado2, LocalDateTime fechaRegistro2, LocalDateTime fechaResolucion2, String resolucion2, LocalDateTime fechaEliminacion2, String causaEliminacion2) {
    }

    /**
     * Constructor para las búsquedas por código
     * @param codigo
     * @param fechaRegistro2 
     * @param estado2 
     * @param puesto2 
     * @param problema2 
     */
    public Incidencia(String codigo, String problema2, int puesto2, String estado2, LocalDate fechaRegistro2) {
        this.codigo = codigo;
    }

    /**
     * Constructor
     * @param codigo
     * @param estado
     * @param puesto
     * @param problema
     * @param fechaRegistro
     */
    public Incidencia(String codigo, String estado, int puesto, String problema, LocalDateTime fechaRegistro) {
        this.codigo = codigo;
        this.estado = estado;
        this.puesto = puesto;
        this.problema = problema;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Ordenación natural por código de incidencia
     */
    @Override
    public int compareTo(Incidencia o) {
        return this.codigo.compareTo(o.codigo);
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "Incidencia [codigo=" + codigo + ", estado=" + estado + ", puesto=" + puesto + ", problema=" + problema
                + ", fechaRegistro=" + fechaRegistro + ", fechaResolucion=" + fechaResolucion + ", resolucion="
                + resolucion + ", fechaEliminacion=" + fechaEliminacion + ", causaEliminacion=" + causaEliminacion
                + "]";
    }

    /**
     * hashCode y equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo, estado, puesto, problema, fechaRegistro, fechaResolucion, resolucion, fechaEliminacion, causaEliminacion);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Incidencia other = (Incidencia) obj;
        return puesto == other.puesto && Objects.equals(codigo, other.codigo) && Objects.equals(estado, other.estado)
                && Objects.equals(problema, other.problema) && Objects.equals(fechaRegistro, other.fechaRegistro)
                && Objects.equals(fechaResolucion, other.fechaResolucion) && Objects.equals(resolucion, other.resolucion)
                && Objects.equals(fechaEliminacion, other.fechaEliminacion) && Objects.equals(causaEliminacion, other.causaEliminacion);
    }

	// Getters y setters
	
	/**
	 * Obtiene el código de la incidencia
	 * @return El código de la incidencia.
	 */
	
	public String getCodigo() {
		return codigo;
	}
	
	/**
	 * Establece el código de la incidencia.
	 * @param codigo El código de la incidencia.
	 */

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el estado de la incidencia.
	 * @return
	 */
	
	public String getEstado() {
		return estado;
	}
	
	/**
	 * Establece el estado de la incidencia.
	 * @param estado El estado de la incidencia.
	 */

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * Obtiene el número del puesto.
	 * @return El número del puesto.
	 */

	public int getPuesto() {
		return puesto;
	}
	
	/**
	 * Establece el número del puesto.
	 * @param puesto El número del puesto.
	 */

	public void setPuesto(int puesto) {
		this.puesto = puesto;
	}
	
	/**
	 * Obtiene el problema indicado en la incidencia.
	 * @return Problema indicado en la incidencia.
	 */

	public String getProblema() {
		return problema;
	}
	
	/**
	 * Establece el problema indicado en la incidencia.
	 * @param problema El problema indicado en la incidencia.
	 */

	public void setProblema(String problema) {
		this.problema = problema;
	}
	
	/**
	 * Obtiene la fecha y hora de registro de la incidencia.
	 * @return La fecha y hora de registro de la incidencia.
	 */

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}
	
	/**
	 * Establece la fecha y hora de registro de la incidencia.
	 * @param fechaRegistro La fecha y hora de registro de la incidencia.
	 */

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	/**
	 * Obtiene la fecha y hora de resolución de la incidencia.
	 * @return La fecha y hora de resolución de la incidencia.
	 */

	public LocalDateTime getFechaResolucion() {
		return fechaResolucion;
	}
	
	/**
	 * Establece la fecha y hora de resolución de la incidencia.
	 * @param fechaResolucion La fecha y hora de resolución de la incidencia.
	 */

	public void setFechaResolucion(LocalDateTime fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	/**
	 * Obtiene la descripción de la resolución de la incidencia.
	 * @return La descripción de la resolución de la incidencia.
	 */
	
	public String getResolucion() {
		return resolucion;
	}
	
	/**
	 * Establece la descripción de la resolución de la incidencia.
	 * @param resolucion
	 */

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	
	/**
	 * Obtiene la fecha y hora de eliminación de la incidencia.
	 * @return La fecha y hora de eliminación de la incidencia.
	 */

	public LocalDateTime getFechaEliminacion() {
		return fechaEliminacion;
	}

	/**
	 * Establece la fecha y hora de eliminación de la incidencia.
	 * @param fechaEliminacion La fecha y hora de eliminación de la incidencia.
	 */
	
	public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
	
	/**
	 * Obtiene la causa de eliminación de la incidencia.
	 * @return La causa de eliminación de la incidencia.
	 */

	public String getCausaEliminacion() {
		return causaEliminacion;
	}
	
	/**
	 * Establece la causa de eliminación de la incidencia.
	 * @param causaEliminacion La causa de eliminación de la incidencia.
	 */

	public void setCausaEliminacion(String causaEliminacion) {
		this.causaEliminacion = causaEliminacion;
	}
}