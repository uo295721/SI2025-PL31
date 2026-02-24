package giis.demo.tkrun.controlador;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import modelo.IncidenciaDTO;
import modelo.TecnicoModelo;
import vista.DialogoPlanificar;
import vista.VentanaTecnico;

public class TecnicoControlador {
	
	private TecnicoModelo modeloTec1;
	private VentanaTecnico ventanaTec1;
	private String emailTecnico;
	
	public TecnicoControlador(TecnicoModelo modelo, VentanaTecnico vista, String email) {
		
		this.modeloTec1 = modelo;
		this.ventanaTec1 = vista;
		this.emailTecnico = email;
		
		//Cargamos los datos en la tabla al iniciar
		listarIncidencias();
		
		//Asignamos un evento al botón "Planificar incidencia de la ventana principal"
		this.ventanaTec1.getBtnPlanificar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarAperturaDialogo();
			}
		});
		
	}

	private void listarIncidencias() {
		List<IncidenciaDTO> incidencias = modeloTec1.getIncidenciasAsignadas(emailTecnico);
		
		//Definimos las columnas que queremos ver
		String[] columnas = {"ID","Título","Descripción","Estado"};
		DefaultTableModel tablaModelo = new DefaultTableModel(columnas,0);
		
		//Rellenamos la tabla modelo con la lista del DTO
		for (IncidenciaDTO i : incidencias) {
			Object[] fila = {i.getIdIncidencia(),i.getDescripcion(),i.getDescripcionCiudadano(),i.getEstado()};
			tablaModelo.addRow(fila);
		}
		
		//Usamos getter para poner los datos en la JTable
		ventanaTec1.getTablaIncidencias().setModel(tablaModelo);
		
	}
	
	private void gestionarAperturaDialogo() {
		
		int fila = ventanaTec1.getFilaSeleccionada();
		
		if (fila == -1) {
			JOptionPane.showMessageDialog(ventanaTec1, "Seleccione una incidencia.");
			return;
		}
		
		int idIncidencia = (int) ventanaTec1.getTablaIncidencias().getValueAt(fila, 0);
		DialogoPlanificar dialogo = new DialogoPlanificar();
		dialogo.setModal(true);
		
		//Configuramos el JButton 'Aceptar' utilizando los getters
		dialogo.getBtnAceptar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int horas = Integer.parseInt(dialogo.getTextHoras());
					String trabajos = dialogo.getTextArea();
					
					//Llamamos al modelo para guardar en la BD
					modeloTec1.planificarIncidencia(idIncidencia, horas, trabajos, emailTecnico);
					
					dialogo.dispose(); //Cerramos el diálogo
					listarIncidencias(); //Actualizamos la tabla Principal
					JOptionPane.showMessageDialog(ventanaTec1, "Planificación guardada con éxito.");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(dialogo, "Por favor, introduce un número de horas válido.");
				}
			}
		});
		
		//El JButton 'Cancelar' se encarga únicamente de cerrar
		dialogo.getBtnCancelar().addActionListener(e -> dialogo.dispose());
		dialogo.setVisible(true);
		
	}
	
}
