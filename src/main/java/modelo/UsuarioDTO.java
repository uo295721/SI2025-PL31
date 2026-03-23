package modelo;

public class UsuarioDTO {
	private String id_usuario;
	private String nombre;
	private String apellidos;
	private String email;
	private String rol;
	private String especialidad;

	public UsuarioDTO() {
	}

	public UsuarioDTO(String id, String nombre, String email, String rol, String especialidad) {
		this.id_usuario = id;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
		this.especialidad = especialidad;
	}

	public String getIdUsuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
}