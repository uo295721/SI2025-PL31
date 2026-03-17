package controlador;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.TipoIncidenciaDTO;
import modelo.TipoIncidenciaModelo;
import modelo.ZonaDTO;
import modelo.ZonaModelo;
import vista.VentanaInformes;

public class InformesControlador {

	private VentanaInformes ventanaInformes;
	private IncidenciaModelo incidencia;
	private ZonaModelo zonaModelo;
	private TipoIncidenciaModelo tipoIncidencia;
	
	public InformesControlador(VentanaInformes vista) {
		this.ventanaInformes = vista;
		this.incidencia = new IncidenciaModelo();
		this.zonaModelo = new ZonaModelo();
		this.tipoIncidencia = new TipoIncidenciaModelo();
		
		inicializarFiltros();
		
		this.ventanaInformes.btnGenerar.addActionListener(e -> ejecutarInforme());
        this.ventanaInformes.btnExportarCSV.addActionListener(e -> exportarResultadosCSV());
		this.ventanaInformes.btnCerrar.addActionListener(e -> vista.dispose());
		this.ventanaInformes.setVisible(true);
	}
	
	private void inicializarFiltros() {
		ventanaInformes.cBZonas.addItem("Todas");
		List<ZonaDTO> zonas = zonaModelo.obtenerZonas();
		for (ZonaDTO z : zonas)
			ventanaInformes.cBZonas.addItem(z.getNombre());
		
		ventanaInformes.cBTipos.addItem("Todos");
		List<TipoIncidenciaDTO> tipos = tipoIncidencia.obtenerTodosLosTipos();
		for (TipoIncidenciaDTO t : tipos)
			ventanaInformes.cBTipos.addItem(t.getNombre());
	}
	
	private void ejecutarInforme() {
		String fechaInicio = ventanaInformes.txtFechaInicio.getText();
		String fechaFin = ventanaInformes.txtFechaFin.getText();
        String tipo = ventanaInformes.cBTipos.getSelectedItem().toString();
        String zona = ventanaInformes.cBZonas.getSelectedItem().toString();
        String estado = ventanaInformes.cBEstados.getSelectedItem().toString();
        
        List<IncidenciaDTO> resultados = incidencia.getEstadisticasFiltradas(fechaInicio, fechaFin, 
        																		tipo, zona, estado);
        ventanaInformes.modeloTabla.setRowCount(0);
        for (IncidenciaDTO i : resultados) {
        	Object[] fila = {
        			i.getIdIncidencia(),
        			i.getFecha(),
        			i.getEstado(),
        			i.getTipo(),
        			i.getTiempoResolucion()
        	};
        	ventanaInformes.modeloTabla.addRow(fila);
        }
        calcularMedia(resultados);
	}
	
	private void exportarResultadosCSV() {
		if(ventanaInformes.modeloTabla.getRowCount() == 0) {
			JOptionPane.showMessageDialog(ventanaInformes,"No hay datos para exportar. Genera un informe primero");
			return;
		}
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Guardar Informe en CSV");
		int userSelection = selector.showSaveDialog(ventanaInformes);
		
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			String ruta = selector.getSelectedFile().toString();
			if (!ruta.toLowerCase().endsWith("csv")) ruta += ".csv";
			
			try(FileWriter csv = new FileWriter(ruta)){
				DefaultTableModel modelo = ventanaInformes.modeloTabla;
				for (int i = 0; i < modelo.getColumnCount(); i++) {
                    csv.write(modelo.getColumnName(i) + (i == modelo.getColumnCount() - 1 ? "" : ","));
                }
                csv.write("\n");
                
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        Object valor = modelo.getValueAt(i, j);
                        // Limpiamos posibles comas en los textos para no romper el CSV
                        String texto = (valor != null) ? valor.toString().replace(",", " ") : "";
                        csv.write(texto + (j == modelo.getColumnCount() - 1 ? "" : ","));
                    }
                    csv.write("\n");
                }
                
                csv.write("\n" + ventanaInformes.lblMediaGlobal.getText().replace(",", ".") + "\n");
                JOptionPane.showMessageDialog(ventanaInformes, "Informe exportado con éxito en: " + ruta);
			} catch (IOException e) {
                JOptionPane.showMessageDialog(ventanaInformes, "Error al escribir el archivo: " + e.getMessage());
            }
		}
	}
	
	private void calcularMedia(List<IncidenciaDTO> lista) {
		int suma = 0, cont = 0;
		for (IncidenciaDTO i : lista) {
			String t = i.getTiempoResolucion();
			if ( t != null && t.contains("días")) {
				suma += Integer.parseInt(t.replace("días", "").trim());
				cont++;
			}
		}
		if(cont>0) {
			ventanaInformes.lblMediaGlobal.setText(String.format("Tiempo Medio de Resolución (conjunto filtrado): %.2f días", 
					(double)suma/cont));
		} else {
			ventanaInformes.lblMediaGlobal.setText("Tiempo Medio de Resolución (conjunto filtrado): N/A");
		}	
	}
	
}
