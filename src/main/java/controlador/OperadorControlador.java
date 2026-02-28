package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

import modelo.HistorialDTO;
import modelo.IncidenciaDTO;
import modelo.IncidenciaModelo;
import modelo.TecnicoDTO;
import vista.VentanaOperador;
import vista.VentanaHistorial;
import vista.VentanaOperador;



public class OperadorControlador {

	private IncidenciaModelo modelo;
	private VentanaOperador vista;
	private String emailOperador;

	public OperadorControlador(VentanaOperador vista, IncidenciaModelo modelo) {
		this.vista = vista;
		this.modelo = modelo;
		
		this.configurarEventos();
	}

	private void cargarDatosEnComponentes() {
		// Rellenamos tabla de incidencias
		List<IncidenciaDTO> incidencia = modelo.getIncidenciasValidadas();
		vista.getModeloTabla().setRowCount(0);
		for (IncidenciaDTO i : incidencia) {
			vista.getModeloTabla()
					.addRow(new Object[] {
							i.getIdIncidencia(),
							i.getDescripcion(),
							i.getFecha(),
							i.getEstado() });
		}

		// Rellenamos lista de técnicos
		List<TecnicoDTO> tecnicos = modelo.obtenerListaTecnicos();
		vista.getModeloListaTecnicos().clear();
		for (TecnicoDTO t : tecnicos) {
			vista.getModeloListaTecnicos().addElement(t);
		}
	}

	private void configurarEventos() {
		
		vista.getTxtEmail().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String emailInput = vista.getTxtEmail().getText().trim();
		        // Comprobamos que el email contiene un formato válido
		        if (emailInput.isEmpty() || !emailInput.contains("@")) {
		            JOptionPane.showMessageDialog(vista, "Por favor, introduzca un email válido.");
		            return;
		        }
		        // Validamos que el usuario que está intentando acceder cumple con el rol de Operador
		        modelo.UsuarioModelo uM = new modelo.UsuarioModelo(); 
		        
		        if (uM.esUsuarioConRol(emailInput, "OPERADOR")) {
		        	// Si es operador le dejamos entrar
		            emailOperador = emailInput; 
		            vista.getLblEmailOperador().setText("Operador identificado: " + emailOperador);
		            
		            desbloquearInterfaz(); 
		            cargarDatosEnComponentes(); 
		            vista.getTxtEmail().setEnabled(false);
		        } else {
		            // Si no lo es, no le dejamos hacer nada
		            JOptionPane.showMessageDialog(vista, 
		                "Acceso denegado: El email no corresponde a un Operador.", 
		                "Error de Permisos", 
		                JOptionPane.ERROR_MESSAGE);
		            vista.getTxtEmail().setText(""); // Limpiamos el campo
		        }
		    }
		});

		
		vista.getBtnAsignar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = vista.getTablaIncidencias().getSelectedRow();
				TecnicoDTO tecnico = vista.getListaTecnicos().getSelectedValue();

				if (fila == -1 || tecnico == null) {
					JOptionPane.showMessageDialog(vista, "Seleccione incidencia y técnico");
					return;
				}

				int idIncidencia = (int) vista.getModeloTabla().getValueAt(fila, 0);

				if (modelo.asignarTecnicoIncidencia(idIncidencia, tecnico.getIdUsuario(), emailOperador)) {
					
					String comentario = "Asignada al técnico: " + tecnico.getNombre();
					modelo.registrarCambioHistorial(idIncidencia, emailOperador, "Asignada", comentario);
					
					JOptionPane.showMessageDialog(vista, "Asignación correcta");
					cargarDatosEnComponentes(); 
				}
			}
		});
	}
	
	// Método que va a permitir al operador acceder a las indicendias sus respectivos técnicos
	private void desbloquearInterfaz() {
		vista.getTablaIncidencias().setEnabled(true);
		vista.getListaTecnicos().setEnabled(true);
		vista.getBtnAsignar().setEnabled(true);
	}

}
