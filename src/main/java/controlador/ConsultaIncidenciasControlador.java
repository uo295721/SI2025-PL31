package controlador;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.ZonaModelo;
import vista.RegistrarIncidencia;
import vista.VentanaMisIncidencias;

public class ConsultaIncidenciasControlador {

	private VentanaMisIncidencias ventana;
	private IncidenciaModelo incidencias;
	private ZonaModelo zona;
	private String idCiudadano;
	
	public ConsultaIncidenciasControlador(VentanaMisIncidencias ventana, IncidenciaModelo incidencias, ZonaModelo zona, String idCiudadano) {
		this.ventana = ventana;
		this.zona = zona;
		this.incidencias = incidencias;
		this.idCiudadano = idCiudadano;
		
		this.inicializarTabla();
		this.cargarDatos();
		
		// Listener para el filtro
		this.ventana.getCbEstados().addActionListener(e -> cargarDatos());
		
		// Listener para habilitar/deshabilitar el botón de reabrir según selección
		this.ventana.getTablaIncidencias().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				actualizarEstadoBotonReabrir();
			}
		});

		// Acción del botón Reabrir
		this.ventana.getBtnReabrir().addActionListener(e -> reabrirSeleccionada());
		
		// Acción de Nueva Incidencia
		this.ventana.getBtnNuevaIncidencia().addActionListener(e -> {
			RegistrarIncidencia ventanaReg = new RegistrarIncidencia();
			new RegistrarIncidenciasControlador(ventanaReg, incidencias, zona, idCiudadano);
			ventanaReg.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					cargarDatos(); 
				}
			});
			ventanaReg.setVisible(true);
		});
	}

	/**
	 * Habilita el botón de reapertura solo si la incidencia está Rechazada o Cerrada.
	 */
	private void actualizarEstadoBotonReabrir() {
	    int fila = ventana.getTablaIncidencias().getSelectedRow();
	    if (fila != -1) {
	        String estado = ventana.getTablaIncidencias().getValueAt(fila, 4).toString();
	        // Cambiado aquí también para que coincida con la BBDD
	        boolean puedeReabrir = estado.equals("Rechazada") || estado.equals("Cerrada");
	        ventana.getBtnReabrir().setEnabled(puedeReabrir);
	    } else {
	        ventana.getBtnReabrir().setEnabled(false);
	    }
	}

	/**
	 * Lógica de la HU: Solicita motivo obligatorio y reabre la incidencia.
	 */
	private void reabrirSeleccionada() {
		int fila = ventana.getTablaIncidencias().getSelectedRow();
		if (fila == -1) return;

		int idInci = (int) ventana.getTablaIncidencias().getValueAt(fila, 0);
		
		// Solicitar motivo obligatorio
		String motivo = JOptionPane.showInputDialog(ventana, 
				"Indique el motivo detallado de la reapertura (OBLIGATORIO):", 
				"Reabrir Incidencia #" + idInci, JOptionPane.QUESTION_MESSAGE);

		// Validar obligatoriedad
		if (motivo == null) return; // Canceló el diálogo
		
		if (motivo.trim().isEmpty()) {
			JOptionPane.showMessageDialog(ventana, "Error: El motivo de reapertura no puede estar vacío.", 
					"Campo Obligatorio", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ejecutar cambio en el modelo
		if (incidencias.reabrirIncidencia(idInci, idCiudadano, motivo)) {
			JOptionPane.showMessageDialog(ventana, "La incidencia se ha reabierto correctamente y pasa a estado 'Nueva'.");
			cargarDatos();
		} else {
			JOptionPane.showMessageDialog(ventana, "Error técnico al intentar reabrir la incidencia.");
		}
	}
	
	private void inicializarTabla() {
		String[] columnas = {"ID","Tipo","Descripción","Fecha","Estado"};
		DefaultTableModel tablaModelo = new DefaultTableModel(columnas,0) {
			@Override
			public boolean isCellEditable(int fila, int columna) { return false; }
		};
		ventana.getTablaIncidencias().setModel(tablaModelo);
	}
	
	private void cargarDatos() {
		DefaultTableModel tablaModelo = (DefaultTableModel) ventana.getTablaIncidencias().getModel();
		tablaModelo.setRowCount(0);
		String filtradoEstado = (String) ventana.getCbEstados().getSelectedItem();
		List<IncidenciaDTO> listaIncidencias = incidencias.incidenciasRegistradasCiudadano(idCiudadano);
		
		for (IncidenciaDTO iDTO : listaIncidencias) {
			if (filtradoEstado.equals("Todas") || iDTO.getEstado().equals(filtradoEstado)) {
				Object[] fila = {
						iDTO.getIdIncidencia(),
						iDTO.getTipo(),
						iDTO.getDescripcion(),
						iDTO.getFecha(),
						iDTO.getEstado()
				};
				tablaModelo.addRow(fila);
			}
		}
	}
}