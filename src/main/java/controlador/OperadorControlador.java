package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.UsuarioModelo;
import vista.VentanaOperador;

public class OperadorControlador {

    private static final int CARGA_MAXIMA = 3; 
    
    private IncidenciaModelo modelo;
    private UsuarioModelo usuario = new UsuarioModelo(); 
    private VentanaOperador vista;
    private String emailOperador;

    public OperadorControlador(VentanaOperador vista, IncidenciaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.configurarEventos();
    }

    private void cargarDatosEnComponentes() {
        List<IncidenciaDTO> incidencia = modelo.getIncidenciasPorEstado("Validada");
        vista.getModeloTabla().setRowCount(0);
        for (IncidenciaDTO i : incidencia) {
            vista.getModeloTabla().addRow(
                    new Object[] { i.getIdIncidencia(), i.getDescripcion(), i.getFecha(), i.getEstado(), i.getId_tipo() });
        }
        // Inicialmente no hay incidencia seleccionada, mostramos lista vacía o aviso
        vista.getModeloTablaTecnicos().setRowCount(0);
    }

    private void configurarEventos() {
        // Idnetificación por corro
        vista.getTxtEmail().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = vista.getTxtEmail().getText().trim();
                if (emailInput.isEmpty() || !emailInput.contains("@")) {
                    JOptionPane.showMessageDialog(vista, "Por favor, introduzca un email válido.");
                    return;
                }

                if (usuario.esUsuarioConRol(emailInput, "OPERADOR")) {
                    emailOperador = emailInput;
                    vista.getLblEmailOperador().setText("Operador identificado: " + emailOperador);
                    desbloquearInterfaz();
                    cargarDatosEnComponentes();
                    vista.getTxtEmail().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(vista, "Acceso denegado: El email no corresponde a un Operador.",
                            "Error de Permisos", JOptionPane.ERROR_MESSAGE);
                    vista.getTxtEmail().setText("");
                }
            }
        });

        vista.getTablaIncidencias().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = vista.getTablaIncidencias().getSelectedRow();
                    if (fila != -1) {
                        int idTipo = (int) vista.getModeloTabla().getValueAt(fila, 4);
                        actualizarListaTecnicos(idTipo);
                    }
                }
            }
        });

        vista.getBtnAsignar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaIncidencia = vista.getTablaIncidencias().getSelectedRow();
                // Ahora usamos getSelectedRows() para obtener el array de filas elegidas
                int[] filasTecnicos = vista.getTablaTecnicos().getSelectedRows();

                if (filaIncidencia == -1 || filasTecnicos.length == 0) {
                    JOptionPane.showMessageDialog(vista, "Seleccione una incidencia y al menos un técnico.");
                    return;
                }
                
                for (int filaIdx : filasTecnicos) {
                	int cargaActual = (int) vista.getModeloTablaTecnicos().getValueAt(filaIdx, 3); // Conseguimos el valor de la carga actual de cada técnico
                	String nombreTecnico = vista.getModeloTablaTecnicos().getValueAt(filaIdx, 1).toString();
                	
                	if (cargaActual >= 3) {
                		JOptionPane.showMessageDialog(vista, "No se puede realizar la asignación de la tarea.\nEl técnico "+ nombreTecnico + 
                		" ya tiene el máximo de incidencias asignadas (3).",	"Carga excedida", JOptionPane.WARNING_MESSAGE);
                		return;
                	}
                }

                int idIncidencia = (int) vista.getModeloTabla().getValueAt(filaIncidencia, 0);
                int idTipo = (int) vista.getModeloTabla().getValueAt(filaIncidencia, 4); 
                List<String> idsSeleccionados = new ArrayList<>();

                // Recorremos todos los técnicos seleccionados (Ctrl + Click)
                for (int filaIdx : filasTecnicos) {
                    String idTecnico = vista.getModeloTablaTecnicos().getValueAt(filaIdx, 0).toString();
                    idsSeleccionados.add(idTecnico);
                }

                // Ejecutamos la asignación múltiple en el modelo
                if (modelo.asignarVariosTecnicos(idIncidencia, idsSeleccionados, emailOperador)) {
                    JOptionPane.showMessageDialog(vista, 
                        "Incidencia #" + idIncidencia + " asignada con éxito a " + idsSeleccionados.size() + " técnicos.");
                    
                    // Refrescamos la vista para que desaparezca la incidencia ya asignada
                    cargarDatosEnComponentes();
                    actualizarListaTecnicos(idTipo);
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al realizar la asignación múltiple.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void desbloquearInterfaz() {
        vista.getTablaIncidencias().setEnabled(true);
        vista.getTablaTecnicos().setEnabled(true);
        vista.getBtnAsignar().setEnabled(true);
    }

    //  nuevo método del modelo para una carga inferior a 3

    private void actualizarListaTecnicos(int idTipo) {
        List<Object[]> tecnicos = modelo.getTecnicosDisponiblesPorCarga(idTipo);
        
        vista.getModeloTablaTecnicos().setRowCount(0);

        if (tecnicos.isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "No hay técnicos disponibles con esta especialidad o todos tienen carga máxima (3).", 
                "Sin personal", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Object[] t : tecnicos) {
                Object[] filaParaTabla = { t[0], t[1], "Especialista Tipo " + idTipo, t[2] };
                vista.getModeloTablaTecnicos().addRow(filaParaTabla);
            }
        }
    }
}
