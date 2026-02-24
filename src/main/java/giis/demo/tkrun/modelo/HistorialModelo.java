package giis.demo.tkrun.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Jdbc;

public class HistorialModelo {

    /**
     * Obtiene todo el historial de una incidencia específica.
     * Útil para cumplir con la parte de la HU que requiere visualizar cambios previos.
     */
    public List<HistorialDTO> getHistorialPorIncidencia(int idIncidencia) {
        List<HistorialDTO> lista = new ArrayList<>();
        String sql = "SELECT id_modificacion, id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario "
                   + "FROM Historial WHERE id_incidencia = ? ORDER BY fecha_modificacion DESC";

        try (Connection con = Jdbc.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, idIncidencia);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                HistorialDTO h = new HistorialDTO();
                h.setId_modificacion(rs.getInt("id_modificacion"));
                h.setId_incidencia(rs.getInt("id_incidencia"));
                h.setId_usuario(rs.getString("id_usuario"));
                h.setEstado_nuevo(rs.getString("estado_nuevo"));
                h.setFecha_modificacion(rs.getString("fecha_modificacion"));
                h.setComentario(rs.getString("comentario"));
                
                lista.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el historial de la incidencia", e);
        }
        return lista;
    }

    /**
     * Inserta un registro en el historial de forma independiente.
     * Nota: Para cumplir con la atomicidad de tu HU (marcar resuelta + historial),
     * es preferible usar una transacción (commit/rollback) dentro de TecnicoModelo.
     */
    public void insertarRegistroHistorial(HistorialDTO historial) {
        String sql = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, fecha_modificacion, comentario) "
                   + "VALUES (?, ?, ?, datetime('now','localtime'), ?)";

        try (Connection con = Jdbc.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, historial.getId_incidencia());
            pst.setString(2, historial.getId_usuario());
            pst.setString(3, historial.getEstado_nuevo());
            pst.setString(4, historial.getComentario());

            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar registro en el historial", e);
        }
    }
}