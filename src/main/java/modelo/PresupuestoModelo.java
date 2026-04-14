package modelo;

import java.util.List;
import util.Database;

public class PresupuestoModelo {
    private Database db = new Database();

    public List<PresupuestoDTO> obtenerPresupuestos() {
        String sql = "SELECT p.*, t.nombre as nombreTipo FROM Presupuesto p " +
                     "JOIN TipoIncidencia t ON p.id_tipo = t.id_tipo ORDER BY p.fecha_inicio DESC";
        return db.executeQueryPojo(PresupuestoDTO.class, sql);
    }

    public List<TipoIncidenciaDTO> obtenerTipos() {
        return db.executeQueryPojo(TipoIncidenciaDTO.class, "SELECT * FROM TipoIncidencia");
    }

    public boolean guardarPresupuesto(int idTipo, double total, String inicio, String fin) {
        // Regla: No solapar fechas para un mismo tipo
        String sqlCheck = "SELECT COUNT(*) FROM Presupuesto WHERE id_tipo = ? " +
                          "AND (? <= fecha_fin AND ? >= fecha_inicio)";
        
        List<Object[]> result = db.executeQueryArray(sqlCheck, idTipo, inicio, fin);
        if (Integer.parseInt(result.get(0)[0].toString()) > 0) {
            return false; 
        }

        String sqlIns = "INSERT INTO Presupuesto (id_tipo, importe_total, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        db.executeUpdate(sqlIns, idTipo, total, inicio, fin);
        return true;
    }
}