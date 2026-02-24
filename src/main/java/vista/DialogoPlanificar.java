package vista;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogoPlanificar extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textHoras;
	private JTextArea textArea;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public DialogoPlanificar() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 450, 233);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblHorasEstimadas = new JLabel("Horas estimadas:");
			lblHorasEstimadas.setBounds(23, 6, 109, 16);
			contentPanel.add(lblHorasEstimadas);
		}
		
		textHoras = new JTextField();
		textHoras.setBounds(144, 1, 130, 26);
		contentPanel.add(textHoras);
		textHoras.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(23, 34, 109, 16);
		contentPanel.add(lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 52, 413, 181);
		contentPanel.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 233, 450, 39);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				okButton = new JButton("Aceptar");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public String getTextHoras() {
		return textHoras.getText();
	}

	public String getTextArea() {
		return textArea.getText();
	}

	public JButton getBtnAceptar() {
		return okButton;
	}

	public JButton getBtnCancelar() {
		return cancelButton;
	}
	
}
