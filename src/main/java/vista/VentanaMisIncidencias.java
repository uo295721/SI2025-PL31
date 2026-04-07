package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class VentanaMisIncidencias extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> cbEstados;
	private JTable tablaIncidencias;
	private JButton btnNuevaIncidencia;
	private JButton btnReabrir; // NUEVO

	public VentanaMisIncidencias() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));
		setTitle("Portal del Ciudadano: Mis Incidencias");
		
		JPanel panelFiltro = new JPanel();
		contentPane.add(panelFiltro, BorderLayout.NORTH);
		panelFiltro.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblFiltro = new JLabel("Filtrar por estado:");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelFiltro.add(lblFiltro);
		
		cbEstados = new JComboBox<String>();
		cbEstados.setModel(new DefaultComboBoxModel<String>(new String[] {
			    "Todas", "Nueva", "Validada", "Asignada", "En curso", "Resuelta", "Rechazada", "Cerrada"
			}));
		panelFiltro.add(cbEstados);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablaIncidencias = new JTable();
		tablaIncidencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selección de una sola fila
		tablaIncidencias.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tablaIncidencias);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

		btnNuevaIncidencia = new JButton("Nueva incidencia");
		panelSur.add(btnNuevaIncidencia);
		
		// NUEVO BOTÓN SPRINT 3
		btnReabrir = new JButton("Reabrir incidencia");
		btnReabrir.setEnabled(false); // Inactivo por defecto
		panelSur.add(btnReabrir);
	}

	public JTable getTablaIncidencias() { return tablaIncidencias; }
	public JComboBox<String> getCbEstados() { return cbEstados; }
	public JButton getBtnNuevaIncidencia() { return btnNuevaIncidencia; }
	public JButton getBtnReabrir() { return btnReabrir; } // Getter nuevo
}