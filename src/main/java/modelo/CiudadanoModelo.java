package modelo;

import java.util.List;

import util.Database;

public class CiudadanoModelo {

    // Creamos la instancia de la base de datos como en la plantilla
    private Database db = new Database();

    
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