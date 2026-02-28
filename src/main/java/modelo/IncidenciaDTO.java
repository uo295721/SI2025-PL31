package modelo;

public class IncidenciaDTO {

	private int idIncidencia;
	private String tipo;
	private String descripcion;
	private String descripcionCiudadano;
	private String estado;
	private String fecha; // Para poder ordenar
	private String localización;
	
	// Nuevos atributos para historia de Usuario
	private int horasEstimadas;
	private String descripcionTrabajos;
	
	// Constructor de la clase
	public IncidenciaDTO() {}
	
	public IncidenciaDTO(int idIncidencia,String tipo, String titulo, String fecha, String estado) {
		this.idIncidencia = idIncidencia;
		this.tipo = tipo;
		this.descripcion = titulo;
		this.setFecha(fecha);
		this.estado = estado;
	}

	// Getters y Setters correspondientes para cada atributo
	
	public int getIdIncidencia() {
		return idIncidencia;
	}
	
	public String getTipo() {
		return tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getDescripcionCiudadano() {
		return descripcionCiudadano;
	}

	public String getEstado() {
		return estado;
	}
	
	public String getLocalizacion() {
		return localización;
	}

	public int getHorasEstimadas() {
		return horasEstimadas;
	}

	public String getDescripcionTrabajos() {
		return descripcionTrabajos;
	}

	public void setId_incidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setId_ciudadano(String id) {
		this.descripcionCiudadano = id;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void setLocalización(String localizacion) {
		this.localización = localizacion;
	}

	public void setHoras_estimadas(int horasEstimadas) {
		this.horasEstimadas = horasEstimadas;
	}

	public void setDescripcion_trabajos(String descripcionTrabajos) {
		this.descripcionTrabajos = descripcionTrabajos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
