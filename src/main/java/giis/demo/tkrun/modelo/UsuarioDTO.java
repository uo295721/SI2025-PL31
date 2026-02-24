package giis.demo.tkrun.modelo;

public class UsuarioDTO {
	
	private String idUsuario;
	private String nombre;
	private String email;
	private String rol;
	
	public UsuarioDTO(String id, String nombre, String email, String rol) {
		this.idUsuario = id;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
	}

	
	public String getIdUsuario() {
		return idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEmail() {
		return email;
	}
	public String getRol() {
		return rol;
	}

}
