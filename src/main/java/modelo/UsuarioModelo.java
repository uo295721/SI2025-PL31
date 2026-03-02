package modelo;

import java.util.List;

import util.Database;

public class UsuarioModelo {
	
	private Database db = new Database();
	
	//Comprobamos si el DNI o email dado pertenece a un usuario
	//con un rol especificado
	public boolean esUsuarioConRol(String id, String rol) {
		
		//La consulta busca en la columnda del DNI y la del email
		//filtrando por el rol que pasamos como parámetro
		String sql = "SELECT rol FROM Usuario WHERE (id_usuario = ? or email = ?) AND rol = ?";
		
		List<Object[]> result = db.executeQueryArray(sql, id, id, rol);
		
		return !result.isEmpty();
		
	}

	public boolean esUsuarioPorEmail(String email, String rol) {
		
		//La consulta busca en la columna del email
		//filtrando por el rol que pasamos como parámetro
		email = email.trim();
		String sql = "SELECT rol FROM Usuario WHERE email = ? AND rol = ?";
		
		List<Object[]> result = db.executeQueryArray(sql, email, rol);
		
		return !result.isEmpty();
	}
	
	public boolean esUsuario(String id) {
		
		//La consulta busca en la columna del email
		//filtrando por el rol que pasamos como parámetro
		id = id.trim();
		String sql = "SELECT rol FROM Usuario WHERE (id_usuario = ? or email = ?)";
		
		List<Object[]> result = db.executeQueryArray(sql, id, id);
		
		return !result.isEmpty();
	}
	
	public List<TecnicoDTO> obtenerTodosLosTecnicos(){
		
		//Filtramos por el rol exacto en la BD
		String sql = "SELECT id_usuario, nombre, email FROM Usuario WHERE rol = 'TÉCNICO' "
				   + "ORDER BY nombre";
		
		return db.executeQueryPojo(TecnicoDTO.class, sql);
		
	}
	
    public String getIdUsuarioByEmail(String email) {
    	
        String sql = "SELECT id_usuario FROM Usuario WHERE email = ?";
        
        List<Object[]> result = db.executeQueryArray(sql, email);
        if (result.isEmpty()) {
            return null;
        }
        
        return result.get(0)[0].toString();
    }
	
    public String asegurarID(String input) {
    	
    	if (!input.contains("@"))
    		return input;
    	else
    		return getIdUsuarioByEmail(input);
    	
    }
    
    public CiudadanoDTO loginCiudadano(String identificador) {
        // Definimos la SQL
        String sql = "SELECT dni, nombre, email FROM Ciudadano WHERE dni = ? OR email = ?";
        
        // Ejecutamos la query. 
        List<CiudadanoDTO> ciudadanos = db.executeQueryPojo(CiudadanoDTO.class, sql, identificador, identificador);
        
        //  Comprobamos si encontró a alguien
        if (ciudadanos.isEmpty()) {
            return null; // Si no existe
        } else {
            return ciudadanos.get(0); // Devolvemos el primero encontrado
        }
    }
    
}
