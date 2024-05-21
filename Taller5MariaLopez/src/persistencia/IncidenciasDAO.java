package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dominio.Incidencia;

public class IncidenciasDAO {
	
	private Connection conexion;
	private final String USUARIO="pepe";
	private final String PASSWORD="12345";
	private final String MAQUINA="localhost";
	private final String BD="incidencia";
	
	public IncidenciasDAO(){
		conexion = conectar();
	}
	
	/**
	 * Crea una conexión con el SGBD y la devuelve
	 * @return
	 */
	private Connection conectar() {
		Connection con = null;
		String url = "jdbc:mysql://"+MAQUINA+"/"+BD;
		try {
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println("Conectado a la base de datos");
		}catch(SQLException ex){
			System.out.println("Error al conectar a la base de datos");
			ex.printStackTrace();
		}
		return con;
	}
	
	/**
	 * Cierra la conexion
	 */
	public void cerrarConexion(){
		try {
			this.conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que busca una incidencia con el codigo pasado como argumento
	 * @param codigo
	 * @return
	 */
		public Incidencia buscaIncidencia(String codigo) {
			Incidencia incidencia = null;
			String sql = "SELECT * FROM incidencia WHERE codigo = ?";
			try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
				sentencia.setString(1, codigo);
				try (ResultSet rs = sentencia.executeQuery()) {
					if (rs.next()) {
						String problema = rs.getString("problema");
						int puesto = rs.getInt("puesto");
						String estado = rs.getString("estado");
						LocalDate fechaRegistro = rs.getDate("fecha_registro").toLocalDate();
						// Recuperar otros campos si es necesario

						incidencia = new Incidencia(codigo, problema, puesto, estado, fechaRegistro);
					}
				} catch (SQLException ex) {
					System.out.println("Error en la consulta de incidencia por codigo");
					ex.printStackTrace();
				}
			} catch (SQLException ex) {
				System.out.println("Error en la consulta de incidencia por codigo");
				ex.printStackTrace();
			}
			return incidencia;
		}
	
	
		/**
		 * Método que inserta en la base de datos una nueva incidencia con los datos que se le aporta en consulta parametrizada
		 * @param incidencia
		 */
	    public void create(Incidencia incidencia) {
	        if (incidencia != null) {
	            String sql = "INSERT INTO incidencia (codigo, problema, puesto, estado, fecha_registro) VALUES (?, ?, ?, 'pendiente', ?)";
	            try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
	                sentencia.setString(1, incidencia.getCodigo());
	                sentencia.setString(2, incidencia.getProblema());
	                sentencia.setInt(3, incidencia.getPuesto());
	                sentencia.setTimestamp(4, Timestamp.valueOf(incidencia.getFechaRegistro()));
	                sentencia.executeUpdate();
	                System.out.println("Incidencia insertada correctamente");
	            } catch (SQLException ex) {
	                System.out.println("Error al insertar una nueva incidencia");
	                ex.printStackTrace();
	            }
	        } else {
	            System.out.println("La incidencia no puede ser nula");
	        }
	    }
	
	/**
	 * Método que elimina de la base de datos una incidencia cuyos datos se le aporta en consulta parametrizada
	 * @param codigo
	 */
	public void delete(String codigo) {
		String sql = "DELETE FROM incidencia WHERE codigo = ?";
		try(PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, codigo);
			int filasAfectadas = sentencia.executeUpdate();
	        if (filasAfectadas > 0) {
	            System.out.println("Incidencia eliminada correctamente");
	        } else {
	            System.out.println("No se encontró ninguna incidencia con el código proporcionado");
	        }
		}catch(SQLException ex) {
			System.out.println("Error al eliminar incidencia");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Método que actualiza los datos de la incidencia que se indica
	 * @param incidencia
	 */
	public void update(Incidencia incidencia) {
	    if (incidencia != null) {
	        String sql = "UPDATE incidencia SET problema = ?, puesto = ?, estado = ?, fechaRegistro = ? WHERE codigo = ?";
	        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
	            sentencia.setString(1, incidencia.getProblema());
	            sentencia.setInt(2, incidencia.getPuesto());
	            sentencia.setString(3, incidencia.getEstado());
	            sentencia.setTimestamp(4, java.sql.Timestamp.valueOf(incidencia.getFechaRegistro()));
	            sentencia.setString(5, incidencia.getCodigo());
	            int filasAfectadas = sentencia.executeUpdate();
	            if (filasAfectadas > 0) {
	                System.out.println("Incidencia actualizada correctamente");
	            } else {
	                System.out.println("No se encontró ninguna incidencia con el código proporcionado");
	            }
	        } catch (SQLException ex) {
	            System.out.println("Error al actualizar los datos de la incidencia");
	            ex.printStackTrace();
	        }
	    }
	}
	
	
	 /**
	  * Método que devuelve listado de incidencias
	  * @return
	  */
    public List<Incidencia> listarIncidencias() {
        List<Incidencia> listadoIncidencias = new ArrayList<>();
        String sql = "SELECT * FROM incidencia";
        try (Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql)) {

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String problema = rs.getString("problema");
                int puesto = rs.getInt("puesto");
                String estado = rs.getString("estado");
                LocalDateTime fechaRegistro = rs.getTimestamp("fechaRegistro").toLocalDateTime();
                LocalDateTime fechaResolucion = rs.getTimestamp("fechaResolucion") != null ? rs.getTimestamp("fechaResolucion").toLocalDateTime() : null;
                String resolucion = rs.getString("resolucion");
                LocalDateTime fechaEliminacion = rs.getTimestamp("fechaEliminacion") != null ? rs.getTimestamp("fechaEliminacion").toLocalDateTime() : null;
                String causaEliminacion = rs.getString("causaEliminacion");

                Incidencia incidencia = new Incidencia(codigo, problema, puesto, estado, fechaRegistro, fechaResolucion, resolucion, fechaEliminacion, causaEliminacion);
                listadoIncidencias.add(incidencia);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar las incidencias");
            ex.printStackTrace();
        }

        return listadoIncidencias;
    }
}