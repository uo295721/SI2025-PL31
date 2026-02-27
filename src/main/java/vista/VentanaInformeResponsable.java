package vista;

import javax.swing.*;
import java.awt.BorderLayout;

public class VentanaInformeResponsable extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private JLabel lblEmail;
	private JButton btnExportarCSV;

	public VentanaInformeResponsable(String emailResponsable) {
		setTitle("Informe Mensual de Técnicos");
		setBounds(100, 100, 500, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		lblEmail = new JLabel("Responsable: " + emailResponsable);
		getContentPane().add(lblEmail, BorderLayout.NORTH);

		tabla = new JTable();
		getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);

		btnExportarCSV = new JButton("Exportar a CSV");
		getContentPane().add(btnExportarCSV, BorderLayout.SOUTH);
	}

	public JButton getBtnExportarCSV() {
		return btnExportarCSV;
	}

	public JTable getTabla() {
		return tabla;
	}

	public void setTabla(JTable tabla) {
		this.tabla = tabla;
	}

	public JLabel getLblEmail() {
		return lblEmail;
	}

	public void setLblEmail(JLabel lblEmail) {
		this.lblEmail = lblEmail;
	}
}