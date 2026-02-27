package util;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controlador.Controlador33787;
import controlador.IncidenciaControlador;
import controlador.OperadorControlador;
import controlador.TecnicoControlador;
import modelo.IncidenciaModelo;
import modelo.Modelo33787;
import modelo.TecnicoModelo;
import modelo.UsuarioModelo;
import vista.Vista33787;

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
	private modelo.UsuarioModelo usuario = new modelo.UsuarioModelo();

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
	    		IncidenciaModelo modelo = new IncidenciaModelo();
	    		IncidenciaControlador controladorC = new IncidenciaControlador(modelo, vL, idIngresado);
	            vL.setVisible(true);
	    	}
	    	
	    });
	    frame.getContentPane().add(btnClau);

	    // Botón para la Historia de Liam (Técnico - Planificación)
	    JButton btnLiam = new JButton("Historia: Liam");
	    btnLiam.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		String idIngresado = JOptionPane.showInputDialog(frame, "Ingrese su ID o Email de Técnico:");
	    		
	    		if (idIngresado == null || idIngresado.trim().isEmpty())
	    			return;
	    		
	    		if (usuario.esUsuarioConRol(idIngresado, "TÉCNICO")) {
	    			
	    			vista.VentanaTecnico vT = new vista.VentanaTecnico();
	    			modelo.TecnicoModelo mT = new modelo.TecnicoModelo();
	    			
	    			//Pasamos el id al controlador para usarlo posteriormente
	    			new controlador.TecnicoControlador(mT, vT, idIngresado);
	    			vT.setVisible(true);
	    			
	    		} else {
	    			
	    			JOptionPane.showMessageDialog(frame, "Acceso denegado. No se encuentra dicho técnico o no tiene permisos.",
	    											"Error de Acceso", JOptionPane.ERROR_MESSAGE);
	    		}
	    	}
	    	
	    });
	    frame.getContentPane().add(btnLiam);
	    
	    // Botón para la Historia de Hugo (Técnico - Planificación)
	    JButton btnHugo = new JButton("Historia: Hugo");
	    btnHugo.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    			    		
	    		vista.VentanaOperador vH = new vista.VentanaOperador();
	    		IncidenciaModelo modelo = new IncidenciaModelo();
	    		OperadorControlador controladorH = new OperadorControlador(vH, modelo);
	            vH.setVisible(true);
	    	}
	    	
	    });
	    frame.getContentPane().add(btnHugo);
	    
	    JButton btnDani = new JButton("Historia: Dani");
	    btnDani.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    			    		
	    		vista.RegistrarIncidencia rI = new vista.RegistrarIncidencia();
	    		IncidenciaModelo modelo = new IncidenciaModelo();
	    		IncidenciaControlador controladorH = new IncidenciaControlador(modelo, rI);
	            rI.setVisible(true);
	    	}
	    	
	    });
	    frame.getContentPane().add(btnDani);
	    
	    JButton btnClaudia = new JButton("Historia: Claudia (HU 33787 - Clasificar)");
        btnClaudia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idIngresado = JOptionPane.showInputDialog(frame, "Ingrese su Email de Operador:");
                
                if (idIngresado == null || idIngresado.trim().isEmpty()) return;

                if (usuario.esUsuarioConRol(idIngresado, "OPERADOR")) {
                    // Usando los nuevos nombres solicitados
                    Vista33787 vista = new Vista33787(idIngresado);
                    Modelo33787 modelo = new Modelo33787();
                    new Controlador33787(modelo, vista, idIngresado);
                    vista.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Acceso denegado. Se requiere rol OPERADOR.",
                            "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.getContentPane().add(btnClaudia);
	    
	}

}	