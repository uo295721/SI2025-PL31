package giis.demo.tkrun.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.Jdbc;

public class IncidenciasCModelo {
    private Connection conexion;

    public IncidenciasCModelo() {
        try {
            // El propio modelo establece la conexión al instanciarse
            this.conexion = Jdbc.getConnection();
            System.out.println("Modelo: Conexión establecida con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al conectar desde el Modelo: " + e.getMessage());
        }
    }
    
    //Este metodo obtiene el id de unn tecnico a partir de su email 
    public String getIdUsuarioByEmail(String email) {
        // Consulta para obtener el ID (TEXT) basado en el nombre
        String sql = "SELECT id_usuario FROM Usuario WHERE email = ?";
        
        // Uso la conexión que tenemos en el modelo para conectarse a la base
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("id_usuario");
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el ID del usuario: " + e.getMessage());
        }
        
        return null; // Retorna null si no lo encuentra
    }
    
    public List<Object[]> obtenerIncidenciasProceso(String idTecnico) throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id_incidencia, tipo, fecha, localización, horas_estimadas " +
                     "FROM Incidencia WHERE id_tecnico = ? AND estado = 'Proceso'";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idTecnico);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("id_incidencia"),
                        rs.getString("tipo"),
                        rs.getString("fecha"),
                        rs.getString("localización"),
                        rs.getInt("horas_estimadas")
                    });
                }
            }
        }
        return lista;
    }

    /**
     * Proceso transaccional de resolución.
     */
    public boolean marcarComoResuelta(int idIncidencia, String idTecnico, double tiempoReal, String trabajos) throws SQLException {
        String sqlUpdate = "UPDATE Incidencia SET estado = 'Resuelta', " +
                           "descripcion_trabajos = ?, coste = ? WHERE id_incidencia = ?";
        
        String sqlHistorial = "INSERT INTO Historial (id_incidencia, id_usuario, estado_nuevo, " +
                              "fecha_modificacion, comentario) VALUES (?, ?, 'Resuelta', datetime('now'), ?)";

        try {
            conexion.setAutoCommit(false); // Inicia transacción

            // 1. Update Incidencia
            try (PreparedStatement psUpdate = conexion.prepareStatement(sqlUpdate)) {
                psUpdate.setString(1, trabajos);
                psUpdate.setDouble(2, tiempoReal);
                psUpdate.setInt(3, idIncidencia);
                psUpdate.executeUpdate();
            }

            // 2. Insert Historial
            try (PreparedStatement psHist = conexion.prepareStatement(sqlHistorial)) {
                psHist.setInt(1, idIncidencia);
                psHist.setString(2, idTecnico);
                psHist.setString(3, trabajos);
                psHist.executeUpdate();
            }

            conexion.commit();
            return true;
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(true);
        }
    }
}