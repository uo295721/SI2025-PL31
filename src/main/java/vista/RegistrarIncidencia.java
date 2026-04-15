package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class RegistrarIncidencia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> cBTiposIncidencia;
	private JComboBox<String> cBLocalizacion;
	private JTextArea textDescripcion;
	private JButton buttonCancelar;
	private JButton buttonRegistrar;

	
	public RegistrarIncidencia() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel LabelTipo = new JLabel("Seleccione el tipo de incidencia:");
		LabelTipo.setBounds(24, 27, 189, 14);
		contentPane.add(LabelTipo);
		
		cBTiposIncidencia = new JComboBox<String>();
		cBTiposIncidencia.setModel(new DefaultComboBoxModel<String>(new String[] {"Sin tipo", "Electricidad", "Mobiliario", "Fontanería", "Obras"}));
		cBTiposIncidencia.setName("");
		cBTiposIncidencia.setBounds(223, 23, 180, 22);
		contentPane.add(cBTiposIncidencia);
		
		JLabel labelDescripcion = new JLabel("Describa su incidencia:");
		labelDescripcion.setBounds(24, 125, 163, 14);
		contentPane.add(labelDescripcion);
		
		buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cBTiposIncidencia.setSelectedIndex(0);
		        cBLocalizacion.setSelectedIndex(0);
		        textDescripcion.setText("");
		        dispose();
			}
		});
		buttonCancelar.setBounds(10, 234, 88, 22);
		contentPane.add(buttonCancelar);
		
		buttonRegistrar = new JButton("Registrar");
		buttonRegistrar.setBounds(338, 234, 88, 22);
		contentPane.add(buttonRegistrar);
		
		JLabel LabelLocalizacion = new JLabel("Introduzca localización:");
		LabelLocalizacion.setBounds(24, 80, 147, 14);
		contentPane.add(LabelLocalizacion);
		
		textDescripcion = new JTextArea();
		textDescripcion.setBorder(new LineBorder(new Color(0, 0, 0)));
		textDescripcion.setBounds(24, 139, 379, 78);
		contentPane.add(textDescripcion);
		
		cBLocalizacion = new JComboBox<String>();
		cBLocalizacion.setModel(new DefaultComboBoxModel<String>());
		cBLocalizacion.setName("");
		cBLocalizacion.setBounds(168, 76, 180, 22);
		contentPane.add(cBLocalizacion);
	}


	public JTextArea getTextDescripcion() {
		return textDescripcion;
	}


	public void setTextDescripcion(JTextArea textDescripcion) {
		this.textDescripcion = textDescripcion;
	}


	public JComboBox<String> getCbTipo() {
		return cBTiposIncidencia;
	}


	public JButton getBtnRegistrar() {
		return buttonRegistrar;
	}

	public JComboBox<String> getcBLocalizacion() {
		return cBLocalizacion;
	}


	public void setcBLocalizacion(JComboBox<String> cBLocalizacion) {
		this.cBLocalizacion = cBLocalizacion;
	}
	
}
