package modelo;

import java.util.ArrayList;
import java.util.List;
import util.Database;

public class Modelo33787 {
    private Database db = new Database();

    /**
     * Busca el id_usuario (ej. 'O1') asociado a un email.
     */
    public String obtenerIdPorEmail(String email) {
        String sql = "SELECT id_usuario FROM Usuario WHERE email = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, email);
        if (!resultado.isEmpty()) {
            return (String) resultado.get(0)[0];
        }
        return null;
    }

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
     * Ahora recibe el idOperador (ej. 'O1') para guardarlo en la FK.
     */
    public void validarClasificacion(int idIncidencia, String nuevoTipo, String idOperador) {
        String sqlUpdate = "UPDATE Incidencia SET tipo = ?, estado = 'Validada', id_operador = ? " +
                           "WHERE id_incidencia = ?";
        
        String sqlHistorial = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) " +
                              "VALUES (?, ?, 'Validada', datetime('now','localtime'), 'Validación por operador id: " + idOperador + "')";

        try {
            db.executeUpdate(sqlUpdate, nuevoTipo, idOperador, idIncidencia);
            db.executeUpdate(sqlHistorial, idIncidencia, idOperador);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar BBDD: " + e.getMessage());
        }
    }
}