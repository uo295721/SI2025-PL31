package util;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controlador.IncidenciasCControlador;
import modelo.IncidenciasCModelo;

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
	    btnClau.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		String idIngresado = JOptionPane.showInputDialog(frame, "Ingrese su ID o Email para :");
	    		
	    		vista.IncidenciasTecnicoProceso vL = new vista.IncidenciasTecnicoProceso(idIngresado);
	    		IncidenciasCModelo modelo = new IncidenciasCModelo();
	    		IncidenciasCControlador controlador = new IncidenciasCControlador(modelo, vL, idIngresado);
	            vL.setVisible(true);
	    	}
	    	
	    });
	    frame.getContentPane().add(btnClau);

	    // Botón para la Historia de Liam (Técnico - Planificación)
	    /*
	    JButton btnLiam = new JButton("Historia: Liam");
	    btnLiam.addActionListener(e -> ejecutarHistoria("LIAM"));
	    frame.getContentPane().add(btnLiam);
	    */
	}

	/**
	 * Método centralizado que pide login y lanza la vista correspondiente
	 */
}	