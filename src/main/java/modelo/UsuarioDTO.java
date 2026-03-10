package modelo;

public class UsuarioDTO {
    // Nombres exactos de las columnas en tu SQL
    private String id_usuario; 
    private String nombre;
    private String email;
    private String rol;

    public UsuarioDTO() {}

    public UsuarioDTO(String id, String nombre, String email, String rol) {
        this.id_usuario = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public String getIdUsuario() { return id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}