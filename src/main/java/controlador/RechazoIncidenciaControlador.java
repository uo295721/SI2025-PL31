package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import vista.VistaRechazoOperador;

public class RechazoIncidenciaControlador {

    private VistaRechazoOperador vista;
    private IncidenciaModelo modelo;
    private String emailOperador;
    private int idSeleccionado = -1;

    public RechazoIncidenciaControlador(VistaRechazoOperador vista, IncidenciaModelo modelo, String emailOperador) {
        this.vista = vista;
        this.modelo = modelo;
        this.emailOperador = emailOperador;

        cargarDatos();

        // Listener para detectar clics en la columna "Acción"
        this.vista.getTablaIncidencias().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTablaIncidencias().rowAtPoint(e.getPoint());
                int col = vista.getTablaIncidencias().columnAtPoint(e.getPoint());

                if (fila < 0 || col != 4) return;

                // Guardamos ID de la incidencia seleccionada
                idSeleccionado = (int) vista.getModeloTabla().getValueAt(fila, 0);

                // Calcular si pulsó VALIDAR (izquierda) o RECHAZAR (derecha)
                int anchoColumna = vista.getTablaIncidencias().getColumnModel().getColumn(4).getWidth();
                int xDentroCelda = e.getX() - vista.getTablaIncidencias().getCellRect(fila, col, false).x;

                if (xDentroCelda < anchoColumna / 2) {
                    validarInSitu();
                } else {
                    vista.mostrarPanelRechazo(idSeleccionado);
                }
            }
        });

        // Botones del panel inferior
        this.vista.getBtnConfirmar().addActionListener(e -> confirmarRechazo());
        this.vista.getBtnCancelar().addActionListener(e -> vista.ocultarPanelRechazo());
    }

    private void cargarDatos() {
        vista.getModeloTabla().setRowCount(0);
        // El modelo obtiene el nombre real para que aparezca "Omar" en la tabla
        String nombreVisible = modelo.obtenerNombrePorEmail(emailOperador);
        
        List<IncidenciaDTO> lista = modelo.getIncidenciasPorEstado("Nueva");
        for (IncidenciaDTO i : lista) {
            vista.getModeloTabla().addRow(new Object[]{
                i.getIdIncidencia(),
                i.getDescripcion(),
                nombreVisible, // Columna Responsable
                i.getTipo(),
                "  VALIDAR  |  RECHAZAR  " // Columna Acción
            });
        }
    }

    private void validarInSitu() {
        int confirm = JOptionPane.showConfirmDialog(vista, 
            "¿Desea validar la incidencia #" + idSeleccionado + "?", 
            "Confirmar Validación", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            modelo.validarIncidenciaSimple(idSeleccionado, emailOperador);
            JOptionPane.showMessageDialog(vista, "Incidencia validada correctamente.");
            vista.ocultarPanelRechazo();
            cargarDatos();
        }
    }

    private void confirmarRechazo() {
        String motivo = vista.getTxtMotivo().getText().trim();
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El motivo es obligatorio para rechazar la incidencia.");
            return;
        }

        if (modelo.rechazarIncidencia(idSeleccionado, emailOperador, motivo)) {
            JOptionPane.showMessageDialog(vista, "Incidencia #" + idSeleccionado + " rechazada con éxito.");
            vista.ocultarPanelRechazo();
            cargarDatos();
        }
    }
}