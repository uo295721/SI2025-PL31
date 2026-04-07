package modelo;

import java.util.List;
import util.Database;
import modelo.IncidenciaDTO;
import java.util.ArrayList;

public class IncidenciaModelo {

	private Database db = new Database();

	public IncidenciaModelo() {
	}

	/**
	 * HU Sprint 3: REAPERTURA DE INCIDENCIA
	 * Devuelve la incidencia al estado 'Nueva' y registra el motivo en el Historial.
	 */
	public boolean reabrirIncidencia(int idIncidencia, String idUsuario, String motivo) {
		// 1. Actualizamos la incidencia: vuelve a 'Nueva' y quitamos fecha de resolución
		String sqlU = "UPDATE Incidencia SET estado = 'Nueva', fecha_resolucion = NULL WHERE id_incidencia = ?";
		
		// 2. Insertamos en el historial el motivo de la reapertura
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'Nueva', datetime('now','localtime'), ?)";
		
		try {
			db.executeUpdate(sqlU, idIncidencia);
			db.executeUpdate(sqlH, idIncidencia, idUsuario, "REAPERTURA: " + motivo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// --- MÉTODOS EXISTENTES DEL PROYECTO ---

	public List<IncidenciaDTO> incidenciasRegistradasCiudadano(String idCiudadano) {
		String sql = "SELECT i.id_incidencia, i.estado, i.descripcion, i.id_ciudadano, "
				+ "i.localizacion, i.fecha, i.fecha_resolucion, t.nombre as tipo " + "FROM Incidencia i "
				+ "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " + "WHERE i.id_ciudadano = ? "
				+ "ORDER BY i.fecha DESC";
		return db.executeQueryPojo(IncidenciaDTO.class, sql, idCiudadano);
	}

	public List<IncidenciaDTO> obtenerIncidenciasProceso(String idTecnico) {
		String sql = "SELECT i.*, t.nombre as tipo FROM Incidencia i "
				+ "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " + "WHERE i.id_tecnico = ? AND i.estado = 'Proceso'";
		return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico);
	}

	public void validarIncidenciaSimple(int idIncidencia, String emailOperador) {
		String idReal = obtenerIdPorEmail(emailOperador);
		String sqlU = "UPDATE Incidencia SET estado = 'Validada', id_operador = ? WHERE id_incidencia = ?";
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'Validada', datetime('now','localtime'), 'Validación rápida por operador')";
		db.executeUpdate(sqlU, idReal, idIncidencia);
		db.executeUpdate(sqlH, idIncidencia, idReal);
	}

	public boolean rechazarIncidencia(int idIncidencia, String emailOperador, String motivoRechazo) {
		String idReal = obtenerIdPorEmail(emailOperador);
		String sqlU = "UPDATE Incidencia SET estado = 'Rechazada por Operador', id_operador = ? WHERE id_incidencia = ?";
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'Rechazada por Operador', datetime('now','localtime'), ?)";
		try {
			db.executeUpdate(sqlU, idReal, idIncidencia);
			db.executeUpdate(sqlH, idIncidencia, idReal, "Motivo: " + motivoRechazo);
			return true;
		} catch (Exception e) { return false; }
	}

	public String obtenerIdPorEmail(String email) {
		String sql = "SELECT id_usuario FROM Usuario WHERE LOWER(email) = LOWER(?)";
		List<Object[]> result = db.executeQueryArray(sql, email);
		if (result != null && !result.isEmpty()) {
			return result.get(0)[0].toString();
		}
		return null;
	}

	public List<IncidenciaDTO> getTodasLasIncidencias() {
		String sql = "SELECT id_incidencia, descripcion, fecha, estado FROM Incidencia ORDER BY fecha DESC";
		return db.executeQueryPojo(IncidenciaDTO.class, sql);
	}

	public boolean marcarComoResueltaConCoste(int idInci, String idTec, double horas, double coste, String trabajos) {
		String sqlU = "UPDATE Incidencia SET estado = 'Resuelta', descripcion_trabajos = ?, coste = ? WHERE id_incidencia = ?";
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'Resuelta', datetime('now','localtime'), ?)";
		try {
			db.executeUpdate(sqlU, trabajos, coste, idInci);
			db.executeUpdate(sqlH, idInci, idTec, "Cerrada con coste total: " + coste + "€");
			return true;
		} catch (Exception e) { return false; }
	}
	
	public double getPrecioHoraTecnico(String idTecnico) {
		String sql = "SELECT precio_hora FROM Usuario WHERE id_usuario = ? OR email = ?";
		List<Object[]> result = db.executeQueryArray(sql, idTecnico, idTecnico);
		if (result != null && !result.isEmpty() && result.get(0)[0] != null) {
			return Double.parseDouble(result.get(0)[0].toString());
		}
		return 0.0;
	}
}