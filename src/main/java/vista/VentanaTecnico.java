package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;

public class VentanaTecnico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaIncidencias;
	private JTextField txtHoras;
	private JTextArea txtAreaTrabajos;
	private JButton btnGuardar;
	private JButton btnGestionarTareas;

	

	/**
	 * Create the frame.
	 */
	public VentanaTecnico() {
		// 1. Título de la ventana
		setTitle("Panel de Planificación Técnica - Gestión de Incidencias");
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500); // Aumentamos tamaño para que quepa el panel inferior
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));
		
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablaIncidencias = new JTable();
		tablaIncidencias.setToolTipText("Seleccione una incidencia para habilitar la planificación");
		scrollPane.setViewportView(tablaIncidencias);
		
		JPanel panelPlanificacion = new JPanel();
		panelPlanificacion.setPreferredSize(new Dimension(10, 180));
		panelPlanificacion.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
				"Detalles de Planificación", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panelPlanificacion, BorderLayout.SOUTH);
		panelPlanificacion.setLayout(null);
		
		JLabel lblHoras = new JLabel("Horas estimadas:");
		lblHoras.setBounds(20, 30, 120, 16);
		panelPlanificacion.add(lblHoras);
		
		txtHoras = new JTextField();
		txtHoras.setBounds(140, 25, 80, 26);
		panelPlanificacion.add(txtHoras);
		txtHoras.setColumns(10);
		
		JLabel lblTrabajos = new JLabel("Descripción del trabajo:");
		lblTrabajos.setBounds(20, 65, 160, 16);
		panelPlanificacion.add(lblTrabajos);
		
		JScrollPane scrollArea = new JScrollPane();
		scrollArea.setBounds(20, 85, 540, 50);
		panelPlanificacion.add(scrollArea);
		
		txtAreaTrabajos = new JTextArea();
		txtAreaTrabajos.setLineWrap(true);
		txtAreaTrabajos.setWrapStyleWord(true);
		scrollArea.setViewportView(txtAreaTrabajos);
		
		btnGuardar = new JButton("Guardar Planificación");
		btnGuardar.setBounds(380, 145, 180, 29);
		panelPlanificacion.add(btnGuardar);
		
        btnGestionarTareas = new JButton("Gestionar Tareas Diarias");
        btnGestionarTareas.setBounds(20, 145, 200, 29); 
        panelPlanificacion.add(btnGestionarTareas);
		

		desactivarCampos();
	}
	
	public void activarCampos() {
		txtHoras.setEnabled(true);
		txtAreaTrabajos.setEnabled(true);
		btnGuardar.setEnabled(true);
	}

	public void desactivarCampos() {
		txtHoras.setEnabled(false);
		txtAreaTrabajos.setEnabled(false);
		btnGuardar.setEnabled(false);
		txtHoras.setText("");
		txtAreaTrabajos.setText("");
	}
	
	public JTable getTablaIncidencias() {
		return tablaIncidencias;
	}

	public JTextField getTxtHoras() {
		return txtHoras;
	}

	public JTextArea getTxtAreaTrabajos() {
		return txtAreaTrabajos;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public DefaultTableModel getModeloTabla() {
		return (DefaultTableModel) tablaIncidencias.getModel();
	}

	public int getFilaSeleccionada() {
		return tablaIncidencias.getSelectedRow();
	}
	public JButton getBtnGestionarTareas() {
		return btnGestionarTareas;
	}

}