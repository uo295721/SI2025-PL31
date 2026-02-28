package modelo;

public class OperadorDTO {
    private String id_usuario;
    private String nombre;
    private String apellidos;
    private String email;

    public OperadorDTO() {}

    // Getters y Setters
    public String getId_usuario() { return id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}