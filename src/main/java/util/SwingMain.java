package util;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controlador.Clasificar_Incidencias;
import controlador.IncidenciaControlador;
import controlador.InformeResponsableControlador;
import controlador.OperadorControlador;
import controlador.RechazoIncidenciaControlador;
import controlador.RegistrarIncidenciasControlador;
import controlador.TecnicoControlador;
import modelo.IncidenciaModelo;
import modelo.UsuarioModelo;
import vista.IncidenciasTecnicoProceso;
import vista.RegistrarIncidencia;
import vista.VentanaInformeResponsable;
import vista.VentanaOperador;
import vista.VentanaTecnico;
import vista.Vista33787;
import vista.VistaRechazoOperador;
import vista.VentanaHistorial;

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
	private UsuarioModelo usuario = new UsuarioModelo();

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingMain window = new SwingMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
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
		frame.setBounds(0, 0, 400, 350);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		JButton btnInicializarBaseDeDatos = new JButton("Inicializar Base de Datos en Blanco");
		btnInicializarBaseDeDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database db = new Database();
				db.createDatabase(false);
			}
		});
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(btnInicializarBaseDeDatos);

		JButton btnCargarDatosIniciales = new JButton("Cargar Datos Iniciales para Pruebas");
		btnCargarDatosIniciales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database db = new Database();
				db.createDatabase(false);
				db.loadDatabase();
			}
		});
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(btnCargarDatosIniciales);

		// Botón para la Historia de Claudia (Técnico - Resolución)
		JButton btnResolver = new JButton("Resolver Incidencias en proceso");
		btnResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idIngresado = JOptionPane.showInputDialog(frame, "Ingrese su ID o Email para :");

				IncidenciasTecnicoProceso vL = new vista.IncidenciasTecnicoProceso(idIngresado);
				IncidenciaModelo modelo = new IncidenciaModelo();
				new IncidenciaControlador(modelo, vL, idIngresado);
				vL.setVisible(true);
			}
		});
		frame.getContentPane().add(btnResolver);

		// Botón para la Historia de Liam (Técnico - Planificación)
		JButton btnPlanificar = new JButton("Planificar resolución de incidencia");
		btnPlanificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idIngresado = JOptionPane.showInputDialog(frame, "Ingrese su ID o Email de Técnico:");

				if (idIngresado == null || idIngresado.trim().isEmpty())
					return;

				if (usuario.esUsuarioConRol(idIngresado, "TÉCNICO")) {

					VentanaTecnico vT = new vista.VentanaTecnico();
					IncidenciaModelo iM = new IncidenciaModelo();

					// Pasamos el id al controlador para usarlo posteriormente
					new TecnicoControlador(iM, vT, idIngresado);
					vT.setVisible(true);

				} else {

					JOptionPane.showMessageDialog(frame,
							"Acceso denegado. No se encuentra dicho técnico o no tiene permisos.", "Error de Acceso",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		frame.getContentPane().add(btnPlanificar);
		
		//Botón para la Historia de Liam (Ciudadano - Consultar incidencias)
		JButton btnConsultarIncidencias = new JButton("Consultar mis incidencias (ciudadano)");
		btnConsultarIncidencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = JOptionPane.showInputDialog(frame, "DNI o EMAIL:");
				if (id != null  && usuario.esUsuario(id)) {
					
					String idReal = usuario.asegurarID(id);
					vista.VentanaMisIncidencias vMI = new vista.VentanaMisIncidencias();
					new controlador.ConsultaIncidenciasControlador(vMI, new IncidenciaModelo(), idReal);
					vMI.setVisible(true);
				}
				
			}
		});
		frame.getContentPane().add(btnConsultarIncidencias);

		// Botón para la Historia de Hugo (Operador - Asignar)
		JButton btnAsignar = new JButton("Asignar incidencia a técnico como operador");
		btnAsignar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VentanaOperador vH = new vista.VentanaOperador();
				IncidenciaModelo modelo = new IncidenciaModelo();
				OperadorControlador controladorH = new OperadorControlador(vH, modelo);
				vH.setVisible(true);
			}

		});
		frame.getContentPane().add(btnAsignar);

		// Botón para la Historia de Dani (Registrar - Incidencia)
		JButton btnRegistrarI = new JButton("Registrar una nueva incidencia");
		btnRegistrarI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String idIntroducido = JOptionPane.showInputDialog(frame, "Ingrese su DNI o Email para identificarse:");

				if (idIntroducido == null || idIntroducido.trim().isEmpty())
					return;

				if (usuario.esUsuario(idIntroducido)) {
					RegistrarIncidencia vRI = new RegistrarIncidencia();
					IncidenciaModelo mI = new IncidenciaModelo();
					RegistrarIncidenciasControlador controladorRI = new RegistrarIncidenciasControlador(vRI, mI,
							idIntroducido);
					vRI.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(frame, "Acceso denegado. No se encuentra al usuario introducido",
							"Error de Acceso", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		frame.getContentPane().add(btnRegistrarI);

		// Botón para la Historia de Dani (Responsable - Informe)
		JButton btnInforme = new JButton("Generar informe de responsable");
		btnInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = JOptionPane.showInputDialog(frame, "Ingrese su Email como responsable:");

				if (email == null || email.trim().isEmpty())
					return;

				if (usuario.esUsuarioPorEmail(email, "RESPONSABLE")) {
					VentanaInformeResponsable vI = new VentanaInformeResponsable(email);
					IncidenciaModelo mI = new IncidenciaModelo();
					UsuarioModelo mU = new UsuarioModelo();

					new InformeResponsableControlador(mI, mU, vI, email);
					vI.setVisible(true);

				} else {

					JOptionPane.showMessageDialog(frame,
							"Acceso denegado. No se encuentra el email del responsable en la base de datos",
							"Error de Acceso", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		frame.getContentPane().add(btnInforme);

		// Botón para la Historia de Claudia (Operador - Planificación)
		JButton btnClasificarI = new JButton("Clasificar incidencias como operador");
		btnClasificarI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String emailIngresado = JOptionPane.showInputDialog(frame, "Ingrese su Email de Operador:");

				if (emailIngresado == null || emailIngresado.trim().isEmpty())
					return;

				if (usuario.esUsuarioConRol(emailIngresado, "OPERADOR")) {

					
					// Obtenemos el ID real (ej. 'O1') usando el email
					String idReal = usuario.getIdUsuarioByEmail(emailIngresado);

					if (idReal != null) {
						Vista33787 vistaHU = new Vista33787(idReal); // La vista mostrará 'O1'
						new Clasificar_Incidencias(new IncidenciaModelo(), vistaHU, idReal);
						vistaHU.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(frame, "No se pudo recuperar el ID del usuario.");
					}

				} else {
					JOptionPane.showMessageDialog(frame, "Acceso denegado: El email no pertenece a un Operador.");
				}
			}
		});
		
		frame.getContentPane().add(btnClasificarI);
		
		// Botón para la historia de Hugo (Historial)
		JButton btnHistorial = new JButton("Visualizar historial de incidencia");
		btnHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = JOptionPane.showInputDialog(frame, "Ingrese su Email (Operador o Técnico):");
				        
				if (email == null || email.trim().isEmpty()) 
					return;

				// Validación de Rol
				if (usuario.esUsuarioConRol(email, "OPERADOR") || usuario.esUsuarioConRol(email, "TÉCNICO")) {
				            
					String idStr = JOptionPane.showInputDialog(frame, "Ingrese el ID de la incidencia:");
					if (idStr == null || idStr.trim().isEmpty()) 
						return;
					
					try {
		                int idInci = Integer.parseInt(idStr);   
		                VentanaHistorial vH = new VentanaHistorial(frame, idInci);
		                modelo.IncidenciaModelo mI = new modelo.IncidenciaModelo();
		                new controlador.HistorialControlador(vH, mI, idInci);             
		                vH.setVisible(true);
		                
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(frame, "Error: El ID debe ser un número entero.", "ID no válido", JOptionPane.ERROR_MESSAGE);
				    }
				} else {
					JOptionPane.showMessageDialog(frame, "Acceso denegado. No tiene permisos de Operador o Técnico.", "Error de Acceso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnHistorial);
		
		JButton btnRechazoClaudia = new JButton("Rechazar Incidencias (Claudia)");
		btnRechazoClaudia.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String idIngresado = JOptionPane.showInputDialog(frame, "Email/Nombre Operador:");
		        if (idIngresado != null && !idIngresado.trim().isEmpty()) {
		            if (usuario.esUsuarioConRol(idIngresado, "OPERADOR")) {
		                // Instancia de la VISTA
		                vista.VistaRechazoOperador v = new vista.VistaRechazoOperador(idIngresado);
		                // Instancia del MODELO
		                modelo.IncidenciaModelo m = new modelo.IncidenciaModelo();
		                // Llamada al CONTROLADOR
		                new controlador.RechazoIncidenciaControlador(v, m, idIngresado);
		                v.setVisible(true);
		            } else {
		                JOptionPane.showMessageDialog(frame, "No es un operador válido.");
		            }
		        }
		    }
		});
		frame.getContentPane().add(btnRechazoClaudia);

	}

	public JFrame getFrame() {
		return this.frame;
	}
}