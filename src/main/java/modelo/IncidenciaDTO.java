package modelo;

public class IncidenciaDTO {

	private int idIncidencia;
	private String tipo;
	private String descripcion;
	private String estado;
	private String fecha; // Para poder ordenar
	private String localización;

	// Nuevos atributos para historia de Usuario
	private int horas_estimadas;
	private String descripcion_trabajos;
	private String descripción_ciudadano;

	// Constructor de la clase
	public IncidenciaDTO() {

	}

	public IncidenciaDTO(int idIncidencia, String tipo, String titulo, String fecha, String estado) {
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

	public String getEstado() {
		return estado;
	}

	public String getLocalizacion() {
		return localización;
	}

	public int getHoras_estimadas() {
		return horas_estimadas;
	}

	public String getDescripcion_trabajos() {
		return descripcion_trabajos;
	}

	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setTitulo(String titulo) {
		this.descripcion = titulo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setLocalizacion(String localizacion) {
		this.localización = localizacion;
	}

	public void setHoras_estimadas(int horasEstimadas) {
		this.horas_estimadas = horasEstimadas;
	}

	public void setDescripcion_trabajos(String descripcionTrabajos) {
		this.descripcion_trabajos = descripcionTrabajos;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripción_ciudadano() {
		return descripción_ciudadano;
	}

	public void setDescripción_ciudadano(String descripción_ciudadano) {
		this.descripción_ciudadano = descripción_ciudadano;
	}

}
