package modelo;

import java.util.ArrayList;
import java.util.List;

public class IncidenciaDTO {

	private int idIncidencia;
	private String tipo;
	private int id_tipo;
	private String descripcion;
	private String estado;
	private String fecha; // Para poder ordenar
	private String localización;
	private List<HistorialDTO> historial;


	// Nuevos atributos para historia de Usuario
	private int horas_estimadas;
	private String descripcion_trabajos;
	private String descripcionCiudadano;
	private String tiempoResolucion;

	// Constructor de la clase
	public IncidenciaDTO() {}
	
	public IncidenciaDTO(int idIncidencia, String tipo, String titulo, String fecha, String estado) {
		this.idIncidencia = idIncidencia;
		this.tipo = tipo;
		this.descripcion = titulo;
		this.setFecha(fecha);
		this.estado = estado;
		this.historial = new ArrayList<>();
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
	
	public String getTiempoResolucion() {
		return tiempoResolucion;
	}
	
	public List<HistorialDTO> getHistorial(){
		return historial;
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

	public String getDescripcionCiudadano() {
		return descripcionCiudadano;
	}

	public void setDescripcionCiudadano(String descripcionCiudadano) {
		this.descripcionCiudadano = descripcionCiudadano;
	}

	public void setTiempoResolucion(String resol) {
		this.tiempoResolucion = resol;
	}
	
	public void setHistorial(List<HistorialDTO> hist) {
		this.historial = hist;
	}
	
	@Override
	public String toString() {
	    return "ID: " + idIncidencia + " - " + descripcion;
	}

	public int getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}
}
