package giis.demo.tkrun.modelo;

/**
 * Clase DTO para representar una entrada en el historial de modificaciones
 * de una incidencia. Refleja la estructura de la tabla Historial.
 */
public class HistorialDTO {
    
    private int id_modificacion;
    private int id_incidencia;
    private String id_usuario; 
    private String estado_nuevo;
    private String fecha_modificacion; 
    private String comentario;

  
    public HistorialDTO() {}

    
    public HistorialDTO(int id_modificacion, int id_incidencia, String id_usuario, 
                        String estado_nuevo, String fecha_modificacion, String comentario) {
        this.id_modificacion = id_modificacion;
        this.id_incidencia = id_incidencia;
        this.id_usuario = id_usuario;
        this.estado_nuevo = estado_nuevo;
        this.fecha_modificacion = fecha_modificacion;
        this.comentario = comentario;
    }

    // Getters y Setters
    public int getId_modificacion() { return id_modificacion; }
    public void setId_modificacion(int id_modificacion) { this.id_modificacion = id_modificacion; }

    public int getId_incidencia() { return id_incidencia; }
    public void setId_incidencia(int id_incidencia) { this.id_incidencia = id_incidencia; }

    public String getId_usuario() { return id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }

    public String getEstado_nuevo() { return estado_nuevo; }
    public void setEstado_nuevo(String estado_nuevo) { this.estado_nuevo = estado_nuevo; }

    public String getFecha_modificacion() { return fecha_modificacion; }
    public void setFecha_modificacion(String fecha_modificacion) { this.fecha_modificacion = fecha_modificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
