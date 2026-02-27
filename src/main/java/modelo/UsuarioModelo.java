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
}
