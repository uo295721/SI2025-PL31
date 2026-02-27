package modelo;

public class IncidenciaDTO {

	private int idIncidencia;
	private String tipo;
	private String descripcion;
	private String descripcionCiudadano;
	private String estado;
	private String fecha; // Para poder ordenar
	private String localizacion;
	
	// Nuevos atributos para historia de Usuario
	private int horasEstimadas;
	private String descripcionTrabajos;
	
	// Constructor de la clase
	public IncidenciaDTO() {
		
	}
	
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
		return localizacion;
	}

	public int getHorasEstimadas() {
		return horasEstimadas;
	}

	public String getDescripcionTrabajos() {
		return descripcionTrabajos;
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

	public void setDescripcionCiudadano(String descripcionCiudadano) {
		this.descripcionCiudadano = descripcionCiudadano;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public void setHorasEstimadas(int horasEstimadas) {
		this.horasEstimadas = horasEstimadas;
	}

	public void setDescripcionTrabajos(String descripcionTrabajos) {
		this.descripcionTrabajos = descripcionTrabajos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
