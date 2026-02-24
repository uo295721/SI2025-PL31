package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario; 
	private JButton btnAceptar;
	private JButton btnCancelar;

	public VentanaLogin() {
		setTitle("Acceso Directo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 200); // Reducido el alto a 200
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Identificación de Usuario");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblTitulo.setBounds(50, 20, 250, 20);
		panel.add(lblTitulo);
		
		JLabel lblUsuario = new JLabel("DNI / EMAIL:");
		lblUsuario.setBounds(30, 70, 100, 16);
		panel.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(130, 65, 180, 26);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		btnAceptar = new JButton("Entrar");
		btnAceptar.setBounds(193, 120, 117, 29); // Subido el botón
		panel.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(30, 120, 117, 29);
		panel.add(btnCancelar);
	}

	// --- GETTERS ---
	
	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
}
