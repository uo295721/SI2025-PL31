package modelo;

import java.util.List;

import util.Database;

public class IncidenciasCModelo {
    
    
    private Database db = new Database();

    public IncidenciasCModelo() {
       
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