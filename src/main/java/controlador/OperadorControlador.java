package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

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
		// 1. Rellenar Tabla: Usamos el método unificado por estado
		List<IncidenciaDTO> incidencia = modelo.getIncidenciasPorEstado("Validada");
		vista.getModeloTabla().setRowCount(0);
		for (IncidenciaDTO i : incidencia) {
			vista.getModeloTabla().addRow(new Object[] {
					i.getIdIncidencia(),
					i.getDescripcion(),
					i.getFecha(),
					i.getEstado() 
			});
		}

		// 2. Rellenar Técnicos: Pedimos la lista al modelo de usuarios
		List<TecnicoDTO> tecnicos = usuario.obtenerTodosLosTecnicos();
		vista.getModeloListaTecnicos().clear();
		for (TecnicoDTO t : tecnicos) {
			vista.getModeloListaTecnicos().addElement(t);
		}
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
					JOptionPane.showMessageDialog(vista, 
						"Acceso denegado: El email no corresponde a un Operador.", 
						"Error de Permisos", 
						JOptionPane.ERROR_MESSAGE);
					vista.getTxtEmail().setText("");
				}
			}
		});

		// Evento de Asignación
		vista.getBtnAsignar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = vista.getTablaIncidencias().getSelectedRow();
				TecnicoDTO tecnico = vista.getListaTecnicos().getSelectedValue();

				if (fila == -1 || tecnico == null) {
					JOptionPane.showMessageDialog(vista, "Seleccione incidencia y técnico");
					return;
				}

				int idIncidencia = (int) vista.getModeloTabla().getValueAt(fila, 0);

				// Ejecutamos la asignación en el modelo
				if (modelo.asignarTecnicoIncidencia(idIncidencia, tecnico.getIdUsuario(), emailOperador)) {
					
					// Registramos el cambio en el historial
					String comentario = "Asignada al técnico: " + tecnico.getNombre();
					modelo.registrarCambioHistorial(idIncidencia, emailOperador, "Asignada", comentario);
					
					JOptionPane.showMessageDialog(vista, "Asignación correcta");
					cargarDatosEnComponentes(); 
				}
			}
		});
	}
	
	private void desbloquearInterfaz() {
		vista.getTablaIncidencias().setEnabled(true);
		vista.getListaTecnicos().setEnabled(true);
		vista.getBtnAsignar().setEnabled(true);
	}
}