package modelo;

import java.util.List;

import util.Database;

public class HistorialModelo {

    private Database db = new Database();

   
    public List<HistorialDTO> getHistorialPorIncidencia(int idIncidencia) {
        String sql = "SELECT id_modificacion, id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario "
                   + "FROM Historial WHERE id_incidencia = ? ORDER BY fecha_modificacion DESC";
        
        
        return db.executeQueryPojo(HistorialDTO.class, sql, idIncidencia);
    }

   
    public void insertarRegistroHistorial(HistorialDTO historial) {
        String sql = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                   + "VALUES (?, ?, ?, datetime('now','localtime'), ?)";

      
        db.executeUpdate(sql, 
            historial.getId_incidencia(), 
            historial.getId_usuario(), 
            historial.getEstado_nuevo(), 
            historial.getComentario()
        );
    }
}