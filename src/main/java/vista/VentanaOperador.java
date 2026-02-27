package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.TecnicoDTO;

import java.awt.*;
import java.util.List;

public class VentanaOperador extends JFrame {

	private JTable tablaIncidencias;
	private DefaultTableModel modeloTabla;
	private JList<TecnicoDTO> listaTecnicos;
	private DefaultListModel<TecnicoDTO> modeloListaTecnicos;
	private JButton btnAsignar;
	private JButton btnHistorial;
	private JLabel lblEmailOperador;
	private JTextField txtEmail; // Campo para que el operador introduzca su correo

	public VentanaOperador() {
		setTitle("Gestión de Incidencias - Panel de Operador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		getContentPane().setLayout(new BorderLayout(10, 10));

		// Panel superior
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelSuperior.setBackground(new Color(240, 240, 240));
		
		panelSuperior.add(new JLabel("Introduzca su email y pulse enter: "));
		txtEmail = new JTextField(20);
		panelSuperior.add(txtEmail);
		
		lblEmailOperador = new JLabel("Operador: sin identificar");
		lblEmailOperador.setFont(new Font("Arial", Font.BOLD, 12));
		panelSuperior.add(lblEmailOperador);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);

		// Panel central
		String[] columnas = { "ID", "Título", "Fecha", "Estado" };
		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaIncidencias = new JTable(modeloTabla);
		tablaIncidencias.setEnabled(false); // La tabla comienza bloqueada hasta que el operador se identifica
		tablaIncidencias.getTableHeader().setReorderingAllowed(false); // Bloqueamos el movimiento de las columnas con el ratón
		JScrollPane scrollTabla = new JScrollPane(tablaIncidencias);
		scrollTabla.setBorder(BorderFactory.createTitledBorder("1. Seleccione una Incidencia Validada"));
		getContentPane().add(scrollTabla, BorderLayout.CENTER);

		// Panel Derecha
		JPanel panelDerecha = new JPanel(new BorderLayout());
		panelDerecha.setPreferredSize(new Dimension(250, 0));

		modeloListaTecnicos = new DefaultListModel<>();
		listaTecnicos = new JList<>(modeloListaTecnicos);
		listaTecnicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaTecnicos.setEnabled(false); // Empieza bloqueada también

		JScrollPane scrollLista = new JScrollPane(listaTecnicos);
		scrollLista.setBorder(BorderFactory.createTitledBorder("2. Seleccione Técnico"));
		panelDerecha.add(scrollLista, BorderLayout.CENTER);

		getContentPane().add(panelDerecha, BorderLayout.EAST);

		// Panel inferior
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		btnHistorial = new JButton("Ver Historial");
		btnHistorial.setEnabled(false); // Empieza bloqueado igual que el otro
		panelInferior.add(btnHistorial);
		
		
		btnAsignar = new JButton("Asignar Incidencia");
		btnAsignar.setEnabled(false); // Empieza bloqueado también
		btnAsignar.setFont(new Font("Arial", Font.BOLD, 13));
		btnAsignar.setPreferredSize(new Dimension(200, 40));
		panelInferior.add(btnAsignar);
		getContentPane().add(panelInferior, BorderLayout.SOUTH);
	}

	// getters para el controlador
	public JTable getTablaIncidencias() {
		return tablaIncidencias;
	}

	public DefaultTableModel getModeloTabla() {
		return modeloTabla;
	}

	public JList<TecnicoDTO> getListaTecnicos() {
		return listaTecnicos;
	}

	public DefaultListModel<TecnicoDTO> getModeloListaTecnicos() {
		return modeloListaTecnicos;
	}

	public JButton getBtnAsignar() {
		return btnAsignar;
	}
	public JButton getBtnHistorial() {
	    return btnHistorial;
	}
	public JLabel getLblEmailOperador() {
		return lblEmailOperador;
	}
	public JTextField getTxtEmail() {
		return txtEmail;
	}

}
