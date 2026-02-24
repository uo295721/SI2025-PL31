package util;

import controlador.LoginControlador;
import modelo.LoginUsuarioModelo;
import vista.VentanaLogin;

public class SwingMain {

    public static void main(String[] args) {
        
        VentanaLogin vista = new VentanaLogin();
        LoginUsuarioModelo login = new LoginUsuarioModelo();
        
        // Creo el controlador que gestionará el acceso
        new LoginControlador(vista,login);
        
        // Hacemos visible la ventana principal
        vista.setVisible(true);
        vista.setLocationRelativeTo(null); // Para que salga centrada
        
        System.out.println("Sistema de Gestión de Incidencias: Esperando identificación...");
        
    }
}