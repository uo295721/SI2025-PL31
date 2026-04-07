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
			public void actionPerformed(ActionEvent e) {
				int filaIncidencia = vista.getTablaIncidencias().getSelectedRow();
				int filaTecnico = vista.getTablaTecnicos().getSelectedRow();

				if (filaIncidencia == -1 || filaTecnico == -1) {
					JOptionPane.showMessageDialog(vista, "Seleccione incidencia y técnico");
					return;
				}

				String idTecnico = vista.getModeloTablaTecnicos().getValueAt(filaTecnico, 0).toString();
				String nombreTecnico = vista.getModeloTablaTecnicos().getValueAt(filaTecnico, 1).toString();
				
				int cargaActual = (int) vista.getModeloTablaTecnicos().getValueAt(filaTecnico, 3);

				if (cargaActual >= 3) {
					JOptionPane.showMessageDialog(vista,
							"El técnico " + nombreTecnico + " ya tiene el máximo de incidencias permitidas (3).",
							"Bloqueo de carga", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int idIncidencia = (int) vista.getModeloTabla().getValueAt(filaIncidencia, 0);
		        
				if (modelo.asignarTecnicoIncidencia(idIncidencia, idTecnico, emailOperador)) {
		            modelo.registrarCambioHistorial(idIncidencia, emailOperador, "Asignada", "Asignada a: " + nombreTecnico);
		            JOptionPane.showMessageDialog(vista, "La incidencia se ha asignado correctamente.");
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
		vista.getTablaTecnicos().setEnabled(true);
		vista.getBtnAsignar().setEnabled(true);
	}

	private void actualizarListaTecnicos(int idTipo) {
		List<TecnicoDTO> tecnicos = usuario.obtenerTecnicosCargaPorEspecialidad(idTipo);
		vista.getModeloTablaTecnicos().setRowCount(0);

		if (tecnicos.isEmpty()) {
			JOptionPane.showMessageDialog(vista, "No hay personal cualificado disponible.", "Aviso", JOptionPane.WARNING_MESSAGE);
		} else {
			for (TecnicoDTO t : tecnicos) {
				Object[] fila = { t.getIdUsuario(), t.getNombre() + " " + t.getApellidos(), t.getEspecialidad(),
						t.getCarga() };
				vista.getModeloTablaTecnicos().addRow(fila);
			}
		}
	}
}