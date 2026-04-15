package controlador;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modelo.HistorialDTO;
import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.TipoIncidenciaModelo;
import modelo.ZonaModelo;
import vista.VentanaExportarHistorial;

public class ExportarHistorialControlador {

	private VentanaExportarHistorial vista;
	private IncidenciaModelo modelo;
	
	public ExportarHistorialControlador(VentanaExportarHistorial hist) {
		this.vista = hist;
		this.modelo = new IncidenciaModelo();
		
		this.cargarFiltros();
		this.configurarEventos();
	}
	
	private void cargarFiltros() {
		vista.cBZonas.addItem("Todas");
		new ZonaModelo().obtenerZonas().forEach(z -> vista.cBZonas.addItem(z.getNombre()));
		
		vista.cBTipos.addItem("Todos");
		new TipoIncidenciaModelo().obtenerTodosLosTipos().forEach(t -> vista.cBTipos.addItem(t.getNombre()));
	}
	
	private void configurarEventos() {
		vista.btnExportarJSON.addActionListener(e -> procesarExportacion());
		vista.btnCancelar.addActionListener(e -> vista.dispose());
	}
	
	private void procesarExportacion() {
		String fInicio = vista.txtFechaInicio.getText().trim();
		String fFin = vista.txtFechaFin.getText().trim();
		
		if (!fechasValidas(fInicio, fFin)) {
	        return; 
	    }
		
		String tipo = vista.cBTipos.getSelectedItem().toString();
		String zonas = vista.cBZonas.getSelectedItem().toString();
		
		List<IncidenciaDTO> lista = modelo.getIncidenciasConHistorialParaExportar(fInicio, fFin, tipo, zonas);
		
		if (lista.isEmpty()) {
			JOptionPane.showMessageDialog(vista, "No hay datos para las fechas/filtros seleccionados.");
			return;
		}
		
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Guardar Historial como JSON");
		selector.setSelectedFile(new java.io.File("historial_incidencia.json"));
		
		if (selector.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
			String ruta = selector.getSelectedFile().getAbsolutePath();
			if (!ruta.toLowerCase().endsWith("json"))
				ruta += ".json";
			
			try {
				String jsonFinal = generarJsonManual(lista);
				guardarArchivo(ruta,jsonFinal);
				JOptionPane.showMessageDialog(vista, "Exportación completada en: "+ruta);
				vista.dispose();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(vista, "Error al guardar: "+e.getMessage());
			}
		}
	}
	
	private String generarJsonManual(List<IncidenciaDTO> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n"); // Inicio del array principal

        for (int i = 0; i < lista.size(); i++) {
            IncidenciaDTO inc = lista.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": ").append(inc.getIdIncidencia()).append(",\n");
            sb.append("    \"descripcion\": \"").append(escaparJson(inc.getDescripcion())).append("\",\n");
            sb.append("    \"fecha\": \"").append(inc.getFecha()).append("\",\n");
            sb.append("    \"tipo\": \"").append(inc.getTipo()).append("\",\n");
            sb.append("    \"zona\": \"").append(inc.getLocalizacion()).append("\",\n");
            sb.append("    \"coste\": ").append(inc.getCoste()).append(",\n");
            sb.append("    \"tiempo_total\": \"").append(inc.getTiempoResolucion()).append("\",\n");
            sb.append("    \"historial\": [\n");
            
            List<HistorialDTO> hist = inc.getHistorial();
            for (int j = 0; j < hist.size(); j++) {
                HistorialDTO h = hist.get(j);
                sb.append("      {\n");
                sb.append("        \"fecha\": \"").append(h.getFecha_modificacion()).append("\",\n");
                sb.append("        \"estado\": \"").append(h.getEstado_nuevo()).append("\",\n");
                sb.append("        \"comentario\": \"").append(escaparJson(h.getComentario())).append("\"\n");
                sb.append("      }").append(j < hist.size() - 1 ? "," : "").append("\n");
            }
            sb.append("    ]\n");
            
            sb.append("  }").append(i < lista.size() - 1 ? "," : "").append("\n");
        }

        sb.append("]");
        return sb.toString();
    }
	
	private void guardarArchivo(String ruta, String contenido) throws IOException{
		try (FileWriter fw = new FileWriter(ruta)) {
			fw.write(contenido);
		}
	}
	
	private String escaparJson(String texto) {
		if (texto == null)
			return "";
		return texto.replace("\"", "\\\"");
	}
	
	private boolean fechasValidas(String fechaInicio, String fechaFin) {
		try {
	        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
	            JOptionPane.showMessageDialog(vista, "Rellena ambas fechas (AAAA-MM-DD)");
	            return false;
	        }

	        LocalDate inicio = LocalDate.parse(fechaInicio);
	        LocalDate fin = LocalDate.parse(fechaFin);

	        if (inicio.isAfter(fin)) {
	            JOptionPane.showMessageDialog(vista, "La fecha de inicio no puede ser posterior a la de fin."
	            		,"Error de Fecha", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	        return true;

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(vista, 
	            "Formato incorrecto.\nUsa el estándar: AAAA-MM-DD\nEjemplo: 2026-04-15", 
	            "Error de Fecha", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	}
}
