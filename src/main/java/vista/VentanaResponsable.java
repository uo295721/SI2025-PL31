package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaResponsable extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaIncidencias;
	private DefaultTableModel modeloTabla;
	private JButton btnSeleccionarTodas;
	private JButton btnArchivar;
	private JLabel lblInfoResponsable; 
	
	public VentanaResponsable() {

		setTitle("Control de Calidad - Cierre de Incidencias");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));

		JPanel panelNorte = new JPanel(new BorderLayout());
		JLabel lblTitulo = new JLabel("Control de Calidad: Revisión de Incidencias Resueltas");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelNorte.add(lblTitulo, BorderLayout.NORTH);
		
		lblInfoResponsable = new JLabel("Identificando responsable...");
		lblInfoResponsable.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoResponsable.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panelNorte.add(lblInfoResponsable, BorderLayout.SOUTH);
		
		contentPane.add(panelNorte, BorderLayout.NORTH);

		String[] columnas = {"Seleccionar", "ID", "Descripción", "Localización", "Tipo", "Fecha Registro"};
		
		modeloTabla = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) return Boolean.class;
				return super.getColumnClass(columnIndex);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		};

		tablaIncidencias = new JTable(modeloTabla);
		tablaIncidencias.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tablaIncidencias);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);

		btnSeleccionarTodas = new JButton("Seleccionar Todas");
		panelSur.add(btnSeleccionarTodas);

		btnArchivar = new JButton("Archivar Seleccionadas (Cierre Definitivo)");
		panelSur.add(btnArchivar);
	}

	// GETTERS para que el Controlador pueda manejar la lógica
	public JTable getTablaIncidencias() { return tablaIncidencias; }
	public DefaultTableModel getModeloTabla() { return modeloTabla; }
	public JButton getBtnSeleccionarTodas() { return btnSeleccionarTodas; }
	public JButton getBtnArchivar() { return btnArchivar; }
	public JLabel getLblInfoResponsable() { return lblInfoResponsable; }
	
}