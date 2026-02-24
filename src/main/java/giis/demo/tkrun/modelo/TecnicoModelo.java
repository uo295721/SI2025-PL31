package giis.demo.tkrun.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.Jdbc;

public class TecnicoModelo {
	
	// Método  para que el operador vea los técnicos
    public List<TecnicoDTO> obtenerTodosLosTecnicos() {
        List<TecnicoDTO> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, email FROM Usuario WHERE rol = 'Técnico' ORDER BY nombre";

        try (Connection con = Jdbc.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                // Usamos el DTO para guardar los datos de cada fila
                lista.add(new TecnicoDTO(
                		rs.getString("id_tecnico"),
                		rs.getString("nombre"),
                		rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
	

	// Muestra las incidencias asignadas a un técnico en concreto
	public List<IncidenciaDTO> getIncidenciasAsignadas(String idTecnico){
		
		List<IncidenciaDTO> lista = new ArrayList<>();
		
		// Buscamos únicamente las incidencias asignadas a éste técnico
		String sql = "SELECT id_incidencia, tipo, descripcion, estado "
				   + "FROM Incidencia WHERE id_tecnico = ? AND estado = 'Asignada'";
		
		try (Connection con = Jdbc.getConnection();
			PreparedStatement pst = con.prepareStatement(sql)){
				pst.setString(1, idTecnico);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()) {
					IncidenciaDTO incidencia = new IncidenciaDTO();
					
					incidencia.setIdIncidencia(rs.getInt("id_incidencia"));
					incidencia.setTitulo(rs.getString("tipo"));
					incidencia.setDescripcionCiudadano(rs.getString("descripcion"));
					incidencia.setEstado(rs.getString("estado"));
					
					lista.add(incidencia);
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Error al cargar las incidencias asginadas",e);
			}
		
		return lista;
		
	}
	
	// Guarda la planificación (horas y trabajo) y cambia el estado
	public void planificarIncidencia(int idIncidencia, int horas, String trabajos, String emailTecnico) {
		
		// Actualizamos la incidencia
		String sqlUpdate = "UPDATE Incidencia SET horas_estimadas = ?, descripcion_trabajos = ?,"
						 + "estado = 'En curso' WHERE id_incidencia = ?";
		
		String sqlHistorial = "INSERT into Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'En curso', datetime('now','localtime'), 'Planificación de trabajos por el técnico')";
		
		Connection con = null;
		try {
			
			con = Jdbc.getConnection();
			con.setAutoCommit(false);
			
			// Ejecutamos, en primer lugar, la actualización de la tabla Incidencia
			try(PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate)){
				pstUpdate.setInt(1, horas);
				pstUpdate.setString(2, trabajos);
				pstUpdate.setInt(3, idIncidencia);
				pstUpdate.executeUpdate();
			}
			
			// Ejecutamos la inserción de la tabla Historial
			try(PreparedStatement pstHistorial = con.prepareStatement(sqlHistorial)){
				pstHistorial.setInt(1, idIncidencia);
				pstHistorial.setString(2, emailTecnico);
				pstHistorial.executeUpdate();
			}
			
			// Confirmamos los dos cambios a la vez en la Base de Datos
			con.commit();
			
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new RuntimeException("Error al planificar la incidencia y actualizar el historial",e);
			
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
