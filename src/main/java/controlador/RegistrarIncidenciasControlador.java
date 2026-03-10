package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import modelo.IncidenciaModelo;
import vista.RegistrarIncidencia;

public class RegistrarIncidenciasControlador {

    private RegistrarIncidencia vista;
    private IncidenciaModelo modelo;
    private String idCiudadano; // Guardamos el DNI del usuario logueado

    public RegistrarIncidenciasControlador(RegistrarIncidencia vista, IncidenciaModelo modelo, String idCiudadano) {
        this.vista = vista;
        this.modelo = modelo;
        this.idCiudadano = idCiudadano;

        // Configuramos el evento del botón
        this.vista.getBtnRegistrar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(registrar())
                	vista.dispose();
            }
        });
    }

    private boolean registrar() {
    	// Empiezo obteniendo lo que se ha introducido y hago la comprobacion pertinente
        String tipo = (String) vista.getCbTipo().getSelectedItem();
        String localizacion = vista.getTextLocalizacion().getText().trim();
        String descripcion = vista.getTextDescripcion().getText().trim();

        if (localizacion.isEmpty() || descripcion.isEmpty() || tipo.trim() == "Sin tipo") {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos para crear la incidencia.");
            return false;
        }

        // Inserto los valores dados usando la función creada en el modelo
        boolean insertado = modelo.insertarIncidencia(tipo, descripcion, localizacion, idCiudadano);

        if (insertado) {
        	String resumen = "Incidencia registrada correctamente!\n"
					   + "--- Resumen de la Operación ---\n"
					   + "• Usuario asociado: " + idCiudadano+ "\n"
					   + "• Tipo: " + tipo + "\n"
		               + "• Localización: " + localizacion + " h\n"
		               + "• Descripción: " + descripcion + "\n\n"
		               + "La incidencia ha pasado al estado 'Nueva'.";
		
        	JOptionPane.showMessageDialog(vista, resumen, 
				"Confirmación de Registro",JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error técnico al guardar en la base de datos.");
            return false;
        }
    }
}