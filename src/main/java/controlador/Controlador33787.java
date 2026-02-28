package controlador;

import modelo.Modelo33787;
import vista.Vista33787;
import modelo.IncidenciaDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Controlador33787 {
    private Modelo33787 modelo;
    private Vista33787 vista;
    private String idOperador; // Guardamos 'O1', no el mail

    public Controlador33787(Modelo33787 modelo, Vista33787 vista, String idOperador) {
        this.modelo = modelo;
        this.vista = vista;
        this.idOperador = idOperador;

        refrescarTabla();

        // Evento de selección de tabla
        this.vista.getTablaIncidencias().getSelectionModel().addListSelectionListener(e -> {
            int fila = vista.getTablaIncidencias().getSelectedRow();
            if (fila != -1) {
                vista.setLblDetalleId(vista.getTablaIncidencias().getValueAt(fila, 0).toString());
                vista.getCbTipos().setSelectedItem(vista.getTablaIncidencias().getValueAt(fila, 3).toString());
            }
        });

        // Botón Validar
        this.vista.getBtnValidar().addActionListener(e -> {
            int fila = vista.getTablaIncidencias().getSelectedRow();
            if (fila == -1) return;

            int idInc = (int) vista.getTablaIncidencias().getValueAt(fila, 0);
            String tipo = (String) vista.getCbTipos().getSelectedItem();

            try {
                // Pasamos el ID 'O1' al modelo
                modelo.validarClasificacion(idInc, tipo, idOperador);
                JOptionPane.showMessageDialog(vista, "Guardado con éxito)");
                refrescarTabla();
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
            m.addRow(new Object[]{i.getIdIncidencia(), i.getFecha(), i.getDescripcionCiudadano(), i.getTipo()});
        }
    }
}