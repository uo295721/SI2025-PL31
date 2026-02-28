package modelo;

import java.util.List;
import util.Database;

public class IncidenciaModelo {
	
    private Database db = new Database();

    public IncidenciaModelo() {}
    
    public List<IncidenciaDTO> getIncidenciasAsignadasTecnico(String idTecnico) {
        String sql = "SELECT * FROM Incidencia WHERE (id_tecnico = ? OR id_tecnico = (SELECT id_usuario "
                   + "FROM Usuario WHERE email = ?)) AND estado IN ('Validada', 'Asignada') "
                   + "ORDER BY id_incidencia ASC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico, idTecnico);
    }
    
    public List<IncidenciaDTO> getIncidenciasPorEstado(String estado) {
        String sql = "SELECT * FROM Incidencia WHERE estado = ? ORDER BY fecha ASC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, estado);
    }

    /** Para tu historia del Ciudadano */
    public List<IncidenciaDTO> incidenciasRegistradasCiudadano(String idCiudadano) {
        String sql = "SELECT * FROM Incidencia WHERE id_ciudadano = ? ORDER BY fecha DESC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idCiudadano);
    }

    /** Para el técnico cuando va a resolver */
    public List<IncidenciaDTO> obtenerIncidenciasProceso(String idTecnico) {
        String sql = "SELECT * FROM Incidencia WHERE id_tecnico = ? AND estado = 'Proceso'";
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico);
    }

    /** Para el informe de Dani (Responsable) */
    public List<Object[]> getInformeMensualIncidencias(String fechaInicio, String fechaFin) {
        String sql = "SELECT u.nombre, COUNT(i.id_incidencia) AS total_incidencias, SUM(i.coste) AS coste_total " +
                     "FROM Usuario u JOIN Incidencia i ON u.id_usuario = i.id_tecnico " +
                     "WHERE u.rol = 'TÉCNICO' AND i.estado = 'Resuelta' " +
                     "AND (i.fecha >= ? AND i.fecha <= ?) GROUP BY u.nombre ORDER BY u.nombre ASC";
        return db.executeQueryArray(sql, fechaInicio, fechaFin);
    }
    
    public boolean insertarIncidencia(String tipo, String descripcion, String localizacion, String idCiudadano) {
        String sql = "INSERT INTO Incidencia (estado, descripcion, id_ciudadano, localización, tipo, fecha) "
                   + "VALUES ('Nueva', ?, ?, ?, ?, datetime('now', 'localtime'))";
        try {
            db.executeUpdate(sql, descripcion, idCiudadano, localizacion, tipo);
            return true;
        } catch (Exception e) { return false; }
    }

    public void validarClasificacion(int idIncidencia, String nuevoTipo, String idOperador) {
        String sqlU = "UPDATE Incidencia SET tipo = ?, estado = 'Validada', id_operador = ? WHERE id_incidencia = ?";
        String sqlH = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                    + "VALUES (?, ?, 'Validada', datetime('now','localtime'), 'Validación Operador')";
        db.executeUpdate(sqlU, nuevoTipo, idOperador, idIncidencia);
        db.executeUpdate(sqlH, idIncidencia, idOperador);
    }

    public boolean asignarTecnicoIncidencia(int idIncidencia, String idTecnico, String emailOperador) {
        String sqlU = "UPDATE Incidencia SET id_tecnico = ?, estado = 'Asignada' WHERE id_incidencia = ?";
        String sqlH = "INSERT INTO Historial (id_incidencia, estado_nuevo, fecha_modificacion, comentario) "
                    + "VALUES (?, 'Asignada', datetime('now', 'localtime'), ?)";
        try {
            db.executeUpdate(sqlU, idTecnico, idIncidencia);
            db.executeUpdate(sqlH, idIncidencia, "Asignada por: " + emailOperador);
            return true;
        } catch (Exception e) { return false; }
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
    
}