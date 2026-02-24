package giis.demo.tkrun.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Jdbc;

public class LoginUsuario {
	
	public UsuarioDTO validarAcceso(String identificador) {
		
		System.out.println("Buscando en la BD el identificador: [" + identificador + "]");
				
		//Consulta para buscr por DNI o por Email
		String sql = "Select id_usuario, nombre, email, rol FROM Usuario "
					+"WHERE id_usuario = ? or email = ?";
		
		try(Connection con = Jdbc.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setString(1, identificador);
			stmt.setString(2, identificador);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new UsuarioDTO(
						rs.getString("id_usuario"),
						rs.getString("nombre"),
						rs.getString("email"),
						rs.getString("rol"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Si no hay concidencia
		return null;
	}
}
