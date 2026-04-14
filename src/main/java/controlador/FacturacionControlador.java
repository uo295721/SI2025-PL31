package controlador;

import java.util.List;

import javax.swing.JOptionPane;

import modelo.FacturaModelo;
import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import vista.VentanaFacturas;

public class FacturacionControlador {
	
	private VentanaFacturas vista;
	private FacturaModelo modeloFactura;
	private IncidenciaModelo modeloIncidencia;
	
	public FacturacionControlador(VentanaFacturas v) {
		this.vista = v;
		this.modeloFactura = new FacturaModelo();
		this.modeloIncidencia = new IncidenciaModelo();
		
		this.refrescarTablas();
		
		this.vista.btnGenerarFactura.addActionListener(e -> generarFactura());
        this.vista.btnAnularFactura.addActionListener(e -> anularFactura());
        this.vista.btnRefrescar.addActionListener(e -> refrescarTablas());
	}
	
	private void refrescarTablas() {
		vista.modeloPendientes.setRowCount(0);
		List<IncidenciaDTO> pendientes = modeloIncidencia.getIncidenciasPendientesFacturar();
		for (IncidenciaDTO i: pendientes) {
			vista.modeloPendientes.addRow(new Object[] {
					i.getIdIncidencia(),
					i.getFecha(),
					i.getDescripcion_trabajos(),
					i.getTiempoResolucion()
			});
		}
		
		vista.modeloFacturas.setRowCount(0); 
	    List<modelo.FacturaDTO> facturas = modeloFactura.obtenerTodasLasFacturas();
	    
	    for (modelo.FacturaDTO f : facturas) {
	        vista.modeloFacturas.addRow(new Object[] {
	        	f.getIdFactura(),	
	            f.getNumeroFactura(),
	            f.getIdIncidencia(),
	            f.getFechaEmision(),
	            f.getTotal(),
	            f.getEstado()
	        });
	    }
	}

	private void generarFactura() {
		int fila = vista.tablaPendientes.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(vista, "Selecciona una incidencia de la tabla superior.");
			return;
		}
		
		int idInc = (int) vista.modeloPendientes.getValueAt(fila, 0);
		if(modeloFactura.crearFacturaDesdeIncidencia(idInc)) {
			JOptionPane.showMessageDialog(vista, "Factura generada con éxito.");
			refrescarTablas();
		} else {
			JOptionPane.showMessageDialog(vista, "Error al generar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void anularFactura() {
		int fila = vista.tablaFacturas.getSelectedRow();
		if (fila == -1) return;
		
		int idFactura = (int) vista.modeloFacturas.getValueAt(fila, 0);
		int confirm = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea ANULAR la factura " + idFactura + "?");
		
		if (confirm == JOptionPane.YES_OPTION) {
			modeloFactura.anularFactura(idFactura);
			refrescarTablas();
			JOptionPane.showMessageDialog(vista, "Factura "+idFactura+" anulada correctamente.");
		}
	}
}
