package modelo;

import java.util.ArrayList;
import java.util.List;
import util.Database;

public class Modelo33787 {
    private Database db = new Database();

    /**
     * Obtiene solo las incidencias con estado 'Nueva' para ser clasificadas.
     */
    public List<IncidenciaDTO> getIncidenciasNuevas() {
        List<IncidenciaDTO> lista = new ArrayList<>();
        String sql = "SELECT id_incidencia, fecha, id_ciudadano, tipo, localización FROM Incidencia " +
                     "WHERE estado = 'Nueva' ORDER BY id_incidencia ASC";
        
        List<Object[]> filas = db.executeQueryArray(sql);
        for (Object[] o : filas) {
            IncidenciaDTO dto = new IncidenciaDTO();
            dto.setIdIncidencia((int) o[0]);
            dto.setFecha((String) o[1]);
            dto.setIdCiudadano((String) o[2]);
            dto.setTipo((String) o[3]); // Tipo propuesto por el ciudadano
            dto.setLocalizacion((String) o[4]);
            lista.add(dto);
        }
        return lista;
    }

    /**
     * Valida la incidencia actualizando su tipo, estado y registrando el historial.
     */
    public void validarClasificacion(int idIncidencia, String nuevoTipo, String emailOperador) {
        String sqlUpdate = "UPDATE Incidencia SET tipo = ?, estado = 'Validada', id_operador = ? " +
                           "WHERE id_incidencia = ?";
        
        String sqlHistorial = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, " +
                              "fecha_modificacion, comentario) VALUES (?, ?, 'Validada', " +
                              "datetime('now','localtime'), 'Incidencia clasificada por el operador')";

        try {
            // Ejecución en bloque (idealmente transaccional si el objeto db lo soporta)
            db.executeUpdate(sqlUpdate, nuevoTipo, emailOperador, idIncidencia);
            db.executeUpdate(sqlHistorial, idIncidencia, emailOperador);
        } catch (Exception e) {
            throw new RuntimeException("Error al validar la incidencia: " + e.getMessage());
        }
    }
}