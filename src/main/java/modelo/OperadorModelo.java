package modelo;

import java.util.List;
import util.Database;

public class OperadorModelo {
    
    private Database db = new Database();

  
    public List<IncidenciaDTO> getTodasLasIncidencias() {
        String sql = "SELECT id_incidencia, descripcion, fecha, estado FROM Incidencia ORDER BY fecha DESC";
        return db.executeQueryPojo(IncidenciaDTO.class, sql);
    }

    
    public List<IncidenciaDTO> getIncidenciasParaAsignar() {
        String sql = "SELECT id_incidencia, descripcion, fecha, estado FROM Incidencia WHERE estado = 'Validada'";
        return db.executeQueryPojo(IncidenciaDTO.class, sql);
    }
}