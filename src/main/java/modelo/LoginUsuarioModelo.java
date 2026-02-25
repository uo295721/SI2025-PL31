package modelo;

import java.util.List;

import util.Database;

public class LoginUsuarioModelo {
    
    private Database db = new Database();
  
    public UsuarioDTO validarAcceso(String identificador) {
        
        System.out.println("Buscando en la BD el identificador: [" + identificador + "]");
                
        // Consulta para buscar por ID o por Email
        String sql = "SELECT id_usuario, nombre, email, rol FROM Usuario "
                    + "WHERE id_usuario = ? OR email = ?";
        
        // Ejecutamos la consulta. Pasamos el identificador dos veces para los dos '?'
        List<UsuarioDTO> usuarios = db.executeQueryPojo(UsuarioDTO.class, sql, identificador, identificador);
        
        // Si la lista no está vacía, devolvemos el primer usuario encontrado
        if (usuarios.isEmpty()) {
            return null;
        } else {
            return usuarios.get(0);
        }
    }
}