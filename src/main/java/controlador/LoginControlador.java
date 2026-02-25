package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.IncidenciaModelo;
import modelo.LoginUsuarioModelo;
import modelo.TecnicoModelo;
import modelo.UsuarioDTO;
import vista.VentanaLogin;
import vista.VentanaMisIncidencias;
import vista.VentanaOperador;
import vista.VentanaTecnico;



public class LoginControlador {
	
	private VentanaLogin ventanaLogin;
	private LoginUsuarioModelo login;
	
	public LoginControlador(VentanaLogin ventanaLogin, LoginUsuarioModelo login) {
		this.ventanaLogin = ventanaLogin;
		this.login = login;
		
		this.ventanaLogin.getBtnAceptar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				intentarAcceder();
			}
		});
		
		this.ventanaLogin.getBtnCancelar().addActionListener(e -> System.exit(0));
		
	}
	
	private void intentarAcceder() {
		
		String identificador = ventanaLogin.getTxtUsuario().getText().trim();
		
		if (identificador.isEmpty()) {
			JOptionPane.showMessageDialog(ventanaLogin,"Por favor, introduzca su DNI o EMAIL para continuar.");
			return;
		}
		
		//El modelo busca al Usuario y nos devuelve su DTO(Ciudadano, Operador, Técnico)
		UsuarioDTO usuario = login.validarAcceso(identificador);
		
		if (usuario == null) {
			JOptionPane.showMessageDialog(ventanaLogin, "No se ha encontrado a ningún usuario con ese DNI o EMAIL");
		} else {
			lanzarAplicacionSegunRol(usuario); //Si el usuario existe, abrimos sus ventanas específicas
			ventanaLogin.dispose(); //Cerramos la ventana de Login
		}
		
	}
	
	private void lanzarAplicacionSegunRol(UsuarioDTO usuario) {
		//Usamos el ID (DNI) y el ROL que nos llega desde la BD
		String rol = usuario.getRol().toUpperCase();
		String id = usuario.getIdUsuario();
		
		switch (rol) {
		case "CIUDADANO":
			VentanaMisIncidencias vMI = new VentanaMisIncidencias();
			IncidenciaModelo iM = new IncidenciaModelo();
			new ConsultaIncidenciasControlador(vMI, iM, id);
			vMI.setVisible(true);
			break;
			
		case "TÉCNICO":
			
			VentanaTecnico vT = new VentanaTecnico();
			TecnicoModelo tM = new TecnicoModelo();
			new TecnicoControlador(tM, vT, id);
			vT.setVisible(true);
			break;
			
			
		case "OPERADOR":
			VentanaOperador vO = new VentanaOperador();
			IncidenciaModelo iO = new IncidenciaModelo();
			new OperadorControlador(vO, iO);
			vO.setVisible(true);
			break;
		
		default:
			JOptionPane.showMessageDialog(ventanaLogin, "Error: Rol de usuario no definido.");
			break;
		}
		
	}
	
}