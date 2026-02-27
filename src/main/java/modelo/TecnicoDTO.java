package modelo;

public class TecnicoDTO extends UsuarioDTO{
	
	public TecnicoDTO() {
		super();
	}


	public TecnicoDTO(String id, String nombre, String email) {
		super(id, nombre, email, "Técnico");
	}

	@Override
	public String toString() {
		return getNombre(); // Para que el JComboBox o JList muestre el nombre directamente
	}
}