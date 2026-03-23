package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaOperador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tablaIncidencias;
	private DefaultTableModel modeloTabla;
	private JList<String> listaTecnicos;
	private DefaultListModel<String> modeloListaTecnicos;
	private JButton btnAsignar;
	private JLabel lblEmailOperador;
	private JTextField txtEmail;

	public VentanaOperador() {
		setTitle("Gestión de Incidencias - Panel de Operador");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 950, 600); // Un poco más ancha para las especialidades
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

		String[] columnas = { "ID", "Título", "Fecha", "Estado", "Tipo ID" };
		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaIncidencias = new JTable(modeloTabla);
		tablaIncidencias.setEnabled(false);
		tablaIncidencias.getTableHeader().setReorderingAllowed(false);

		// tablaIncidencias.getColumnModel().getColumn(4).setMinWidth(0);
		// tablaIncidencias.getColumnModel().getColumn(4).setMaxWidth(0);

		JScrollPane scrollTabla = new JScrollPane(tablaIncidencias);
		scrollTabla.setBorder(BorderFactory.createTitledBorder("1. Seleccione una Incidencia Validada"));
		getContentPane().add(scrollTabla, BorderLayout.CENTER);

		// Panel Derecha
		JPanel panelDerecha = new JPanel(new BorderLayout());
		panelDerecha.setPreferredSize(new Dimension(350, 0));

		modeloListaTecnicos = new DefaultListModel<>();
		listaTecnicos = new JList<>(modeloListaTecnicos);
		listaTecnicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaTecnicos.setEnabled(false);

		JScrollPane scrollLista = new JScrollPane(listaTecnicos);
		scrollLista.setBorder(BorderFactory.createTitledBorder("2. Técnicos Especialistas"));
		panelDerecha.add(scrollLista, BorderLayout.CENTER);

		getContentPane().add(panelDerecha, BorderLayout.EAST);

		// Panel inferior
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		btnAsignar = new JButton("Asignar Incidencia");
		btnAsignar.setEnabled(false);
		btnAsignar.setFont(new Font("Arial", Font.BOLD, 13));
		btnAsignar.setPreferredSize(new Dimension(200, 40));
		panelInferior.add(btnAsignar);
		getContentPane().add(panelInferior, BorderLayout.SOUTH);
	}

	// Getters actualizados
	public JTable getTablaIncidencias() {
		return tablaIncidencias;
	}

	public DefaultTableModel getModeloTabla() {
		return modeloTabla;
	}

	public JList<String> getListaTecnicos() {
		return listaTecnicos;
	}

	public DefaultListModel<String> getModeloListaTecnicos() {
		return modeloListaTecnicos;
	}

	public JButton getBtnAsignar() {
		return btnAsignar;
	}

	public JLabel getLblEmailOperador() {
		return lblEmailOperador;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}
}