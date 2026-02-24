package modelo;

import java.util.List;

import util.Database;

public class TecnicoModelo {

    private Database db = new Database();

    
    public List<TecnicoDTO> obtenerTodosLosTecnicos() {
        String sql = "SELECT id_usuario, nombre, email FROM Usuario WHERE rol = 'Técnico' ORDER BY nombre";
        return db.executeQueryPojo(TecnicoDTO.class, sql);
    }

    
    public List<IncidenciaDTO> getIncidenciasAsignadas(String idTecnico) {
        String sql = "SELECT id_incidencia, tipo, descripcion, estado "
                   + "FROM Incidencia WHERE id_tecnico = ? AND estado = 'Asignada'";
        
        return db.executeQueryPojo(IncidenciaDTO.class, sql, idTecnico);
    }

    
    public void planificarIncidencia(int idIncidencia, int horas, String trabajos, String emailTecnico) {
        String sqlUpdate = "UPDATE Incidencia SET horas_estimadas = ?, descripcion_trabajos = ?, "
                         + "estado = 'En curso' WHERE id_incidencia = ?";
        
        String sqlHistorial = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                            + "VALUES (?, ?, 'En curso', datetime('now','localtime'), 'Planificación de trabajos por el técnico')";

        try {
           
            db.executeUpdate(sqlUpdate, horas, trabajos, idIncidencia);
            
          
            db.executeUpdate(sqlHistorial, idIncidencia, emailTecnico);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al planificar la incidencia: " + e.getMessage(), e);
        }
    }
}