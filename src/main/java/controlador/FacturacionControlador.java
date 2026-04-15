package controlador;

import java.util.List;

import javax.swing.JOptionPane;

import modelo.FacturaDTO;
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
	    for (IncidenciaDTO i : pendientes) {
	        vista.modeloPendientes.addRow(new Object[] {
	            i.getIdIncidencia(),      
	            i.getFecha(),            
	            i.getDescripcion_trabajos(), 
	            i.getTiempoResolucion(),  
	            i.getCoste()              
	        });
	    }

	    vista.modeloFacturas.setRowCount(0); 
	    List<FacturaDTO> facturas = modeloFactura.obtenerTodasLasFacturas();
	    for (FacturaDTO f : facturas) {
	        vista.modeloFacturas.addRow(new Object[] {
	            f.getIdFactura(),        
	            f.getNumeroFactura(),     
	            f.getIdIncidencia(),      
	            f.getFechaEmision(),      
	            f.getTiempoResolucion(),  
	            String.format("%.2f €", f.getTotal()), 
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
		
		Object valorCoste = vista.modeloPendientes.getValueAt(fila, 4);
	    if (valorCoste == null || valorCoste.toString().trim().isEmpty() || Double.parseDouble(valorCoste.toString()) <= 0) {
	        JOptionPane.showMessageDialog(vista, "ERROR: No se puede facturar una incidencia sin coste previo.", 
	                "Validación de Coste", JOptionPane.WARNING_MESSAGE);
	        return; 
	    }

	    int idInc = Integer.parseInt(vista.modeloPendientes.getValueAt(fila, 0).toString());
	    String resultado = modeloFactura.crearFacturaDesdeIncidencia(idInc);

	    switch (resultado) {
	        case "OK":
	            JOptionPane.showMessageDialog(vista, "Factura generada con éxito.");
	            refrescarTablas();
	            break;
	        case "DUPLICADO":
	            JOptionPane.showMessageDialog(vista, "Esta incidencia ya tiene una factura activa.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            break;
	        case "SIN_COSTE":
	            JOptionPane.showMessageDialog(vista, "La incidencia no tiene coste asignado en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
	            break;
	        default:
	            JOptionPane.showMessageDialog(vista, "Error técnico al generar la factura.");
	            break;
	    }
	}
	
	private void anularFactura() {
		int fila = vista.tablaFacturas.getSelectedRow();
		if (fila == -1) return;
		
		int idFactura = Integer.parseInt(vista.modeloFacturas.getValueAt(fila, 0).toString());
		String numFactura = vista.modeloFacturas.getValueAt(fila, 1).toString();
	    
	    int confirm = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea ANULAR la factura " + numFactura + "?");
	    
	    if (confirm == JOptionPane.YES_OPTION) {
	        modeloFactura.anularFactura(idFactura);
	        refrescarTablas();
	        JOptionPane.showMessageDialog(vista, "Factura " + numFactura + " anulada correctamente.");
	    }
	}
}
