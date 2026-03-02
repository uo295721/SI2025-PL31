package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class VentanaTecnico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaIncidencias;
	private JButton btnPlanificar;

	/**
	 * Create the frame.
	 */
	public VentanaTecnico() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablaIncidencias = new JTable();
		tablaIncidencias.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tablaIncidencias);
		
		btnPlanificar = new JButton("Planificar Incidencia");
		contentPane.add(btnPlanificar, BorderLayout.SOUTH);

	}

	public JTable getTablaIncidencias() {
		return tablaIncidencias;
	}

	public JButton getBtnPlanificar() {
		return btnPlanificar;
	}

	public int getFilaSeleccionada() {
		return tablaIncidencias.getSelectedRow();
	}
	
}
