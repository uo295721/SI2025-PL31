package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import modelo.*;
import vista.VentanaPresupuestos;

public class PresupuestoControlador {
    private VentanaPresupuestos vista;
    private PresupuestoModelo modelo;

    public PresupuestoControlador(VentanaPresupuestos vista, PresupuestoModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        cargarTipos();
        actualizarTabla();

        this.vista.btnGuardar.addActionListener(e -> guardar());
    }

    private void cargarTipos() {
        for (TipoIncidenciaDTO t : modelo.obtenerTipos()) {
            vista.cbTipos.addItem(t);
        }
    }

    private void actualizarTabla() {
        vista.modeloTabla.setRowCount(0);
        List<PresupuestoDTO> lista = modelo.obtenerPresupuestos();
        for (PresupuestoDTO p : lista) {
            vista.modeloTabla.addRow(new Object[]{
                p.getNombreTipo(), 
                p.getImporte_total() + "€", 
                p.getImporte_consumido() + "€", 
                p.getFecha_inicio(), 
                p.getFecha_fin()
            });
        }
    }

    private void guardar() {
        try {
            TipoIncidenciaDTO tipo = (TipoIncidenciaDTO) vista.cbTipos.getSelectedItem();
            double total = Double.parseDouble(vista.txtImporte.getText());
            String inicio = vista.txtInicio.getText();
            String fin = vista.txtFin.getText();

            if (modelo.guardarPresupuesto(tipo.getId_tipo(), total, inicio, fin)) {
                JOptionPane.showMessageDialog(vista, "Presupuesto registrado correctamente.");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error: Existe un solapamiento de fechas para este tipo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Por favor, revise los datos introducidos.");
        }
    }
}