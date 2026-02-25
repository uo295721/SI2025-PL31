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
		
		JButton btnEjecutarClau = new JButton("Botón Clau");
		btnEjecutarClau.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				VentanaLogin vista = new VentanaLogin();
		        LoginUsuarioModelo login = new LoginUsuarioModelo();
		        
		        // Creo el controlador que gestionará el acceso
		        new LoginControlador(vista,login);
		        
		        // Hacemos visible la ventana principal
		        vista.setVisible(true);
		        vista.setLocationRelativeTo(null); // Para que salga centrada
		        
		        System.out.println("Sistema de Gestión de Incidencias: Esperando identificación...");
			}
		});
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(btnEjecutarClau);
		/*
				
				
		
		
				
		frame.getContentPane().add(btnEjecutar33511);
		
		JButton btnEntregaReportaje = new JButton("Boton Dani");
		btnEntregaReportaje.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				String nombre = javax.swing.JOptionPane.showInputDialog("Introduzca su nombre de reportero:");
				if (nombre != null && !nombre.trim().isEmpty()) {
					EntregaReportajeController controller = new EntregaReportajeController(
						new EntregaReportajeModel(), 
						new EntregaReportajeView(), 
						nombre
					);
					controller.initController();
				}
			}
		});
		frame.getContentPane().add(btnEntregaReportaje);

		JButton btnOfrecerReportajes = new JButton("Boton Hugo");
		btnOfrecerReportajes.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				String nombre = javax.swing.JOptionPane.showInputDialog("Introduzca el nombre de su agencia: ");
				if(nombre != null && !nombre.trim().isEmpty()) {
					ReportajeController controller=new ReportajeController(new ReportajeModel(), new ReportajeVista(), nombre);
					controller.initController();
				}
				
			}
		});
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(btnOfrecerReportajes);

		JButton btnOfrecimiento = new JButton("Gestión de los ofrecimientos a una empresa ( #33514)");
		btnOfrecimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String empresa = javax.swing.JOptionPane.showInputDialog("Introduzca el nombre de la empresa: ");
				if(empresa != null) {
					OfrecimientoController controller = new OfrecimientoController(
						new OfrecimientoModel(),
						new OfrecimientoView(),
						empresa
					);
					controller.initController();
				}
			}
		});
		frame.getContentPane().add(btnOfrecimiento);
		
		
		JButton btnVerReportaje = new JButton("Boton Liam);
        btnVerReportaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empresa = javax.swing.JOptionPane.showInputDialog("Introduzca el nombre de la empresa: ");
                if(empresa != null && !empresa.trim().isEmpty()) {
                    ReportajeOfrecimientoController controller = new ReportajeOfrecimientoController(
                        new ReportajeOfrecimientoModel(),
                        new ReportajeOfrecimientoView(),
                        empresa
                    );
                    controller.initController();
                }
            }
        });
        frame.getContentPane().add(btnVerReportaje);
		*/
	}
	
		
	

	public JFrame getFrame() { return this.frame; }
}