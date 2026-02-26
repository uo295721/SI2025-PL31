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
	private JTextArea textDescripcion;
	private JTextArea textLocalizacion;
	private JButton buttonCancelar;
	private JButton buttonRegistrar;

	
	public RegistrarIncidencia() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel LabelTipo = new JLabel("Seleccione el tipo de incidencia:");
		LabelTipo.setBounds(24, 27, 189, 14);
		contentPane.add(LabelTipo);
		
		cBTiposIncidencia = new JComboBox();
		cBTiposIncidencia.setModel(new DefaultComboBoxModel(new String[] {"Sin tipo", "Alumbrado", "Limpieza", "Mobiliario urbano", "Zonas verdes", "Señalización", "Calzada"}));
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
		        textLocalizacion.setText("");
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
		LabelLocalizacion.setBounds(24, 66, 147, 14);
		contentPane.add(LabelLocalizacion);
		
		textDescripcion = new JTextArea();
		textDescripcion.setBorder(new LineBorder(new Color(0, 0, 0)));
		textDescripcion.setBounds(24, 139, 379, 78);
		contentPane.add(textDescripcion);
		
		textLocalizacion = new JTextArea();
		textLocalizacion.setBorder(new LineBorder(new Color(0, 0, 0)));
		textLocalizacion.setBounds(165, 66, 238, 51);
		contentPane.add(textLocalizacion);

	}


	public JTextArea getTextDescripcion() {
		return textDescripcion;
	}


	public void setTextDescripcion(JTextArea textDescripcion) {
		this.textDescripcion = textDescripcion;
	}


	public JTextArea getTextLocalizacion() {
		return textLocalizacion;
	}


	public void setTextLocalizacion(JTextArea textLocalizacion) {
		this.textLocalizacion = textLocalizacion;
	}


	public JComboBox<String> getCbTipo() {
		return cBTiposIncidencia;
	}


	public JButton getBtnRegistrar() {
		return buttonRegistrar;
	}


	
}
