package modelo;

import java.util.ArrayList;
import java.util.List;
import util.Database;

public class Modelo33787 {
    private Database db = new Database();

    /**
     * Recupera las incidencias en estado 'Nueva'.
     * Mapea id_ciudadano al campo descripcionCiudadano del DTO.
     */
    public List<IncidenciaDTO> getIncidenciasNuevas() {
        List<IncidenciaDTO> lista = new ArrayList<>();
        String sql = "SELECT id_incidencia, fecha, id_ciudadano, tipo FROM Incidencia " +
                     "WHERE estado = 'Nueva' ORDER BY id_incidencia ASC";
        
        List<Object[]> filas = db.executeQueryArray(sql);
        for (Object[] o : filas) {
            IncidenciaDTO dto = new IncidenciaDTO();
            dto.setIdIncidencia((int) o[0]);
            dto.setFecha((String) o[1]);
            dto.setDescripcionCiudadano((String) o[2]); 
            dto.setTipo((String) o[3]);
            lista.add(dto);
        }
        return lista;
    }

    /**
     * Actualiza el tipo y estado de la incidencia e inserta en el historial.
     */
    public void validarClasificacion(int idIncidencia, String nuevoTipo, String emailOperador) {
        String sqlUpdate = "UPDATE Incidencia SET tipo = ?, estado = 'Validada', id_operador = ? " +
                           "WHERE id_incidencia = ?";
        
        String sqlHistorial = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) " +
                              "VALUES (?, ?, 'Validada', datetime('now','localtime'), 'Clasificación validada por operador')";

        try {
            db.executeUpdate(sqlUpdate, nuevoTipo, emailOperador, idIncidencia);
            db.executeUpdate(sqlHistorial, idIncidencia, emailOperador);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la validación: " + e.getMessage());
        }
    }
}