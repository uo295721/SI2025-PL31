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
    private String idOperador;
    private int idSeleccionado = -1;

    public RechazoIncidenciaControlador(VistaRechazoOperador vista, IncidenciaModelo modelo, String idOperador) {
        this.vista = vista;
        this.modelo = modelo;
        this.idOperador = idOperador;

        cargarDatos();

        this.vista.getTablaIncidencias().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTablaIncidencias().rowAtPoint(e.getPoint());
                int columna = vista.getTablaIncidencias().columnAtPoint(e.getPoint());

                if (fila < 0) return;

                // 1. Obtener el ID de la incidencia seleccionada
                idSeleccionado = (int) vista.getModeloTabla().getValueAt(fila, 0);

                // 2. Si el clic es en la columna "Acción" (Índice 4)
                if (columna == 4) {
                    // Calculamos el ancho de la columna para dividirla en dos zonas
                    int anchoColumna = vista.getTablaIncidencias().getColumnModel().getColumn(4).getWidth();
                    int clickX = e.getX() - vista.getTablaIncidencias().getCellRect(fila, columna, false).x;

                    if (clickX < anchoColumna / 2) {
                        // CLIC EN LA IZQUIERDA: VALIDAR
                        vista.ocultarPanelRechazo(); // Por si estaba abierto
                        ejecutarValidacionDirecta();
                    } else {
                        // CLIC EN LA DERECHA: RECHAZAR
                        vista.mostrarPanelRechazo(String.valueOf(idSeleccionado));
                    }
                }
            }
        });

        // Evento botón Confirmar (en el panel de rechazo)
        this.vista.getBtnConfirmar().addActionListener(e -> confirmarRechazo());

        // Evento botón Cancelar (en el panel de rechazo)
        this.vista.getBtnCancelar().addActionListener(e -> vista.ocultarPanelRechazo());
    }

    private void cargarDatos() {
        vista.getModeloTabla().setRowCount(0);
        // Cargamos incidencias nuevas
        List<IncidenciaDTO> lista = modelo.getIncidenciasPorEstado("Nueva");
        for (IncidenciaDTO i : lista) {
            // El formato visual de los botones en la celda
            String acciones = "  VALIDAR  |  RECHAZAR  ";
            
            // Intentamos usar los nombres de métodos de tu DTO (ajusta si fallan los get)
            vista.getModeloTabla().addRow(new Object[]{
                i.getIdIncidencia(), 
                i.getDescripcion(), 
                idOperador, 
                i.getTipo(), 
                acciones
            });
        }
    }

    private void ejecutarValidacionDirecta() {
        int respuesta = JOptionPane.showConfirmDialog(vista, 
            "¿Desea validar la incidencia #" + idSeleccionado + "?", 
            "Confirmar Validación", JOptionPane.YES_NO_OPTION);
            
        if (respuesta == JOptionPane.YES_OPTION) {
            // Usamos el método que ya tenías en tu modelo
            modelo.validarClasificacion(idSeleccionado, "General", idOperador);
            JOptionPane.showMessageDialog(vista, "Incidencia validada correctamente.");
            vista.ocultarPanelRechazo();
            cargarDatos();
        }
    }

    private void confirmarRechazo() {
        String motivo = vista.getTxtMotivo().getText().trim();
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe indicar un motivo para el rechazo.");
            return;
        }

        if (modelo.rechazarIncidencia(idSeleccionado, idOperador, motivo)) {
            JOptionPane.showMessageDialog(vista, "La incidencia ha sido rechazada.");
            vista.ocultarPanelRechazo();
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(vista, "Error técnico al procesar el rechazo.");
        }
    }
}