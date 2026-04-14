package modelo;

import java.util.List;
import util.Database;
import java.util.ArrayList;

public class IncidenciaModelo {

	private Database db = new Database();

	public IncidenciaModelo() {
	}

	// ==========================================================
	// SPRINT 3: REAPERTURA (HU CIUDADANO)
	// ==========================================================

	public boolean reabrirIncidencia(int idIncidencia, String idUsuario, String motivo) {
		String sqlU = "UPDATE Incidencia SET estado = 'Nueva', fecha_resolucion = NULL WHERE id_incidencia = ?";
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

	// ==========================================================
	// INFORMES Y ESTADÍSTICAS (CONTROLADOR DE INFORMES)
	// ==========================================================

	public List<IncidenciaDTO> getEstadisticasFiltradas(String fechaInicio, String fechaFin, String tipo,
														String zona, String estado) {
		StringBuilder sql = new StringBuilder("SELECT i.id_incidencia, i.fecha, i.estado, " +
				"CASE " +
				"  WHEN i.fecha_resolucion IS NULL THEN 'En curso' " +
				"  ELSE CAST(julianday(i.fecha_resolucion) - julianday(i.fecha) AS INTEGER) || ' días' " +
				"END as tiempoResolucion, " +
				"t.nombre as tipo " +
				"FROM Incidencia i " +
				"JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " +
				"WHERE i.fecha >= ? AND i.fecha <= ?");

		List<Object> parametros = new ArrayList<>();
		parametros.add(fechaInicio);
		parametros.add(fechaFin);

		if (tipo != null && !tipo.equals("Todos")) {
			sql.append(" AND t.nombre = ?");
			parametros.add(tipo);
		}
		if (zona != null && !zona.equals("Todas")) {
			sql.append(" AND i.localizacion = ?");
			parametros.add(zona);
		}
		if (estado != null && !estado.equals("Todos")) {
			sql.append(" AND i.estado = ?");
			parametros.add(estado);
		}

		sql.append(" ORDER BY i.fecha DESC");

		return db.executeQueryPojo(IncidenciaDTO.class, sql.toString(), parametros.toArray());
	}

	public List<Object[]> getInformeMensualIncidencias(String fechaInicio, String fechaFin) {
		String sql = "SELECT u.nombre, COUNT(i.id_incidencia) AS total_incidencias, SUM(i.coste) AS coste_total "
				+ "FROM Usuario u JOIN Incidencia i ON u.id_usuario = i.id_tecnico "
				+ "WHERE u.rol = 'TÉCNICO' AND i.estado = 'Resuelta' "
				+ "AND (i.fecha >= ? AND i.fecha <= ?) GROUP BY u.nombre ORDER BY u.nombre ASC";
		return db.executeQueryArray(sql, fechaInicio, fechaFin);
	}

	// ==========================================================
	// GESTIÓN DE OPERADORES Y CLASIFICACIÓN
	// ==========================================================

	public void validarClasificacion(int idIncidencia, String nuevoTipo, String idOperador) {
		String sqlU = "UPDATE Incidencia SET id_tipo = (SELECT id_tipo FROM TipoIncidencia WHERE nombre = ?), "
				+ "estado = 'Validada', id_operador = ? WHERE id_incidencia = ?";
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'Validada', datetime('now','localtime'), 'Clasificación realizada')";
		db.executeUpdate(sqlU, nuevoTipo, idOperador, idIncidencia);
		db.executeUpdate(sqlH, idIncidencia, idOperador);
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
	    // Cambiado 'Rechazada por Operador' por 'Rechazada'
	    String sqlU = "UPDATE Incidencia SET estado = 'Rechazada', id_operador = ? WHERE id_incidencia = ?";
	    String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
	            + "VALUES (?, ?, 'Rechazada', datetime('now','localtime'), ?)";
	    try {
	        db.executeUpdate(sqlU, idReal, idIncidencia);
	        db.executeUpdate(sqlH, idIncidencia, idReal, "Motivo: " + motivoRechazo);
	        return true;
	    } catch (Exception e) { return false; }
	}

