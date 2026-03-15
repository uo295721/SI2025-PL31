package modelo;

public class CiudadanoDTO extends UsuarioDTO {
    
    public CiudadanoDTO(String Dni, String Nombre, String Email)
	{
    	super(Dni, Nombre, Email, "Ciudadano",null);
	}
    
}