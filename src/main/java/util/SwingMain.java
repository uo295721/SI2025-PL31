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
import controlador.RegistrarIncidenciasControlador;
import controlador.TecnicoControlador;
import modelo.IncidenciaModelo;
import modelo.UsuarioModelo;
import modelo.ZonaModelo;
import vista.IncidenciasTecnicoProceso;
import vista.RegistrarIncidencia;
import vista.VentanaInformeResponsable;
import vista.VentanaOperador;
import vista.VentanaResponsable;
import vista.VentanaTecnico;
import vista.Vista33787;
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

		// Botón para la Historia de Liam (Ciudadano - Consultar incidencias)
		JButton btnConsultarIncidencias = new JButton("Consultar mis incidencias (ciudadano)");
		btnConsultarIncidencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = JOptionPane.showInputDialog(frame, "DNI o EMAIL:");
				if (id != null && usuario.esUsuario(id)) {

					String idReal = usuario.asegurarID(id);
					vista.VentanaMisIncidencias vMI = new vista.VentanaMisIncidencias();
					new controlador.ConsultaIncidenciasControlador(vMI, new IncidenciaModelo(), new ZonaModelo(), idReal);
					vMI.setVisible(true);
				}

			}
		});
		frame.getContentPane().add(btnConsultarIncidencias);
		
		//Botón para la Historia de Liam (Responsable - Cierre de Incidencias)
		JButton btnCierreResponsable = new JButton("Control de calidad (Responsable)");
		btnCierreResponsable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idIngresado = JOptionPane.showInputDialog(frame, "Ingrese su DNI o EMAIL de Responsable:");
				if (idIngresado == null || idIngresado.trim().isEmpty())
					return;
				if (usuario.esUsuarioConRol(idIngresado, "RESPONSABLE")) {
					VentanaResponsable vR = new VentanaResponsable();
					new controlador.ResponsableControlador(vR, idIngresado);
					
					vR.setVisible(true);
					vR.setLocationRelativeTo(null);
				} else {
					JOptionPane.showMessageDialog(frame, "Acceso denegado. No se encuentra al responsable "
							+ "o no tiene permisos.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnCierreResponsable);

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
					ZonaModelo mZ = new ZonaModelo();
					RegistrarIncidenciasControlador controladorRI = new RegistrarIncidenciasControlador(vRI, mI, mZ, idIntroducido);
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

		// Botón para la historia de Hugo (Historial) - Versión Final con Selector
		JButton btnHistorial = new JButton("Visualizar historial de incidencia");
		btnHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = JOptionPane.showInputDialog(frame, "Ingrese su Email (Operador o Técnico):");

				if (email == null || email.trim().isEmpty())
					return;

				if (usuario.esUsuarioConRol(email, "OPERADOR") || usuario.esUsuarioConRol(email, "TÉCNICO")) {

					IncidenciaModelo mI = new IncidenciaModelo();
					// Obtenemos la lista de incidencias desde el método que acabas de crear
					java.util.List<modelo.IncidenciaDTO> listaIncidencias = mI.getTodasLasIncidencias();

					if (listaIncidencias.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "No hay incidencias registradas.");
						return;
					}

					// Mostramos el selector (evita que el usuario tenga que escribir el ID)
					Object seleccion = JOptionPane.showInputDialog(frame,
							"Seleccione la incidencia para ver su historial:", "Consulta de Historial",
							JOptionPane.QUESTION_MESSAGE, null, listaIncidencias.toArray(), listaIncidencias.get(0));

					if (seleccion != null) {
						modelo.IncidenciaDTO inciSeleccionada = (modelo.IncidenciaDTO) seleccion;
						int idInci = inciSeleccionada.getIdIncidencia();

						// Abrimos la ventana de historial con el ID obtenido del objeto seleccionado
						VentanaHistorial vH = new VentanaHistorial(frame, idInci);
						new controlador.HistorialControlador(vH, mI, idInci);
						vH.setVisible(true);
					}

				} else {
					JOptionPane.showMessageDialog(frame, "Acceso denegado. Rol no autorizado.", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(btnHistorial);

	}

	public JFrame getFrame() {
		return this.frame;
	}
}