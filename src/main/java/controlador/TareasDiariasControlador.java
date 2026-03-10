package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.IncidenciaModelo;
import vista.VentanaTareasDiarias;

public class TareasDiariasControlador {

    private VentanaTareasDiarias vista;
    private IncidenciaModelo modelo;
    private int idIncidencia;
    private String idTecnico;

    public TareasDiariasControlador(VentanaTareasDiarias vista, IncidenciaModelo modelo, int idIncidencia,
            String idTecnico) {
        this.vista = vista;
        this.modelo = modelo;
        this.idIncidencia = idIncidencia;
        this.idTecnico = idTecnico;

        // Cargamos el historial nada más abrir la ventana
        cargarHistorialTareas();

        this.vista.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarNuevaTarea();
            }
        });
    }

    private void cargarHistorialTareas() {
        DefaultTableModel model = vista.getModeloTabla();
        model.setRowCount(0);

        List<Object[]> tareas = modelo.getTareasPorIncidencia(idIncidencia);

        for (Object[] tarea : tareas) {
            model.addRow(tarea);
        }
    }

    private void registrarNuevaTarea() {
        try {
            // Recogemos los datos actuales de la vista
            String fecha = vista.getTxtFecha().getText().trim();
            String desc = vista.getAreaDescripcion().getText().trim();
            String horasStr = vista.getTxtHoras().getText().trim();

            // Validaciones
            if (desc.isEmpty() || horasStr.isEmpty() || fecha.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
                return;
            }
            
            double horas = Double.parseDouble(horasStr);

            // Guardar en BD
            modelo.registrarTareaDiaria(idIncidencia, idTecnico, fecha, desc, horas);

            // --- NUEVO: Construcción del mensaje de resumen detallado ---
            String resumen = "Tarea registrada con éxito.\n"
                           + "--- Detalle de la Actividad ---\n"
                           + "• Fecha: " + fecha + "\n"
                           + "• Horas: " + horas + " h\n"
                           + "• Descripción: " + desc + "\n\n"
                           + "El listado histórico ha sido actualizado.";

            JOptionPane.showMessageDialog(vista, resumen, 
                    "Confirmación de Registro", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar formulario para la siguiente anotación
            vista.getAreaDescripcion().setText("");
            vista.getTxtHoras().setText("");

            // Refrescar la tabla para que el técnico vea su historial actualizado
            cargarHistorialTareas();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El campo 'Horas' debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
        }
    }
}