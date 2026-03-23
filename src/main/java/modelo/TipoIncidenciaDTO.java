package modelo;

public class TipoIncidenciaDTO {
	
	private int id_tipo;
	private String nombre;
	
	public TipoIncidenciaDTO(){};
	
	public TipoIncidenciaDTO(int id, String nombre) {
		this.id_tipo = id;
		this.nombre = nombre;
	}

	public int getId_tipo() {
		return id_tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
