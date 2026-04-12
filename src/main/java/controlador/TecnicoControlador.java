package controlador;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.UsuarioModelo;
import vista.VentanaTecnico;
import vista.VentanaTareasDiarias;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TecnicoControlador {
	
	private IncidenciaModelo modeloTec1;
	private VentanaTecnico ventanaTec1;
	private UsuarioModelo usuario = new UsuarioModelo();
	private String emailTecnico;
	
	public TecnicoControlador(IncidenciaModelo modelo, VentanaTecnico vista, String email) {
		
		this.modeloTec1 = modelo;
		this.ventanaTec1 = vista;
		this.emailTecnico = email;
		
		//Cargamos los datos en la tabla al iniciar
		listarIncidencias();
		
		this.configurarSeleccionTabla();
		this.configurarBotonGuardar();
		this.configurarBotonTareas();
		
	}

	private void listarIncidencias() {
		
		List<IncidenciaDTO> incidencias = modeloTec1.getIncidenciasAsignadasTecnico(emailTecnico);
		
		//Definimos las columnas que queremos ver
		String[] columnas = {"ID","Título","Localización","Estado"};
		DefaultTableModel tablaModelo = new DefaultTableModel(columnas,0);
		
		//Rellenamos la tabla modelo con la lista del DTO
		for (IncidenciaDTO i : incidencias) {
			Object[] fila = {i.getIdIncidencia(),
							 i.getDescripcion(),
							 i.getLocalizacion(),
							 i.getEstado()};
			
			tablaModelo.addRow(fila);
		}
		
		//Usamos getter para poner los datos en la JTable
		ventanaTec1.getTablaIncidencias().setModel(tablaModelo);
		
	}
	
	private void configurarSeleccionTabla() {
		ventanaTec1.getTablaIncidencias().getSelectionModel().addListSelectionListener(
		new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (!e.getValueIsAdjusting()) {
					if (ventanaTec1.getFilaSeleccionada() != -1)
						ventanaTec1.activarCampos();
					else
						ventanaTec1.desactivarCampos();
				}
				
			}
		});
	}
	
	private void configurarBotonTareas() {
        ventanaTec1.getBtnGestionarTareas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = ventanaTec1.getFilaSeleccionada();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(ventanaTec1, "Seleccione una incidencia de la tabla");
                    return;
                }

                int idIncidencia = (int) ventanaTec1.getTablaIncidencias().getValueAt(fila, 0);
                String tituloIncidencia = ventanaTec1.getTablaIncidencias().getValueAt(fila,1).toString();
                String idTec = usuario.getIdUsuarioByEmail(emailTecnico);

                VentanaTareasDiarias vTareas = new VentanaTareasDiarias();
                vTareas.setLblIncidenciaInfo("#" + idIncidencia + " - " + tituloIncidencia);
                vTareas.setModal(true);
                vTareas.setLocationRelativeTo(ventanaTec1);

                new TareasDiariasControlador(vTareas, modeloTec1, idIncidencia, idTec);

                vTareas.setVisible(true);
            } 
        });   
    }
	
	private void configurarBotonGuardar() {
		
		ventanaTec1.getBtnGuardar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				int fila = ventanaTec1.getFilaSeleccionada();
				if (fila == -1) {
					JOptionPane.showMessageDialog(ventanaTec1, "Seleccione una incidencia de la tabla.");
					return;
				}
					
				try {
					int idIncidencia = (int) ventanaTec1.getTablaIncidencias().getValueAt(fila, 0);
					
					String horasEstimadas = ventanaTec1.getTxtHoras().getText().trim();
					String descp = ventanaTec1.getTxtAreaTrabajos().getText().trim();
					
					if (horasEstimadas.isEmpty() || descp.isEmpty()) {
						JOptionPane.showMessageDialog(ventanaTec1, "Debe rellenar las horas y la descripción");
						return;
					}
					
					int horas = Integer.parseInt(horasEstimadas);
					if (horas <= 0) {
						JOptionPane.showMessageDialog(ventanaTec1, 
								"Las horas estimadas deben ser un número mayor de cero.",
								"Error de validación", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					String idTec = usuario.getIdUsuarioByEmail(emailTecnico);
					modeloTec1.planificarIncidencia(idIncidencia, horas, descp, idTec);
					
					String resumen = "Planificación guardada con éxito.\n"
								   + "--- Resumen de la Operación ---\n"
								   + "• ID Incidencia: " + idIncidencia + "\n"
					               + "• Horas asignadas: " + horas + " h\n"
					               + "• Descripción: " + descp + "\n\n"
					               + "La incidencia ha pasado al estado 'En proceso'.";
					
					JOptionPane.showMessageDialog(ventanaTec1, resumen, 
							"Confirmación de Planificación",JOptionPane.INFORMATION_MESSAGE);
					ventanaTec1.desactivarCampos();
					listarIncidencias();
					
							
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(ventanaTec1, "Por favor, introduzca un número de horas válido");
				}
			}
		});
		
	}
	
}
