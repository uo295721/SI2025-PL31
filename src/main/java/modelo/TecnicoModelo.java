package modelo;

import java.util.ArrayList;
import java.util.List;

import util.Database;

public class TecnicoModelo {

    private Database db = new Database();

    
    public List<TecnicoDTO> obtenerTodosLosTecnicos() {
        String sql = "SELECT id_usuario, nombre, email FROM Usuario WHERE rol = 'Técnico' ORDER BY nombre";
        return db.executeQueryPojo(TecnicoDTO.class, sql);
    }

    
    public List<IncidenciaDTO> getIncidenciasAsignadas(String idTecnico) {
    	
    	List<IncidenciaDTO> lista = new ArrayList<>();
    	
        String sql = "SELECT id_incidencia, descripcion, estado, tipo, localización FROM Incidencia " +
                	"WHERE (id_tecnico = ? OR id_tecnico = (SELECT id_usuario FROM Usuario WHERE email = ?)) " +
                	"AND estado IN ('Validada', 'Asignada') ORDER BY id_incidencia ASC";
        
        List<Object[]> filas = db.executeQueryArray(sql, idTecnico, idTecnico);
        
        for (Object[] o : filas) {
        	IncidenciaDTO incidencia = new IncidenciaDTO();
        	
        	incidencia.setIdIncidencia((int) o[0]);
        	incidencia.setTitulo((String) o[1]);
        	incidencia.setEstado((String) o[2]);
        	incidencia.setTipo((String) o[3]);
        	incidencia.setLocalizacion((String) o[4]);
        	incidencia.setDescripcionCiudadano((String) o[1]);
        	
        	lista.add(incidencia);
        }
        
        return lista;
        
    }

    
    public void planificarIncidencia(int idIncidencia, int horas, String trabajos, String emailTecnico) {
        String sqlUpdate = "UPDATE Incidencia SET horas_estimadas = ?, descripcion_trabajos = ?, "
                         + "estado = 'Proceso' WHERE id_incidencia = ?";
        
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