package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

import modelo.HistorialDTO;
import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.TecnicoDTO;
import vista.VentanaOperador;
import vista.VentanaHistorial;
import vista.VentanaOperador;



public class OperadorControlador {

	private IncidenciaModelo modelo;
	private VentanaOperador vista;
	private String emailOperador;

	public OperadorControlador(VentanaOperador vista, IncidenciaModelo modelo) {
		this.vista = vista;
		this.modelo = modelo;
		
		this.configurarEventos();
	}

	private void cargarDatosEnComponentes() {
		// 1. Rellenar Tabla de Incidencias
		List<IncidenciaDTO> incidencia  = modelo.getIncidenciasValidadas();
		vista.getModeloTabla().setRowCount(0);
		for (IncidenciaDTO i : incidencia) {
			vista.getModeloTabla()
					.addRow(new Object[] {
							i.getIdIncidencia(),
							i.getDescripcion(),
							i.getFecha(),
							i.getEstado() });
		}

		// 2. Rellenar Lista de Técnicos
		List<TecnicoDTO> tecnicos = modelo.obtenerListaTecnicos();
		vista.getModeloListaTecnicos().clear();
		for (TecnicoDTO t : tecnicos) {
			vista.getModeloListaTecnicos().addElement(t);
		}
	}

	private void configurarEventos() {
		
		vista.getTxtEmail().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String emailInput = vista.getTxtEmail().getText().trim();
				
				// Validación del Email
				if (emailInput.isEmpty() || !emailInput.contains("@")) {
					JOptionPane.showMessageDialog(vista, "Por favor, introduzca un email válido.");
					return;
				}

				// Si el email es válido:
				emailOperador = emailInput; // Guardamos el email
				
				// Actualizamos la etiqueta y desbloqueamos la interfaz
				vista.getLblEmailOperador().setText("Operador identificado: " + emailOperador);
				
				desbloquearInterfaz(); // Desbloqueamos la interfaz para que aparezcan los datos
				cargarDatosEnComponentes(); // Traemos los datos de la base de datos
				
				// Deshabilitamos el campo de email para que no lo cambien
				vista.getTxtEmail().setEnabled(false);
			}
		});

		
		vista.getBtnAsignar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// El controlador extrae los datos directamente de los componentes de la vista
				int fila = vista.getTablaIncidencias().getSelectedRow();
				TecnicoDTO tecnico = vista.getListaTecnicos().getSelectedValue();

				if (fila == -1 || tecnico == null) {
					JOptionPane.showMessageDialog(vista, "Seleccione incidencia y técnico");
					return;
				}

				// Obtener ID de la columna 0 de la fila seleccionada
				int idIncidencia = (int) vista.getModeloTabla().getValueAt(fila, 0);

				// Ejecutar lógica en el modelo
				if (modelo.asignarTecnicoIncidencia(idIncidencia, tecnico.getIdUsuario(), emailOperador)) {
					JOptionPane.showMessageDialog(vista, "Asignación correcta");
					cargarDatosEnComponentes(); 
				}
			}
		});
		vista.getBtnHistorial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.getTablaIncidencias().getSelectedRow();

                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Seleccione primero una incidencia de la lista.");
                    return;
                }

                // 1. Obtener ID de la fila seleccionada
                int idIncidencia = (int) vista.getModeloTabla().getValueAt(fila, 0);

                // 2. Obtener datos del modelo (la lista de cambios)
                List<HistorialDTO> historial = modelo.obtenerHistorialIncidencia(idIncidencia);

                // 3. Abrir la ventana de historial
                VentanaHistorial vh = new VentanaHistorial(vista, idIncidencia, historial);
                vh.setVisible(true);
            }
        });
	}
	
	// Método que va a permitir al operador acceder a las indicendias sus respectivos técnicos
	private void desbloquearInterfaz() {
		vista.getTablaIncidencias().setEnabled(true);
		vista.getListaTecnicos().setEnabled(true);
		vista.getBtnAsignar().setEnabled(true);
		vista.getBtnHistorial().setEnabled(true);
	}

}
