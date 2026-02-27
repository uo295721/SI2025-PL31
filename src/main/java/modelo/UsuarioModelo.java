package modelo;

import java.util.List;

import util.Database;

public class UsuarioModelo {
	
	private Database db = new Database();
	
	//Comprobamos si un ID (DNI/email) pertenece a un usuario
	//con un rol especificado
	public boolean esUsuarioConRol(String id, String rol) {
		
		//La consulta busca en la columnda del DNI y la del email
		//filtrando por el rol que pasamos como parámetro
		String sql = "SELECT rol FROM Usuario WHERE (id_usuario = ? or email = ?) AND rol = ?";
		
		List<Object[]> result = db.executeQueryArray(sql, id, id, rol);
		
		return !result.isEmpty();
		
	}

}
