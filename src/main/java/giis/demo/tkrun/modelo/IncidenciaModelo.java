package giis.demo.tkrun.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.Jdbc;

public class IncidenciaModelo {

	// Vamos a obtener todas las incidencias con estado "Validada" ordenadas por
	// fecha, en este caso las antiguas van primero como marca la historia de usuario

	public List<IncidenciaDTO> getIncidenciasValidadas() {
		List<IncidenciaDTO> lista = new ArrayList<>();

		String sql = "SELECT id_incidencia, tipo, descripcion, fecha, estado FROM Incidencia "
	               + "WHERE estado = 'Validada' ORDER BY fecha ASC";

		try (Connection conn = Jdbc.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				// Usamos el constructor de IncidenciaDTO 
				lista.add(new IncidenciaDTO(rs.getInt("id_incidencia"),
						rs.getString("tipo"),
						rs.getString("descripcion"),
						rs.getString("fecha"),
						rs.getString("estado")));
			}
		} catch (SQLException e) {
			System.err.println("Error obteniendo incidencias validadas: " + e.getMessage());
		}
		return lista;
	}

	// Asignamos técnico a la incidencia, también cambiamos el estado y el historial

	public boolean asignarTecnicoIncidencia(int idIncidencia, String idTecnico, String emailOperador) {
		
		String sqlUpdate = "UPDATE Incidencia SET id_tecnico = ?, estado = 'Asignada' WHERE id_incidencia = ?";
		
		String sqlHistorial = "INSERT INTO Historial (id_incidencia, estado_nuevo, fecha_modificacion, comentario) VALUES (?, 'Asignada', CURRENT_TIMESTAMP, ?)";

		try (Connection conn = Jdbc.getConnection()) {
			// Desactivamos el autocommit
			conn.setAutoCommit(false);

			try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
					PreparedStatement psHist = conn.prepareStatement(sqlHistorial)) {

				// Ejecutamos Update
				psUpdate.setString(1, idTecnico);
				psUpdate.setInt(2, idIncidencia);
				psUpdate.executeUpdate();

				// Ejecutamos Historial con el correo electrónico
				psHist.setInt(1, idIncidencia);
				psHist.setString(2, "Asignada por: "+ emailOperador);
				psHist.executeUpdate();

				conn.commit(); // Confirmamos los dos cambios
				return true;

			} catch (SQLException e) {
				conn.rollback(); // Si falla uno cancelamos los dos
				System.err.println("Error en la asignación: " + e.getMessage());
				return false;
			}
		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e.getMessage());
			return false;
		}
	}

	//  Obtener lista de nombres de técnicos para el operador
	public List<TecnicoDTO> obtenerListaTecnicos() {
		List<TecnicoDTO> listaTecnicos = new ArrayList<>();
		// Creamos la consulta sql correspondiente
		String sql = "SELECT id_usuario, nombre, email FROM Usuario WHERE rol = 'Tecnico' ORDER BY nombre";

		try (Connection conn = Jdbc.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				// Ahora usamos TecnicoDTO
				listaTecnicos.add(new TecnicoDTO(
						rs.getString("id_usuario"),
						rs.getString("nombre"),
						rs.getString("email")));
			}
		} catch (SQLException e) {
			System.err.println("Error al obtener técnicos: " + e.getMessage());
		}
		return listaTecnicos;
	}
	
	public List<IncidenciaDTO> incidenciasRegistradasCiudadano(String idCiudadano){
		
		List<IncidenciaDTO> listaIncidencias = new ArrayList<>();
		
		// Creamos la consulta que nos permita extraer las incidencias asignadas a un ciudadano en específico
		String sql = "SELECT id_incidencia, tipo, descripcion, fecha, estado FROM Incidencia "
	               + "WHERE id_ciudadano = ? ORDER BY fecha DESC";
		
		try (Connection con = Jdbc.getConnection();
				PreparedStatement  psStmt = con.prepareStatement(sql)){
			
			psStmt.setString(1, idCiudadano);
			
			try(ResultSet rs = psStmt.executeQuery()){
				
				while(rs.next()) {
					listaIncidencias.add(new IncidenciaDTO(rs.getInt("id_incidencia"),
							rs.getString("tipo"),
							rs.getString("descripcion"),
							rs.getString("fecha"),
							rs.getString("estado")));
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Error al obtener las incidencias del ciudadano: "+e.getMessage());
		}
		
		return listaIncidencias;
		
	}
	
	public boolean insertarIncidencia(String tipo, String descripcion, String localizacion, String idCiudadano) {
	    
	    String sql = "INSERT INTO Incidencia (estado, descripcion, id_ciudadano, localización, tipo, fecha) "
	               + "VALUES ('Nueva', ?, ?, ?, ?, datetime('now', 'localtime'))";

	    try (Connection conn = util.Jdbc.getConnection();
	         PreparedStatement pst = conn.prepareStatement(sql)) {

	        pst.setString(1, descripcion);
	        pst.setString(2, idCiudadano);
	        pst.setString(3, localizacion);
	        pst.setString(4, tipo);

	        int filas = pst.executeUpdate();
	        
	        //Despues de todo esto, si se insertó correctamente devuelvolvemos true
	        return (filas > 0);

	    } catch (SQLException e) {
	        System.err.println("Error al insertar incidencia: " + e.getMessage());
	        return false;
	    }
	}

}
