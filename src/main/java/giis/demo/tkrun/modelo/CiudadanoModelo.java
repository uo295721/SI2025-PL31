package giis.demo.tkrun.modelo;

import java.sql.*;

import giis.demo.util.Database;

public class CiudadanoModelo {

    public CiudadanoDTO loginCiudadano(String identificador) {
    	
        String sql = "SELECT dni, nombre, email FROM Ciudadano WHERE dni = ? OR email = ?";
        
        try (Connection con = Database.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            	pst.setString(1, identificador);
            	pst.setString(2, identificador);
            	ResultSet rs = pst.executeQuery();
            
            	if (rs.next()) {
            		// Si existe, metemos los datos en el DTO (la maleta)
            		CiudadanoDTO ciudadano = new CiudadanoDTO(
            				rs.getString("dni"),
            				rs.getString("nombre"),
            				rs.getString("email"));
                	return ciudadano;
            	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no existe o hay error
    }
}