	public boolean asignarTecnicoIncidencia(int idIncidencia, String idTecnico, String emailOperador) {
		String sqlU = "UPDATE Incidencia SET id_tecnico = ?, estado = 'Asignada' WHERE id_incidencia = ?";
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, (SELECT id_usuario FROM Usuario WHERE email = ?), 'Asignada', datetime('now', 'localtime'), ?)";
		try {
			db.executeUpdate(sqlU, idTecnico, idIncidencia);
			db.executeUpdate(sqlH, idIncidencia, emailOperador, "Asignada por: " + emailOperador);
			return true;
		} catch (Exception e) { return false; }
	}

	// ==========================================================
	// TÉCNICOS Y TAREAS DIARIAS
	// ==========================================================

	public void registrarTareaDiaria(int idInci, String idTec, String fecha, String desc, double horas) {
		String sql = "INSERT INTO TareaDiaria (id_incidencia, id_tecnico, fecha, descripcion_tarea, horas_dedicadas) VALUES (?, ?, ?, ?, ?)";
		db.executeUpdate(sql, idInci, idTec, fecha, desc, horas);
	}

	public List<Object[]> getTareasPorIncidencia(int idInci) {
		String sql = "SELECT fecha, descripcion_tarea, horas_dedicadas FROM TareaDiaria WHERE id_incidencia = ? ORDER BY fecha DESC";
		return db.executeQueryArray(sql, idInci);
	}

	public double getPrecioHoraTecnico(String idTecnico) {
		String sql = "SELECT precio_hora FROM Usuario WHERE id_usuario = ? OR email = ?";
		List<Object[]> result = db.executeQueryArray(sql, idTecnico, idTecnico);
		if (result != null && !result.isEmpty() && result.get(0)[0] != null) {
			return Double.parseDouble(result.get(0)[0].toString());
		}
		return 0.0;
	}

	public boolean marcarComoResueltaConCoste(int idInci, String idTec, double horas, double coste, String trabajos) {
		String sqlU = "UPDATE Incidencia SET estado = 'Resuelta', descripcion_trabajos = ?, coste = ?, fecha_resolucion = datetime('now') WHERE id_incidencia = ?";
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'Resuelta', datetime('now','localtime'), ?)";
		try {
			db.executeUpdate(sqlU, trabajos, coste, idInci);
			db.executeUpdate(sqlH, idInci, idTec, "Cerrada con coste total: " + coste + "€");
			return true;
		} catch (Exception e) { return false; }
	}

	public List<IncidenciaDTO> obtenerIncidenciasProceso(String idTecnico) {
		String sql = "SELECT i.*, t.nombre as tipo FROM Incidencia i "
				+ "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " + "WHERE i.id_tecnico = ? AND i.estado = 'Proceso'";
		return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico);
	}

	public List<IncidenciaDTO> getIncidenciasAsignadasTecnico(String idTecnico) {
		String sql = "SELECT * FROM Incidencia WHERE (id_tecnico = ? OR id_tecnico = (SELECT id_usuario "
				+ "FROM Usuario WHERE email = ?)) AND estado IN ('Validada', 'Asignada') "
				+ "ORDER BY id_incidencia ASC";
		return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico, idTecnico);
	}

	public void planificarIncidencia(int idIncidencia, int horas, String trabajos, String idTecnico) {
		String sqlU = "UPDATE Incidencia SET horas_estimadas = ?, descripcion_trabajos = ?, estado = 'Proceso' WHERE id_incidencia = ?";
		String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, ?, 'En curso', datetime('now','localtime'), 'Planificación técnica')";
		db.executeUpdate(sqlU, horas, trabajos, idIncidencia);
		db.executeUpdate(sqlH, idIncidencia, idTecnico);
	}
	
	// ==========================================================
	// HISTORIA DE USUARIO: INFORME ECONÓMICO POR CATEGORÍA
	// ==========================================================

	public List<InformeEconomicoDTO> obtenerInformeEconomico() {
	    List<InformeEconomicoDTO> lista = new ArrayList<>();
	
	    String sql = "SELECT ti.nombre, " +
	                 "COUNT(DISTINCT i.id_incidencia) as volumen, " +
	                 "SUM(COALESCE(td.horas_dedicadas, 0) * COALESCE(u.precio_hora, 0)) as coste_total " +
	                 "FROM TipoIncidencia ti " +
	                 "JOIN Incidencia i ON ti.id_tipo = i.id_tipo " +
	                 "LEFT JOIN TareaDiaria td ON i.id_incidencia = td.id_incidencia " +
	                 "LEFT JOIN Usuario u ON td.id_tecnico = u.id_usuario " +
	                 "GROUP BY ti.id_tipo, ti.nombre";

	    List<Object[]> resultados = db.executeQueryArray(sql);

	    if (resultados != null) {
	        for (Object[] fila : resultados) {
	            String categoria = fila[0].toString();
	            int volumen = Integer.parseInt(fila[1].toString());
	            double total = Double.parseDouble(fila[2].toString());
	
	            double media = (volumen > 0) ? total / volumen : 0.0;

	            lista.add(new InformeEconomicoDTO(categoria, volumen, total, media));
	        }
	    }
	    return lista;
	}
	

	// ==========================================================
	// HISTORIAL Y CIUDADANO
	// ==========================================================

	public void registrarCambioHistorial(int idInci, String emailUser, String nuevoEstado, String comentario) {
		String sql = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
				+ "VALUES (?, (SELECT id_usuario FROM Usuario WHERE email = ?), ?, datetime('now'), ?)";
		db.executeUpdate(sql, idInci, emailUser, nuevoEstado, comentario);
	}

	public List<HistorialDTO> obtenerHistorialIncidencia(int idIncidencia) {
		String sql = "SELECT id_modificacion, id_incidencia, id_usuario, estado_nuevo, "
				+ "fecha_modificacion, comentario FROM Historial WHERE id_incidencia = ? "
				+ "ORDER BY fecha_modificacion DESC";
		return db.executeQueryPojo(HistorialDTO.class, sql, idIncidencia);
	}

	public List<IncidenciaDTO> incidenciasRegistradasCiudadano(String idCiudadano) {
		String sql = "SELECT i.id_incidencia, i.estado, i.descripcion, i.id_ciudadano, "
				+ "i.localizacion, i.fecha, i.fecha_resolucion, t.nombre as tipo " + "FROM Incidencia i "
				+ "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " + "WHERE i.id_ciudadano = ? "
				+ "ORDER BY i.fecha DESC";
		return db.executeQueryPojo(IncidenciaDTO.class, sql, idCiudadano);
	}

	public boolean insertarIncidencia(String tipo, String descripcion, String localizacion, String idCiudadano) {
		String sql = "INSERT INTO Incidencia (estado, descripcion, id_ciudadano, localizacion, id_tipo, fecha) "
				+ "VALUES ('Nueva', ?, ?, ?, (SELECT id_tipo FROM TipoIncidencia WHERE nombre = ?), datetime('now', 'localtime'))";
		try {
			db.executeUpdate(sql, descripcion, idCiudadano, localizacion, tipo);
			return true;
		} catch (Exception e) { return false; }
	}

	// ==========================================================
	// MÉTODOS GENERALES Y AUXILIARES
	// ==========================================================

	public String obtenerIdPorEmail(String email) {
		String sql = "SELECT id_usuario FROM Usuario WHERE LOWER(email) = LOWER(?)";
		List<Object[]> result = db.executeQueryArray(sql, email);
		if (result != null && !result.isEmpty()) {
			return result.get(0)[0].toString();
		}
		return null;
	}

	public String obtenerNombrePorEmail(String email) {
		String sql = "SELECT nombre FROM Usuario WHERE LOWER(email) = LOWER(?)";
		List<Object[]> result = db.executeQueryArray(sql, email);
		if (result != null && !result.isEmpty()) {
			return result.get(0)[0].toString();
		}
		return email;
	}

	public List<IncidenciaDTO> getTodasLasIncidencias() {
		String sql = "SELECT id_incidencia, descripcion, fecha, estado FROM Incidencia ORDER BY fecha DESC";
		return db.executeQueryPojo(IncidenciaDTO.class, sql);
	}

	public List<IncidenciaDTO> getIncidenciasPorEstado(String estado) {
		String sql = "SELECT * FROM Incidencia WHERE estado = ? ORDER BY fecha ASC";
		return db.executeQueryPojo(IncidenciaDTO.class, sql, estado);
	}

	public void archivarIncidencia(List<Integer> listaIds) {
		String sql = "UPDATE Incidencia SET estado = 'Cerrada' WHERE id_incidencia = ?";
		for (int i : listaIds)
			db.executeUpdate(sql, i);
	}

	public List<IncidenciaDTO> getIncidenciasParaControlCalidad(String especialidad) {
		String sql = "SELECT i.id_incidencia, i.descripcion, i.localizacion, i.fecha, t.nombre as tipo "
				+ "FROM Incidencia i " + "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo "
				+ "WHERE t.nombre = ? AND i.estado = 'Resuelta'";
		return db.executeQueryPojo(IncidenciaDTO.class, sql, especialidad);
	}
	
	public List<IncidenciaDTO> getIncidenciasConHistorialParaExportar(String fInicio, String fFin, String tipo, String zona){
		StringBuilder sqlBuilder = new StringBuilder("SELECT i.id_incidencia, i.descripcion, i.localizacion, "
												   + "i.fecha, i.id_ciudadano as descripcionCiudadano, t.nombre as tipo "
												   + "FROM incidencia i "
												   + "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo WHERE 1=1");
		List<Object> parametros = new ArrayList<>();
		if (fInicio != null && !fInicio.trim().isEmpty()) {
			sqlBuilder.append(" AND i.fecha >= ?");
			parametros.add(fInicio);
		}
		
		if (fFin != null && !fFin.trim().isEmpty()) {
			sqlBuilder.append(" AND i.fecha <= ?");
			parametros.add(fFin);
		}
		
		if (tipo != null && !tipo.equals("Todos")) {
	        sqlBuilder.append(" AND t.nombre = ?");
	        parametros.add(tipo);
	    }
		
	    if (zona != null && !zona.equals("Todas")) {
	        sqlBuilder.append(" AND i.localizacion = ?");
	        parametros.add(zona);
	    }
	    
	    sqlBuilder.append(" ORDER BY i.fecha ASC");
	    
	    List<IncidenciaDTO> lista = db.executeQueryPojo(IncidenciaDTO.class, sqlBuilder.toString(), parametros.toArray());
	    
	    for (IncidenciaDTO i : lista) {
	    	i.setHistorial(obtenerHistorialIncidencia(i.getIdIncidencia()));
	    }
	    
	    return lista;
	}

	public List<Object[]> getTecnicosDisponiblesPorCarga(int idTipoIncidencia) {
	    String sql = "SELECT u.id_usuario, u.nombre || ' ' || u.apellidos as nombreCompleto, " +
	                 " (SELECT COUNT(*) FROM Asignacion_Incidencia ai " +
	                 "  JOIN Incidencia i ON ai.id_incidencia = i.id_incidencia " +
	                 "  WHERE ai.id_tecnico = u.id_usuario " +
	                 "  AND i.estado NOT IN ('Resuelta', 'Cerrada', 'Rechazada por Operador')) as carga " +
	                 "FROM Usuario u " +
	                 "JOIN Tecnico_Especialidad te ON u.id_usuario = te.id_usuario " +
	                 "WHERE u.rol = 'TÉCNICO' AND te.id_tipo = ? " +
	                 "ORDER BY carga ASC";
	    
	    return db.executeQueryArray(sql, idTipoIncidencia);
	}
	
	public boolean asignarVariosTecnicos(int idIncidencia, List<String> idsTecnicos, String emailOperador) {
	    String idOperador = obtenerIdPorEmail(emailOperador);
	    
	    // SQLs
	    String sqlDelete = "DELETE FROM Asignacion_Incidencia WHERE id_incidencia = ?";
	    String sqlAsignar = "INSERT INTO Asignacion_Incidencia (id_incidencia, id_tecnico) VALUES (?, ?)";
	    String sqlUpdateInci = "UPDATE Incidencia SET estado = 'Asignada', id_tecnico = NULL WHERE id_incidencia = ?";
	    String sqlHistorial = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) " +
	                          "VALUES (?, ?, 'Asignada', datetime('now','localtime'), ?)";

	    try {
	        // 1. Limpiamos asignaciones previas de esta incidencia
	        db.executeUpdate(sqlDelete, idIncidencia);

	        // 2. Insertamos los nuevos técnicos
	        for (String idTec : idsTecnicos) {
	            db.executeUpdate(sqlAsignar, idIncidencia, idTec);
	        }

	        // 3. Actualizamos la incidencia (ponemos id_tecnico a NULL porque ahora mandan los múltiples)
	        db.executeUpdate(sqlUpdateInci, idIncidencia);

	        // 4. Historial
	        String comentario = "Asignada a " + idsTecnicos.size() + " técnicos por " + emailOperador;
	        db.executeUpdate(sqlHistorial, idIncidencia, idOperador, comentario);

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}	
}