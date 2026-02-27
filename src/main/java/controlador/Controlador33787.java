package controlador;

import modelo.IncidenciaDTO;
import modelo.Modelo33787;
import vista.Vista33787;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Controlador33787 {
    private Modelo33787 modelo;
    private Vista33787 vista;
    private String emailOperador;

    public Controlador33787(Modelo33787 modelo, Vista33787 vista, String email) {
        this.modelo = modelo;
        this.vista = vista;
        this.emailOperador = email;

        refrescarTabla();

        // Listener: Al seleccionar una fila, actualiza el label y el combo
        this.vista.getTablaIncidencias().getSelectionModel().addListSelectionListener(e -> {
            int fila = vista.getTablaIncidencias().getSelectedRow();
            if (fila != -1) {
                String id = vista.getTablaIncidencias().getValueAt(fila, 0).toString();
                String tipoPropuesto = vista.getTablaIncidencias().getValueAt(fila, 3).toString();
                
                vista.setLblDetalleId(id);
                vista.getCbTipos().setSelectedItem(tipoPropuesto);
            }
        });

        // Botón Validar
        this.vista.getBtnValidar().addActionListener(e -> {
            int fila = vista.getTablaIncidencias().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Seleccione una incidencia para validar.");
                return;
            }

            int id = (int) vista.getTablaIncidencias().getValueAt(fila, 0);
            String tipoFinal = (String) vista.getCbTipos().getSelectedItem();

            try {
                modelo.validarClasificacion(id, tipoFinal, emailOperador);
                JOptionPane.showMessageDialog(vista, "Clasificación registrada correctamente.");
                refrescarTabla();
                vista.setLblDetalleId("---");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
            }
        });
    }

    private void refrescarTabla() {
        List<IncidenciaDTO> lista = modelo.getIncidenciasNuevas();
        DefaultTableModel m = (DefaultTableModel) vista.getTablaIncidencias().getModel();
        m.setRowCount(0);
        for (IncidenciaDTO i : lista) {
            m.addRow(new Object[]{
                i.getIdIncidencia(), 
                i.getFecha(), 
                i.getDescripcionCiudadano(), 
                i.getTipo()
            });
        }
    }
}