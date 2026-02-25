package util;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controlador.LoginControlador;
import modelo.LoginUsuarioModelo;
import vista.VentanaLogin;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Punto de entrada principal que incluye botones para la ejecucion de las
 * pantallas de las aplicaciones de ejemplo y acciones de inicializacion de la
 * base de datos. No sigue MVC pues es solamente temporal para que durante el
 * desarrollo se tenga posibilidad de realizar acciones de inicializacion
 */
public class SwingMain {

	private JFrame frame;

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { // NOSONAR codigo autogenerado
			public void run() {
				try {
					SwingMain window = new SwingMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); // NOSONAR codigo autogenerado
				}
			}
		});
	}

	/**
	 * Create the application
	 */
	public SwingMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Main");
		frame.setBounds(0, 0, 287, 185); 
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		JButton btnInicializarBaseDeDatos = new JButton("Inicializar Base de Datos en Blanco");
		btnInicializarBaseDeDatos.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				Database db = new Database();
				db.createDatabase(false);
			}
		});
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(btnInicializarBaseDeDatos);
			
		JButton btnCargarDatosIniciales = new JButton("Cargar Datos Iniciales para Pruebas");
		btnCargarDatosIniciales.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				Database db = new Database();
				db.createDatabase(false);
				db.loadDatabase();
			}
		});
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(btnCargarDatosIniciales);
		
		// Botón para la Historia de Claudia (Técnico - Resolución)
	    JButton btnClau = new JButton("Historia: Claudia");
	    btnClau.addActionListener(e -> ejecutarHistoria("CLAUDIA"));
	    frame.getContentPane().add(btnClau);

	    // Botón para la Historia de Liam (Técnico - Planificación)
	    JButton btnLiam = new JButton("Historia: Liam");
	    btnLiam.addActionListener(e -> ejecutarHistoria("LIAM"));
	    frame.getContentPane().add(btnLiam);
	}

	/**
	 * Método centralizado que pide login y lanza la vista correspondiente
	 */
	private void ejecutarHistoria(String integrante) {
	    // 1. Panel emergente para pedir identificación
	    String idIngresado = JOptionPane.showInputDialog(frame, "Ingrese su ID o Email para " + integrante + ":");
	    
	    if (idIngresado == null || idIngresado.trim().isEmpty()) return;

	    // 2. Validamos contra la BD usando el modelo existente
	    modelo.LoginUsuarioModelo modeloLogin = new modelo.LoginUsuarioModelo();
	    modelo.UsuarioDTO usuario = modeloLogin.validarAcceso(idIngresado.trim());

	    if (usuario == null) {
	        JOptionPane.showMessageDialog(frame, "Usuario no encontrado en la base de datos.");
	        return;
	    }

	    // 3. Si el usuario existe, lanzamos la vista que toca según el botón pulsado
	    lanzarVistaEspecifica(integrante, usuario);
	}

	private void lanzarVistaEspecifica(String integrante, modelo.UsuarioDTO usuario) {
	    String id = usuario.getIdUsuario();
	    
	    switch (integrante) {
	        case "CLAUDIA":
	            
	         // Aquí lanzas la vista de planificación directamente
	            vista.IncidenciasTecnicoProceso vL = new vista.IncidenciasTecnicoProceso(id);
	            vL.setVisible(true);
	            break;
	        case "LIAM":
	            
	        	vista.VentanaTecnico vT = new vista.VentanaTecnico();
	            new controlador.TecnicoControlador(new modelo.TecnicoModelo(), vT, id);
	            vT.setVisible(true);
	            break;
	        // Puedes añadir aquí a Daniel y Hugo siguiendo el mismo patrón
	    }
	}
}	