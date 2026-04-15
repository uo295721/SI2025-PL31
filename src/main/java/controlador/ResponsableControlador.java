package controlador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.UsuarioDTO;
import modelo.UsuarioModelo;
import vista.VentanaResponsable;

public class ResponsableControlador {
	
	private VentanaResponsable vista;
	private IncidenciaModelo incidencia;
	private UsuarioModelo usuario;
	private UsuarioDTO responsable;
	
	public ResponsableControlador(VentanaResponsable vista, String id) {
		this.vista = vista;
		this.incidencia = new IncidenciaModelo();
		this.usuario = new UsuarioModelo();
		
		this.responsable = usuario.buscarResponsable(id);
		
		if (responsable != null) {
			inicializarVista();
			asociarEventos();
			cargarTabla();
		}
		
	}
	
	private void inicializarVista() {
		String info = String.format("Responsable: %s (%s) - Área: %s", 
						responsable.getNombre(),responsable.getIdUsuario(),responsable.getEspecialidad());
		vista.getLblInfoResponsable().setText(info);
	}

	private void asociarEventos() {
		vista.getBtnSeleccionarTodas().addActionListener(e -> {
			DefaultTableModel modelo = vista.getModeloTabla();
			for (int i = 0; i < modelo.getRowCount(); i++)
				modelo.setValueAt(true, i, 0);
		});
		
		vista.getBtnArchivar().addActionListener(e ->{
			archivarSeleccionadas();
		});
		
	}
	
	private void archivarSeleccionadas() {
	    DefaultTableModel modelo = vista.getModeloTabla();
	    List<Integer> idsParaCerrar = new ArrayList<>();
	    
	    for (int i = 0; i < modelo.getRowCount(); i++) {
	        Boolean seleccionado = (Boolean) modelo.getValueAt(i, 0);
	        if (seleccionado != null && seleccionado) {
	            int id = (int) modelo.getValueAt(i, 1);
	            idsParaCerrar.add(id);
	        }
	    }
	    
	    if (!idsParaCerrar.isEmpty()) {
	        String resultado = incidencia.archivarIncidencias(idsParaCerrar, responsable.getEmail());
	        if (resultado.equals("OK")) {
	            JOptionPane.showMessageDialog(vista, "Control de calidad finalizado con éxito.\n"
	                   + "Incidencias archivadas: " + idsParaCerrar.size());
	        } else {
	            // Si el resultado no es OK, es que ha habido un problema entonces lo imprimo
	            JOptionPane.showMessageDialog(vista, resultado, 
	                    "Validación de Presupuesto", JOptionPane.WARNING_MESSAGE);
	        }
	    
	        cargarTabla(); //Vuelvo a cargar la tabla para que se pueda ver cuales se cerraron y cuales no
	    } else {
	        JOptionPane.showMessageDialog(vista, "Por favor, seleccione al menos una incidencia.");
	    }
	}
	
	private void cargarTabla() {
		List<IncidenciaDTO> lista = incidencia.getIncidenciasParaControlCalidad(responsable.getEspecialidad());
		DefaultTableModel modelo = vista.getModeloTabla();
		
		modelo.setRowCount(0);
		
		for (IncidenciaDTO i : lista) {
			modelo.addRow(new Object[] {
					false,
					i.getIdIncidencia(),
					i.getDescripcion(),
					i.getLocalizacion(),
					i.getTipo(),
					i.getFecha()
			});
		}
	}
	
}
