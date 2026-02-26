package vista;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class VentanaMisIncidencias extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> cbEstados;
	private JTable tablaIncidencias;
	private JButton btnNuevaIncidencia;


	public VentanaMisIncidencias() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Filtrar por estado:");
		panel.add(lblNewLabel);
		
		cbEstados = new JComboBox<String>();
		cbEstados.setModel(new DefaultComboBoxModel<String>(new String[] {"Todas", "Nueva", "Validada", "Asignada", "En curso", "Resuelta"}));
		panel.add(cbEstados);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablaIncidencias = new JTable();
		tablaIncidencias.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tablaIncidencias);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnNuevaIncidencia = new JButton("Nueva incidencia");
		panelSur.add(btnNuevaIncidencia);

	}

	public JTable getTablaIncidencias() {
		return tablaIncidencias;
	}
	
	public JComboBox<String> getCbEstados() {
		return cbEstados;
	}
	
	public JButton getBtnNuevaIncidencia() {
	    return btnNuevaIncidencia;
	}

}
