package controlador;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import modelo.IncidenciaModelo;
import modelo.UsuarioModelo;
import vista.VentanaInformeResponsable;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InformeResponsableControlador {
    private VentanaInformeResponsable vista;
    private IncidenciaModelo modIncidencia;
    private UsuarioModelo modUsuario;
    private String idIngresado;

    public InformeResponsableControlador(IncidenciaModelo mi, UsuarioModelo mu, VentanaInformeResponsable vista, String id) {
        this.modIncidencia = mi;
        this.modUsuario = mu;
        this.vista = vista;
        this.idIngresado = id;
        
        
        this.cargarTabla();
        this.vista.getBtnExportarCSV().addActionListener(e -> exportarTablaACsv());
        this.vista.setVisible(true);
        
    }

    private void cargarTabla() {
    	LocalDate hoy = LocalDate.now();
    	LocalDate haceTreintaDias = hoy.minusDays(30);
    	String fechaHoy = hoy.format(DateTimeFormatter.ISO_LOCAL_DATE);
    	String fechaInicio = haceTreintaDias.format(DateTimeFormatter.ISO_LOCAL_DATE);

        List<Object[]> datos = modIncidencia.getInformeMensualIncidencias(fechaInicio, fechaHoy);
        String[] columnas = {"Técnico", "Resueltas", "Tiempo Total"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Object[] fila : datos) {
            model.addRow(fila);
        }
        vista.getTabla().setModel(model);
    }
    
    private void exportarTablaACsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Informe como CSV");
        
        // Mostramos el diálogo de guardar
        int seleccion = chooser.showSaveDialog(vista);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try (FileWriter csv = new FileWriter(chooser.getSelectedFile() + ".csv")) {
                TableModel modelo = vista.getTabla().getModel();
                
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    csv.write(modelo.getColumnName(i) + (i == modelo.getColumnCount() - 1 ? "" : ","));
                }
                csv.write("\n");

                for (int i = 0; i < modelo.getRowCount(); i++) {
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        csv.write(modelo.getValueAt(i, j).toString() + (j == modelo.getColumnCount() - 1 ? "" : ","));
                    }
                    csv.write("\n");
                }
                
                JOptionPane.showMessageDialog(vista, "Informe exportado con éxito.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(vista, "Error al guardar el archivo: " + e.getMessage());
            }
        }
    }
}