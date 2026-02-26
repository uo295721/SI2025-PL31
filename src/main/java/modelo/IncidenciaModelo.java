package modelo;

import java.util.List;

import util.Database;

public class IncidenciaModelo {

    private Database db = new Database();

    public IncidenciaModelo() {
        
    }
    
    public List<IncidenciaDTO> getIncidenciasValidadas() {
        String sql = "SELECT id_incidencia, tipo, descripcion, fecha, estado FROM Incidencia "
                   + "WHERE estado = 'Validada' ORDER BY fecha ASC";
        
        return db.executeQueryPojo(IncidenciaDTO.class, sql);
    }

   
    public boolean asignarTecnicoIncidencia(int idIncidencia, String idTecnico, String emailOperador) {
        String sqlUpdate = "UPDATE Incidencia SET id_tecnico = ?, estado = 'Asignada' WHERE id_incidencia = ?";
        String sqlHistorial = "INSERT INTO Historial (id_incidencia, estado_nuevo, fecha_modificacion, comentario) "
                            + "VALUES (?, 'Asignada', datetime('now', 'localtime'), ?)";

        try {
           
            db.executeUpdate(sqlUpdate, idTecnico, idIncidencia);
            db.executeUpdate(sqlHistorial, idIncidencia, "Asignada por: " + emailOperador);
            return true;
        } catch (Exception e) {
            
            return false;
        }
    }

   
    public List<TecnicoDTO> obtenerListaTecnicos() {
        String sql = "SELECT id_usuario, nombre, email FROM Usuario WHERE rol = 'Tecnico' ORDER BY nombre";
        return db.executeQueryPojo(TecnicoDTO.class, sql);
    }

    public List<IncidenciaDTO> incidenciasRegistradasCiudadano(String idCiudadano) {
        String sql = "SELECT id_incidencia, tipo, descripcion, fecha, estado FROM Incidencia "
                   + "WHERE id_ciudadano = ? ORDER BY fecha DESC";
        
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idCiudadano);
    }

   
    public boolean insertarIncidencia(String tipo, String descripcion, String localizacion, String idCiudadano) {
        String sql = "INSERT INTO Incidencia (estado, descripcion, id_ciudadano, localización, tipo, fecha) "
                   + "VALUES ('Nueva', ?, ?, ?, ?, datetime('now', 'localtime'))";

        try {
            db.executeUpdate(sql, descripcion, idCiudadano, localizacion, tipo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
   
    public String getIdUsuarioByEmail(String email) {
        String sql = "SELECT id_usuario FROM Usuario WHERE email = ?";
        List<Object[]> result = db.executeQueryArray(sql, email);
        
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0)[0].toString();
    }
    
   
    public List<Object[]> obtenerIncidenciasProceso(String idTecnico) {
        String sql = "SELECT id_incidencia, tipo, fecha, localización, horas_estimadas " +
                     "FROM Incidencia WHERE id_tecnico = ? AND estado = 'Proceso'";

        return db.executeQueryArray(sql, idTecnico);
    }

   
    public boolean marcarComoResuelta(int idIncidencia, String idTecnico, double tiempoReal, String trabajos) {
        String sqlUpdate = "UPDATE Incidencia SET estado = 'Resuelta', " +
                           "descripcion_trabajos = ?, coste = ? WHERE id_incidencia = ?";
        
        String sqlHistorial = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, " +
                              "fecha_modificacion, comentario) VALUES (?, ?, 'Resuelta', datetime('now'), ?)";

        try {
           
            db.executeUpdate(sqlUpdate, trabajos, tiempoReal, idIncidencia);
            db.executeUpdate(sqlHistorial, idIncidencia, idTecnico, trabajos);
            return true;
        } catch (Exception e) {
            
            return false;
        }
    }
}

