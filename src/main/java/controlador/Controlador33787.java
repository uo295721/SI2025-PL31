package controlador;

import modelo.IncidenciaDTO;
import modelo.OperadorModelo;
import vista.Vista33787;
import modelo.Modelo33787;

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

        listarIncidencias();

        // Evento al seleccionar una fila para pre-cargar el tipo en el combo si se desea
        this.vista.getBtnValidar().addActionListener(e -> gestionarValidacion());
    }

    private void listarIncidencias() {
        List<IncidenciaDTO> lista = modelo.getIncidenciasNuevas();
        DefaultTableModel m = (DefaultTableModel) vista.getTablaIncidencias().getModel();
        m.setRowCount(0);
        for (IncidenciaDTO i : lista) {
            m.addRow(new Object[]{i.getIdIncidencia(), i.getFecha(), i.getIdCiudadano(), i.getTipo()});
        }
    }

    private void gestionarValidacion() {
        int fila = vista.getTablaIncidencias().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una incidencia de la lista.");
            return;
        }

        int id = (int) vista.getTablaIncidencias().getValueAt(fila, 0);
        String nuevoTipo = (String) vista.getCbTipos().getSelectedItem();

        try {
            modelo.validarClasificacion(id, nuevoTipo, emailOperador);
            JOptionPane.showMessageDialog(vista, "Cambio registrado con éxito. Incidencia validada.");
            listarIncidencias(); // Refrescar lista
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }
}