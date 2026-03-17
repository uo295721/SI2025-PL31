package controlador;

import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.UsuarioModelo;
import vista.IncidenciasTecnicoProceso;
import vista.RegistrarIncidencia;

import java.util.List;

import javax.swing.JOptionPane;

public class IncidenciaControlador {
	
    private IncidenciaModelo modelo;
    private IncidenciasTecnicoProceso vista;
    private String emailTecnico;
    private RegistrarIncidencia vistaReg;
    private UsuarioModelo usuario = new UsuarioModelo();

    public IncidenciaControlador(IncidenciaModelo modelo, IncidenciasTecnicoProceso vista, String emailTecnico) {
        this.modelo = modelo;
        this.vista = vista;
        this.emailTecnico = emailTecnico;

        cargarTabla();

        // Escuchar selección de tabla para autocompletar
        this.vista.tablaIncidencias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                rellenarFormularioDesdeTabla();
            }
        });

        // Eventos de botones
        this.vista.btnMarcarResuelta.addActionListener(e -> marcarComoResuelta());
        this.vista.btnCancelar.addActionListener(e -> limpiarFormulario());
        this.vista.btnSalir.addActionListener(e -> vista.dispose());
    }
    
    public IncidenciaControlador(IncidenciaModelo modelo, RegistrarIncidencia vista) {
        this.modelo = modelo;
        this.vistaReg = vista;
        
        vista.setVisible(true);
    }

    private void cargarTabla() {
    	
        try {
            vista.modeloTabla.setRowCount(0);
            String idTecnico = usuario.asegurarID(emailTecnico);
            
            List<IncidenciaDTO> lista = modelo.obtenerIncidenciasProceso(idTecnico);
            for (IncidenciaDTO i : lista) {
            	Object[] fila = {
            			i.getIdIncidencia(),
            			i.getTipo(),
            			i.getFecha(),
            			i.getLocalizacion(),
            			i.getHoras_estimadas()
            	};
            	vista.modeloTabla.addRow(fila);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar: " + e.getMessage());
        }
        
    }

    private void rellenarFormularioDesdeTabla() {
        int fila = vista.tablaIncidencias.getSelectedRow();
        if (fila != -1) {
            vista.txtIdIncidencia.setText(vista.modeloTabla.getValueAt(fila, 0).toString());
            vista.txtTituloIncidencia.setText(vista.modeloTabla.getValueAt(fila, 1).toString());
            vista.txtHorasEstimadas.setText(vista.modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    // --- LÓGICA AMPLIADA HU 33954 ---
    private void marcarComoResuelta() {
        try {
            if (vista.txtIdIncidencia.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione una incidencia primero.");
                return;
            }

            int id = Integer.parseInt(vista.txtIdIncidencia.getText());
            double horasReales = Double.parseDouble(vista.txtHorasReales.getText());
            double costeMateriales = Double.parseDouble(vista.txtCosteMateriales.getText());
            String desc = vista.txtAreaTrabajos.getText();
            
            String idReal = usuario.getIdUsuarioByEmail(emailTecnico);

            // 1. Obtener precio/hora del técnico
            double precioHora = modelo.getPrecioHoraTecnico(idReal);

            // 2. Calcular coste total: (Horas * Precio/Hora) + Materiales
            double costeTotal = (horasReales * precioHora) + costeMateriales;

            // 3. Mostrar cálculo al usuario para validación
            String resumen = String.format(
                "Cálculo de Resolución:\n" +
                "- Horas Reales: %.2f h\n" +
                "- Tarifa Técnico: %.2f €/h\n" +
                "- Coste Materiales: %.2f €\n\n" +
                "COSTE TOTAL: %.2f €\n\n" +
                "¿Desea confirmar la resolución?", 
                horasReales, precioHora, costeMateriales, costeTotal);

            int confirm = JOptionPane.showConfirmDialog(vista, resumen, "Confirmación Financiera", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (modelo.marcarComoResueltaConCoste(id, idReal, horasReales, costeTotal, desc)) {
                    JOptionPane.showMessageDialog(vista, "¡Incidencia resuelta y costes registrados!");
                    limpiarFormulario();
                    cargarTabla();
                }
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vista, "Error: Ingrese valores numéricos válidos en Horas y Materiales.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error crítico: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        vista.txtIdIncidencia.setText("");
        vista.txtTituloIncidencia.setText("");
        vista.txtHorasEstimadas.setText("");
        vista.txtHorasReales.setText("");
        vista.txtCosteMateriales.setText(""); // NUEVO
        vista.txtAreaTrabajos.setText("");
        vista.tablaIncidencias.clearSelection();
    }
}