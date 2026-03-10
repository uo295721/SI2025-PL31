package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

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
		
		this.ventana.getCbEstados().addActionListener(e -> cargarDatos());
		
		
		this.ventana.getBtnNuevaIncidencia().addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        RegistrarIncidencia ventanaReg = new RegistrarIncidencia();
		        
		        new RegistrarIncidenciasControlador(ventanaReg, incidencias, zona, idCiudadano);
		        
		        //Necesito que se introduzca la incidencia en la tabla asi que uso un listener de la ventana
		        ventanaReg.addWindowListener(new WindowAdapter() {
		            public void windowClosed(WindowEvent e) {
		            	/**En cuanto la ventana se cierre (dispose)
		                 * recargamos los datos de la tabla para que
		                 * aparezca la nueva incidencia creada*/
		                cargarDatos(); 
		                System.out.println("Tabla de incidencias actualizada tras el registro.");
		            }
		        });
		        
		        ventanaReg.setVisible(true);
		        
		    }
		});
	}
	
	private void inicializarTabla() {
		
		String[] columnas = {"ID","Tipo","Descripción","Fecha","Estado"};
		
		DefaultTableModel tablaModelo = new DefaultTableModel(columnas,0) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		
		//Aplicamos la tabla modelo que acabamos de crear a la del paquete Vista
		ventana.getTablaIncidencias().setModel(tablaModelo);
	}
	
	private void cargarDatos() {
		
		DefaultTableModel tablaModelo = (DefaultTableModel) ventana.getTablaIncidencias().getModel();
		tablaModelo.setRowCount(0);
		
		String filtradoEstado = (String) ventana.getCbEstados().getSelectedItem();
		
		List<IncidenciaDTO> listaIncidencias = incidencias.incidenciasRegistradasCiudadano(idCiudadano);
		
		//Recorremos la lista añadiendo solo las que cumplan el estado
		for (IncidenciaDTO iDTO : listaIncidencias) {
			
			//Comprobamos el estado
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
