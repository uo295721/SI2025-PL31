package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.TecnicoDTO;
import modelo.UsuarioModelo;
import vista.VentanaOperador;

public class OperadorControlador {

	private IncidenciaModelo modelo;
	private UsuarioModelo usuario = new UsuarioModelo(); // Centralizado
	private VentanaOperador vista;
	private String emailOperador;

	public OperadorControlador(VentanaOperador vista, IncidenciaModelo modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.configurarEventos();
	}

	private void cargarDatosEnComponentes() {

		List<IncidenciaDTO> incidencia = modelo.getIncidenciasPorEstado("Validada");
		vista.getModeloTabla().setRowCount(0);
		for (IncidenciaDTO i : incidencia) {
			vista.getModeloTabla().addRow(
					new Object[] { i.getIdIncidencia(), i.getDescripcion(), i.getFecha(), i.getEstado(), i.getId_tipo() // Columna
																														// oculta
																														// o
																														// necesaria
																														// para
																														// el
																														// filtro
					});
		}
// 2. Cargamos todos los técnicos inicialmente (pasamos -1 para no filtrar)
		actualizarListaTecnicos(-1);
	}

	private void configurarEventos() {
// Evento de Identificación (Email)
		vista.getTxtEmail().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String emailInput = vista.getTxtEmail().getText().trim();
				if (emailInput.isEmpty() || !emailInput.contains("@")) {
					JOptionPane.showMessageDialog(vista, "Por favor, introduzca un email válido.");
					return;
				}

// Validamos el rol usando el modelo de usuario
				if (usuario.esUsuarioConRol(emailInput, "OPERADOR")) {
					emailOperador = emailInput;
					vista.getLblEmailOperador().setText("Operador identificado: " + emailOperador);
					desbloquearInterfaz();
					cargarDatosEnComponentes();
					vista.getTxtEmail().setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(vista, "Acceso denegado: El email no corresponde a un Operador.",
							"Error de Permisos", JOptionPane.ERROR_MESSAGE);
					vista.getTxtEmail().setText("");
				}
			}
		});
		vista.getTablaIncidencias().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					filtrarTecnicosPorEspecialidad();
				}
			}
		});

// Evento de Asignación
		vista.getBtnAsignar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = vista.getTablaIncidencias().getSelectedRow();
				String tecnicoTexto = vista.getListaTecnicos().getSelectedValue();

				if (fila == -1 || tecnicoTexto == null) {
					JOptionPane.showMessageDialog(vista, "Seleccione incidencia y técnico");
					return;
				}

				int idIncidencia = (int) vista.getModeloTabla().getValueAt(fila, 0);
				String idTecnico = tecnicoTexto.split(" - ")[0];
// Ejecutamos la asignación en el modelo
				if (modelo.asignarTecnicoIncidencia(idIncidencia, idTecnico, emailOperador)) {

					String comentario = "Asignada a: " + tecnicoTexto;
					modelo.registrarCambioHistorial(idIncidencia, emailOperador, "Asignada", comentario);
					JOptionPane.showMessageDialog(vista, "Asignación correcta");
					cargarDatosEnComponentes();
				}
			}
		});
	}

	private void filtrarTecnicosPorEspecialidad() {
		int fila = vista.getTablaIncidencias().getSelectedRow();
		if (fila == -1) {
			actualizarListaTecnicos(-1); // Si no hay nada seleccionado muestro todos
			return;
		}

		int idTipo = (int) vista.getModeloTabla().getValueAt(fila, 4);
		actualizarListaTecnicos(idTipo);
	}

	private void desbloquearInterfaz() {
		vista.getTablaIncidencias().setEnabled(true);
		vista.getListaTecnicos().setEnabled(true);
		vista.getBtnAsignar().setEnabled(true);
	}

	private void actualizarListaTecnicos(int idTipo) {
	    List<TecnicoDTO> tecnicos = usuario.obtenerTecnicosCargaPorEspecialidad(idTipo);
	    vista.getModeloListaTecnicos().clear();
	    
	    if (tecnicos.isEmpty()) {
	        JOptionPane.showMessageDialog(vista, 
	            "No hay personal cualificado disponible.", 
	            "Aviso", JOptionPane.WARNING_MESSAGE);
	    } else {
	        for (TecnicoDTO t : tecnicos) {
	            String item = String.format("%s - %s %s (Carga: %d) - Esp: %s",
	                t.getIdUsuario(), 
	                t.getNombre(), 
	                t.getApellidos(), 
	                t.getCarga(), 
	                t.getEspecialidad());
	            
	            vista.getModeloListaTecnicos().addElement(item);
	        }
	    }
	}
}