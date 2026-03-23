package modelo;

public class TecnicoDTO extends UsuarioDTO {
	private int carga;

	public TecnicoDTO() {
		super();
	}

	public TecnicoDTO(String id, String nombre, String email) {
		super(id, nombre, email, "Técnico", null);
		this.carga = 0;
	}

	public TecnicoDTO(String id, String nombre, String email, String especialidad) {
		super(id, nombre, email, "Técnico", especialidad);
		this.carga = 0;
	}

	public int getCarga() {
		return carga;
	}

	public void setCarga(int carga) {
		this.carga = carga;
	}

	@Override
	public String toString() {
		return this.getNombre() + " (Carga: " + carga + ")";
	}
}