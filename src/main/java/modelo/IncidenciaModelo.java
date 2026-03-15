package modelo;

import java.util.List;
import util.Database;
import modelo.IncidenciaDTO;
import java.util.ArrayList;


public class IncidenciaModelo {
    
    private Database db = new Database();

    public IncidenciaModelo() {}
    
    public List<IncidenciaDTO> getIncidenciasAsignadasTecnico(String idTecnico) {
        String sql = "SELECT * FROM Incidencia WHERE (id_tecnico = ? OR id_tecnico = (SELECT id_usuario "
                   + "FROM Usuario WHERE email = ?)) AND estado IN ('Validada', 'Asignada') "
                   + "ORDER BY id_incidencia ASC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico, idTecnico);
    }

    public List<IncidenciaDTO> obtenerIncidenciasProceso(String idTecnico) {
    	String sql = "SELECT i.*, t.nombre as tipo " +
                "FROM Incidencia i " +
                "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " +
                "WHERE i.id_tecnico = ? AND i.estado = 'Proceso'";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico);
    }

    public void planificarIncidencia(int idIncidencia, int horas, String trabajos, String idTecnico) {
        String sqlU = "UPDATE Incidencia SET horas_estimadas = ?, descripcion_trabajos = ?, estado = 'Proceso' WHERE id_incidencia = ?";
        String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                    + "VALUES (?, ?, 'En curso', datetime('now','localtime'), 'Planificación técnica')";
        db.executeUpdate(sqlU, horas, trabajos, idIncidencia);
        db.executeUpdate(sqlH, idIncidencia, idTecnico);
    }

    public boolean marcarComoResuelta(int idIncidencia, String idTecnico, double tiempoReal, String trabajos) {
        String sqlU = "UPDATE Incidencia SET estado = 'Resuelta', descripcion_trabajos = ?, coste = ? WHERE id_incidencia = ?";
        String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                    + "VALUES (?, ?, 'Resuelta', datetime('now'), ?)";
        try {
            db.executeUpdate(sqlU, trabajos, tiempoReal, idIncidencia);
            db.executeUpdate(sqlH, idIncidencia, idTecnico, trabajos);
            return true;
        } catch (Exception e) { return false; }
    }

    public List<IncidenciaDTO> getIncidenciasPorEstado(String estado) {
        String sql = "SELECT * FROM Incidencia WHERE estado = ? ORDER BY fecha ASC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, estado);
    }
    
    public List<IncidenciaDTO> getIncidenciasParaControlCalidad(String especialidad) {
        String sql = "SELECT i.id_incidencia, i.descripcion, i.localizacion, i.fecha, t.nombre as tipo " +
                     "FROM Incidencia i " +
                     "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " +
                     "WHERE t.nombre = ? AND i.estado = 'Resuelta'";
        
        return db.executeQueryPojo(IncidenciaDTO.class, sql, especialidad);
    }
    
    public void archivarIncidencia(List<Integer> listaIds) {
    	String sql = "UPDATE Incidencia SET estado = 'Cerrada' WHERE id_incidencia = ?";
    	for (int i : listaIds)
    		db.executeUpdate(sql, i);
    }

    public void validarClasificacion(int idIncidencia, String nuevoTipo, String idOperador) {
        String sqlU = "UPDATE Incidencia SET id_tipo = ?, estado = 'Validada', id_operador = ? WHERE id_incidencia = ?";
        String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                     + "VALUES (?, ?, 'Validada', datetime('now','localtime'), 'Validación Operador')";
        db.executeUpdate(sqlU, nuevoTipo, idOperador, idIncidencia);
        db.executeUpdate(sqlH, idIncidencia, idOperador);
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

    public boolean insertarIncidencia(String tipo, String descripcion, String localizacion, String idCiudadano) {
        String sql = "INSERT INTO Incidencia (estado, descripcion, id_ciudadano, localizacion, id_tipo, fecha) "
                   + "VALUES ('Nueva', ?, ?, ?, ?, datetime('now', 'localtime'))";
        try {
            db.executeUpdate(sql, descripcion, idCiudadano, localizacion, tipo);
            return true;
        } catch (Exception e) { return false; }
    }

    public List<IncidenciaDTO> incidenciasRegistradasCiudadano(String idCiudadano) {
        String sql = "SELECT i.id_incidencia, i.estado, i.descripcion, i.id_ciudadano, " +
                     "i.localizacion, i.fecha, i.fecha_resolucion, t.nombre as tipo " +
                     "FROM Incidencia i " +
                     "JOIN TipoIncidencia t ON i.id_tipo = t.id_tipo " +
                     "WHERE i.id_ciudadano = ? " +
                     "ORDER BY i.fecha DESC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idCiudadano);
    }

    public List<Object[]> getInformeMensualIncidencias(String fechaInicio, String fechaFin) {
        String sql = "SELECT u.nombre, COUNT(i.id_incidencia) AS total_incidencias, SUM(i.coste) AS coste_total " +
                     "FROM Usuario u JOIN Incidencia i ON u.id_usuario = i.id_tecnico " +
                     "WHERE u.rol = 'TÉCNICO' AND i.estado = 'Resuelta' " +
                     "AND (i.fecha >= ? AND i.fecha <= ?) GROUP BY u.nombre ORDER BY u.nombre ASC";
        return db.executeQueryArray(sql, fechaInicio, fechaFin);
    }

    public List<HistorialDTO> obtenerHistorialIncidencia(int idIncidencia){
        String sql = "SELECT id_modificacion, id_incidencia, id_usuario, estado_nuevo, "
                + "fecha_modificacion, comentario FROM Historial "
                + "WHERE id_incidencia = ? "
                + "ORDER BY fecha_modificacion DESC";
        return db.executeQueryPojo(HistorialDTO.class, sql, idIncidencia);
    }

    public void registrarCambioHistorial(int idInci, String emailUser, String nuevoEstado, String comentario) {
        String sql = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) " +
                     "VALUES (?, (SELECT id_usuario FROM Usuario WHERE email = ?), ?, datetime('now'), ?)";
        db.executeUpdate(sql, idInci, emailUser, nuevoEstado, comentario);
    }

    public List<IncidenciaDTO> getTodasLasIncidencias() {
        String sql = "SELECT id_incidencia, descripcion, fecha, estado FROM Incidencia ORDER BY fecha DESC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql);
    }
   public void registrarTareaDiaria(int idInci, String idTec, String fecha, String desc, double horas) {
	   String sql = "INSERT INTO TareaDiaria (id_incidencia, id_tecnico, fecha, descripcion_tarea, horas_dedicadas) VALUES (?, ?, ?, ?, ?)";
	    db.executeUpdate(sql, idInci, idTec, fecha, desc, horas);
   }
   public List<Object[]> getTareasPorIncidencia(int idInci) {
	    String sql = "SELECT fecha, descripcion_tarea, horas_dedicadas FROM TareaDiaria WHERE id_incidencia = ? ORDER BY fecha DESC";
	    return db.executeQueryArray(sql, idInci);
	}
   /**
    * Obtiene el ID real del usuario (ej. 'O1', 'T2') a partir de su email.
    */
   public String obtenerIdPorEmail(String email) {
       String sql = "SELECT id_usuario FROM Usuario WHERE LOWER(email) = LOWER(?)";
       List<Object[]> result = db.executeQueryArray(sql, email);
       if (result != null && !result.isEmpty()) {
           return result.get(0)[0].toString();
       }
       return null;
   }

   /**
    * Obtiene el Nombre del usuario a partir de su email para mostrar en la Vista.
    */
   public String obtenerNombrePorEmail(String email) {
       String sql = "SELECT nombre FROM Usuario WHERE LOWER(email) = LOWER(?)";
       List<Object[]> result = db.executeQueryArray(sql, email);
       if (result != null && !result.isEmpty()) {
           return result.get(0)[0].toString();
       }
       return email;
   }

   /**
    * Valida una incidencia rápidamente.
    * Actualiza el estado y el id_operador en la tabla Incidencia.
    */
   public void validarIncidenciaSimple(int idIncidencia, String emailOperador) {
       String idReal = obtenerIdPorEmail(emailOperador);
       
       String sqlU = "UPDATE Incidencia SET estado = 'Validada', id_operador = ? WHERE id_incidencia = ?";
       String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                   + "VALUES (?, ?, 'Validada', datetime('now','localtime'), 'Validación rápida por operador')";
       
       db.executeUpdate(sqlU, idReal, idIncidencia);
       db.executeUpdate(sqlH, idIncidencia, idReal);
   }

   /**
    * Rechaza una incidencia con un motivo obligatorio.
    * Guarda el ID real del operador que realiza la acción.
    */
   public boolean rechazarIncidencia(int idIncidencia, String emailOperador, String motivoRechazo) {
       String idReal = obtenerIdPorEmail(emailOperador);
       
       String sqlU = "UPDATE Incidencia SET estado = 'Rechazada por Operador', id_operador = ? WHERE id_incidencia = ?";
       String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                   + "VALUES (?, ?, 'Rechazada por Operador', datetime('now','localtime'), ?)";
       try {
           db.executeUpdate(sqlU, idReal, idIncidencia);
           db.executeUpdate(sqlH, idIncidencia, idReal, "Motivo: " + motivoRechazo);
           return true;
       } catch (Exception e) { 
           return false; 
       }
   }
}