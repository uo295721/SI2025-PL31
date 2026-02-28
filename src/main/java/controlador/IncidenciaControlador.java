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
            			i.getHorasEstimadas()
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

    private void marcarComoResuelta() {
        try {
            int id = Integer.parseInt(vista.txtIdIncidencia.getText());
            double tReal = Double.parseDouble(vista.txtHorasReales.getText());
            String desc = vista.txtAreaTrabajos.getText();
            
            String idReal = usuario.getIdUsuarioByEmail(emailTecnico);

            if (modelo.marcarComoResuelta(id, idReal, tReal, desc)) {
                JOptionPane.showMessageDialog(vista, "¡Guardado con éxito!");
                limpiarFormulario();
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: Datos inválidos o no seleccionados.");
        }
    }

    private void limpiarFormulario() {
        vista.txtIdIncidencia.setText("");
        vista.txtTituloIncidencia.setText("");
        vista.txtHorasEstimadas.setText("");
        vista.txtHorasReales.setText("");
        vista.txtAreaTrabajos.setText("");
        vista.tablaIncidencias.clearSelection();
    }
}